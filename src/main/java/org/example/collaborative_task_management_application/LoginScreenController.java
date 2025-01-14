package org.example.collaborative_task_management_application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import org.example.collaborative_task_management_application.backend.*;
import org.example.collaborative_task_management_application.databases.Main_database_connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML
    private Button exit_button;

    @FXML
    private TextField id_textfield = new TextField();

    @FXML
    private AnchorPane login_anchor;

    @FXML
    private Button login_button;
    @FXML
    private Label role_label;

    @FXML
    private PasswordField password_field;

    @FXML
    private ComboBox<String> role_picker;

    @FXML
    private Button signup_button;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
//    private String name = id_textfield.getText();
//    private String password = password_field.getText();
    private double x = 0;
    private double y = 0;
    public void login_data(){
    }
    public void combo_box_testing(){

    }
    @FXML
    public void setSignup_button() throws IOException {
        signup_button.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("SignIn-screen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_textfield.setOnKeyPressed(e->{
            switch (e.getCode()){
                case ENTER -> password_field.requestFocus();
            }
        });
        password_field.setOnKeyPressed(e->{
            switch (e.getCode()){
                case ENTER -> login_button.fire();
            }
        });
        String []items = {"employee","Admin","Manager"};

        role_picker.getItems().addAll(items);
        role_picker.setOnAction(e->{
            String data = role_picker.getSelectionModel().getSelectedItem().toString();

            if (data=="Manager"){
                role_label.setText("Captain");
                System.out.println("hi i am manager");
                login.print();
            } else if (data=="Admin") {
                role_label.setText("Admin mawa");
                System.out.println("hi boss i am Admin");
            }else{
                role_label.setText(data);
            }
            id_textfield.requestFocus();

        });
    }
    public void setLogin_button(ActionEvent e) throws IOException {
        if (id_textfield.getText().isEmpty()||password_field.getText().isEmpty()){
            showAlert(Alert.AlertType.WARNING,"error","enter id and password");
        }else {
            if (Main_database_connection.loginEmployee(id_textfield.getText(),password_field.getText())){
                login_button.getScene().getWindow().hide();
                if (role_picker.getSelectionModel().getSelectedItem().toString()=="Admin"){
                    setadminscreen();
                    showAlert(Alert.AlertType.CONFIRMATION,"success","login success");
                }else {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-screen.fxml"));
                    Stage stage = new Stage();
                    Pane root = fxmlLoader.load();
                    HomeScreenController homeScreenController = fxmlLoader.getController();
                    homeScreenController.setName_label(id_textfield.getText());

                    root.setOnMousePressed(event -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged(event -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("all.css").toExternalForm());
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.show();
                    showAlert(Alert.AlertType.CONFIRMATION,"success","login success");
                }
            }else {
                showAlert(Alert.AlertType.ERROR,"invalid info:"," type slowly boy\n try again or signUp");
                id_textfield.clear();
                password_field.clear();
            }
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.show();

    }
    private void setadminscreen() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("admin-home-screen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

}
// connect to the database and make sure it logins wiht the correct crediantions ans for tommorow ame signup screen and the sql for it