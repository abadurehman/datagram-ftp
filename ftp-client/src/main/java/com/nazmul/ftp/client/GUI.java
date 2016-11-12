package com.nazmul.ftp.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;


/**
 * FTP Client Application
 */
public class GUI extends Application {
    static final Logger logger = Logger.getLogger(GUI.class);

    public static void main(String... args) {
        logger.info("FTP Client ready");
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
