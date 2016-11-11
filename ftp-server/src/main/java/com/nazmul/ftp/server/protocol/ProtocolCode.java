package com.nazmul.ftp.server.protocol;

public enum ProtocolCode {
    ;
    public static final short RRQ = 100;
    public static final short WRQ = 200;
    public static final short DATA = 300;
    public static final short ACK = 400;
    public static final short ERROR = 500;
    public static final short LOGIN = 600;
    public static final short LOGOUT = 700;
}
