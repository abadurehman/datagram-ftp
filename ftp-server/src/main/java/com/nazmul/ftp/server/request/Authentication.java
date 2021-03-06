package com.nazmul.ftp.server.request;

import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.logger.LoggerSingleton;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.server.user.AbstractUser;
import com.nazmul.ftp.server.user.RegisteredUser;
import com.nazmul.ftp.server.user.UserRepository;
import com.nazmul.ftp.server.model.DataPacket;

import java.io.IOException;

/**
 * Login and Logoff
 */
public class Authentication {

  private static final LoggerSingleton LOGGER = LoggerSingleton.INSTANCE;

  private final DataPacket dataPacket;
  private AbstractUser user;

  public Authentication(DataPacket dataPacket) {
    this.dataPacket = dataPacket;
    user = new RegisteredUser();
  }

  public void login() throws IOException {
    processLogin();
  }

  public void logout() throws IOException {
    processLogout();
  }

  public AbstractUser getUser() {

    return user;
  }

  private AbstractUser validateUser(String username, String password) throws InvalidArgException {

    final AbstractUser users = new UserRepository();
    RegisteredUser valUser = (RegisteredUser) users.findByUsername(username);

    if (valUser == null) {
      throw new InvalidArgException(String.valueOf(ResponseCode.INVALID_USERNAME_OR_PASSWORD));
    }

    // Authenticate user
    if (password.equals(valUser.getPassword())) {
      valUser.setAuthenticated(true);
      return valUser;
    }

    throw new InvalidArgException(String.valueOf(ResponseCode.USERNAME_OK_NEED_PASSWORD));
  }

  private void processLogin() throws IOException {

    String username = dataPacket.getUsername();
    String password = dataPacket.getPassword();

      LOGGER.info(ProtocolCode.LOGIN + " Authentication request received");
      try {
        user = validateUser(username, password);

          dataPacket.getSocket()
                  .sendDataPackets(
                          dataPacket.getRequest().getHost(),
                          dataPacket.getRequest().getPort(),
                          String.valueOf(ResponseCode.USER_LOGGED_IN_PROCEED));
          LOGGER.info(ResponseCode.USER_LOGGED_IN_PROCEED + " Authenticated");

      } catch (InvalidArgException exc) {
        dataPacket.getSocket()
                .sendDataPackets(
                        dataPacket.getRequest().getHost(),
                        dataPacket.getRequest().getPort(), exc.getMessage());
        LOGGER.info(exc.getMessage() + " Authentication unsuccessful");
      }
  }

  private void processLogout() throws IOException {
    String username = dataPacket.getUsername();
    String password = dataPacket.getPassword();

    LOGGER.info(ProtocolCode.LOGOUT + " Logout request received");

    try {
      user = validateUser(username, password);
      dataPacket.getSocket()
                .sendDataPackets(
                        dataPacket.getRequest().getHost(),
                        dataPacket.getRequest().getPort(),
                        String.valueOf(ResponseCode.USER_LOGGED_OUT_SERVICE_TERMINATED));
        LOGGER.info(ResponseCode.USER_LOGGED_OUT_SERVICE_TERMINATED + " User logged out");

        user.setAuthenticated(false);
    } catch (InvalidArgException exc) {
      dataPacket.getSocket()
              .sendDataPackets(
                      dataPacket.getRequest().getHost(),
                      dataPacket.getRequest().getPort(),
                      exc.getMessage());
      LOGGER.debug(exc.getMessage() + " Logout was unsuccessful");
    }
  }

}
