package com.nazmul.ftp.common;

import java.net.InetAddress;

public class Data {

  private InetAddress host;

  private int port;

  private String message;

  public Data() {
  }

  public Data(String message) {

    this.message = message;
  }

  public Data(String message, InetAddress host, int port) {

    this.message = message;
    this.host = host;
    this.port = port;
  }

  public void putVal(String message, InetAddress host, int port) {

    this.message = message;
    this.host = host;
    this.port = port;
  }


  public String getMessage() {

    return message;
  }

  public void setMessage(String message) {

    this.message = message;
  }

  public InetAddress getHost() {

    return host;
  }

  public void setHost(InetAddress host) {

    this.host = host;
  }

  public int getPort() {

    return port;
  }

  public void setPort(int port) {

    this.port = port;
  }

}
