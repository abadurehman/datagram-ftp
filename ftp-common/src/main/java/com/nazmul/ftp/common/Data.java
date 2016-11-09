package com.nazmul.ftp.common;

import java.net.InetAddress;

/**
 * Created by nazmul on 09/11/16.
 */
public class Data {

    private String message;
    private InetAddress host;
    private int port;

    public Data() {
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
