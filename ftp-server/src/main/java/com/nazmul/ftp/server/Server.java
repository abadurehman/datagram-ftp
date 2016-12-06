package com.nazmul.ftp.server;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.DataSocketImpl;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.logger.LoggerSingleton;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.util.CommonUtils;
import com.nazmul.ftp.server.auth.User;
import com.nazmul.ftp.server.session.LoginPacket;
import com.nazmul.ftp.server.session.WRQPacket;

import java.io.IOException;

public class Server {

  private static final LoggerSingleton LOGGER = LoggerSingleton.INSTANCE;

  static User loggedInUser = new User();

  private static LoginPacket loginPacket;

  private static WRQPacket writePacket;

  public static void run(String... args) {

    try {

      final int DEFAULT_SERVER_PORT = 3000;
      DataSocket socket = new DataSocketImpl(DEFAULT_SERVER_PORT);
      LOGGER.info("FTP server ready");

      while (true) {
        Data request = socket.receiveDataPacketsWithSender();
        String message = request.getMessage();
        short opcode = CommonUtils.extractOpcode(message);

        if (loggedInUser != null && loggedInUser.isAuthenticated()) {
          loginPacket = new LoginPacket(opcode, message, request, socket);
          writePacket = new WRQPacket(request, socket, loggedInUser.getUsername());
          loggedInUser = requestByCode(opcode);

        } else if (loggedInUser != null && !loggedInUser.isAuthenticated()) {
          loginPacket = new LoginPacket(opcode, message, request, socket);
          loggedInUser = requestByCode(opcode);
        }
      }

    } catch (IOException | InvalidArgException | ClassNotFoundException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  //State pattern or Command
  private static User requestByCode(short opcode)
          throws IOException, ClassNotFoundException {

    switch (opcode) {
      case ProtocolCode.LOGIN:
        return loginPacket.processAuthentication();

      case ProtocolCode.LOGOUT:
        return loginPacket.processAuthentication();

      case ProtocolCode.WRQ:
        if (writePacket.isValidRequest()) {
          LOGGER.info(ProtocolCode.WRQ + " Data upload has started");
          writePacket.writeDataOnServer();
        }
        return loggedInUser;

      case ProtocolCode.DATA:
        if (writePacket.isValidRequest()) {
          LOGGER.info(ProtocolCode.DATA + " Data download has started");
          writePacket.writeDataOnClient();
        }
        return loggedInUser;

      default:
        return loggedInUser;
    }
  }
}
