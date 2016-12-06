package com.nazmul.ftp.server.session;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.io.FileEvent;
import com.nazmul.ftp.common.logger.LoggerSingleton;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.CommonUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

public class WRQPacket {

  private static final LoggerSingleton LOGGER = LoggerSingleton.INSTANCE;

  private Data request;

  private DataSocket socket;

  private String username;

  public WRQPacket() {

  }

  public WRQPacket(Data request, DataSocket socket, String username) {

    this.request = request;
    this.socket = socket;
    this.username = username;
  }

  public Data getRequest() {

    return request;
  }

  public void setRequest(Data request) {

    this.request = request;
  }

  public DataSocket getSocket() {

    return socket;
  }

  public void setSocket(DataSocket socket) {

    this.socket = socket;
  }

  public String getUsername() {

    return username;
  }

  public void setUsername(String username) {

    this.username = username;
  }

  public void writeDataOnServer()
          throws IOException, ClassNotFoundException {

    boolean dataWritten = true;

    FileEvent fileEvent = socket.receiveFilePacketsWithSender();
    if ("Error".equalsIgnoreCase(fileEvent.getStatus())) {
      dataWritten = false;
      socket
              .sendDataPackets(
                      request.getHost(),
                      request.getPort(),
                      String.valueOf(ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN));
    }

    try {
      CommonUtils.createAndWriteFile(fileEvent, username);

    } catch (FileNotFoundException file) {
      dataWritten = false;
      socket
              .sendDataPackets(
                      request.getHost(),
                      request.getPort(),
                      String.valueOf(ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN));

      LOGGER.warn(ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN + " Writing data was unsuccessful");
      LOGGER.warn(file.getMessage());

    } catch (InvalidArgException e) {
      dataWritten = false;
      socket.sendDataPackets(request.getHost(), request.getPort(), e.getMessage());

      LOGGER.warn(e.getMessage() + " Writing data was unsuccessful");

    } finally {
      // If file was created successfully
      if (dataWritten) {
        LOGGER.info(ResponseCode.CLOSING_DATA_CONNECTION + " Successfully written data");
        socket
                .sendDataPackets(
                        request.getHost(),
                        request.getPort(),
                        String.valueOf(ResponseCode.CLOSING_DATA_CONNECTION));
      }
    }
  }

  public void writeDataOnClient()
          throws IOException, ClassNotFoundException {

    writeDataOnServer();
  }

  public void processHandshake() throws IOException {
    LOGGER.info(ProtocolCode.WRQ + " handshake received");
    //write request command is received and response sent to client is okay
    socket
            .sendDataPackets(
                    request.getHost(),
                    request.getPort(),
                    String.valueOf(ResponseCode.COMMAND_OKAY));
    LOGGER.info(ProtocolCode.ACK + " Acknowledgement sent");

  }

  public boolean isValidRequest() throws IOException {
    boolean valid = true;
    if (request.getMessage().trim().endsWith(String.valueOf(ProtocolCode.ERROR))) {
      LOGGER.warn(ProtocolCode.ERROR + " Restricted data access");
      socket
              .sendDataPackets(
                      request.getHost(),
                      request.getPort(),
                      String.valueOf(ResponseCode.REQUESTED_ACTION_NOT_TAKEN));
      LOGGER.warn(ResponseCode.REQUESTED_ACTION_NOT_TAKEN + " Requested action not taken");
      valid = false;
    } else {
      processHandshake();
    }
    return valid;
  }
}
