package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import utils.ClientQuery;
import utils.LoanQuery;
import utils.ProjectQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ReportController implements Initializable {
    public TableView projectStatusTable;
    public TableColumn projectIdCol;
    public TableColumn projectDescCol;
    public TableColumn projectRequest;
    public ComboBox statusCombo;
    public ComboBox clientCombo;
    public TableView clientLoansTable;
    public TableColumn loanIdCol;
    public TableColumn originalAmountCol;
    public TableColumn amountRemainCol;
    public TableColumn statusCol;
    public TableColumn lastPaymentDateCol;
    public TableColumn lastPaymentCol;
    public TableColumn borrowerProgramIdCol;
    public TableColumn clientNameCol;

    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> projectStatus = FXCollections.observableArrayList("Approved", "Denied", "Under Review");
        statusCombo.setItems(projectStatus);
        ObservableList<Client> clients = null;
        try {
            clients = ClientQuery.getAllClients();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clientCombo.setItems(clients);
    }
    public void onGetProjects(ActionEvent actionEvent) throws SQLException {
        String projectStatus = statusCombo.getSelectionModel().getSelectedItem().toString();
        ObservableList<ProjectReport> projects = ProjectQuery.getAllProjects(projectStatus);

        projectStatusTable.setItems(projects);

        projectIdCol.setCellValueFactory(new PropertyValueFactory<>("projectID"));
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        projectDescCol.setCellValueFactory(new PropertyValueFactory<>("projectDesc"));
        projectRequest.setCellValueFactory(new PropertyValueFactory<>("loanRequest"));
    }

    public void onGetLoans(ActionEvent actionEvent) throws SQLException {
        Client client = (Client) clientCombo.getSelectionModel().getSelectedItem();
        int clientId = client.getClientID();
        ObservableList<Loan> clientLoans = LoanQuery.getAllClientLoans(clientId);

        clientLoansTable.setItems(clientLoans);

        loanIdCol.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        originalAmountCol.setCellValueFactory(new PropertyValueFactory<>("originalAmount"));
        amountRemainCol.setCellValueFactory(new PropertyValueFactory<>("amountRemain"));
        lastPaymentCol.setCellValueFactory(new PropertyValueFactory<>("lastPayment"));
        lastPaymentDateCol.setCellValueFactory(new PropertyValueFactory<>("lastPaymentDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


    }

    public void onExit(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();

    }
}
