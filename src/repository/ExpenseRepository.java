/*@author Samir Hasanov */
package repository;

import db.DataManager;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Expense;
import model.ExpenseCategory;
import model.Income;
import model.IncomeCategory;

public class ExpenseRepository extends DataManager implements Repository<Expense> {

    @Override
    public void add(Expense expense) {
        String query = "insert into expense (note,amount,date,category_id) values(?,?,?,?)";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, expense.getNote());
            preparedStatement.setInt(2, expense.getAmount());
            preparedStatement.setDate(3, (Date) expense.getDate());
            preparedStatement.setInt(4, expense.getExpenseCategory().getId());
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(Expense expense) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Expense expense) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Expense> findAll() {
        List<Expense> expenses = new ArrayList<>();
        String query = "select * from expense left join expense_category on expense.category_id = expense_category.id where expense.status = 1 and expense_category.status = 1";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Expense expense = new Expense();
                ExpenseCategory expenseCategory = new ExpenseCategory();
                expense.setId(resultSet.getInt("expense.id"));
                expense.setNote(resultSet.getString("expense.note"));
                expense.setDate(resultSet.getDate("expense.date"));
                expense.setAmount(resultSet.getInt("expense.amount"));
                expenseCategory.setId(resultSet.getInt("expense_category.id"));
                expenseCategory.setName(resultSet.getString("expense_category.name"));
                expense.setExpenseCategory(expenseCategory);
                expenses.add(expense);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return expenses;
    }
    
        public List<Expense> findForChart() {
        List<Expense> expenses = new ArrayList<>();
        String query = "select * , sum(amount) as sum from expense left join expense_category on expense.category_id = expense_category.id where expense.status = 1 and expense_category.status = 1 group by (category_id)";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Expense expense = new Expense();
                ExpenseCategory expenseCategory = new ExpenseCategory();
                expense.setAmount(resultSet.getInt("sum"));
                expenseCategory.setName(resultSet.getString("expense_category.name"));
                expense.setExpenseCategory(expenseCategory);
                expenses.add(expense);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return expenses;
    }
    
    
    public List<Expense> findByDate(Date startDate,Date endDate) {
        List<Expense> expenses = new ArrayList<>();
        String query = "select * from expense left join expense_category on expense.category_id = expense_category.id where expense.status = 1 and expense_category.status = 1 and expense.date between '"+startDate+"' and '"+endDate+"'";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Expense expense = new Expense();
                ExpenseCategory expenseCategory = new ExpenseCategory();
                expense.setId(resultSet.getInt("expense.id"));
                expense.setNote(resultSet.getString("expense.note"));
                expense.setDate(resultSet.getDate("expense.date"));
                expense.setAmount(resultSet.getInt("expense.amount"));
                expenseCategory.setId(resultSet.getInt("expense_category.id"));
                expenseCategory.setName(resultSet.getString("expense_category.name"));
                expense.setExpenseCategory(expenseCategory);
                expenses.add(expense);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return expenses;
    }
}
