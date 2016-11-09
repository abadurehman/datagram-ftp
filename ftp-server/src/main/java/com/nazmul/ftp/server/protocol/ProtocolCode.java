package com.nazmul.ftp.server.protocol;

public enum ProtocolCode {
    ;
    public static final int RRQ = 100;
    public static final int WRQ = 200;
    public static final int DATA = 300;
    public static final int ACK = 400;
    public static final int ERROR = 500;
    public static final int LOGIN = 600;
    public static final int LOGOUT = 700;
}
