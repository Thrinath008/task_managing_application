package org.example.collaborative_task_management_application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AdminHomeController {

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

}
