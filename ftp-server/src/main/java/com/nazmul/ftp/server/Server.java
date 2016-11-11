package com.nazmul.ftp.server;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.util.Utils;
import com.nazmul.ftp.server.auth.User;
import com.nazmul.ftp.server.session.LoginPacket;

public class Server {
    public static void run(String... args) {
        int port = 3000;    // default port
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        try {
            DataSocket serverSocket = new DataSocket(port);
            System.out.println("Status: FTP server ready");

            Data loginRequest = serverSocket.receiveCredentials();
            String message = loginRequest.getMessage();
            short opcode = Utils.extractOpcode(message);

            LoginPacket loginPacket = new LoginPacket();
            User loggedInUser = loginPacket.processAuthentication(opcode, message, loginRequest, serverSocket);

            while (loggedInUser.isAuthenticated()) {
                Data newRequest = serverSocket.receiveMessageAndSender();
                String commands = loginRequest.getMessage();
                serverSocket.sendMessage(loginRequest.getHost(), loginRequest.getPort(), commands);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
