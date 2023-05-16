package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * Java Database Connection
 * Contains methods to query the database
 * @author Aaron Rose
 */

public abstract class JDBC {

    /**
     * creates connection with database
     */
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "capstonedb";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "dbUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * opens connection with database
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * closes connection with database
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }





}







