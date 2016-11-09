package com.nazmul.ftp.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private TextArea logTextarea;

    @FXML
    private Button connectButton;

    @FXML
    private TreeView<File> systemTreeview;

    @FXML
    private TextArea eventTextarea;


    @FXML
    public void test() {
        logTextarea.appendText(LocalDate.now() + " " + LocalTime.now() + ": You have successfully connected to the system!\n");
    }


    public void initialize(URL location, ResourceBundle resources) {
        File currentDir = new File("/home/nazmul/Desktop/datagram-ftp"); // current directory
        findFiles(currentDir);
    }

    public void findFiles(File dir) {
        TreeItem<File> root = new TreeItem<File>(new File("Files:"));
        root.setExpanded(true);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                findFiles(file);
            } else {
                root.getChildren().add(new TreeItem<File>(file));
            }
            root.getChildren().add(new TreeItem<File>(file));
        }

        systemTreeview.setRoot(root);
    }
}
