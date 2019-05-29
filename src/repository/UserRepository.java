/*@author Samir Hasanov */
package repository;

import db.DataManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

public class UserRepository extends DataManager {

    public User getUser() {
        User user = null;
        String query = "select * from user where status = 1 and id = 1";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
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
        return user;
    }

    public void removeUser() {
        String query = "update user set status = 0 where id = 1";
        String query2 = "update expense set status = 0 where status = 1";
        String query3 = "update income set status = 0 where status = 1";
        String query4 = "update expense_category set status = 0 where status = 1";
        String query5 = "update expense_category set status = 0 where status = 1";
        String query6 = "update plan set status = 0 where status = 1";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(query3);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(query4);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(query5);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(query6);
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

    public void registerUser(User user) {
        String query = "update user set name = ? ,surname = ? ,username = ? ,password = ? , status = 1 where id = 1";
        try {
            connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getPassword());
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
}
