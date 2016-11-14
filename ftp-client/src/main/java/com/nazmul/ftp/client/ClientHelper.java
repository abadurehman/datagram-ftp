package com.nazmul.ftp.client;

import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.io.FileEvent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientHelper {
    private final DataSocket mySocket;
    private final InetAddress serverHost;
    private final int serverPort;

    public ClientHelper(String hostName, String portNum)
            throws SocketException, UnknownHostException {
        serverHost = InetAddress.getByName(hostName);
        serverPort = Integer.parseInt(portNum);
        mySocket = new DataSocket();
    }

    public String authenticate(String opcode, String user, String pass) throws IOException {
        mySocket.login(serverHost, serverPort, opcode, user, pass);
        return mySocket.receiveConfirmationMessage();
    }

    public String sendMessageRequest(String message)
            throws IOException {
        mySocket.sendMessage(serverHost, serverPort, message);
        return mySocket.receiveConfirmationMessage();
    }

    public String uploadDataPacket(String opcode, String user, String pass, FileEvent event) throws IOException {
        mySocket.sendPacket(event, serverHost, serverPort);
        return mySocket.receiveConfirmationMessage();
    }

    public String downloadDataPacket(String opcode, String user, String pass, FileEvent event) throws IOException {
        return uploadDataPacket(opcode, user, pass, event);
    }

    public void done() throws SocketException {
        mySocket.close();
    }
}
