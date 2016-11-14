package com.nazmul.ftp.client;

import com.nazmul.ftp.client.ui.UiWindow;

import javax.swing.SwingUtilities;

public class ClientApplication {

  public static void main(String... args) {

    SwingUtilities.invokeLater(new Runnable() {

      public void run() {

        new UiWindow().setVisible(true);
      }
    });
  }
}
