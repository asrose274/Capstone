package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Queries for Clients
 * @author Aaron Rose
 */

public abstract class ClientQuery {



    public static ObservableList<Client> getAllClients() throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "select * from clients";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        ObservableList<Client> clients = FXCollections.observableArrayList();
        while(rs.next()){
            int clientID = rs.getInt("clientId");
            String clientName = rs.getString("clientName");
            String contactName = rs.getString("contactName");
            String contactEmail = rs.getString("contactEmail");
            String contactPhone = rs.getString("contactPhone");
            Client client = new Client(clientID, clientName, contactName, contactEmail, contactPhone);
            clients.add(client);
        }
        return clients;
    }

    public static ObservableList<Client> getAllClients(String input) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "SELECT * FROM clients WHERE clientName LIKE ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setString(1, input + "%");
        ResultSet rs = ps.executeQuery();
        ObservableList<Client> clients = FXCollections.observableArrayList();
        while(rs.next()){
            int clientId = rs.getInt("clientID");
            String clientName = rs.getString("clientName");
            String contactName = rs.getString("contactName");
            String contactEmail = rs.getString("contactEmail");
            String contactPhone = rs.getString("contactPhone");
            Client client = new Client(clientId, clientName, contactName, contactEmail, contactPhone);
            clients.add(client);
        }
        return clients;
    }

    public static int addClient(Client client) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "INSERT INTO clients (clientName, contactName, contactEmail, contactPhone) VALUES (?, ?, ?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setString(1, client.getClientName());
        ps.setString(2, client.getContactName());
        ps.setString(3, client.getContactEmail());
        ps.setString(4, client.getContactPhone());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }



    public static int updateClient(Client client) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "UPDATE clients SET clientID = ?, clientName = ?, contactName = ?, contactEmail = ?, contactPhone = ? WHERE clientId = ? ;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, client.getClientID());
        ps.setString(2, client.getClientName());
        ps.setString(3, client.getContactName());
        ps.setString(4, client.getContactEmail());
        ps.setString(5, client.getContactPhone());
        ps.setInt(6,  client.getClientID());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int deleteClient(Client client) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "DELETE FROM clients WHERE clientID = ? ;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, client.getClientID());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
