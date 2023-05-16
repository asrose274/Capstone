package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static main.Main.isInteger;

/**
 * Java Database Connection specifically for loans
 * @author Aaron Rose
 */

public abstract class LoanQuery {




    public static ObservableList<Loan> getAllLoans() throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "select * from loans";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        ObservableList<Loan> loans = FXCollections.observableArrayList();
        while(rs.next()){
            int loanId = rs.getInt("loanID");
            int projectId = rs.getInt("projectID");
            double amountRemain = rs.getDouble("amountRemain");
            String status = rs.getString("status");
            double lastPayment = rs.getDouble("lastPayment");
            String lastPaymentDate = rs.getString("lastPaymentDate");
            double originalAmount = rs.getDouble("originalAmount");
            String borrower_programID = rs.getString("borrower_ProgramID");

            if (isInteger(borrower_programID)){
                SBALoan newLoan = new SBALoan(loanId, projectId, amountRemain, status, lastPayment, lastPaymentDate, originalAmount, borrower_programID);
                loans.add(newLoan);
            }
            else{
                PPPLoan newLoan = new PPPLoan(loanId, projectId, amountRemain, status, lastPayment, lastPaymentDate, originalAmount, borrower_programID);
                loans.add(newLoan);
            }

        }
        return loans;
    }

    public static ObservableList<Loan> getAllLoans(int inputProjectId) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "select * from loans where projectid = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, inputProjectId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Loan> loans = FXCollections.observableArrayList();
        while(rs.next()){
            int loanId = rs.getInt("loanID");
            int projectId = rs.getInt("projectID");
            double amountRemain = rs.getDouble("amountRemain");
            String status = rs.getString("status");
            double lastPayment = rs.getDouble("lastPayment");
            String lastPaymentDate = rs.getString("lastPaymentDate");
            double originalAmount = rs.getDouble("originalAmount");
            String borrower_programID = rs.getString("borrower_ProgramID");

                if (isInteger(borrower_programID)){
                    SBALoan newLoan = new SBALoan(loanId, projectId, amountRemain, status, lastPayment, lastPaymentDate, originalAmount, borrower_programID);
                    loans.add(newLoan);
                }
                else{
                PPPLoan newLoan = new PPPLoan(loanId, projectId, amountRemain, status, lastPayment, lastPaymentDate, originalAmount, borrower_programID);
                loans.add(newLoan);
                }

        }
        return loans;
    }

    public static ObservableList<Loan> getAllClientLoans(int clientId) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "select clients.clientID, loans.projectID, loanID, amountRemain, loans.status, lastPayment, lastPaymentDate, originalAmount, borrower_programID\n" +
                "from loans\n" +
                "Join projects on projects.projectId = loans.projectID\n" +
                "Join clients on clients.clientId = projects.clientID\n" +
                "where clients.clientID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, clientId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Loan> loans = FXCollections.observableArrayList();
        while(rs.next()){
            int loanId = rs.getInt("loanID");
            int projectId = rs.getInt("projectID");
            double amountRemain = rs.getDouble("amountRemain");
            String status = rs.getString("status");
            double lastPayment = rs.getDouble("lastPayment");
            String lastPaymentDate = rs.getString("lastPaymentDate");
            double originalAmount = rs.getDouble("originalAmount");
            String borrower = rs.getString("borrower_ProgramID");
            String programID = rs.getString("borrower_ProgramID");


            if (isInteger(programID)){
                SBALoan newLoan = new SBALoan(loanId, projectId, amountRemain, status, lastPayment, lastPaymentDate, originalAmount, programID);
                loans.add(newLoan);
            }
            else{
                PPPLoan newLoan = new PPPLoan(loanId, projectId, amountRemain, status, lastPayment, lastPaymentDate, originalAmount, borrower);
                loans.add(newLoan);
            }

        }
        return loans;
    }

    public static int addLoan(SBALoan loan) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "INSERT INTO loans ( projectID, amountRemain, status, lastPayment, lastPaymentDate, originalAmount, borrower_programID) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, loan.getProjectID());
        ps.setDouble(2, loan.getAmountRemain());
        ps.setString(3, loan.getStatus());
        ps.setDouble(4, loan.getLastPayment());
        ps.setString(5, loan.getLastPaymentDate());
        ps.setDouble(6, loan.getOriginalAmount());
        ps.setString(7, loan.getProgramID());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int addLoan(PPPLoan loan) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "INSERT INTO loans (loanID, projectID, amountRemain, status, lastPayment, lastPaymentDate, originalAmount, borrower_programID) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, loan.getLoanID());
        ps.setInt(2, loan.getProjectID());
        ps.setDouble(3, loan.getAmountRemain());
        ps.setString(4, loan.getStatus());
        ps.setDouble(5, loan.getLastPayment());
        ps.setString(6, loan.getLastPaymentDate());
        ps.setDouble(7, loan.getOriginalAmount());
        ps.setString(8, loan.getLender());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }



    public static int updateLoan(SBALoan loan) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "UPDATE loans SET loanID = ?, projectID = ?, amountRemain = ?, status = ?, lastPayment = ?, lastPaymentDate = ?, originalAmount = ?, borrower_programID = ?  WHERE loanID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, loan.getLoanID());
        ps.setInt(2, loan.getProjectID());
        ps.setDouble(3, loan.getAmountRemain());
        ps.setString(4, loan.getStatus());
        ps.setDouble(5, loan.getLastPayment());
        ps.setString(6, loan.getLastPaymentDate());
        ps.setDouble(7, loan.getOriginalAmount());
        ps.setString(8, loan.getProgramID());
        ps.setInt(9, loan.getLoanID());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int updateLoan(PPPLoan loan) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "UPDATE loans SET loanID = ?, projectID = ?, amountRemain = ?, status = ?, lastPayment = ?, lastPaymentDate = ?, originalAmount = ?, borrower_programID = ? WHERE loanID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, loan.getLoanID());
        ps.setInt(2, loan.getProjectID());
        ps.setDouble(3, loan.getAmountRemain());
        ps.setString(4, loan.getStatus());
        ps.setDouble(5, loan.getLastPayment());
        ps.setString(6, loan.getLastPaymentDate());
        ps.setDouble(7, loan.getOriginalAmount());
        ps.setString(8, loan.getLender());
        ps.setInt(10, loan.getLoanID());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int deleteLoan(Loan loan) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "DELETE FROM loans WHERE loanID = ? ;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, loan.getLoanID());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


}
