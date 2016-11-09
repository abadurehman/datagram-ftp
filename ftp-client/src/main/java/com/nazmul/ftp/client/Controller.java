package com.nazmul.ftp.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
    private TreeView<String> treeView;

    @FXML
    private VBox treeBox;

    @FXML
    private MenuItem menuItemStartServer;

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

    public void generateVbox() {
        //create tree pane
        treeBox.setPadding(new Insets(10, 10, 10, 10));
        treeBox.setSpacing(10);
        //setup the file browser root
        String hostName = "localhost";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException x) {
        }
        TreeItem<String> rootNode = new TreeItem<String>(hostName,
                new ImageView(new Image(String.valueOf(getClass().getResource("/icons/computer.png")))));
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path name : rootDirectories) {
            FilePathTreeItem treeNode = new FilePathTreeItem(name);
            rootNode.getChildren().add(treeNode);
        }
        rootNode.setExpanded(true);
        //create the tree view
        treeView.setRoot(rootNode);
        //add everything to the tree pane
        treeBox.getChildren().addAll(new Label("File browser"), treeView);
//        VBox.setVgrow(treeView, Priority.ALWAYS);
//        StackPane root=new StackPane();
//        root.getChildren().addAll(treeBox);
//        treeBox.getChildren().add(treeBox);
    }

    @FXML
    public void serverDialog() {
        InitializeServer dialog = new InitializeServer();
        dialog.pack();
        dialog.setVisible(true);
    }
}
