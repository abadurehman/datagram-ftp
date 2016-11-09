package com.nazmul.ftp.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML
    private TextArea logTextarea;

    @FXML
    private Button connectButton;

    @FXML
    public void test() {
        logTextarea.appendText("You have successfully connected to the system!");
    }
}
