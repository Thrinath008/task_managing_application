package org.example.collaborative_task_management_application;
import org.example.collaborative_task_management_application.backend.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML
    private Button exit_button;

    @FXML
    private TextField id_textfield;

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

    public void login_data(){

    }
    public void combo_box_testing(){


    }


    @FXML
    public void exit(ActionEvent event){
        Stage stage = (Stage)exit_button.getScene().getWindow();
        stage.close();

    }
    public String name;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String []items = {"employee","Admin","Manager"};
        role_picker.getItems().addAll(items);
        role_picker.setOnAction(e->{
            String data = role_picker.getSelectionModel().getSelectedItem().toString();

            if (data=="Manager"){
                role_label.setText("Captain");
                name = "hi i am manager";
                login.print();
            } else if (data=="Admin") {
                role_label.setText("Admin mawa");
                name = "hi boss i am Admin";
            }else{
                role_label.setText(data);
            }

        });

    }
    public void setLogin_button(){
        login_button.setOnAction(e->{
            System.out.println(name);
        });
    }

}
// connect to the database and make sure it logins wiht the correct crediantions ans for tommorow ame signup screen and the sql for it