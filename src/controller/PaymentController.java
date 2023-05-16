package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Loan;
import model.PPPLoan;
import model.SBALoan;
import utils.LoanQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    private static Loan selectedLoan;
    public TextField loanRemainText;
    public DatePicker lastPaymentDatePicker;
    public TextField loanPaymentAmountText;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void setLoan(Loan currentLoan) {
        selectedLoan = currentLoan;
    }

    public void initialize(URL url, ResourceBundle rb){
        if(selectedLoan.getAmountRemain() != 0){
        loanRemainText.setText(String.valueOf(selectedLoan.getAmountRemain()));}
        else{
            loanRemainText.setText(String.valueOf(selectedLoan.getOriginalAmount()));
        }
        loanPaymentAmountText.setText(String.valueOf(selectedLoan.getLastPayment()));

        if(selectedLoan.getLastPaymentDate() != null){
        LocalDate lastPaymentDate = LocalDate.parse(selectedLoan.getLastPaymentDate(), formatter);
        lastPaymentDatePicker.setValue(lastPaymentDate);}


    }

    public void onProcessPayment(ActionEvent actionEvent) throws SQLException, IOException {
        double payment = Double.valueOf(loanPaymentAmountText.getText());
        double remain = Double.valueOf(loanRemainText.getText()) - payment;
        String paymentDate = lastPaymentDatePicker.getValue().toString();

        if(selectedLoan instanceof SBALoan){
        SBALoan loan = new SBALoan(selectedLoan.getLoanID(), selectedLoan.getProjectID(), remain, selectedLoan.getStatus(), payment, paymentDate, selectedLoan.getOriginalAmount(), ((SBALoan) selectedLoan).getProgramID());
        LoanQuery.updateLoan(loan);}
        else if(selectedLoan instanceof PPPLoan){
            SBALoan loan = new SBALoan(selectedLoan.getLoanID(), selectedLoan.getProjectID(), remain, selectedLoan.getStatus(), payment, paymentDate, selectedLoan.getOriginalAmount(), ((PPPLoan) selectedLoan).getLender());
            LoanQuery.updateLoan(loan);}

        selectedLoan.setAmountRemain(remain);
        LoanController.setModLoan(selectedLoan);

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoanScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Loan View");
        stage.setScene(scene);
        stage.show();

    }

    public void onFinished(ActionEvent actionEvent) throws IOException {
        LoanController.setModLoan(selectedLoan);

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoanScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Loan View");
        stage.setScene(scene);
        stage.show();
    }

}
