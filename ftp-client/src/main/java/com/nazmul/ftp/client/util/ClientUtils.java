package com.nazmul.ftp.client.util;

import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.util.CommonUtils;

import org.apache.log4j.Logger;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ClientUtils {

  public static final Logger LOGGER = Logger.getLogger(ClientUtils.class);

  public static String validServerPort(JTextField portField) throws InvalidArgException {

    if (portField.getText().isEmpty()) {
      throw new InvalidArgException("Server port cannot be empty");
    }
    return "3000";
  }

  public static String validHostAddress(JTextField hostField) throws InvalidArgException {

    if (hostField.getText().isEmpty()) {
      throw new InvalidArgException("Server address cannot be empty");
    }
    return "localhost";
  }

  public static String validPassword(JPasswordField passwordField) throws InvalidArgException {

    if (passwordField.getText().isEmpty()) {
      return "!";
    }

    String password = passwordField.getText();
    if (CommonUtils.fieldEndsWith(password, '!')) {
      return password;
    }
    throw new InvalidArgException("Password delimiter must be provided. It must end with '!'");
  }

  public static String validUsername(JTextField usernameField) throws InvalidArgException {

    if (usernameField.getText().isEmpty()) {
      usernameField.setText("!anonymous@");
      return "!anonymous@";
    }

    String username = usernameField.getText();
    if (CommonUtils.fieldStartsWith(username, '!') && CommonUtils.fieldEndsWith(username, '@')) {
      return username;
    }
    throw new InvalidArgException("Username delimiter is not provided. It must start with '!' and end with '@'");
  }

}
