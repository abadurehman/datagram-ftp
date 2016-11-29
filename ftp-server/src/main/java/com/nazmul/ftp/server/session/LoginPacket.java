package com.nazmul.ftp.server.session;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.logger.LoggerSingleton;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.CommonUtils;
import com.nazmul.ftp.server.auth.User;
import com.nazmul.ftp.server.auth.service.UserServiceImpl;

import java.io.IOException;

public class LoginPacket {

  private static final LoggerSingleton LOGGER = LoggerSingleton.getLoggerInstance();

  private final UserServiceImpl userService = new UserServiceImpl();

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

  public User processAuthentication(short opcode, String message, Data request, DataSocket dataSocket)
          throws IOException {

    User loggedInUser = new User();
    String username = CommonUtils.extractUsername(message);
    String password = CommonUtils.extractPassword(message);

    if (opcode == ProtocolCode.LOGIN) {
      LOGGER.info(ProtocolCode.LOGIN + " Authentication request received");
      try {
        loggedInUser = validateUser(username, password);
        dataSocket
                .sendMessage(
                        request.getHost(),
                        request.getPort(),
                        String.valueOf(ResponseCode.USER_LOGGED_IN_PROCEED));
        LOGGER.info(ResponseCode.USER_LOGGED_IN_PROCEED + " Authenticated");

      } catch (InvalidArgException exc) {
        dataSocket.sendMessage(request.getHost(), request.getPort(), exc.getMessage());
        LOGGER.info(exc.getMessage() + " Authentication unsuccessful");
      }
      return loggedInUser;
    }

    if (opcode == ProtocolCode.LOGOUT) {
      LOGGER.info(ProtocolCode.LOGOUT + " Logout request received");
      try {
        loggedInUser = validateUser(username, password);
        dataSocket
                .sendMessage(
                        request.getHost(),
                        request.getPort(),
                        String.valueOf(ResponseCode.USER_LOGGED_OUT_SERVICE_TERMINATED));
        loggedInUser.setAuthenticated(false);
        LOGGER.info(ResponseCode.USER_LOGGED_OUT_SERVICE_TERMINATED + " User logged out");

      } catch (InvalidArgException exc) {
        dataSocket.sendMessage(request.getHost(), request.getPort(), exc.getMessage());
        LOGGER.debug(exc.getMessage() + " Logout was unsuccessful");
      }
      return loggedInUser;
    }
    //if invalid opcode was received
    dataSocket
            .sendMessage(
                    request.getHost(),
                    request.getPort(),
                    String.valueOf(ResponseCode.SYNTAX_ERROR_COMMAND_UNRECOGNIZED));

    return null;
  }

}
