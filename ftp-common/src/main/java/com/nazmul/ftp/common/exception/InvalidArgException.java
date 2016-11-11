package com.nazmul.ftp.common.exception;

public class InvalidArgException extends DatagramException {

    private static final long serialVersionUID = 3833158092194742998L;

    public InvalidArgException(String message) {
        super(message);
    }
}
