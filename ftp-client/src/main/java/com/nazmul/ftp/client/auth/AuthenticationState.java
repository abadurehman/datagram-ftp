package com.nazmul.ftp.client.auth;

import com.nazmul.ftp.client.socket.ClientHelper;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Intent
 * Allow an object to alter its behavior when its internal state changes.
 * The object will appear to change its class.
 * An object-oriented state machine
 * wrapper + polymorphic wrappee + collaboration
 */
public interface AuthenticationState {

  void authenticate(Authentication auth);

  void processAuthentication(JTextField serverInput,
                             JTextField portInput,
                             JTextField userInput,
                             JPasswordField passwordInput,
                             JTextArea logArea,
                             ClientHelper helper);
}
