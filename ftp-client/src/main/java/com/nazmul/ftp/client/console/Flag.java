package com.nazmul.ftp.client.console;

public class Flag {

  private String host;

  private String port;

  private String opcode;

  private String user;

  private String password;

  public Flag() {

  }

  public Flag(String host, String port, String opcode, String user, String password) {

    this.host = host;
    this.port = port;
    this.opcode = opcode;
    this.user = user;
    this.password = password;
  }

  public String getHost() {

    return host;
  }

  public void setHost(String host) {

    this.host = host;
  }

  public String getPort() {

    return port;
  }

  public void setPort(String port) {

    this.port = port;
  }

  public String getOpcode() {

    return opcode;
  }

  public void setOpcode(String opcode) {

    this.opcode = opcode;
  }

  public String getUser() {

    return user;
  }

  public void setUser(String user) {

    this.user = user;
  }

  public String getPassword() {

    return password;
  }

  public void setPassword(String password) {

    this.password = password;
  }
}
