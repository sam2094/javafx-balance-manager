package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import repository.UserRepository;

public class LoginController implements Initializable {

    UserRepository userRepository;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button registerButton;
    @FXML
    Button deleteButton;
    @FXML
    Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonsVisible();
    }

    @FXML
    public void userLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        if (!username.isEmpty() && !password.isEmpty()) {
            userRepository = new UserRepository();
            User user = userRepository.getUser();
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    try {
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        MainViewController controller = loader.getController();
                        controller.loginController = this;
                        stage.setTitle("Main View");
                        stage.setScene(scene);
                        closeThisWindow();
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect password !", ButtonType.OK);
                    alert.show();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect username !", ButtonType.OK);
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fields cannot be empty or contain a space !", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void removeUser() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        if (!username.isEmpty() && !password.isEmpty()) {
            userRepository = new UserRepository();
            User user = userRepository.getUser();
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    userRepository = new UserRepository();
                    Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
                    dialog.setHeaderText("Do you realy want remove user ?");
                    final Optional<ButtonType> result = dialog.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        userRepository.removeUser();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "User succesfully deleted", ButtonType.OK);
                        alert.showAndWait();
                        buttonsVisible();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect password !", ButtonType.OK);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect username !", ButtonType.OK);
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fields cannot be empty or contain a space !", ButtonType.OK);
            alert.show();
        }
    }

    public void buttonsVisible() {
        userRepository = new UserRepository();
        User user = userRepository.getUser();
        if (user == null) {
            registerButton.setVisible(true);
            deleteButton.setVisible(false);
            loginButton.setVisible(false);
        } else {
            registerButton.setVisible(false);
            deleteButton.setVisible(true);
            loginButton.setVisible(true);
        }
    }

    public void closeThisWindow() {
        Stage thisStage = (Stage) deleteButton.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    public void gotoRegister() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            RegisterViewController controller = loader.getController();
            stage.setTitle("Register user");
            stage.setScene(scene);
            closeThisWindow();
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
