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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.collaborative_task_management_application.databases.Main_database_connection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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




    @Override

    public void initialize(URL location, ResourceBundle resources) {
    }
}
