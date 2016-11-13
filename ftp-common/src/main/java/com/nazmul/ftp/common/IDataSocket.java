package com.nazmul.ftp.common;

import java.io.IOException;
import java.net.InetAddress;

public interface IDataSocket {

    void sendMessage(InetAddress receiverHost,
                     int receiverPort,
                     String message) throws IOException;

    void login(InetAddress host, int port, String opcode, String username, String password) throws IOException;

    String receiveConfirmationMessage() throws IOException;

    Data receivePacketsWithSender() throws IOException;

}
