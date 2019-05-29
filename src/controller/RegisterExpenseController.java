package controller;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Expense;
import model.ExpenseCategory;
import model.Income;
import repository.ExpenseRepository;
import util.MathOperation;

public class RegisterExpenseController implements Initializable {

    @FXML
    TextField noteField;
    @FXML
    TextField amountField;
    @FXML
    DatePicker datePicker;
    @FXML
    Label categoryLabel;

    private MainViewController mainViewController;
    private ExpenseCategory selectedExpenseCategory;
    ExpenseRepository expenseRepository;

    public MainViewController getMainViewController() {
        return mainViewController;
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
        selectedExpenseCategory = mainViewController.getExpenseCategory();
        categoryLabel.setText("Selected category: " + selectedExpenseCategory.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeNumeric();
        datePicker.setValue(LocalDate.now());
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
    public void addExpense() {
        expenseRepository = new ExpenseRepository();
        String note = noteField.getText().trim();
        String amount = amountField.getText().trim();
        if (!note.isEmpty() && !amount.isEmpty() && Integer.parseInt(amount) > 0 && datePicker.getValue() != null) {
            Expense expense = new Expense();
            expense.setNote(note);
            expense.setAmount(Integer.parseInt(amount));
            expense.setExpenseCategory(selectedExpenseCategory);
            java.sql.Date dateFormat = java.sql.Date.valueOf(datePicker.getValue());
            expense.setDate(dateFormat);
            MathOperation mathOperation = new MathOperation();
            int totalAmount = mathOperation.getTotalAmount();
            if (expense.getAmount() > totalAmount) {
                Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
                dialog.setHeaderText("Your balance is not enough for a given expense. Continue anyway?");
                final Optional<ButtonType> result = dialog.showAndWait();
                if (result.get() == ButtonType.OK) {
                    expenseRepository.add(expense);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Expense successfully addedy", ButtonType.OK);
                    alert.showAndWait();
                    clearField();
                    mainViewController.clear();
                    closeThisWindow();
                    mainViewController.expenseChartAction();
                }
            } else {
                expenseRepository.add(expense);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Expense successfully addedy", ButtonType.OK);
                alert.showAndWait();
                clearField();
                mainViewController.clear();
                closeThisWindow();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please,fill all the fields and set amount above 0", ButtonType.OK);
            alert.show();
        }
    }

    public void clearField() {
        noteField.setText("");
        amountField.setText("");
    }

    public void closeThisWindow() {
        Stage thisStage = (Stage) noteField.getScene().getWindow();
        thisStage.close();
    }
}
