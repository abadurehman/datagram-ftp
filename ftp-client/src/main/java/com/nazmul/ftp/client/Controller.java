package com.nazmul.ftp.client;

import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Controller {

    static final Logger logger = Logger.getLogger(Controller.class);
    static final String LOGIN = String.valueOf(ProtocolCode.LOGIN);
    static final String LOGOUT = String.valueOf(ProtocolCode.LOGOUT);

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
        String responseCode = "";
        boolean valid = true;

        if ("Connect".equals(connectButton.getText())) {
            try {
                String host = validHostAddress(hostField);
                String port = validServerPort(portField);
                String username = validUsername(usernameField);
                String password = validPassword(passwordField);

                ClientHelper helper = new ClientHelper(host, port);
                logTextarea.appendText("Status: Logging into " + host + "\n");
                responseCode = helper.authenticate(LOGIN, username, password);

            } catch (SocketException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
                valid = false;
            } catch (UnknownHostException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
                valid = false;
            } catch (IOException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
                valid = false;
            } catch (InvalidArgException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
                valid = false;
            }

            // If server request was successful
            if (valid) {
                onResponseCode(Short.parseShort(responseCode.trim()));
            }

        } else {
            try {
                String host = validHostAddress(hostField);
                String port = validServerPort(portField);
                String username = validUsername(usernameField);
                String password = validPassword(passwordField);

                ClientHelper client = new ClientHelper(host, port);
                logTextarea.appendText("Status: Logging out of " + host + "\n");
                responseCode = client.sendRequest(LOGOUT + username + password);

            } catch (SocketException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
                valid = false;
            } catch (UnknownHostException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
                valid = false;
            } catch (IOException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
                valid = false;
            } catch (InvalidArgException e) {
                logTextarea.appendText("Status: " + e.getMessage() + "\n");
            }

            // If server request was successful
            if (valid) {
                onResponseCode(Short.parseShort(responseCode.trim()));
            }
        }
    }

    private String validServerPort(TextField portField) throws InvalidArgException {
        final String DEFAULT_PORT = "3000";
        if(portField.getText().isEmpty()) {
            throw new InvalidArgException("Server port cannot be empty");
        }
        return DEFAULT_PORT;
    }

    private String validHostAddress(TextField hostField) throws InvalidArgException {
        final String DEFAULT_HOST = "localhost";
        if(hostField.getText().isEmpty()) {
            throw new InvalidArgException("Server address cannot be empty");
        }
        return DEFAULT_HOST;
    }

    private String validPassword(TextField passwordField) throws InvalidArgException {
        if (passwordField.getText().isEmpty()) {
            return "!";
        }

        String password = passwordField.getText();
        if (Utils.fieldEndsWith(password, '!')) {
            return password;
        }
        throw new InvalidArgException("Password delimiter must be provided. It must end with '!'");
    }

    private String validUsername(TextField usernameField) throws InvalidArgException {
        if (usernameField.getText().isEmpty()) {
            usernameField.setText("!anonymous@");
            return "!anonymous@";
        }

        String username = usernameField.getText();
        if (Utils.fieldStartsWith(username, '!') && Utils.fieldEndsWith(username, '@')) {
            return username;
        }
        throw new InvalidArgException("Username delimiter is not provided. It must start with '!' and end with '@'");
    }

    @FXML
    public void serverDialog() {
        InitializeServer dialog = new InitializeServer();
        dialog.pack();
        dialog.setVisible(true);
    }

    private void onResponseCode(short code) {
        switch (code) {
            case ResponseCode.USER_LOGGED_IN_PROCEED:
                logTextarea.appendText("Status: " + code + " Logged in\n");
                connectButton.setText("Disconnect");
                hostField.setDisable(true);
                usernameField.setDisable(true);
                passwordField.setDisable(true);
                portField.setDisable(true);
                uploadButton.setDisable(false);
                downloadButton.setDisable(false);
                break;
            case ResponseCode.USER_LOGGED_OUT_SERVICE_TERMINATED:
                logTextarea.appendText("Status: " + code + " Logged out\n");
                connectButton.setText("Connect");
                hostField.setDisable(false);
                usernameField.setDisable(false);
                passwordField.setDisable(false);
                portField.setDisable(false);
                uploadButton.setDisable(true);
                downloadButton.setDisable(true);
                break;
            case ResponseCode.INVALID_USERNAME_OR_PASSWORD:
                logTextarea.appendText("Status: " + code + " Invalid username or password\n");
                break;
            case ResponseCode.USERNAME_OK_NEED_PASSWORD:
                logTextarea.appendText("Status " + code + " Username ok, need password\n");
                break;
            case ResponseCode.SYNTAX_ERROR_COMMAND_UNRECOGNIZED:
                logTextarea.appendText("Status: " + code + " Syntax error in parameters or arguments\n");
                break;
            default:
                logger.info("Invalid response code");
        }
    }
}
