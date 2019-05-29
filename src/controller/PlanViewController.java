package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Expense;
import model.ExpenseCategory;
import model.Plan;
import repository.ExpenseCategoryRepository;
import repository.PlanRepository;
import util.MathOperation;

public class PlanViewController implements Initializable {

    MainViewController mainViewController;

    public MainViewController getMainViewController() {
        return mainViewController;
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }
    ExpenseCategoryRepository categoryRepository;
    PlanRepository planRepository;
    MathOperation mathOperation;
    int totalAmount = 0;
    int currentBalance = 0;
    @FXML
    TableView<ExpenseCategory> categoryTableView;
    @FXML
    TableColumn<ExpenseCategory, String> nameCol;
    @FXML
    TableColumn<ExpenseCategory, Integer> amountCol;
    @FXML
    TextField nameField;
    @FXML
    TextField amountField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    Label currentBalanceLabel;
    @FXML
    Label totalAmountLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mathOperation = new MathOperation();
        currentBalance = mathOperation.getTotalAmount();
        currentBalanceLabel.setText("Current balance: " + currentBalance);
        makeNumeric();
        fillCategoryList();
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
    }

    public void makeNumeric() {
        amountField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,5}(\\d{0,0})?")) {
                    amountField.setText(oldValue);
                }
            }
        });
    }

    @FXML
    public void onCategorySelect() {
        ExpenseCategory selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            if (selectedCategory.getAmount() == 0) {
                amountField.setText("");
            }else{
                amountField.setText(selectedCategory.getAmount() + "");
            }
        }
    }

    public void fillCategoryList() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryRepository = new ExpenseCategoryRepository();
        List<ExpenseCategory> categoryList = categoryRepository.findAll();
        categoryTableView.getItems().setAll(categoryList);
    }

    @FXML
    public void setAmount() {
        totalAmount = 0;
        ExpenseCategory selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            if (amountField.getText().trim().isEmpty()) {
                selectedCategory.setAmount(0);
            } else {
                selectedCategory.setAmount(Integer.parseInt(amountField.getText()));
            }
            categoryTableView.getItems().set(categoryTableView.getSelectionModel().getSelectedIndex(), selectedCategory);
            for (ExpenseCategory e : categoryTableView.getItems()) {
                totalAmount += e.getAmount();
            }
            totalAmountLabel.setText("Total amount: " + totalAmount);
        }

    }

    @FXML
    public void add() {
        if (!nameField.getText().trim().isEmpty() && startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
            java.sql.Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
            java.sql.Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());
            if (endDate.after(startDate)) {
                boolean result = false;
                for (ExpenseCategory e : categoryTableView.getItems()) {
                    if (e.getAmount() >= 1) {
                        result = true;
                    } else {
                        result = false;
                        break;
                    }
                }
                if (result) {
                    Plan plan = new Plan();
                    planRepository = new PlanRepository();
                    plan.setName(nameField.getText().trim());
                    plan.setStartDate(startDate);
                    plan.setEndDate(endDate);
                    plan.setTotalAmount(totalAmount);
                    List<ExpenseCategory> expenseList = new ArrayList<>();
                    expenseList.addAll(categoryTableView.getItems());
                    if (totalAmount <= currentBalance) {
                        planRepository.add(plan, expenseList);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Plan successfully addedy", ButtonType.OK);
                        alert.showAndWait();
                        mainViewController.fillPlanList();
                        closeThisWindow();
                    } else {
                        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
                        dialog.setHeaderText("The amount of your balance is lower than the allocated expenses. Continue anyway?");
                        final Optional<ButtonType> dialogResult = dialog.showAndWait();
                        if (dialogResult.get() == ButtonType.OK) {
                            planRepository.add(plan, expenseList);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Plan successfully addedy", ButtonType.OK);
                            alert.showAndWait();
                            mainViewController.fillPlanList();
                            closeThisWindow();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Amount not allocated to all categories", ButtonType.OK);
                    alert.show();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Enter a valid date", ButtonType.OK);
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please,fill all the fields", ButtonType.OK);
            alert.show();
        }

    }

    public void closeThisWindow() {
        Stage thisStage = (Stage) amountField.getScene().getWindow();
        thisStage.close();
    }
}
