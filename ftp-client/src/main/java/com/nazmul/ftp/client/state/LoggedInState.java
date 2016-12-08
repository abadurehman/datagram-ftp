package com.nazmul.ftp.client.state;

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
 * Logged in State
 */
public class LoggedInState implements AuthenticationState {

  @Override
  public void authenticate(Authentication auth) {
    auth.setState(new LoggedOutState());
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

      logArea.append("Status: Logging out \n");
      responseCode = helper.sendMessageRequest(Constants.LOGOUT + username + password);

    } catch (IOException | InvalidArgException io) {
      logArea.append("Status: " + io.getMessage() + "\n");

    } finally {
      if (responseCode != null && !responseCode.isEmpty()) {
        UiWindow.onResponseCode(Short.parseShort(responseCode.trim()));

      }
    }
  }
}
