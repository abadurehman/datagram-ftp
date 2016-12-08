package com.nazmul.ftp.client.util;

import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.util.CommonUtils;

import java.io.File;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public final class ClientUtils {

  private ClientUtils() {
  }

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

    if (passwordField.getPassword().length == 0) {
      return "!";
    }

    String password = String.copyValueOf(passwordField.getPassword());
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

  public static boolean isValidDirectory(String dirPath, String username) {

    return dirPath.equals(username);
  }

  public static boolean hasSelectedAFile(File fileSelected) throws InvalidArgException {

    if (fileSelected == null) {
      throw new InvalidArgException("Must select a file to proceed");
    }
    return true;
  }

  public static boolean doesExist(File file) throws InvalidArgException {
    if (!file.exists()) {
      throw new InvalidArgException("Selected file does not exist in the system");
    }
    return true;
  }

  public static boolean isValidMaxFileSize(File file) throws InvalidArgException {
    //validate file size does not exceed 64 kilobytes
    int maxFileSize = 64;
    long fileSize = file.length() / 1024;
    if (fileSize > maxFileSize) {
      throw new InvalidArgException("File size should not exceed " + maxFileSize + "kb");
    }
    return true;
  }


}
