package bg.tu_varna.sit.hotel.front_end.presentation.admin.controllers;

import bg.tu_varna.sit.hotel.back_end.application.Welcome;
import bg.tu_varna.sit.hotel.back_end.common.CommonTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


// Controller for Admin Login tab
public class AdminLoginController  implements Initializable{

    @FXML
    public TextField adminEmailField;
    @FXML
    public PasswordField adminPasswordField;
    @FXML
    public Button adminEnterButton;
    @FXML
    public Button adminBackButton;
    @FXML
    public Button adminRegisterButton;
    @FXML
    public Button closeWindowButton;

    @FXML
    public void backToWelcomeScreen(ActionEvent actionEvent) throws IOException {
        CommonTask.pageNavigation("/bg/tu_varna/sit/hotel/front_end/presentation/application/pages/WelcomeView.fxml", Welcome.stage,this.getClass(),"Hotel Management System", 800, 500);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }


}