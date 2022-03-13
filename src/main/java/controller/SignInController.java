package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import model.User;
import service.UserService;

public class SignInController {
    @FXML private JFXPasswordField txtPassword;
    @FXML private JFXTextField txtEmail;

    private final SceneController sceneController = new SceneController();
    private final UserService userService = new UserService();

    @FXML
    private void goToRegister() {
        sceneController.activate("Registration", "register.fxml");
    }

    @FXML
    private void signIn() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        String msg = userService.validateUserCredentials(email, password);

        if (msg != null) {
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unsuccessful authentication!");
            alert.setHeaderText(null);
            alert.setContentText(msg);
            alert.show();
            return;
        }
        txtPassword.clear();
        txtEmail.clear();
        User user = userService.getUser(email);

        sceneController.activateAndSetData("User Main Menu", "UserMenu.fxml", user);
    }
}
