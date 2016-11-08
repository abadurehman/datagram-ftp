package com.nazmul.ftp.client;

import javax.swing.*;

/**
 * FTP Client Application
 */
public class Application {
    public static void main(String... args) {
        System.out.println("FTP Client");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);

            }
        });
    }
}
