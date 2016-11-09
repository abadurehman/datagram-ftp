package com.nazmul.ftp.server.exception;

public class DatagramException extends Exception {

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
