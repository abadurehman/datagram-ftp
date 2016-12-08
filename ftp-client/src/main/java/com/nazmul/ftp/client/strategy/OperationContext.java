package com.nazmul.ftp.client.strategy;

import com.nazmul.ftp.client.proxy.ClientHelper;
import com.nazmul.ftp.client.proxy.ClientHelperImpl;
import com.nazmul.ftp.client.util.ClientUtils;
import com.nazmul.ftp.common.exception.InvalidArgException;

import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Strategy context
 */
public class OperationContext {
  private final Strategy strategy;
  private JTextField serverInput;
  private JTextField portInput;
  private JTextField userInput;
  private JPasswordField passwordInput;
  private JFileChooser uploadChooser;
  private JFileChooser downloadChooser;
  private JTextArea logArea;
  private ClientHelper helper;

  public OperationContext(Strategy strategy) {

    this.strategy = strategy;
  }

  public void setServerInput(JTextField serverInput) {

    this.serverInput = serverInput;
  }

  public void setPortInput(JTextField portInput) {

    this.portInput = portInput;
  }

  public void setUserInput(JTextField userInput) {

    this.userInput = userInput;
  }

  public void setPasswordInput(JPasswordField passwordInput) {

    this.passwordInput = passwordInput;
  }

  public void setUploadChooser(JFileChooser uploadChooser) {

    this.uploadChooser = uploadChooser;
  }

  public void setDownloadChooser(JFileChooser downloadChooser) {

    this.downloadChooser = downloadChooser;
  }

  public void setLogArea(JTextArea logArea) {

    this.logArea = logArea;
  }

  public void setHelper(ClientHelper helper) {

    this.helper = helper;
  }

  public void executeOperation(){
    strategy.doOperation(serverInput, portInput, userInput, passwordInput, uploadChooser, downloadChooser, logArea, helper);
  }
}
