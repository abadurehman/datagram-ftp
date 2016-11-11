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
        username = "test";
        password = "test";
        opcode = 0;
        // instantiates a datagram socket for both sending
        // and receiving data
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

    public String getEcho(String message)
            throws IOException {
        mySocket.sendMessage(serverHost, serverPort, message);
        // now receive the echo
        return mySocket.receiveMessage();
    }

    public void done() throws SocketException {
        mySocket.close();
    }
}
