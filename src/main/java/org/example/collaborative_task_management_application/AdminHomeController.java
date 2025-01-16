package org.example.collaborative_task_management_application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

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
        ObservableList<LogEntry> data12 = Main_database_connection.getLogEntry();
        log_tabel_view.setItems(data12);
        todoList.getItems().addAll("Task 1", "Task 2", "Task 3");

        // Enable drag-and-drop functionality
        setupDragAndDrop(todoList, inProgressList);
        setupDragAndDrop(inProgressList, doneList);
        setupDragAndDrop(doneList, todoList);
    }
    private void setupDragAndDrop(ListView<String> listView, ListView<String> inProgressList) {
        // Set up drag detection
        listView.setOnDragDetected(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Dragboard dragboard = listView.startDragAndDrop(TransferMode.MOVE);

                ClipboardContent content = new ClipboardContent();
                content.putString(selectedItem);
                dragboard.setContent(content);

                event.consume();
            }
        });

        // Set up drag over
        listView.setOnDragOver(event -> {
            if (event.getGestureSource() != listView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // Set up drag dropped
        listView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasString()) {
                String draggedItem = dragboard.getString();
                ListView<String> source = (ListView<String>) event.getGestureSource();
                source.getItems().remove(draggedItem);
                listView.getItems().add(draggedItem);
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });

        // Set up drag done
        listView.setOnDragDone(event -> event.consume());
    }
    public void setDelete_log_button() throws SQLException {
        Main_database_connection.deleteLog();
    }
    public void getnametexxtfield(String name1234){

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


}
