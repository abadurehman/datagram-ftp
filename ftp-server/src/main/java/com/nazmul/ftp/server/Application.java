package com.nazmul.ftp.server;

/**
 * FTP Server Application
 */
public class Application {

  private Application() {
    //Utility classes, which are a collection of static members, are not meant to be instantiated.
  }

  public static void main(String... args) {

    new Server().run();
  }
}
