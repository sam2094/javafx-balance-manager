package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Expense;
import model.ExpenseCategory;
import model.Income;
import model.IncomeCategory;
import model.Plan;
import repository.ExpenseCategoryRepository;
import repository.ExpenseRepository;
import repository.IncomeCategoryRepository;
import repository.IncomeRepository;
import repository.PlanRepository;
import util.MathOperation;

public class MainViewController implements Initializable {

    private IncomeCategory incomeCategory;
    private ExpenseCategory expenseCategory;

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    IncomeCategoryRepository incomeCategoryRepository;
    ExpenseCategoryRepository expenseCategoryRepository;
    ExpenseRepository expenseRepository;
    PlanRepository planRepository;
    IncomeRepository incomeRepository;
    LoginController loginController;

    @FXML
    ListView<IncomeCategory> incomeCategoryListView;
    @FXML
    TextField incomeCategoryNameField;
    @FXML
    ListView<ExpenseCategory> expenseCategoryListView;
    @FXML
    ListView<ExpenseCategory> planListView;
    @FXML
    ListView<Plan> plansList;
    @FXML
    TextField expenseCategoryNameField;
    @FXML
    Label totalBalanceLabel;
    @FXML
    HBox forChart;
    @FXML
    PieChart chart;
    // for plan view elements
    ProgressBar progressBar = new ProgressBar(50);
    @FXML
    Label planNameLabel;
    @FXML
    Label planAmountLabel;
    @FXML
    Label planStartDateLabel;
    @FXML
    Label planEndDateLabel;
    @FXML
    TableView<Plan> planTableView;
    @FXML
    TableColumn<Plan, String> categoryCol;
    @FXML
    TableColumn<Plan, Integer> allotmentAmountCol;
    @FXML
    TableColumn<Plan, Double> progressBarCol;
    @FXML
    TableColumn<Plan, Integer> categorySpentAmountCol;
    @FXML
    TableColumn<Plan, Double> procentCol;
    @FXML
    TableColumn<Plan, String> tempCol;
    @FXML
    HBox labelBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillIncomeCategory();
        fillExpenseCategory();
        setTotalAmount();
        incomeChartAction();
        fillPlanList();
        labelBox.setVisible(false);
        planTableView.setVisible(false);
    }

    @FXML
    public void incomeChartAction() {
        IncomeRepository incomeRepository = new IncomeRepository();
        List<Income> incomeList = incomeRepository.findForChart();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Income i : incomeList) {
            pieChartData.add(new PieChart.Data(i.getIncomeCategory().getName(), i.getAmount()));
        }
        chart.setData(pieChartData);
    }

    @FXML
    public void expenseChartAction() {
        Map<String, Integer> map = new TreeMap<>();
        ExpenseRepository expenseRepository = new ExpenseRepository();
        List<Expense> expenseList = expenseRepository.findForChart();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Expense e : expenseList) {
            pieChartData.add(new PieChart.Data(e.getExpenseCategory().getName(), e.getAmount()));
        }
        chart.setData(pieChartData);
    }

    public void setTotalAmount() {
        MathOperation mathOperation = new MathOperation();
        int totalAmount = mathOperation.getTotalAmount();
        totalBalanceLabel.setText("Total balance: " + totalAmount);
    }

    public void fillIncomeCategory() {
        incomeCategoryRepository = new IncomeCategoryRepository();
        List<IncomeCategory> incomeCategories = incomeCategoryRepository.findAll();
        incomeCategoryListView.getItems().setAll(incomeCategories);
    }

    @FXML
    public void onIncomeCategorySelect() {
        IncomeCategory selectedIncomeCategory = incomeCategoryListView.getSelectionModel().getSelectedItem();
        if (selectedIncomeCategory != null) {
            incomeCategoryNameField.setText(selectedIncomeCategory.getName());
        }
    }

    @FXML
    public void saveIncomeCategory() {
        incomeCategoryRepository = new IncomeCategoryRepository();
        String incomeCategoryName = incomeCategoryNameField.getText().trim();
        List<IncomeCategory> categories = incomeCategoryRepository.findAll();
        IncomeCategory selectedIncomeCategory = incomeCategoryListView.getSelectionModel().getSelectedItem();
        if (!incomeCategoryName.isEmpty()) {
            boolean result = false;
            for (IncomeCategory ic : categories) {
                if (!incomeCategoryName.equalsIgnoreCase(ic.getName())) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
            if (result) {
                IncomeCategory category = new IncomeCategory();
                category.setName(incomeCategoryName);
                if (selectedIncomeCategory == null) {
                    incomeCategoryRepository.add(category);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Income category succesfully added", ButtonType.OK);
                    alert.show();
                    clear();
                } else {
                    category.setId(selectedIncomeCategory.getId());
                    incomeCategoryRepository.update(category);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Income category succesfully updated", ButtonType.OK);
                    alert.show();
                    clear();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "This income category already exist", ButtonType.OK);
                alert.show();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Category's name cannot be empty", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void removeIncomeCategory() {
        incomeCategoryRepository = new IncomeCategoryRepository();
        IncomeCategory selectedIncomeCategory = incomeCategoryListView.getSelectionModel().getSelectedItem();
        if (selectedIncomeCategory != null) {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setHeaderText("Do you realy want remove category ?");
            final Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                incomeCategoryRepository.remove(selectedIncomeCategory);
                clear();
            }
        }
    }

    public void clear() {
        incomeCategoryNameField.setText("");
        expenseCategoryNameField.setText("");
        fillIncomeCategory();
        fillExpenseCategory();
        setTotalAmount();
    }

    @FXML
    public void refresh() {
        clear();
    }

    @FXML
    public void addIncome() {
        IncomeCategory selectedIncomeCategory = incomeCategoryListView.getSelectionModel().getSelectedItem();
        if (selectedIncomeCategory != null) {

            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterIncome.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                RegisterIncomeController controller = loader.getController();
                incomeCategory = selectedIncomeCategory;
                controller.setMainViewController(this);
                stage.setTitle("Register income");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please,select category", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void goToIncomeDetails() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/IncomeDetail.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Income's Details");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//Expenses start
    public void fillExpenseCategory() {
        expenseCategoryRepository = new ExpenseCategoryRepository();
        List<ExpenseCategory> expenseCategories = expenseCategoryRepository.findAll();
        expenseCategoryListView.getItems().setAll(expenseCategories);
    }

    @FXML
    public void onExpenseCategorySelect() {
        ExpenseCategory selectedExpenseCategory = expenseCategoryListView.getSelectionModel().getSelectedItem();
        if (selectedExpenseCategory != null) {
            expenseCategoryNameField.setText(selectedExpenseCategory.getName());
        }
    }

    @FXML
    public void saveExpenseCategory() {
        expenseCategoryRepository = new ExpenseCategoryRepository();
        String expenseCategoryName = expenseCategoryNameField.getText().trim();
        List<ExpenseCategory> categories = expenseCategoryRepository.findAll();
        ExpenseCategory selectedExpenseCategory = expenseCategoryListView.getSelectionModel().getSelectedItem();
        if (!expenseCategoryName.isEmpty()) {
            boolean result = false;
            for (ExpenseCategory ic : categories) {
                if (!expenseCategoryName.equalsIgnoreCase(ic.getName())) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
            if (result) {
                ExpenseCategory category = new ExpenseCategory();
                category.setName(expenseCategoryName);
                if (selectedExpenseCategory == null) {
                    expenseCategoryRepository.add(category);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Expense category succesfully added", ButtonType.OK);
                    alert.show();
                    clear();
                } else {
                    category.setId(selectedExpenseCategory.getId());
                    expenseCategoryRepository.update(category);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Expense category succesfully updated", ButtonType.OK);
                    alert.show();
                    clear();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "This expense category already exist", ButtonType.OK);
                alert.show();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Category's name cannot be empty", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void removeExpenseRepository() {
        expenseCategoryRepository = new ExpenseCategoryRepository();
        ExpenseCategory selectedExpenseCategory = expenseCategoryListView.getSelectionModel().getSelectedItem();
        if (selectedExpenseCategory != null) {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setHeaderText("Do you realy want remove category ?");
            final Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                expenseCategoryRepository.remove(selectedExpenseCategory);
                clear();
            }
        }
    }

    @FXML
    public void addExpense() {
        ExpenseCategory selectedExpenseCategory = expenseCategoryListView.getSelectionModel().getSelectedItem();
        if (selectedExpenseCategory != null) {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterExpense.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                RegisterExpenseController controller = loader.getController();
                expenseCategory = selectedExpenseCategory;
                controller.setMainViewController(this);
                stage.setTitle("Register expense");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please,select category", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    public void goToExpenseDetails() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExpenseDetail.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Expense's Details");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

// Plan start
    public void goToPlan() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlanView.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            PlanViewController controller = loader.getController();
            controller.setMainViewController(this);
            stage.setTitle("Add plan");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void fillPlanList() {
        planRepository = new PlanRepository();
        List<Plan> planList = planRepository.findAll();
        plansList.getItems().setAll(planList);
    }

    @FXML
    public void fillPlanTable() {
        Plan selectedPlan = plansList.getSelectionModel().getSelectedItem();
        if (selectedPlan != null) {
            labelBox.setVisible(true);
            planTableView.setVisible(true);
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
            allotmentAmountCol.setCellValueFactory(new PropertyValueFactory<>("categoryAllotmentAmount"));
            categorySpentAmountCol.setCellValueFactory(new PropertyValueFactory<>("categorySpentAmount"));
            progressBarCol.setCellValueFactory(new PropertyValueFactory<>("progress"));
            procentCol.setCellValueFactory(new PropertyValueFactory<>("procent"));
            tempCol.setCellValueFactory(new PropertyValueFactory<>("temp"));
            planRepository = new PlanRepository();
            List<Plan> planList = planRepository.findForSelected(selectedPlan.getName());
            planNameLabel.setText(planList.get(0).getName());
            planAmountLabel.setText(planList.get(0).getTotalAmount() + "");
            planStartDateLabel.setText(planList.get(0).getStartDate() + "");
            planEndDateLabel.setText(planList.get(0).getEndDate() + "");
            progressBarCol.setCellFactory(ProgressBarTableCell.<Plan>forTableColumn());
            planTableView.getItems().setAll(planList);

        }
    }
}
