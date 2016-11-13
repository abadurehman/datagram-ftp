package com.nazmul.ftp.server.session;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.util.Utils;
import com.nazmul.ftp.server.auth.User;
import com.nazmul.ftp.server.auth.service.UserServiceImpl;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import org.apache.log4j.Logger;

import java.io.IOException;

public class LoginPacket {

    static final Logger LOGGER = Logger.getLogger(LoginPacket.class);
    private final UserServiceImpl userService = new UserServiceImpl();
    private boolean authenticated;

    private User validateUser(String username, String password) throws InvalidArgException {
        User checkUser = userService.findByUsername(username);
        if (checkUser == null) {
            throw new InvalidArgException(String.valueOf(ResponseCode.INVALID_USERNAME_OR_PASSWORD));
        }

        // Authenticate user
        if (password.equals(checkUser.getPassword())) {
            checkUser.setAuthenticated(true);
            return checkUser;
        }

        throw new InvalidArgException(String.valueOf(ResponseCode.USERNAME_OK_NEED_PASSWORD));
    }


    public User processAuthentication(short opcode, String message, Data request, DataSocket mySocket)
            throws IOException {

        User loggedInUser = new User();
        String username = Utils.extractUsername(message);
        String password = Utils.extractPassword(message);

        switch (opcode) {

            case ProtocolCode.LOGIN:
                LOGGER.info("Authentication request received");
                try {
                    loggedInUser = validateUser(username, password);
                    mySocket.sendMessage(request.getHost(), request.getPort(), String.valueOf(ResponseCode.USER_LOGGED_IN_PROCEED));
                    LOGGER.info("Authenticated");

                } catch (InvalidArgException exc) {
                    mySocket.sendMessage(request.getHost(), request.getPort(), exc.getMessage());
                    LOGGER.info("Authentication unsuccessful");
                }
                return loggedInUser;

            case ProtocolCode.LOGOUT:
                LOGGER.info("Disconnect request received");
                try {
                    loggedInUser = validateUser(username, password);
                    mySocket.sendMessage(request.getHost(), request.getPort(), String.valueOf(ResponseCode.USER_LOGGED_OUT_SERVICE_TERMINATED));
                    loggedInUser.setAuthenticated(false);
                    LOGGER.info("User logged out");

                } catch (InvalidArgException exc) {
                    mySocket.sendMessage(request.getHost(), request.getPort(), exc.getMessage());
                    LOGGER.debug("Disconnect unsuccessful");
                }
                return loggedInUser;

            default:
                mySocket.sendMessage(request.getHost(), request.getPort(), String.valueOf(ResponseCode.SYNTAX_ERROR_COMMAND_UNRECOGNIZED));

        }
        return null;
    }

}
