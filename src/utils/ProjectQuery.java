package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Project;
import model.ProjectReport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Java Database Connection specifically for login method
 * @author Aaron Rose
 */

public abstract class ProjectQuery {

    public static ObservableList<Project> getAllProjects() throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "select * from projects";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        ObservableList<Project> projects = FXCollections.observableArrayList();
        while(rs.next()){
            int projectId = rs.getInt("projectID");
            int clientId = rs.getInt("clientID");
            String projectDesc = rs.getString("projectDesc");
            double loanRequest = rs.getDouble("loanRequest");
            String status = rs.getString("status");
            Project project = new Project(projectId, clientId,projectDesc, loanRequest, status);
            projects.add(project);
        }
        return projects;
    }

    public static ObservableList<Project> getAllProjects(int clientId) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "select * from projects where clientId = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, clientId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Project> projects = FXCollections.observableArrayList();
        while(rs.next()){
            int projectId = rs.getInt("projectID");
            String projectDesc = rs.getString("projectDesc");
            double loanRequest = rs.getDouble("loanRequest");
            String status = rs.getString("status");
            Project project = new Project(projectId, clientId, projectDesc, loanRequest, status);
            projects.add(project);
        }
        return projects;
    }

    public static ObservableList<ProjectReport> getAllProjects(String status) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "select projects.projectId, projects.clientId, projectDesc, clientName, loanRequest\n" +
                "from projects\n" +
                "Join clients on clients.clientId = projects.clientId\n" +
                "where status = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        ObservableList<ProjectReport> projects = FXCollections.observableArrayList();
        while(rs.next()){
            int projectId = rs.getInt("projectID");
            int clientId = rs.getInt("clientID");
            String clientName = rs.getString("clientName");
            String projectDesc = rs.getString("projectDesc");
            double loanRequest = rs.getDouble("loanRequest");
            ProjectReport project = new ProjectReport(projectId, clientId, projectDesc, loanRequest, status, clientName);
            projects.add(project);
        }
        return projects;
    }

    public static int addProject(Project project) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "INSERT INTO projects (clientID, projectDesc, loanRequest, status) VALUES (?, ?, ?, ?);";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, project.getClientId());
        ps.setString(2, project.getProjectDesc());
        ps.setDouble(3, project.getLoanRequest());
        ps.setString(4, project.getStatus());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int updateProject(Project project) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "UPDATE projects SET projectID = ?, clientID = ?, projectDesc = ?, loanRequest = ?, status = ? WHERE projectID = ? ;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, project.getProjectID());
        ps.setInt(2, project.getClientId());
        ps.setString(3, project.getProjectDesc());
        ps.setDouble(4, project.getLoanRequest());
        ps.setString(5, project.getStatus());
        ps.setInt(6, project.getProjectID());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int deleteProject(Project project) throws SQLException {
        JDBC.openConnection();
        String sqlStatement = "DELETE FROM projects WHERE projectID = ? ;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setInt(1, project.getProjectID());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


}
