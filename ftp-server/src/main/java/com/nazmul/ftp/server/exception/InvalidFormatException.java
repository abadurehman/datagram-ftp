package com.nazmul.ftp.server.exception;

/**
 * Created by nazmul on 09/11/16.
 */
public class InvalidFormatException extends DatagramException {

    public InvalidFormatException(String message) {
        super(message);
    }
}
