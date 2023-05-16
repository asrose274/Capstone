package model;

import java.time.LocalDateTime;


/**
 * Methods for the Project Class Object
 * @author Aaron Rose
 */

public class Project {
    /**
     * variables for Project class
     */
    private int projectId;
    private int clientId;
    private String projectDesc;
    private double loanRequest;
    private String status;



    public Project(int projectId, int clientId, String projectDesc, double loanRequest, String status) {
        this.projectId = projectId;
        this.clientId = clientId;
        this.projectDesc = projectDesc;
        this.loanRequest = loanRequest;
        this.status = status;

    }


    /**
     * setters and getters for Project
     *
     */
    public int getProjectID() {
        return projectId;
    }

    public void setProjectID(int projectId) {
        this.projectId = projectId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public double getLoanRequest() {
        return loanRequest;
    }

    public void setLoanRequest(double loanRequest) {
        this.loanRequest = loanRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
