package com.nazmul.ftp.client;

import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.util.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Controller {

    @FXML
    private MenuItem menuItemStartServer;

    @FXML
    private TextField hostField;

    @FXML
    private TextField portField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextArea logTextarea;

    @FXML
    private Button connectButton;

    @FXML
    private Button uploadButton;

    @FXML
    private Button downloadButton;

    @FXML
    public void login() {
        if ("Connect".equals(connectButton.getText())) {
            String auth = "";
            try {
                String username = validUsername(usernameField);
                String password = validPassword(passwordField);

                ClientHelper helper = new ClientHelper(hostField.getText(), portField.getText(), "600", username, password);
                logTextarea.appendText("Status: Logging into " + hostField.getText() + "\n");
                auth = helper.authenticate("600", usernameField.getText(), passwordField.getText());

            } catch (SocketException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
            } catch (UnknownHostException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
            } catch (IOException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
            } catch (InvalidArgException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
            }

            logTextarea.appendText("Status: " + auth + "\n");
            connectButton.setText("Disconnect");
            hostField.setDisable(true);
            usernameField.setDisable(true);
            passwordField.setDisable(true);
            portField.setDisable(true);
            uploadButton.setDisable(false);
            downloadButton.setDisable(false);

        } else {

            try {
                ClientHelper client = new ClientHelper(
                        hostField.getText(), portField.getText(),
                        "700", usernameField.getText(), passwordField.getText());
                String logOut = client.sendRequest("700" + usernameField.getText() + passwordField.getText());
                logTextarea.appendText("Status: " + logOut + "\n");

                connectButton.setText("Connect");
                hostField.setDisable(false);
                usernameField.setDisable(false);
                passwordField.setDisable(false);
                portField.setDisable(false);
                uploadButton.setDisable(true);
                downloadButton.setDisable(true);

            } catch (SocketException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
            } catch (UnknownHostException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
            } catch (IOException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
            }
        }
    }

    private String validPassword(TextField passwordField) throws InvalidArgException {
        String password = passwordField.getText();
        if (Utils.fieldEndsWith(password, '!')) {
            return password;
        } else {
            throw new InvalidArgException("Invalid password, should end with ! \n");
        }
    }

    private String validUsername(TextField usernameField) throws InvalidArgException {
        String username = usernameField.getText();
        if (Utils.fieldStartsWith(username, '!') && Utils.fieldEndsWith(username, '@')) {
            return username;
        } else {
            throw new InvalidArgException("Invalid username, should start with ! and end with @ \n");
        }
    }

    @FXML
    public void serverDialog() {
        InitializeServer dialog = new InitializeServer();
        dialog.pack();
        dialog.setVisible(true);
    }
}
