/*@author Samir Hasanov */
package main;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Income;
import model.IncomeCategory;
import repository.ExpenseRepository;
import repository.IncomeCategoryRepository;
import repository.IncomeRepository;
import repository.PlanRepository;
import repository.UserRepository;
import util.MathOperation;

public class MyBusiness extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
//        Scene scene = new Scene(root);
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.setTitle("Login");
//        stage.show();
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
