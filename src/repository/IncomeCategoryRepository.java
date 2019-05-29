/*@author Samir Hasanov */
package repository;

import db.DataManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.IncomeCategory;

public class IncomeCategoryRepository extends DataManager implements Repository<IncomeCategory> {

    @Override
    public void add(IncomeCategory category) {
        String query = "insert into income_category(name) values(?)";
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
    public void update(IncomeCategory category) {
        String query = "update income_category set name = ? where id = ?";
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
    public void remove(IncomeCategory category) {
        String query = "update income_category set status = 0 where id = ?";
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
    public List<IncomeCategory> findAll() {
        List<IncomeCategory> categories = new ArrayList<>();
        String query = "select * from income_category where status = 1";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                IncomeCategory category = new IncomeCategory();
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
