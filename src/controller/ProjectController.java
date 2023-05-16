package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Client;
import model.Loan;
import model.PPPLoan;
import model.Project;
import utils.LoanQuery;
import utils.ProjectQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {


    private static Project selectedProject;
    private static Client selectedClient;
    public TextField projectDescText;
    ObservableList<Loan> loans = FXCollections.observableArrayList();

    public TableView loansTable;
    public TableColumn loanIdCol;
    public TableColumn amountRemainCol;
    public TableColumn originalAmountCol;
    public TextField loanRequestText;
    public ComboBox projectStatusCombo;





    public static void setModProject(Project modProject) {
        selectedProject = modProject;
    }

    public static void setModClient(Client modClient) {
        selectedClient = modClient;

    }

    public void initialize(URL url, ResourceBundle rb){
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Approved", "Denied", "Under Review");
        projectStatusCombo.setItems(statusOptions);
        if (selectedProject != null){
            try {
                loans.addAll(LoanQuery.getAllLoans(selectedProject.getProjectID()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            /**
             * sets Loans Table
             */

            loansTable.setItems(loans);

            /**
             * sets table columns
             */
            loanIdCol.setCellValueFactory(new PropertyValueFactory<>("loanID"));
            originalAmountCol.setCellValueFactory(new PropertyValueFactory<>("originalAmount"));
            amountRemainCol.setCellValueFactory(new PropertyValueFactory<>("amountRemain"));


            projectDescText.setText(selectedProject.getProjectDesc());
            loanRequestText.setText(String.valueOf(selectedProject.getLoanRequest()));
            projectStatusCombo.getSelectionModel().select(statusOptions.indexOf(selectedProject.getStatus()));
        }




    }

    public void onSaveProject(ActionEvent actionEvent) throws SQLException, IOException {

        String projectDesc = projectDescText.getText();
        double loanRequest = Double.parseDouble(loanRequestText.getText());
        String status = projectStatusCombo.getValue().toString();

        if(selectedProject == null){
            Project project = new Project(0, selectedClient.getClientID(), projectDesc, loanRequest, status);
            ProjectQuery.addProject(project);
        }
        else{
            Project project = new Project(selectedProject.getProjectID(), selectedClient.getClientID(), projectDesc, loanRequest, status);
            ProjectQuery.updateProject(project);
        }
        Parent root = FXMLLoader.load(getClass().getResource("/view/ClientScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Client");
        stage.setScene(scene);
        stage.show();
    }

    public void onCancelProject(ActionEvent actionEvent) throws IOException {
        ClientController.setModClient(selectedClient);
        Parent root = FXMLLoader.load(getClass().getResource("/view/ClientScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();

        selectedProject = null;
    }

    public void onAddLoan(ActionEvent actionEvent) throws IOException {
        LoanController.setSelectedProject(selectedProject);
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoanScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Loan");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifyLoan(ActionEvent actionEvent) throws IOException {
        Loan modLoan = (Loan) loansTable.getSelectionModel().getSelectedItem();
        LoanController.setSelectedProject(selectedProject);
        if(modLoan == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No loan Selected");
            alert.setContentText("Please select a loan to modify");
            alert.showAndWait();
        }
        else {
            LoanController.setModLoan(modLoan);
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoanScreen.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Loan");
            stage.setScene(scene);
            stage.show();
        }

    }

    public void onDeleteLoan(ActionEvent actionEvent) throws SQLException {
        Loan delLoan = (Loan) loansTable.getSelectionModel().getSelectedItem();

        if(delLoan == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Loan Selected");
            alert.setContentText("Please select a loan to delete");
            alert.showAndWait();
        }
        else {

            /**
             * if a loan is selected it will ask for confirmation before it runs a SQL query to delete the project
             */
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Action");
            confirmation.setHeaderText("Confirm Deletion");
            confirmation.setContentText("Please confirm deletion of this project");
            Optional<ButtonType> selection = confirmation.showAndWait();

            /**
             * if deletion is confirmed it runs the SQL query to delete the client
             */
            if (selection.get() == ButtonType.OK) {
                LoanQuery.deleteLoan(delLoan);
                loans.clear();
                loans.addAll((PPPLoan) LoanQuery.getAllLoans());
                loansTable.setItems(loans);
            }
        }
    }
}

