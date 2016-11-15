package com.nazmul.ftp.client;

import com.nazmul.ftp.client.ui.UiWindow;

import javax.swing.SwingUtilities;

public class ClientApplication {

  public static void main(String... args) {

    SwingUtilities.invokeLater(() -> new UiWindow().setVisible(true));
  }
}
