package org.example.collaborative_task_management_application;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.collaborative_task_management_application.databases.Main_database_connection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class HomeScreenController implements Initializable {


    @FXML
    private Label name_label;
    @FXML
    private Button exit_button;
    @FXML
    private Button revert_button_settings;
    @FXML
    private Button confirm_edit_button_edit_profile;
    @FXML
    private TextField password_textfiled_edit_profile;
    @FXML
    private TextField email_textfiled_edit_profile;
    @FXML
    private TextField name_text_field_edit_profile;
    @FXML
    private Button clear_fields_button_edit_profile;
    @FXML
    private Button delete_account_button;
    @FXML
    private AnchorPane delete_anchorpane;
    @FXML
    private AnchorPane edit_details_anchorpane;
    @FXML
    private AnchorPane more_anchorpane;

    @FXML
    private AnchorPane kanban_anchorpane;

    @FXML
    private AnchorPane settings_anchorpane;

    @FXML
    private Button kanban_button;
    @FXML
    private Button logout_button;

    @FXML
    private AnchorPane messages_anchorpane;

    @FXML
    private Label messegs_label;

    @FXML
    private AnchorPane project_anchorpane;
    @FXML
    private ListView<String> todoList;
    @FXML
    private ListView<String> inProgressList;
    @FXML
    private ListView<String> doneList;
    @FXML
    private Button save_tasks_button;
    @FXML
    private Button new_task_button;
    @FXML
    private TextField new_task_fiels;

    @FXML
    private Button settings_button;

    private String nameme;

    @FXML
    private void setProject_anchorpane(){
        project_anchorpane.setVisible(true);
        messages_anchorpane.setVisible(false);
        kanban_anchorpane.setVisible(false);
        settings_anchorpane.setVisible(false);
    }
    @FXML
    private void setMessages_anchorpane(){
        project_anchorpane.setVisible(false);
        messages_anchorpane.setVisible(true);
        kanban_anchorpane.setVisible(false);
        settings_anchorpane.setVisible(false);
    }
    @FXML
    private void setKanban_anchorpane(){
        project_anchorpane.setVisible(false);
        messages_anchorpane.setVisible(false);
        kanban_anchorpane.setVisible(true);
        settings_anchorpane.setVisible(false);
    }
    @FXML
    private void setSettings_anchorpane(){
        project_anchorpane.setVisible(false);
        messages_anchorpane.setVisible(false);
        kanban_anchorpane.setVisible(false);
        settings_anchorpane.setVisible(true);
    }
    @FXML
    private void setEdit_details_anchorpane(){
        edit_details_anchorpane.setVisible(true);
        more_anchorpane.setVisible(false);

    }
    @FXML
    private void setDelete_anchorpane(){
        delete_anchorpane.setVisible(true);
    }
    @FXML
    private void setRevert_button_settings(){
        delete_anchorpane.setVisible(false);
    }

    @FXML
    public void setlogout_button() throws IOException {
        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);
        logout_button.getScene().getWindow().hide();
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-screen.fxml"));
        Pane root = fxmlLoader.load();
        root.setOnMousePressed(event -> {
            x.set(event.getSceneX());
            y.set(event.getSceneY());
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x.get());
            stage.setY(event.getScreenY() - y.get());
        });
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("all.css").toExternalForm());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void exit(ActionEvent event){
        Stage stage = (Stage)exit_button.getScene().getWindow();
        stage.close();

    }
    @FXML
    public void setName_label(String username){
        name_text_field_edit_profile.setText(username);
    }

    @FXML
    public void set_Name_Label(String username){
        name_label.setText(username);
    }

    @FXML
    public void setEmail(String email){
        email_textfiled_edit_profile.setText(email);
    }

    @FXML
    public void getpassword(String password){
        password_textfiled_edit_profile.setText(password);
    }
    @FXML
    private void setalldetails1(){
        LoginScreenController loginScreenController = new LoginScreenController();
        login test = loginScreenController.getUserLoginDetails();
        String password = test.getPassword();
        String name =test.getId();
        Employee employee = Main_database_connection.getEmployeeByName(name,password);
        System.out.println(employee.getName());
        name_label.setText(employee.getName());
    }

    String draggedItem;
    String targetColumn;
    private void setupDragAndDrop(ListView<String> listView, ListView<String> targetList) {
        // Set up drag detection
        listView.setOnDragDetected(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Start drag-and-drop gesture
                Dragboard dragboard = listView.startDragAndDrop(TransferMode.MOVE);

                // Put the selected item into the dragboard
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedItem);
                dragboard.setContent(content);

                event.consume();
            }
        });

        // Set up drag over for the target list
        targetList.setOnDragOver(event -> {
            // Accept the drag only if it's coming from a different source
            if (event.getGestureSource() != targetList && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // Set up drag dropped
        targetList.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasString()) {
                draggedItem = dragboard.getString();
                targetColumn = getSimpleColumnName(targetList);
                // Log the drag-and-drop operation
                System.out.println("Item dragged: " + draggedItem);
                System.out.println("Source column: " + getSimpleColumnName(listView));
                System.out.println("Target column: " + getSimpleColumnName(targetList));

                // Remove the item from the source list and add it to the target list
                listView.getItems().remove(draggedItem);
                targetList.getItems().add(draggedItem);

                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });

        // Set up drag done
        listView.setOnDragDone(event -> event.consume());
    }
    private String getSimpleColumnName(ListView<String> listView) {
        if (listView == todoList) return "To-Do";
        if (listView == inProgressList) return "In Progress";
        if (listView == doneList) return "Done";
        return "Unknown";
    }
    @FXML
    public void setNew_task_button(){
        String new_task= new_task_fiels.getText();
        try {
            Task task = new Task(new_task);
            task.createTask(task);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        Set<String> taskName = new HashSet<String>();
        try {
            Main_database_connection db = new Main_database_connection();
            ResultSet resultSet = db.selectTasks();
            while(resultSet.next()){
                taskName.add(resultSet.getInt("id")+" - "+ resultSet.getString("task_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        todoList.getItems().clear();
        todoList.getItems().addAll(taskName);
        new_task_fiels.clear();
    }
    @FXML
    public void save_button_onclick(){
        String[] result = draggedItem.split("\\s*-\\s*", 2);

        int numberPart;
        String textPart;

        if (result.length == 2) {
            numberPart = Integer.parseInt(result[0].trim());
            textPart = result[1].trim();
            Task.updateTaskStatus(numberPart,targetColumn);
        }
    }




    @Override

    public void initialize(URL location, ResourceBundle resources) {
        try {
            Main_database_connection db = new Main_database_connection();
            ResultSet resultSet = db.selectTasks();
            Set<String> todoListItems = new HashSet<String>();
            Set<String> progressItems = new HashSet<String>();
            Set<String> doneItems = new HashSet<String>();
            while(resultSet.next()){
                String columnName = resultSet.getString("column_name");
                System.out.println(columnName);
                if(columnName.equals("To-Do")){
                    todoListItems.add(resultSet.getInt("id")+" - "+ resultSet.getString("task_name"));
                }
                else if(columnName.equals("In Progress")){
                    progressItems.add(resultSet.getInt("id")+" - "+ resultSet.getString("task_name"));
                }
                else if(columnName.equals("Done")){
                    doneItems.add(resultSet.getInt("id")+" - "+ resultSet.getString("task_name"));
                }
            }
            todoList.getItems().addAll(todoListItems);
            inProgressList.getItems().addAll(progressItems);
            doneList.getItems().addAll(doneItems);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Enable drag-and-drop functionality
        setupDragAndDrop(todoList, inProgressList);
        setupDragAndDrop(inProgressList, doneList);
        setupDragAndDrop(doneList, todoList);
    }
}
