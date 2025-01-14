package org.example.collaborative_task_management_application;
import org.example.collaborative_task_management_application.databases.Main_database_connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HomeScreenController {

    @FXML
    private Button exit_button;
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
    private Label name_label;

    @FXML
    private AnchorPane project_anchorpane;

    @FXML
    private Button settings_button;


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
    public void setlogout_button() throws IOException {
        logout_button.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("login-screen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
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
        name_label.setText(username);
    }
}
