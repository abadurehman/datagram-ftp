package com.nazmul.ftp.server;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;

/**
 * This module contains the application logic of an echo server
 * which uses a connectionless datagram socket for interprocess
 * communication.
 * A command-line argument is required to specify the server port.
 *
 * @author M. L. Liu
 */
public class Server {
    public static void run(String... args) {
        int port = 3000;    // default port
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        try {
            // instantiates a datagram socket for both sending
            // and receiving data
            DataSocket mySocket = new DataSocket(port);
            System.out.println("FTP server ready.");
            while (true) {  // forever loop
                Data request = mySocket.receiveMessageAndSender();
                System.out.println("Request received");
                String message = request.getMessage();
                System.out.println("message received: " + message);
                // Now send the echo to the requestor
                mySocket.sendMessage(request.getHost(), request.getPort(), message);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
