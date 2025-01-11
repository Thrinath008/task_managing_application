package org.example.collaborative_task_management_application;
import org.example.collaborative_task_management_application.databases.Main_database_connection;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignInScreenController implements Initializable {

    @FXML
    private PasswordField confirm_password_textfield;

    @FXML
    private TextField email_textfield;

    @FXML
    private Button loginIn_button;

    @FXML
    private TextField name_textfield;

    @FXML
    private PasswordField password_textfield;

    @FXML
    private ComboBox<String> role_combobox;

    @FXML
    private Button signup_button;
    @FXML
    private Button exit_button;

    @FXML
    public void setLoginIn_button() throws IOException {
        loginIn_button.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("login-screen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    public void exit_button_on_action(ActionEvent event) {
        Stage stage = (Stage)exit_button.getScene().getWindow();
        stage.close();
    }
    public void setSignup_button(ActionEvent event){
        String name = name_textfield.getText();
        String email = email_textfield.getText();
        String password = password_textfield.getText();
        String confirmPassword = confirm_password_textfield.getText();
        if (name.isEmpty()||email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Error","fill all the blanks");
            return;
        }
        if (!Objects.equals(password,confirmPassword)){
            showAlert(Alert.AlertType.ERROR,"error","password and confirm password must be equal");
            return;
        }else {
            Main_database_connection.connectiondb();
            boolean success = Main_database_connection.addEmployee(name,email,password);
            if (success) {
                System.out.println("Employee added successfully in another package!");
            } else {
                System.out.println("Failed to add employee.");
            }
            Main_database_connection.close();
        }

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String []items = {"employee","admin"};
        role_combobox.getItems().addAll(items);
    }
}
