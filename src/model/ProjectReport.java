package model;

public class ProjectReport extends Project{
    String clientName;

    public ProjectReport(int projectId, int clientId, String projectDesc, double loanRequest, String status, String clientName) {
        super(projectId, clientId, projectDesc, loanRequest, status);
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
