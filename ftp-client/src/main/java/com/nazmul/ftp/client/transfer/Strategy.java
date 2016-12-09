package com.nazmul.ftp.client.transfer;

import com.nazmul.ftp.client.socket.ClientHelper;

import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Strategy Pattern
 */
@FunctionalInterface
public interface Strategy {

  void doOperation(JTextField serverInput,
                   JTextField portInput,
                   JTextField userInput,
                   JPasswordField passwordInput,
                   JFileChooser uploadChooser,
                   JFileChooser downloadChooser,
                   JTextArea logArea,
                   ClientHelper helper);

}
