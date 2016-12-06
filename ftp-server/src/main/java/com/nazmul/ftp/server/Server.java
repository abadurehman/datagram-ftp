package com.nazmul.ftp.server;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.DataSocketImpl;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.logger.LoggerSingleton;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.util.CommonUtils;
import com.nazmul.ftp.server.command.Authentication;
import com.nazmul.ftp.server.command.FileDownloadCommand;
import com.nazmul.ftp.server.command.FileTransfer;
import com.nazmul.ftp.server.command.FileUploadCommand;
import com.nazmul.ftp.server.command.LoginCommand;
import com.nazmul.ftp.server.command.LogoutCommand;
import com.nazmul.ftp.server.command.Operation;
import com.nazmul.ftp.server.model.DataPacket;

import java.io.IOException;

public class Server {

  private static final LoggerSingleton LOGGER = LoggerSingleton.INSTANCE;

  private Operation operation;
  private Authentication auth;
  private int runCount = 0;
  private FileTransfer fileTransfer;

  public void run(String... args) {

    try {

      final int DEFAULT_SERVER_PORT = 3000;
      DataSocket socket = new DataSocketImpl(DEFAULT_SERVER_PORT);
      LOGGER.info("FTP server ready");

      while (true) {
        Data request = socket.receiveDataPacketsWithSender();
        String message = request.getMessage();
        short opcode = CommonUtils.extractOpcode(message);

        operation = new Operation();
        DataPacket dataPacket = new DataPacket(opcode, message, request, socket);

        if (runCount == 0 ) {
          auth = new Authentication(dataPacket);
          runCount++;
        } else if (auth.getUser().isAuthenticated()) {
          fileTransfer = new FileTransfer(dataPacket, auth.getUser().getUsername());
        }

        requestByCode(opcode);
      }

    } catch (IOException | InvalidArgException | ClassNotFoundException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  private void requestByCode(short opcode) throws IOException, ClassNotFoundException {

    switch (opcode) {
      case ProtocolCode.LOGIN:
        LoginCommand login = new LoginCommand(auth);
        operation.setOpcode(login);
        operation.operationCodeRequested();
        break;

      case ProtocolCode.LOGOUT:
        LogoutCommand logout = new LogoutCommand(auth);
        operation.setOpcode(logout);
        operation.operationCodeRequested();
        runCount = 0;
        break;

      case ProtocolCode.WRQ:
        FileUploadCommand fileUpload = new FileUploadCommand(fileTransfer);
        operation.setOpcode(fileUpload);
        operation.operationCodeRequested();
        break;

      case ProtocolCode.DATA:
        FileDownloadCommand fileDownload = new FileDownloadCommand(fileTransfer);
        operation.setOpcode(fileDownload);
        operation.operationCodeRequested();
        break;

      default:
        LOGGER.info("Invalid operation code");
    }
  }
}
