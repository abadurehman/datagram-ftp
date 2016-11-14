package com.nazmul.ftp.common.exception;

public class DatagramException extends Exception {

  private static final long serialVersionUID = 3738591933513825964L;

  public DatagramException() {

  }

  public DatagramException(String message) {

    super(message);
  }

  public DatagramException(String message, Throwable reason) {

    super(message, reason);
  }

  public DatagramException(Throwable reason) {

    super(reason);
  }

}
