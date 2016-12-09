package com.nazmul.ftp.client.auth;

import com.nazmul.ftp.client.socket.ClientHelper;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Authentication wrapper context
 */
public class Authentication {

  private AuthenticationState state;

  private JTextField serverInput;

  private JTextField portInput;

  private JTextField userInput;

  private JPasswordField passwordInput;

  private JTextArea logArea;

  private ClientHelper helper;

  public Authentication() {

    state = new LoggedOutState();
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

  public void setLogArea(JTextArea logArea) {

    this.logArea = logArea;
  }

  public void setHelper(ClientHelper helper) {

    this.helper = helper;
  }

  public void authenticate() {

    state.processAuthentication(serverInput, portInput, userInput, passwordInput, logArea, helper);
    state.authenticate(this);
  }

  public AuthenticationState getState() {

    return state;
  }

  public void setState(AuthenticationState state) {

    this.state = state;
  }

}
