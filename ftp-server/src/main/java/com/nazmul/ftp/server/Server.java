package com.nazmul.ftp.server;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.util.Utils;
import com.nazmul.ftp.server.auth.User;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.server.session.LoginPacket;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.BindException;
import java.net.SocketException;

public class Server {
    static final Logger logger = Logger.getLogger(Server.class);

    public static void run(String... args) {
        int port = 3000;    // default port
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        try {
            DataSocket socket = new DataSocket(port);
            logger.info("FTP server ready");

            Data loginRequest = socket.receiveCredentials();
            String loginReqMessage = loginRequest.getMessage();
            short opcode = Utils.extractOpcode(loginReqMessage);

            LoginPacket packet = new LoginPacket();
            User loggedInUser = packet.processAuthentication(opcode, loginReqMessage, loginRequest, socket);

            while (true) {
                Data request = socket.receiveMessageAndSender();
                String message = request.getMessage();
                opcode = Utils.extractOpcode(message);

                if (loggedInUser != null && loggedInUser.isAuthenticated()) {
                    loggedInUser = requestByCode(opcode, message, request, packet, socket, loggedInUser);

                } else if (loggedInUser != null && !loggedInUser.isAuthenticated()) {
                    loggedInUser = packet.processAuthentication(opcode, message, request, socket);
                }
            }


        } catch (BindException e) {
            logger.debug("Port is not available");
        } catch (SocketException e) {
            logger.debug(e.getMessage());
        } catch (IOException e) {
            logger.debug(e.getMessage());
        } catch (InvalidArgException e) {
            logger.debug(e.getMessage());
        }
    }

    private static User requestByCode(short opcode,
                                      String message,
                                      Data request,
                                      LoginPacket packet,
                                      DataSocket socket,
                                      User user) throws IOException {
        switch(opcode) {
            case ProtocolCode.LOGOUT:
                return packet.processAuthentication(ProtocolCode.LOGOUT, message, request, socket);
            default:
                return null;
        }
    }


}
