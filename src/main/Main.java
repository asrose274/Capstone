package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * Loan Management System
 * Capstone
 * @author Aaron Rose
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args){

        launch(args);


    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
