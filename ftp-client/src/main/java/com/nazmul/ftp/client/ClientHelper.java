package com.nazmul.ftp.client;

import com.nazmul.ftp.common.DataSocket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * This class is a module which provides the application logic
 * for an Echo client using connectionless datagram socket.
 *
 * @author M. L. Liu
 */
public class ClientHelper {
    private final DataSocket mySocket;
    private final InetAddress serverHost;
    private final int serverPort;
    private final short opcode;
    private final String username;
    private final String password;


    ClientHelper(String hostName, String portNum)
            throws SocketException, UnknownHostException {
        serverHost = InetAddress.getByName(hostName);
        serverPort = Integer.parseInt(portNum);
        username = "login";
        password = "login";
        opcode = 0;
        mySocket = new DataSocket();
    }

    ClientHelper(String host, String port, String opcode, String username, String password)
            throws SocketException, UnknownHostException {
        serverHost = InetAddress.getByName(host);
        serverPort = Integer.parseInt(port);
        this.opcode = Short.parseShort(opcode);
        this.username = username;
        this.password = password;
        mySocket = new DataSocket();
    }

    public String authenticate(String opcode, String user, String pass) throws IOException {
        mySocket.login(serverHost, serverPort, opcode, user, pass);
        return mySocket.receiveMessage();
    }

    public String sendRequest(String message)
            throws IOException {
        mySocket.sendMessage(serverHost, serverPort, message);
        return mySocket.receiveMessage();
    }

    public void done() throws SocketException {
        mySocket.close();
    }
}