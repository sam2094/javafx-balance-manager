/*@author Samir Hasanov */
package repository;

import db.DataManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ExpenseCategory;
import model.IncomeCategory;

public class ExpenseCategoryRepository extends DataManager implements Repository<ExpenseCategory> {

    @Override
    public void add(ExpenseCategory category) {
        String query = "insert into expense_category(name) values(?)";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getName());
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
    public void update(ExpenseCategory category) {
        String query = "update expense_category set name = ? where id = ?";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getId());
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
    public void remove(ExpenseCategory category) {
        String query = "update expense_category set status = 0 where id = ?";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, category.getId());
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
    public List<ExpenseCategory> findAll() {
        List<ExpenseCategory> categories = new ArrayList<>();
        String query = "select * from expense_category where status = 1";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ExpenseCategory category = new ExpenseCategory();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                categories.add(category);
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
        return categories;
    }
}
