package com.nazmul.ftp.client.ui;

import com.nazmul.ftp.client.ClientHelper;
import com.nazmul.ftp.client.util.ClientUtils;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.io.FileEvent;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.CommonUtils;

import org.apache.log4j.Logger;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;

public class UiWindow extends JFrame implements ActionListener {

  static final Logger LOGGER = Logger.getLogger(UiWindow.class);

  static final String LOGIN = String.valueOf(ProtocolCode.LOGIN);

  static final String LOGOUT = String.valueOf(ProtocolCode.LOGOUT);

  static final String WRQ = String.valueOf(ProtocolCode.WRQ);

  static final String DATA = String.valueOf(ProtocolCode.DATA);

  static final String DEFAULT_REMOTE_INPUT = "File name on the server";

  private static final long serialVersionUID = 1L;

  static boolean loggedin;

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

  private ClientHelper helper;

  private String responseCode;

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

    add(top, BorderLayout.PAGE_START);

    top.add(serverLabel);
    top.add(serverInput);
    top.add(portLabel);
    top.add(portInput);
    top.add(userLabel);
    top.add(userInput);
    top.add(passwordLabel);
    top.add(passwordInput);
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
    downloadChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    downloadChooser.setApproveButtonText("Download");

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
    logArea.setEnabled(false);
    logArea.setDisabledTextColor(Color.GREEN);
    logArea.setToolTipText("System logs");

    scroll = new JScrollPane(logArea,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.setPreferredSize(new Dimension(getWidth(), 150));

    caret = (DefaultCaret) logArea.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    /** Adding all **/
    add(center, BorderLayout.CENTER);
    add(scroll, BorderLayout.PAGE_END);
  }

  private void configureWindow() {

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    addWindowListener(new CustomWindowAdapter());

    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    Double width = screen.width * 0.5;
    Double height = screen.height * 0.7;
    setSize(width.intValue(), height.intValue());
    setMinimumSize(new Dimension(850, 600));
    setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);

