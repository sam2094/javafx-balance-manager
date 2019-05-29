package controller;

import java.net.URL;
import java.sql.Date;
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
import model.Income;
import model.IncomeCategory;
import repository.IncomeRepository;

public class IncomeDetailController implements Initializable {

    IncomeRepository incomeRepository;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    TableView<Income> incomeTableView;
    @FXML
    TableColumn<Income, String> noteCol;
    @FXML
    TableColumn<Income, Integer> amountCol;
    @FXML
    TableColumn<Income, DatePicker> dateCol;
    @FXML
    TableColumn<Income, IncomeCategory> categoryCol;
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
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("incomeCategory"));
    }

    public void fillTable() {
        int totalamount = 0;
        incomeRepository = new IncomeRepository();
        List<Income> incomes = incomeRepository.findAll();
        incomeTableView.getItems().setAll(incomes);
        for (Income i : incomes) {
            totalamount += i.getAmount();
        }
//        totalamount = incomes.stream().map((i) -> i.getAmount()).reduce(totalamount, Integer::sum);
        countLabel.setText(incomes.size() + " incomes");
        writeMessage("Total amount: " + totalamount);
    }

    @FXML
    public void filterByDate() {
        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
            int totalamount = 0;
            java.sql.Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
            java.sql.Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());
            List<Income> incomes = incomeRepository.findByDate(startDate, endDate);
            incomeTableView.getItems().setAll(incomes);
            for (Income i : incomes) {
                totalamount += i.getAmount();
            }
            countLabel.setText(incomes.size() + " incomes");
            writeMessage("Total amount: " + totalamount);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please,select start and end date", ButtonType.OK);
            alert.show();
        }
    }
    
    @FXML
    public void refresh(){
        fillTable();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }
}
