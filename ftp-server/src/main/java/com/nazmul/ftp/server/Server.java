package com.nazmul.ftp.server;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.logger.LoggerSingleton;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.CommonUtils;
import com.nazmul.ftp.server.auth.User;
import com.nazmul.ftp.server.session.LoginPacket;
import com.nazmul.ftp.server.session.WRQPacket;

import java.io.IOException;

public class Server {

  private static final LoggerSingleton LOGGER = LoggerSingleton.INSTANCE;

  static LoginPacket loginPacket = new LoginPacket();

  static WRQPacket writePacket = new WRQPacket();

  static User loggedInUser = new User();

  static int DEFAULT_SERVER_PORT = 3000;

  public static void run(String... args) {

    try {
      DataSocket socket = new DataSocket(DEFAULT_SERVER_PORT);
      LOGGER.info("FTP server ready");

      while (true) {
        Data request = socket.receivePacketsWithSender();
        String message = request.getMessage();
        short opcode = CommonUtils.extractOpcode(message);

        if (loggedInUser != null && loggedInUser.isAuthenticated()) {
          loggedInUser = (User) requestByCode(opcode, message, request, socket);

        } else if (loggedInUser != null && !loggedInUser.isAuthenticated()) {
          loggedInUser = loginPacket.processAuthentication(opcode, message, request, socket);
        } else {
          socket
                  .sendMessage(
                          request.getHost(),
                          request.getPort(),
                          String.valueOf(ResponseCode.CANT_OPEN_DATA_CONNECTION));
        }
      }

    } catch (IOException | InvalidArgException | ClassNotFoundException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  //State pattern or Command
  private static Object requestByCode(short opcode, String message, Data request, DataSocket socket)
          throws IOException, ClassNotFoundException {

    switch (opcode) {
      case ProtocolCode.LOGIN:
        return loginPacket.processAuthentication(ProtocolCode.LOGIN, message, request, socket);

      case ProtocolCode.LOGOUT:
        return loginPacket.processAuthentication(ProtocolCode.LOGOUT, message, request, socket);

      case ProtocolCode.WRQ:
        LOGGER.info(ProtocolCode.WRQ + " Upload handshake received");
        //write request command is received and response sent to client is okay
        socket
                .sendMessage(
                        request.getHost(),
                        request.getPort(),
                        String.valueOf(ResponseCode.COMMAND_OKAY));
        LOGGER.info(ProtocolCode.ACK + " Acknowledgement sent");

        LOGGER.info(ProtocolCode.WRQ + " Data upload has started");
        //now write data on the server
        writePacket.writeDataOnServer(request, socket, loggedInUser.getUsername());
        return loggedInUser;

      case ProtocolCode.DATA:
        //now send data to the client
        if (request.getMessage().trim().endsWith(String.valueOf(ProtocolCode.ERROR))) {
          LOGGER.warn(ProtocolCode.ERROR + " Restricted data access");
          socket
                  .sendMessage(
                          request.getHost(),
                          request.getPort(),
                          String.valueOf(ResponseCode.REQUESTED_ACTION_NOT_TAKEN));
          LOGGER.warn(ResponseCode.REQUESTED_ACTION_NOT_TAKEN + " Requested action not taken");

        } else {
          LOGGER.info(ProtocolCode.DATA + " Download handshake received");
          //download data request command is received and response sent to client is okay
          socket
                  .sendMessage(
                          request.getHost(),
                          request.getPort(),
                          String.valueOf(ResponseCode.COMMAND_OKAY));
          LOGGER.info(ProtocolCode.ACK + " Acknowledgement sent");

          LOGGER.info(ProtocolCode.DATA + " Data upload has started");
          writePacket.writeDataOnClient(request, socket, loggedInUser.getUsername());
        }
        return loggedInUser;

      default:
        return loggedInUser;
    }
  }
}
