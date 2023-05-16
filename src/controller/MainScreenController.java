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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Client;
import model.Project;
import model.User;
import utils.ClientQuery;
import utils.ProjectQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Aaron Rose
 * Controls all logic and methods for the main screen
 * NOTE: I realized much later that I used the word contact in place of customer
 *functionally it uses customers
 */
public class MainScreenController implements Initializable {



    /**
     * initialized variables and ObservableLists
     */
    private static Client modClient;
    public TableView allClientsTable;
    public TableColumn clientIDCol;
    public TableColumn clientNameCol;
    public TableColumn contactNameCol;
    public TableColumn contactEmailCol;
    public TableColumn contactPhoneCol;
    public TextField searchText;
    public Button searchButton;


    ObservableList<Client> clients = FXCollections.observableArrayList();



    /**
     * sets the current user based on what login credentials were used
     */
    public static User currentUser;

    public static void setCurrentUser(User currentUser) {
        MainScreenController.currentUser = currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        try {
            clients.addAll(ClientQuery.getAllClients());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /**
         * sets Client Table
         */

        allClientsTable.setItems(clients);

        /**
         * sets table columns
         */
        clientIDCol.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactEmailCol.setCellValueFactory(new PropertyValueFactory<>("contactEmail"));
        contactPhoneCol.setCellValueFactory(new PropertyValueFactory<>("contactPhone"));

    }

    /**
     * methods executed when delete client is pushed
     * @param actionEvent click delete client
     * @throws SQLException
     */
    public void onDelClient(ActionEvent actionEvent) throws SQLException {


        Client delClient = (Client) allClientsTable.getSelectionModel().getSelectedItem();
        /**
         * checks if a client is selected
         */
        if(delClient == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Client Selected");
            alert.setContentText("Please select a client to delete");
            alert.showAndWait();
        }
        else {
            /**
             * checks if there are existing projects for the client
             */
            ObservableList<Project> projects = ProjectQuery.getAllProjects(delClient.getClientID());
            if (!projects.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Cannot Delete Client");
                alert.setContentText("There are projects still associated with this client");
                alert.showAndWait();
            } else {

                /**
                 * if a client is selected and there are no projects for the client it will ask for confirmation before it runs a SQL query to delete the client
                 */
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirm Action");
                confirmation.setHeaderText("Confirm Deletion");
                confirmation.setContentText("Please confirm deletion of this client");
                Optional<ButtonType> selection = confirmation.showAndWait();

                /**
                 * if deletion is confirmed it runs the SQL query to delete the client
                 */
                if (selection.get() == ButtonType.OK) {
                    ClientQuery.deleteClient(delClient);
                    clients.clear();
                    clients.addAll(ClientQuery.getAllClients());
                    allClientsTable.setItems(clients);
                }
            }
        }
    }

    /**
     * methods for modify client button
     * @param actionEvent on modify client selected
     * @throws IOException
     */
    public void onModifyClient(ActionEvent actionEvent) throws IOException {
        /**
         * checks to see if a client is selected
         */
        modClient = (Client) allClientsTable.getSelectionModel().getSelectedItem();
        if(modClient == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No client Selected");
            alert.setContentText("Please select a client to modify");
            alert.showAndWait();
        }
        else {
            ClientController.setModClient(modClient);
            Parent root = FXMLLoader.load(getClass().getResource("/view/ClientScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * methods for the add client button
     * @param actionEvent on click add client button
     * @throws IOException
     */
    public void onAddClient(ActionEvent actionEvent) throws IOException {
        /**
         * takes the user to the add client screen
         */
        Parent root = FXMLLoader.load(getClass().getResource("/view/ClientScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * methods for Reports button
     * @param actionEvent on reports button clicked
     * @throws IOException
     */
    public void onReportsButton(ActionEvent actionEvent) throws IOException {
        /**
         *takes the user to the reports screen
         */
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Run Reports");
        stage.setScene(scene);
        stage.show();
    }

    public void onSearchClients(KeyEvent keyEvent) throws SQLException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            String searchValue = searchText.getText();
            ObservableList<Client> searchClients;

            if (searchValue.isBlank()){
                allClientsTable.setItems(clients);
            }
            else{
                searchClients = ClientQuery.getAllClients(searchValue);
                allClientsTable.setItems(searchClients);
            }
        }
    }

    public void onSearchButtonClicked(ActionEvent actionEvent) throws SQLException {
        String searchValue = searchText.getText();
        ObservableList<Client> searchClients;

        if (searchValue.isBlank()){
            allClientsTable.setItems(clients);
        }
        else{
            searchClients = ClientQuery.getAllClients(searchValue);
            allClientsTable.setItems(searchClients);
        }

    }

    public void onExitButton(ActionEvent actionEvent) {
        System.exit(0);
    }
}
