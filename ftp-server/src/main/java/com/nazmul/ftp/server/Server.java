package com.nazmul.ftp.server;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.CommonUtils;
import com.nazmul.ftp.server.auth.User;
import com.nazmul.ftp.server.session.LoginPacket;
import com.nazmul.ftp.server.session.WRQPacket;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.BindException;
import java.net.SocketException;

public class Server {

  static final Logger LOGGER = Logger.getLogger(Server.class);

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
          socket.sendMessage(request.getHost(), request.getPort(), String.valueOf(ResponseCode.CANT_OPEN_DATA_CONNECTION));
        }
      }

    } catch (IOException | InvalidArgException | ClassNotFoundException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  private static Object requestByCode(short opcode, String message, Data request, DataSocket socket)
          throws IOException, ClassNotFoundException {

    switch (opcode) {
      case ProtocolCode.LOGIN:
        return loginPacket.processAuthentication(ProtocolCode.LOGIN, message, request, socket);

      case ProtocolCode.LOGOUT:
        return loginPacket.processAuthentication(ProtocolCode.LOGOUT, message, request, socket);

      case ProtocolCode.WRQ:
        //write request command is received and response sent to client is okay
        socket.sendMessage(request.getHost(), request.getPort(), String.valueOf(ResponseCode.COMMAND_OKAY));
        //now write data on the server
        writePacket.writeDataOnServer(request, socket, loggedInUser.getUsername());
        return loggedInUser;

      case ProtocolCode.DATA:
        //download data request command is received and response sent to client is okay
        socket.sendMessage(request.getHost(), request.getPort(), String.valueOf(ResponseCode.COMMAND_OKAY));
        //now send data to the client
        writePacket.writeDataOnClient(request, socket, loggedInUser.getUsername());
        return loggedInUser;

      default:
        return loggedInUser;
    }
  }
}
