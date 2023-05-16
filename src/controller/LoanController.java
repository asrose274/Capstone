package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.Loan;
import model.PPPLoan;
import model.Project;
import model.SBALoan;
import utils.LoanQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class LoanController implements Initializable {
    private static Loan selectedLoan;
    private static Project selectedProject;

    public TextField originalAmountText;
    public TextField loanRemainText;
    public Label borrowerLabel;
    public TextField borrowerText;
    public ToggleGroup loanTypeGroup;
    public RadioButton sbaLoanRadio;
    public RadioButton pppLoanButton;
    public ComboBox loanStatusCombo;

    public static void setSelectedProject(Project relatedProject){
        selectedProject = relatedProject;
    }

    public static void setModLoan(Loan modLoan) {
        selectedLoan = modLoan;
    }

    public void initialize(URL url, ResourceBundle rb){

        ObservableList<String> loanStatus = FXCollections.observableArrayList("Delinquent", "Paid", "Compliant");

        loanStatusCombo.setItems(loanStatus);
        loanRemainText.clear();


        if(selectedLoan instanceof SBALoan){
            loanTypeGroup.selectToggle(sbaLoanRadio);
            borrowerText.setText(String.valueOf(((SBALoan) selectedLoan).getProgramID()));
            borrowerLabel.setText("Program ID");
        }
        else if (selectedLoan instanceof PPPLoan ){
            loanTypeGroup.selectToggle(pppLoanButton);
            borrowerText.setText(((PPPLoan) selectedLoan).getLender());
            borrowerLabel.setText("Borrower");        }

        if (selectedLoan != null){

          loanRemainText.setText(String.valueOf(selectedLoan.getAmountRemain()));
          loanStatusCombo.getSelectionModel().select(loanStatus.indexOf(selectedLoan.getStatus()));
          loanRemainText.setText(String.valueOf(selectedLoan.getAmountRemain()));
          originalAmountText.setText(String.valueOf(selectedLoan.getOriginalAmount()));
        }

    }

    public void onSaveLoan(ActionEvent actionEvent) throws SQLException, IOException {

        double amountRemain;
        if (selectedLoan != null) {
            amountRemain = selectedLoan.getAmountRemain();
        }
        else{
            amountRemain = Double.parseDouble(originalAmountText.getText());
        }
        double originalAmount = Double.parseDouble(originalAmountText.getText());
        String status =  loanStatusCombo.getSelectionModel().getSelectedItem().toString();
        String borrowerProgramID = borrowerText.getText();

        if(loanTypeGroup.getSelectedToggle().equals(sbaLoanRadio) && !Main.isInteger(borrowerProgramID)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Program ID");
            alert.setHeaderText("Program ID Error");
            alert.setContentText("Program ID should be a number");
            Optional<ButtonType> selection = alert.showAndWait();
        }
        else {

            if (selectedLoan != null) {
                if (loanTypeGroup.getSelectedToggle().equals(sbaLoanRadio)) {
                    SBALoan loan = new SBALoan(selectedLoan.getLoanID(), selectedLoan.getProjectID(), amountRemain, status, selectedLoan.getLastPayment(), selectedLoan.getLastPaymentDate(), originalAmount, borrowerProgramID);
                    LoanQuery.updateLoan(loan);
                } else {
                    PPPLoan loan = new PPPLoan(selectedLoan.getLoanID(), selectedLoan.getProjectID(), amountRemain, status, selectedLoan.getLastPayment(), selectedLoan.getLastPaymentDate(), originalAmount, borrowerProgramID);
                    LoanQuery.updateLoan(loan);
                }
            } else {
                if (loanTypeGroup.getSelectedToggle().equals(sbaLoanRadio)) {
                    SBALoan loan = new SBALoan(0, selectedProject.getProjectID(), amountRemain, status, 0, null, originalAmount, borrowerProgramID);
                    LoanQuery.addLoan(loan);
                } else {
                    PPPLoan loan = new PPPLoan(0, selectedProject.getProjectID(), amountRemain, status, 0, null, originalAmount, borrowerProgramID);
                    LoanQuery.addLoan(loan);
                }
            }
            Parent root = FXMLLoader.load(getClass().getResource("/view/ProjectScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Project");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void onCancelLoan(ActionEvent actionEvent) throws IOException {
        ProjectController.setModProject(selectedProject);

        Parent root = FXMLLoader.load(getClass().getResource("/view/ProjectScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Project");
        stage.setScene(scene);
        stage.show();
    }

    public void onMakePayment(ActionEvent actionEvent) throws IOException {
        if(selectedLoan == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Loan Exists");
            alert.setHeaderText("Loan does not exist");
            alert.setContentText("Loan does not exist or has not been created yet");
            Optional<ButtonType> selection = alert.showAndWait();
        }
        else {
            PaymentController.setLoan(selectedLoan);
            Parent root = FXMLLoader.load(getClass().getResource("/view/PaymentScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Payment");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void onSBALoanSelected(ActionEvent actionEvent) {
        borrowerLabel.setText("Program ID");

    }

    public void onPPPLoanSelected(ActionEvent actionEvent) {
        borrowerLabel.setText("Borrower");
    }
}
