package com.nazmul.ftp.server;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.util.Utils;
import com.nazmul.ftp.server.session.LoginPacket;
import com.sun.media.jfxmedia.logging.Logger;

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
            DataSocket mySocket = new DataSocket(port);
            System.out.println("Status: FTP server ready");

            while (true) {
                Data request = mySocket.receiveCredentials();
                String message = request.getMessage();

                short opcode = Utils.extractOpcode(message);
                LoginPacket loginPacket = new LoginPacket();
                if (opcode == 600) {
                    System.out.println("Status: Authentication request received");
                    String username = Utils.extractUsername(message);
                    String password = Utils.extractPassword(message);
                    try {
                        loginPacket.authenticate(username, password);
                    } catch (InvalidArgException exc) {
                        mySocket.sendMessage(request.getHost(), request.getPort(), "Status : " + exc.getMessage());
                        System.out.println("Status: Authentication unsuccessful");
                    }
                    mySocket.sendMessage(request.getHost(), request.getPort(), "Logged in");
                    System.out.println("Status: Authenticated");
                }
                System.out.println("No opcode");

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
