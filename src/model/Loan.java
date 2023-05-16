package model;

/**
 * Methods for Loan Class
 * @author Aaron Rose
 */
public abstract class Loan  {

    /**
     * variables for Loan
     */
    private int loanID;
    private int projectID;
    private double amountRemain;
    private String status;
    private double lastPayment;
    private String lastPaymentDate;
    private double originalAmount;


    public Loan(int loanId, int projectId, double amountRemain, String status, double lastPayment, String lastPaymentDate, double originalAmount){
        this.loanID = loanId;
        this.projectID = projectId;
        this.amountRemain = amountRemain;
        this.status = status;
        this.lastPayment = lastPayment;
        this.lastPaymentDate = lastPaymentDate;
        this.originalAmount = originalAmount;
    }

    /**
     * setters and getters for Loan
     */
    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public double getAmountRemain() {
        return amountRemain;
    }

    public void setAmountRemain(double amountRemain) {
        this.amountRemain = amountRemain;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(double lastPayment) {
        this.lastPayment = lastPayment;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }


}
