package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import model.User;
import utils.JDBC;
import utils.UserQuery;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Controls the logic and GUI elements on the LoginScreen
 * @author Aaron Rose
 */


public class LoginScreenController {


    public TextField usernameText;
    public TextField passwordText;
    public Label errorLabel;

    public Label usernameLabel;
    public Label passwordLabel;
    public Label titleLabel;
    public Button loginButton;
    public Button exitButton;
    ObservableList<User> allUsers = FXCollections.observableArrayList();


    /**
     * code block for the exit button
     *
     * @param actionEvent the user clicking the exit button
     *                    ends the program
     */
    public void onExitButton(ActionEvent actionEvent) {


        System.exit(0);
    }

    /**
     * code block for the login button
     *
     * @param actionEvent the user click the login button
     */
    public void onLoginButton(ActionEvent actionEvent) throws SQLException, IOException {

        String user_Name = usernameText.getText();
        String password = passwordText.getText();


        /**
         * checks if the username and password fields are blank
         */
        if (user_Name.isEmpty() || password.isEmpty()) {
            errorLabel.setText("BlankField");
            JDBC.closeConnection();
        } else {

            /**
             * checks if username and password match the database
             */
            ResultSet resultSet = UserQuery.loginUser(user_Name, password);
            /**
             * error if username or password are incorrect
             */
            if (!resultSet.isBeforeFirst()) {
                errorLabel.setText("Mismatch");
                JDBC.closeConnection();
            } else {
                /**
                 * if the username and password match the user is taken to the main screen and a user object is created for use through the program
                 */
                User currentUser = null;

                for (User user : UserQuery.getAllUsers()) {
                    if (user.getUser_Name().equals(user_Name)) {
                        currentUser = user;
                    }

                }

                /**
                 * used to send the username to various parts of the program
                 */
                MainScreenController.setCurrentUser(currentUser);


                /**
                 * takes user to main screen
                 */
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
            }


        }
    }


}
