package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import service.UserService;

public class RegistrationController {
    @FXML private JFXTextField txtFirstName;
    @FXML private JFXTextField txtLastName;
    @FXML private JFXTextField txtEmail;
    @FXML private JFXPasswordField txtPassword;
    @FXML private JFXPasswordField txtConfirmPassword;

    private final SceneController sceneController = new SceneController();
    private final UserService userService = new UserService();

    @FXML
    private void register() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String confirmedPassword = txtConfirmPassword.getText();

        String validationMessage = userService.insert(firstName, lastName, email, password, confirmedPassword);

        if (validationMessage != null) {
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid input data");
            alert.setHeaderText(null);
            alert.setContentText(validationMessage);
            alert.show();
        } else {
            txtPassword.clear();
            txtConfirmPassword.clear();
            txtEmail.clear();
            txtFirstName.clear();
            txtLastName.clear();
            loadSignInScene();
        }
    }

    @FXML
    private void loadSignInScene() {
        sceneController.activate("Sign In", "SignIn.fxml");
    }

    @FXML
    private void loadTravellingAgencyMainMenu() {
        sceneController.activate("Travelling Agency Main Menu", "TravellingAgency.fxml");
    }

}
