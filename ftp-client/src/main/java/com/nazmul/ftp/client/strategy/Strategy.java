package com.nazmul.ftp.client.strategy;

import com.nazmul.ftp.client.proxy.ClientHelper;

import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Strategy Pattern
 */
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
