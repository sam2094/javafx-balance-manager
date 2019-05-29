package controller;

import java.net.URL;
import java.time.LocalDate;
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
import model.Income;
import model.IncomeCategory;
import repository.IncomeCategoryRepository;
import repository.IncomeRepository;

public class RegisterIncomeController implements Initializable {

    private MainViewController mainViewController;
    private IncomeCategory selectedIncomeCategory;
    IncomeRepository incomeRepository;

    public MainViewController getMainViewController() {
        return mainViewController;
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
        selectedIncomeCategory = mainViewController.getIncomeCategory();
        categoryLabel.setText("Selected category: " + selectedIncomeCategory.getName());
    }

    @FXML
    TextField noteField;
    @FXML
    TextField amountField;
    @FXML
    DatePicker datePicker;
    @FXML
    Label categoryLabel;

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
    public void addIncome() {
        incomeRepository = new IncomeRepository();
        String note = noteField.getText().trim();
        String amount = amountField.getText().trim();
        if (!note.isEmpty() && !amount.isEmpty() && Integer.parseInt(amount)>0 && datePicker.getValue() != null) {
            Income income = new Income();
            income.setNote(note);
            income.setAmount(Integer.parseInt(amount));
            income.setIncomeCategory(selectedIncomeCategory);
            java.sql.Date dateFormat = java.sql.Date.valueOf(datePicker.getValue());
            income.setDate(dateFormat);
            incomeRepository.add(income);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Income successfully addedy", ButtonType.OK);
            alert.showAndWait();
            clearField();
            mainViewController.clear();
            closeThisWindow();
            mainViewController.incomeChartAction();
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
