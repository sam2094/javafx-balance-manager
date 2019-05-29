package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import repository.UserRepository;

public class RegisterViewController implements Initializable {

    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField repeatPasswordField;
    @FXML
    Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void registerUser() {
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String repeatPassword = repeatPasswordField.getText().trim();
        if (!name.isEmpty() && !surname.isEmpty() && !username.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty()) {
            if (password.equals(repeatPassword)) {
                User user = new User();
                UserRepository userRepository = new UserRepository();
                user.setName(name);
                user.setSurname(surname);
                user.setUsername(username);
                user.setPassword(password);
                userRepository.registerUser(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "User registered succesfully", ButtonType.OK);
                alert.showAndWait();
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    closeThisWindow();
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } else {
                errorLabel.setText("Password doesn't match repeated password");
            }

        } else {
            errorLabel.setText("Please,fill all the fields");
        }
    }

    public void closeThisWindow() {
        Stage thisStage = (Stage) nameField.getScene().getWindow();
        thisStage.close();
    }
}
