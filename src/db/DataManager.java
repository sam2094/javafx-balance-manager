 /*@author Samir Hasanov */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataManager {
 protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;

    public void connect() throws ClassNotFoundException, SQLException {
        String username = "root";
        String password = "1234";
        String URL = "jdbc:mysql://localhost:3306/my_business";
        String driverName = "com.mysql.jdbc.Driver";
        Class.forName(driverName);
        connection = DriverManager.getConnection(URL, username, password);
    }

    public void disconnect() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
