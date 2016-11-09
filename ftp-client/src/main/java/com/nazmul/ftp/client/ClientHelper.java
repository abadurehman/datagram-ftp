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

    ClientHelper(String hostName, String portNum)
            throws SocketException, UnknownHostException {
        serverHost = InetAddress.getByName(hostName);
        serverPort = Integer.parseInt(portNum);
        // instantiates a datagram socket for both sending
        // and receiving data
        mySocket = new DataSocket();
    }

    public String getEcho(String message)
            throws IOException {
        String echo = "";
        mySocket.sendMessage(serverHost, serverPort, message);
        // now receive the echo
        echo = mySocket.receiveMessage();
        return echo;
    }

    public void done() throws SocketException {
        mySocket.close();
    }
}
