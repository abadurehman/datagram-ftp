package com.nazmul.ftp.client.ui;

import com.nazmul.ftp.client.ClientHelper;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.Utils;
import org.apache.log4j.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UiWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1525110630375067115L;

    static final Logger LOGGER = Logger.getLogger(UiWindow.class);
    static final String LOGIN = String.valueOf(ProtocolCode.LOGIN);
    static final String LOGOUT = String.valueOf(ProtocolCode.LOGOUT);
    static final String DEFAULT_REMOTE_INPUT = "File name on the server";
    /**
     * TOP MENU
     **/
    JPanel top;
    JLabel serverLabel;
    JTextField serverInput;
    JLabel portLabel;
    JTextField portInput;
    JLabel userLabel;
    JTextField userInput;
    JLabel passwordLabel;
    JPasswordField passwordInput;
    JButton loginButton;
    /**
     * CENTER CONTAINER
     **/
    JPanel center;
    /**
     * UPLOAD CONTAINER
     **/
    JPanel upload;
    JTextField remoteUploadFileNameInput;
    JFileChooser uploadChooser;
    JProgressBar progressBar;

    /**
     * DOWNLOAD CONTAINER
     **/
    JPanel download;
    JTextField remoteDownloadFileNameInput;
    JFileChooser downloadChooser;

    /**
     * RIGHT PANEL
     **/
    JTextArea logArea;
    JScrollPane scroll;
    DefaultCaret caret;

    public UiWindow() {
        super("Datagram FTP");

        configureWindow();
        drawUI();
        setListeners();
    }

    private void setListeners() {
        loginButton.addActionListener(this);

        uploadChooser.addActionListener(this);
        downloadChooser.addActionListener(this);

        remoteDownloadFileNameInput.addActionListener(this);
    }

    private void drawUI() {

        /** Server informations **/
        top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 15));
        top.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        serverLabel = new JLabel("Host: ");

        serverInput = new JTextField("localhost");
        serverInput.setPreferredSize(new Dimension(120, 20));

        portLabel = new JLabel("Port: ");

        portInput = new JTextField("3000");
        portInput.setPreferredSize(new Dimension(50, 20));

        userLabel = new JLabel("Username : ");

        userInput = new JTextField("!demo@");
        userInput.setPreferredSize(new Dimension(120, 20));

        passwordLabel = new JLabel("Password : ");

        passwordInput = new JPasswordField("demo!");
        passwordInput.setPreferredSize(new Dimension(100, 20));

        loginButton = new JButton("Connect");
        loginButton.setPreferredSize(new Dimension(150, 20));

        progressBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        progressBar.setVisible(false);
        progressBar.setStringPainted(true);
        progressBar.getSize().width += 60;
        progressBar.getSize().height += 20;

        add(top, BorderLayout.PAGE_START);

        top.add(serverLabel);
        top.add(serverInput);
        top.add(portLabel);
        top.add(portInput);
        top.add(userLabel);
        top.add(userInput);
        top.add(passwordLabel);
        top.add(passwordInput);
        top.add(progressBar);
        top.add(loginButton);
        top.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        /** Main content definition **/

        center = new JPanel();
        center.setLayout(new GridLayout(1, 2));

        /** Setting sending components **/

        upload = new JPanel();
        upload.setLayout(new BorderLayout());
        upload.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Upload a file"));
        remoteUploadFileNameInput = new JTextField(DEFAULT_REMOTE_INPUT);

        //Simulate prompt
        remoteUploadFileNameInput.addFocusListener(new UploadFocusListener());

        uploadChooser = new JFileChooser();
        uploadChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        uploadChooser.setApproveButtonText("Upload");
        uploadChooser.setEnabled(true);

        JPanel sendContent = new JPanel();
        sendContent.setLayout(new BorderLayout());
        sendContent.add(uploadChooser, BorderLayout.CENTER);
        sendContent.add(remoteUploadFileNameInput, BorderLayout.PAGE_START);
        upload.add(sendContent, BorderLayout.CENTER);

        /** Setting reception components */
        download = new JPanel();
        download.setLayout(new BorderLayout());
        download.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Download a file"));
        remoteDownloadFileNameInput = new JTextField(DEFAULT_REMOTE_INPUT);

        //Simulate prompt
        remoteDownloadFileNameInput.addFocusListener(new DownloadFocusListener());

        downloadChooser = new JFileChooser();
        downloadChooser.setDialogType(JFileChooser.DIRECTORIES_ONLY);
        downloadChooser.setApproveButtonText("Download");
        downloadChooser.setEnabled(false);

        JPanel receiveContent = new JPanel();
        receiveContent.setLayout(new BorderLayout());
        receiveContent.add(downloadChooser, BorderLayout.CENTER);
        receiveContent.add(remoteDownloadFileNameInput, BorderLayout.PAGE_START);
        download.add(receiveContent, BorderLayout.CENTER);

        /** Adding to the center container **/
        center.add(upload);
        center.add(download);

        /** Adding logging area **/
        logArea = new JTextArea();
        logArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Logs"));
        logArea.setPreferredSize(new Dimension(getWidth(), 150));
