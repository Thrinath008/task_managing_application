package org.example.collaborative_task_management_application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
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

public class AdminHomeController implements Initializable {

    @FXML
    private TableView<LogEntry> log_tabel_view;
    @FXML
    private TableColumn<LogEntry,String> action_type_col;
    @FXML
    private TableColumn<LogEntry,String> description_col;
    @FXML
    private TableColumn<LogEntry,String> time_col;
    @FXML
    private Button delete_log_button;
    @FXML
    private Button refresh_log_button;
    @FXML
    private TextField new_task_fiels;

    @FXML
    private ListView<String> todoList;

    @FXML
    private ListView<String> inProgressList;

    @FXML
    private ListView<String> doneList;

    @FXML
    private AnchorPane addEmployee_anchorpane;

    @FXML
    private Button addNewEmployee_button;

    @FXML
    private AnchorPane logs_anchorpane;

    @FXML
    private Button logs_button;

    @FXML
    private Button usersProfiles_button;

    @FXML
    private AnchorPane users_profile_anchorpane;

    @FXML
    private AnchorPane dashboard_anchorpane;

    @FXML
    private Button dashboard_button;

    @FXML
    private Button exit_button;

    @FXML
    private Button logout_button;

    @FXML
    private AnchorPane reports_anchorpane;

    @FXML
    private Button reports_button;

    @FXML
    private AnchorPane settings_anchorpane;

    @FXML
    private Button settings_button;

    @FXML
    private AnchorPane users_anchorpane;
    @FXML
    private Button new_task_button;
    @FXML
    private Button save_tasks_button;
    @FXML
    private TableView<Employee> emp_table;

    @FXML
    private TableColumn<?,?> empId_col;
    @FXML
    private TableColumn<?,?> emp_name_col;
    @FXML
    private TableView<Task> tasks_table;
    @FXML
    private TableColumn<?,?> taskId_col;
    @FXML
    private TableColumn<?,?> empid_tasks_col;
    @FXML
    private TableColumn<?,?> task_name_col;
    @FXML
    private TextField empid_textfield;
    @FXML
    private TextField task_id_textfield;
    @FXML
    private Button assigen_task_button;
    @FXML
    private Button users_button;

    ObservableList<LogEntry> data12;


    public void setCellValue(){
        time_col.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        action_type_col.setCellValueFactory(new PropertyValueFactory<>("actionType"));
    }

    @FXML
    public void setDashboard_anchorpane(){
        dashboard_anchorpane.setVisible(true);
        reports_anchorpane.setVisible(false);
        users_anchorpane.setVisible(false);
        settings_anchorpane.setVisible(false);
    }
    @FXML
    public void setSettings_anchorpane(){
        dashboard_anchorpane.setVisible(false);
        reports_anchorpane.setVisible(false);
        users_anchorpane.setVisible(false);
        settings_anchorpane.setVisible(true);
    }
    @FXML
    private void setReports_anchorpane(){
        dashboard_anchorpane.setVisible(false);
        reports_anchorpane.setVisible(true);
        users_anchorpane.setVisible(false);
        settings_anchorpane.setVisible(false);
    }
    @FXML
    public void setUsers_anchorpane(){
        dashboard_anchorpane.setVisible(false);
        reports_anchorpane.setVisible(false);
        users_anchorpane.setVisible(true);
        settings_anchorpane.setVisible(false);
    }
    @FXML
    public void setAddEmployee_anchorpane(){
        addEmployee_anchorpane.setVisible(true);
        logs_anchorpane.setVisible(false);
        users_profile_anchorpane.setVisible(false);
    }
    @FXML
    public void setLogs_anchorpane(){
        addEmployee_anchorpane.setVisible(false);
        logs_anchorpane.setVisible(true);
        users_profile_anchorpane.setVisible(false);
    }
    @FXML
    public void setUsers_profile_anchorpane(){
        addEmployee_anchorpane.setVisible(false);
        logs_anchorpane.setVisible(false);
        users_profile_anchorpane.setVisible(true);
    }
    public void exit_button_on_action(ActionEvent event) {
        Stage stage = (Stage)exit_button.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void setLoginOut_button() throws IOException {
        logout_button.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("login-screen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValue();
        setCellValue_empTable();
        setCellValue_taskTable();
        ObservableList<Employee> employeeData = Main_database_connection.selectParticularEmployee();
        ObservableList<Task> tasksss;
        try {
            tasksss = Main_database_connection.getTasksForTabel();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        emp_table.setItems(employeeData);
        tasks_table.setItems(tasksss);
        ObservableList<LogEntry> data12 = Main_database_connection.getLogEntry();
        log_tabel_view.setItems(data12);
        Set<String> taskName = new HashSet<String>();
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

        setupDragAndDrop(todoList, inProgressList);
        setupDragAndDrop(inProgressList, doneList);
        setupDragAndDrop(doneList, todoList);
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

    public void setDelete_log_button() throws SQLException {
        Main_database_connection.deleteLog();
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

    @FXML
    private void setalldetails(){
        LoginScreenController loginScreenController = new LoginScreenController();
        login test = loginScreenController.getUserLoginDetails();
        String password = test.getPassword();
        String name =test.getId();
        Employee employee = Main_database_connection.getEmployeeByName(name,password);
        System.out.println(employee.getName());
    }

    @FXML
    public void setCellValue_empTable(){
        empId_col.setCellValueFactory(new PropertyValueFactory<>("Id"));
        emp_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    public void setCellValue_taskTable(){
        taskId_col.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        task_name_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        empid_tasks_col.setCellValueFactory(new PropertyValueFactory<>("assignedEmployeeId"));
    }


}
