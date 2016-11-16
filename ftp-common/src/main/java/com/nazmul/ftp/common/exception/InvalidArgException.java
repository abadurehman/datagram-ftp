package com.nazmul.ftp.common.exception;

public class InvalidArgException extends DatagramException {

  private static final long serialVersionUID = 1L;

  public InvalidArgException(String message) {

    super(message);
  }
}