//        logArea.setEnabled(false);

        scroll = new JScrollPane (logArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        caret = (DefaultCaret)logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        /** Adding all **/
        add(center, BorderLayout.CENTER);
        add(scroll, BorderLayout.PAGE_END);
    }

    private void configureWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Double width = screen.width * 0.5;
        Double height = screen.height * 0.7;
        setSize(width.intValue(), height.intValue());
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);

        setLayout(new BorderLayout());
    }

    private void displayError(String s) {
        JOptionPane.showMessageDialog(this, s, "Error: ", JOptionPane.WARNING_MESSAGE);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == loginButton) {
            String responseCode = "";
            boolean valid = true;

            if ("Connect".equals(loginButton.getText())) {
                try {
                    String host = validHostAddress(serverInput);
                    String port = validServerPort(portInput);
                    String username = validUsername(userInput);
                    String password = validPassword(passwordInput);

                    ClientHelper helper = new ClientHelper(host, port);
                    logArea.append("Status: Logging into " + host + "\n");
                    responseCode = helper.authenticate(LOGIN, username, password);

                } catch (SocketException socket) {
                    logArea.append("Status: " + socket.getMessage() + "\n");
                    valid = false;
                } catch (UnknownHostException unknown) {
                    logArea.append("Status: " + unknown.getMessage() + "\n");
                    valid = false;
                } catch (IOException io) {
                    logArea.append("Status: " + io.getMessage() + "\n");
                    valid = false;
                } catch (InvalidArgException invalid) {
                    logArea.append("Status: " + invalid.getMessage() + "\n");
                    valid = false;
                }

                // If server request was successful
                if (valid) {
                    onResponseCode(Short.parseShort(responseCode.trim()));
                }

            } else {
                try {
                    String host = validHostAddress(serverInput);
                    String port = validServerPort(portInput);
                    String username = validUsername(userInput);
                    String password = validPassword(passwordInput);

                    ClientHelper client = new ClientHelper(host, port);
                    logArea.append("Status: Logging out of " + host + "\n");
                    responseCode = client.sendRequest(LOGOUT + username + password);

                } catch (SocketException socket) {
                    logArea.append("Status: " + socket.getMessage() + "\n");
                    valid = false;
                } catch (UnknownHostException unknown) {
                    logArea.append("Status: " + unknown.getMessage() + "\n");
                    valid = false;
                } catch (IOException io) {
                    logArea.append("Status: " + io.getMessage() + "\n");
                    valid = false;
                } catch (InvalidArgException invalid) {
                    logArea.append("Status: " + invalid.getMessage() + "\n");
                }

                // If server request was successful
                if (valid) {
                    onResponseCode(Short.parseShort(responseCode.trim()));
                }
            }
        }
    }

    private String validServerPort(JTextField portField) throws InvalidArgException {
        if(portField.getText().isEmpty()) {
            throw new InvalidArgException("Server port cannot be empty");
        }
        return "3000";
    }

    private String validHostAddress(JTextField hostField) throws InvalidArgException {
        if(hostField.getText().isEmpty()) {
            throw new InvalidArgException("Server address cannot be empty");
        }
        return "localhost";
    }

    private String validPassword(JPasswordField passwordField) throws InvalidArgException {
        if (passwordField.getText().isEmpty()) {
            return "!";
        }

        String password = passwordField.getText();
        if (Utils.fieldEndsWith(password, '!')) {
            return password;
        }
        throw new InvalidArgException("Password delimiter must be provided. It must end with '!'");
    }

    private String validUsername(JTextField usernameField) throws InvalidArgException {
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

    private void onResponseCode(short code) {
        switch (code) {
            case ResponseCode.USER_LOGGED_IN_PROCEED:
                logArea.append("Status: " + code + " Logged in\n");
                loginButton.setText("Disconnect");
                serverInput.setEnabled(false);
                userInput.setEnabled(false);
                passwordInput.setEnabled(false);
                portInput.setEnabled(false);
                break;
            case ResponseCode.USER_LOGGED_OUT_SERVICE_TERMINATED:
                logArea.append("Status: " + code + " Logged out\n");
                loginButton.setText("Connect");
                serverInput.setEnabled(true);
                userInput.setEnabled(true);
                passwordInput.setEnabled(true);
                portInput.setEnabled(true);
                break;
            case ResponseCode.INVALID_USERNAME_OR_PASSWORD:
                logArea.append("Status: " + code + " Invalid username or password\n");
                break;
            case ResponseCode.USERNAME_OK_NEED_PASSWORD:
                logArea.append("Status: " + code + " Username ok, need password\n");
                break;
            case ResponseCode.SYNTAX_ERROR_COMMAND_UNRECOGNIZED:
                logArea.append("Status: " + code + " Syntax error in parameters or arguments\n");
                break;
            default:
                LOGGER.info("Invalid response code");
        }
    }

    private class UploadFocusListener implements FocusListener {
        public void focusGained(FocusEvent e) {
            if (remoteUploadFileNameInput.getText().equalsIgnoreCase(DEFAULT_REMOTE_INPUT)) {
                remoteUploadFileNameInput.setText("");
            }
        }

        public void focusLost(FocusEvent e) {
            if ("".equalsIgnoreCase(remoteUploadFileNameInput.getText())) {
                remoteUploadFileNameInput.setText(DEFAULT_REMOTE_INPUT);
            }
        }
    }

    private class DownloadFocusListener implements FocusListener {
        public void focusGained(FocusEvent e) {
            if (remoteDownloadFileNameInput.getText().equalsIgnoreCase(DEFAULT_REMOTE_INPUT)) {
                remoteDownloadFileNameInput.setText("");
            }
        }

        public void focusLost(FocusEvent e) {
            if ("".equalsIgnoreCase(remoteDownloadFileNameInput.getText())) {
                remoteDownloadFileNameInput.setText(DEFAULT_REMOTE_INPUT);
            }
        }
    }
}
