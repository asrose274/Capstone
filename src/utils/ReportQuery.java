package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Java Database Connection specifically for login method
 * @author Aaron Rose
 */

public abstract class ReportQuery {

    /**
     * queries the database for a user with matching username and password
     * @param user_name username inputted by user
     * @param password password inputted by user
     * @return resultset
     * @throws SQLException
     */
    public static ResultSet loginUser(String user_name, String password) throws SQLException {

        JDBC.openConnection();
        String sqlStatement = "select user_name, password from users where user_name = ? and password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setString(1, user_name);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static ObservableList<User> getAllUsers() throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "select * from users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        ObservableList<User> users = FXCollections.observableArrayList();
        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            User user = new User(userId, username, null);
            users.add(user);
        }
        return users;
    }
}
