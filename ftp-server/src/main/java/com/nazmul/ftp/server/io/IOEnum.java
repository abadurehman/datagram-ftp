package com.nazmul.ftp.server.io;

public enum IOEnum {
    NETASCII("netascii"),
    OCTET("octet"),
    MAIL("mail");

    private final String mode;

    IOEnum(String type) {
        mode = type;
    }

    public String getValue() {
        return mode;
    }
}