    setLayout(new BorderLayout());
  }

  private void displayError(String s) {

    JOptionPane.showMessageDialog(this, s, "Error: ", JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == loginButton) {
      if ("Connect".equals(loginButton.getText())) {
        LOGGER.info("Login request sent");
        login();

      } else if ("Disconnect".equals(loginButton.getText()) && loggedin) {
        LOGGER.info("Logout request sent");
        logOut();
      }

    } else if (e.getSource() == uploadChooser && loggedin) {
      try {
        uploadFile();

      } catch (InvalidArgException inval) {
        LOGGER.warn(inval.getMessage());
        logArea.append("Status: " + inval.getMessage() + "\n");
      }

    } else if (e.getSource() == downloadChooser && loggedin) {
      downloadFile();

    }
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
      case ResponseCode.CLOSING_DATA_CONNECTION:
        logArea.append("Status: " + code + " Closing data connection. Requested file action successful\n");
        break;
      case ResponseCode.COMMAND_OKAY:
        logArea.append("Status: " + code + " The requested action has been successfully completed\n");
        break;
      case ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN:
        logArea.append("Status: " + code + " File transfer was unsuccessful\n");
        break;
      default:
        LOGGER.info("Invalid response code");
    }
  }

  private void login() {

    try {
      String host = ClientUtils.validHostAddress(serverInput);
      String port = ClientUtils.validServerPort(portInput);
      String username = ClientUtils.validUsername(userInput);
      String password = ClientUtils.validPassword(passwordInput);

      helper = new ClientHelper(host, port);
      logArea.append("Status: Logging into " + host + "\n");
      responseCode = helper.authenticate(LOGIN, username, password);

    } catch (IOException | InvalidArgException io) {
      logArea.append("Status: " + io.getMessage() + "\n");
    } finally {
      // successfully logged in
      if (responseCode.trim().equals(String.valueOf(ResponseCode.USER_LOGGED_IN_PROCEED))) {
        loggedin = true;
        onResponseCode(Short.parseShort(responseCode.trim()));
      } else {
        onResponseCode(Short.parseShort(responseCode.trim()));
      }
    }

  }

  private void logOut() {

    try {
      String host = ClientUtils.validHostAddress(serverInput);
      String port = ClientUtils.validServerPort(portInput);
      String username = ClientUtils.validUsername(userInput);
      String password = ClientUtils.validPassword(passwordInput);

      logArea.append("Status: Logging out of " + host + "\n");
      responseCode = helper.sendMessageRequest(LOGOUT + username + password);

    } catch (IOException | InvalidArgException io) {
      logArea.append("Status: " + io.getMessage() + "\n");
    } finally {
      if (responseCode.trim().equals(String.valueOf(ResponseCode.USER_LOGGED_OUT_SERVICE_TERMINATED))) {
        loggedin = false;
        onResponseCode(Short.parseShort(responseCode.trim()));

        try {
          helper.done();
        } catch (SocketException e) {
          logArea.append("Status: " + e.getMessage() + "\n");
        }

      } else {
        onResponseCode(Short.parseShort(responseCode.trim()));
      }
    }
  }

  private void uploadFile() throws InvalidArgException {

    File fileSelected = uploadChooser.getSelectedFile();
    if (fileSelected != null && fileSelected.length() != 1) {
      throw new InvalidArgException("Must selected a file to proceed");
    }

    int uploadResult = uploadChooser.getDialogType();

    switch (uploadResult) {
      case JFileChooser.APPROVE_OPTION:
        sendFileToTheServer();
        break;
      case JFileChooser.CANCEL_OPTION:
        logArea.append("Status: Uploading cancelled\n");
        break;
      default:
        logArea.append("Problem uploading a file\n");
    }

  }

  private void downloadFile() {

    int downloadResult = downloadChooser.getDialogType();

    switch (downloadResult) {
      case JFileChooser.APPROVE_OPTION:
        downloadFileFromTheServer();
        break;
      case JFileChooser.CANCEL_OPTION:
        logArea.append("Status: Downloading cancelled\n");
        break;
      default:
        logArea.append("Problem");
    }
  }

  private void sendFileToTheServer() {

    try {
      String host = ClientUtils.validHostAddress(serverInput);
      String port = ClientUtils.validServerPort(portInput);
      String username = ClientUtils.validUsername(userInput);
      String password = ClientUtils.validPassword(passwordInput);

      // Send request to write data
      logArea.append("Status: Sending a request to write data\n");
      responseCode = helper.sendMessageRequest(WRQ + username + password);
      // if data write is allowed
      if (responseCode.trim().equals(String.valueOf(ResponseCode.COMMAND_OKAY))) {
        // send data
        String sourcePath = uploadChooser.getSelectedFile().getAbsolutePath();
        String destinationPath = downloadChooser.getCurrentDirectory().getAbsolutePath();
        FileEvent event = CommonUtils.getFileEvent(sourcePath, destinationPath);
        logArea.append("Status: File upload has started\n");
        responseCode = helper.uploadDataPacket(WRQ, username, password, event);
      }

    } catch (InvalidArgException | IOException inval) {
      logArea.append("Status: " + inval.getMessage() + "\n");
    } finally {
      // if file was successfully uploaded
      if (responseCode.trim().equals(String.valueOf(ResponseCode.CLOSING_DATA_CONNECTION))) {
        boolean uploaded = true;
        // If upload request was successful
        onResponseCode(Short.parseShort(responseCode.trim()));
      } else {
        onResponseCode(Short.parseShort(responseCode.trim()));
      }
    }
  }

  private void downloadFileFromTheServer() {

    try {
      String host = ClientUtils.validHostAddress(serverInput);
      String port = ClientUtils.validServerPort(portInput);
      String username = ClientUtils.validUsername(userInput);
      String password = ClientUtils.validPassword(passwordInput);

      // Send request to download data
      logArea.append("Status: Sending a request to download data\n");
      responseCode = helper.sendMessageRequest(DATA + username + password);
      // if data download is allowed
      if (responseCode.trim().equals(String.valueOf(ResponseCode.COMMAND_OKAY))) {
        // send data source
        String sourcePath = downloadChooser.getSelectedFile().getAbsolutePath();
        String destinationPath = uploadChooser.getCurrentDirectory().getAbsolutePath();
        FileEvent event = CommonUtils.getFileEvent(sourcePath, destinationPath);
        logArea.append("Status: File download has started\n");
        responseCode = helper.downloadDataPacket(DATA, username, password, event);
      }

    } catch (InvalidArgException | IOException inval) {
      logArea.append("Status: " + inval.getMessage() + "\n");
    } finally {
      // if file was successfully downloaded
      if (responseCode.trim().equals(String.valueOf(ResponseCode.CLOSING_DATA_CONNECTION))) {
        onResponseCode(Short.parseShort(responseCode.trim()));
      }
    }
  }

  private class UploadFocusListener implements FocusListener {

    @Override
    public void focusGained(FocusEvent e) {

      if (remoteUploadFileNameInput.getText().equalsIgnoreCase(DEFAULT_REMOTE_INPUT)) {
        remoteUploadFileNameInput.setText("");
      }
    }

    @Override
    public void focusLost(FocusEvent e) {

      if ("".equalsIgnoreCase(remoteUploadFileNameInput.getText())) {
        remoteUploadFileNameInput.setText(DEFAULT_REMOTE_INPUT);
      }
    }
  }

  private class DownloadFocusListener implements FocusListener {

    @Override
    public void focusGained(FocusEvent e) {

      if (remoteDownloadFileNameInput.getText().equalsIgnoreCase(DEFAULT_REMOTE_INPUT)) {
        remoteDownloadFileNameInput.setText("");
      }
    }

    @Override
    public void focusLost(FocusEvent e) {

      if ("".equalsIgnoreCase(remoteDownloadFileNameInput.getText())) {
        remoteDownloadFileNameInput.setText(DEFAULT_REMOTE_INPUT);
      }
    }
  }

  private class CustomWindowAdapter extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {

      if ("Disconnect".equals(loginButton.getText()) && loggedin) {
        LOGGER.info("Logout request sent");
        logOut();
      }
    }
  }
}
