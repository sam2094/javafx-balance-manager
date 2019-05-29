package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Expense;
import model.ExpenseCategory;
import model.Income;
import model.IncomeCategory;
import repository.ExpenseRepository;
import repository.IncomeRepository;

public class ExpenseDetailController implements Initializable {

    ExpenseRepository expenseRepository;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    TableView<Expense> expenseTableView;
    @FXML
    TableColumn<Expense, String> noteCol;
    @FXML
    TableColumn<Expense, Integer> amountCol;
    @FXML
    TableColumn<Expense, DatePicker> dateCol;
    @FXML
    TableColumn<Expense, ExpenseCategory> categoryCol;
    @FXML
    Label countLabel;
    @FXML
    Label messagelabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setValues();
        fillTable();
    }

    public void writeMessage(String message) {
        messagelabel.setText(message);
    }

    public void setValues() {
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("expenseCategory"));
    }

    public void fillTable() {
        int totalamount = 0;
        expenseRepository = new ExpenseRepository();
        List<Expense> expenses = expenseRepository.findAll();
        expenseTableView.getItems().setAll(expenses);
        for (Expense e : expenses) {
            totalamount += e.getAmount();
        }
        countLabel.setText(expenses.size() + " expenses");
        writeMessage("Total amount: " + totalamount);
    }

    @FXML
    public void filterByDate() {
        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
            int totalamount = 0;
            java.sql.Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
            java.sql.Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());
            List<Expense> expenses = expenseRepository.findByDate(startDate, endDate);
            expenseTableView.getItems().setAll(expenses);
            for (Expense e : expenses) {
                totalamount += e.getAmount();
            }
            countLabel.setText(expenses.size() + " expenses");
            writeMessage("Total amount: " + totalamount);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please,select start and end date", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void refresh() {
        fillTable();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }
}
