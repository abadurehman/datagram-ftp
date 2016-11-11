package com.nazmul.ftp.server.session;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.util.Utils;
import com.nazmul.ftp.server.auth.User;
import com.nazmul.ftp.server.auth.service.UserServiceImpl;
import com.nazmul.ftp.server.protocol.ProtocolCode;

import java.io.IOException;

public class LoginPacket {

    private final UserServiceImpl userService = new UserServiceImpl();
    private boolean authenticated;

    private User validateUser(String username, String password) throws InvalidArgException {
        User checkUser = userService.findByUsername(username);
        if (checkUser == null) {
            throw new InvalidArgException("User does not exist in the system");
        }

        // Authenticate user
        if (password.equals(checkUser.getPassword())) {
            checkUser.setAuthenticated(true);
            return checkUser;
        }

        throw new InvalidArgException("Invalid password");
    }


    public User processAuthentication(short opcode, String message, Data request, DataSocket mySocket)
            throws IOException {

        User loggedInUser = new User();
        String username = Utils.extractUsername(message);
        String password = Utils.extractPassword(message);

        switch (opcode) {

            case ProtocolCode.LOGIN:
                System.out.println("Status: Authentication request received");

                try {
                    loggedInUser = validateUser(username, password);
                    mySocket.sendMessage(request.getHost(), request.getPort(), "Logged in");
                    System.out.println("Status: Authenticated");

                } catch (InvalidArgException exc) {
                    mySocket.sendMessage(request.getHost(), request.getPort(), exc.getMessage());
                    System.out.println("Status: Authentication unsuccessful");
                }
                return loggedInUser;

            case ProtocolCode.LOGOUT:
                System.out.println("Status: Disconnect request received");
                try {
                    loggedInUser = validateUser(username, password);
                    mySocket.sendMessage(request.getHost(), request.getPort(), "Logged out");
                    loggedInUser.setAuthenticated(false);
                    System.out.println("Status: User logged out");

                } catch (InvalidArgException exc) {
                    mySocket.sendMessage(request.getHost(), request.getPort(), exc.getMessage());
                    System.out.println("Status: Disconnect unsuccessful");
                }
                return loggedInUser;

            default:
                mySocket.sendMessage(request.getHost(), request.getPort(), "Invalid operation code");

        }
        return null;
    }

}
