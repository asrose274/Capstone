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
import utils.ClientQuery;
import utils.LoanQuery;
import utils.ProjectQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    private static Client selectedClient;
    private static int selectedClientInt;
    public TextField clientIdText;
    public TextField clientNameText;
    public TextField contactNameText;
    public TextField contactPhoneText;
    public TextField contactEmailText;
    public TableView projectsTable;
    public TableColumn projectIdCol;
    public TableColumn requestCol;
    public TableColumn projectStatusCol;
    ObservableList<Project> projects = FXCollections.observableArrayList();


    public static void setModClient(Client modClient) {
        selectedClient = modClient;

    }




    public void initialize(URL url, ResourceBundle rb){

        if (selectedClient != null){
            try {
                projects.addAll(ProjectQuery.getAllProjects(selectedClient.getClientID()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            /**
             * sets Client Table
             */

            projectsTable.setItems(projects);

            /**
             * sets table columns
             */
            projectIdCol.setCellValueFactory(new PropertyValueFactory<>("projectID"));
            requestCol.setCellValueFactory(new PropertyValueFactory<>("loanRequest"));
            projectStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            clientNameText.setText(selectedClient.getClientName());
            contactNameText.setText(selectedClient.getContactName());
            contactEmailText.setText(selectedClient.getContactEmail());
            contactPhoneText.setText(selectedClient.getContactPhone());
        }



    }

    public void onSaveClient(ActionEvent actionEvent) throws SQLException, IOException {
        String clientName = clientNameText.getText();
        String contactName = contactNameText.getText();
        String contactEmail = contactEmailText.getText();
        String contactPhone = contactPhoneText.getText();

        if(selectedClient == null){
            Client newClient = new Client(0, clientName, contactName, contactEmail, contactPhone);
            ClientQuery.addClient(newClient);
        }
        else{
            Client client = new Client(selectedClient.getClientID(), clientName, contactName, contactEmail, contactPhone);
            ClientQuery.updateClient(client);
        }
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void onCancelClient(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();

        selectedClient = null;
    }

    public void onAddProject(ActionEvent actionEvent) throws IOException {
        ProjectController.setModClient(selectedClient);
        Parent root = FXMLLoader.load(getClass().getResource("/view/ProjectScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Project");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifyProject(ActionEvent actionEvent) throws IOException {
        Project modProject = (Project) projectsTable.getSelectionModel().getSelectedItem();
        ProjectController.setModProject(modProject);
        ProjectController.setModClient(selectedClient);

        if(modProject == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No project Selected");
            alert.setContentText("Please select a project to modify");
            alert.showAndWait();
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ProjectScreen.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Project");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void onDelProject(ActionEvent actionEvent) throws SQLException {
        Project delProject = (Project) projectsTable.getSelectionModel().getSelectedItem();
        /**
         * checks if a project is selected
         */
        if(delProject == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Client Selected");
            alert.setContentText("Please select a client to delete");
            alert.showAndWait();
        }
        else {
            /**
             * checks if there are existing loans for the project
             */
            ObservableList<Loan> loans = LoanQuery.getAllLoans(delProject.getProjectID());
            if (!loans.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Cannot Delete Project");
                alert.setContentText("There are loans still associated with this project");
                alert.showAndWait();
            } else {

                /**
                 * if a project is selected and there are no loans for the project it will ask for confirmation before it runs a SQL query to delete the project
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
                    ProjectQuery.deleteProject(delProject);
                    projects.clear();
                    projects.addAll(ProjectQuery.getAllProjects());
                    projectsTable.setItems(projects);
                }
            }
        }
    }
}
