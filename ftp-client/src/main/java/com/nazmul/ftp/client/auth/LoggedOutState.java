package com.nazmul.ftp.client.auth;

import com.nazmul.ftp.client.Constants;
import com.nazmul.ftp.client.proxy.ClientHelper;
import com.nazmul.ftp.client.ui.UiWindow;
import com.nazmul.ftp.client.util.ClientUtils;
import com.nazmul.ftp.common.exception.InvalidArgException;

import java.io.IOException;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Logged out state
 */
public class LoggedOutState implements AuthenticationState {

  @Override
  public void authenticate(Authentication auth) {
    auth.setState(new LoggedInState());
  }

  @Override
  public void processAuthentication(JTextField serverInput,
                                    JTextField portInput,
                                    JTextField userInput,
                                    JPasswordField passwordInput,
                                    JTextArea logArea,
                                    ClientHelper helper) {

    String responseCode = "";
    try {
      String username = ClientUtils.validUsername(userInput);
      String password = ClientUtils.validPassword(passwordInput);

      logArea.append("Status: Logging in \n");
      responseCode = helper.authenticate(Constants.LOGIN, username, password);

    } catch (IOException | InvalidArgException io) {
      logArea.append("Status: " + io.getMessage() + "\n");

    } finally {
      // successfully logged in
      if (responseCode != null && !responseCode.isEmpty()) {
        UiWindow.onResponseCode(Short.parseShort(responseCode.trim()));
      }
    }

  }
}
