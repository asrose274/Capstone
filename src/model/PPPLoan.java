package model;

import java.util.Date;

public class PPPLoan extends Loan {

    /**
     * additional field specific to this type of loan can be added later
     * depending on what the users would want
     */
    private String lender;


    public PPPLoan(int loanId, int projectId, double amountRemain, String status, double lastPayment, String lastPaymentDate, double originalAmount, String lender) {
        super(loanId, projectId, amountRemain, status, lastPayment, lastPaymentDate, originalAmount);
        this.lender = lender;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }
}
