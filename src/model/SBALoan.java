package model;

import java.util.Date;

public class SBALoan extends Loan {

    /**
     * additional field specific to this type of loan can be added later
     * depending on what the users would want
     */
    String programID;


    public SBALoan(int loanId, int projectId, double amountRemain, String status, double lastPayment, String lastPaymentDate, double originalAmount, String programID) {
        super(loanId, projectId, amountRemain, status, lastPayment, lastPaymentDate, originalAmount);
        this.programID = programID;

    }

    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }
}
