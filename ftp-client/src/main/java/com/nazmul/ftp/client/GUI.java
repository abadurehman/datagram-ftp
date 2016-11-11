package com.nazmul.ftp.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * FTP Client Application
 */
public class GUI extends Application {

    public static void main(String... args) {
        System.out.println("FTP Client");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/client.fxml"));
        primaryStage.setTitle("Datagram FTP");
        primaryStage.setScene(new Scene(root, 720, 576));
        primaryStage.setResizable(false);

        primaryStage.show();
    }
}
