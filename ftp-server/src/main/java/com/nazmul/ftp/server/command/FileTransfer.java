package com.nazmul.ftp.server.command;

import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.io.FileEvent;
import com.nazmul.ftp.common.logger.LoggerSingleton;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.CommonUtils;
import com.nazmul.ftp.server.session.DataPacket;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Upload or Download
 */
public class FileTransfer {

  private static final LoggerSingleton LOGGER = LoggerSingleton.INSTANCE;

  private final DataPacket dataPacket;
  private final String username;

  public FileTransfer(DataPacket dataPacket, String username) {
    this.dataPacket = dataPacket;
    this.username = username;
  }

  public void upload() throws IOException, ClassNotFoundException {
    if (isValidRequest()) {
      LOGGER.info(ProtocolCode.WRQ + " Data upload has started");
      writeDataOnServer();
    }
  }

  public void download() throws IOException, ClassNotFoundException {
    if (isValidRequest()) {
      LOGGER.info(ProtocolCode.DATA + " Data download has started");
      writeDataOnClient();
    }
  }

  private void writeDataOnServer() throws IOException, ClassNotFoundException {

    boolean dataWritten = true;

    FileEvent fileEvent = dataPacket.getSocket().receiveFilePacketsWithSender();

    try {
      CommonUtils.createAndWriteFile(fileEvent, username);

    } catch (FileNotFoundException file) {
      dataWritten = false;
      dataPacket.getSocket()
              .sendDataPackets(
                      dataPacket.getRequest().getHost(),
                      dataPacket.getRequest().getPort(),
                      String.valueOf(ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN));

      LOGGER.warn(ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN + " Writing data was unsuccessful");
      LOGGER.warn(file.getMessage());

    } catch (InvalidArgException e) {
      dataWritten = false;
      dataPacket.getSocket()
              .sendDataPackets(
                      dataPacket.getRequest().getHost(),
                      dataPacket.getRequest().getPort(),
                      e.getMessage());

      LOGGER.warn(e.getMessage() + " Writing data was unsuccessful");

    } finally {
      // If file was created successfully
      if (dataWritten) {
        LOGGER.info(ResponseCode.CLOSING_DATA_CONNECTION + " Successfully written data");
        dataPacket.getSocket()
                .sendDataPackets(
                        dataPacket.getRequest().getHost(),
                        dataPacket.getRequest().getPort(),
                        String.valueOf(ResponseCode.CLOSING_DATA_CONNECTION));
      }
    }
  }

  private void writeDataOnClient() throws IOException, ClassNotFoundException {

    writeDataOnServer();
  }

  private void processHandshake() throws IOException {
    LOGGER.info(ProtocolCode.WRQ + " handshake received");
    //write request command is received and response sent to client is okay
    dataPacket.getSocket()
            .sendDataPackets(
                    dataPacket.getRequest().getHost(),
                    dataPacket.getRequest().getPort(),
                    String.valueOf(ResponseCode.COMMAND_OKAY));
    LOGGER.info(ProtocolCode.ACK + " Acknowledgement sent");

  }

  private boolean isValidRequest() throws IOException {
    boolean valid = true;
    if (dataPacket.getRequest().getMessage().trim().endsWith(String.valueOf(ProtocolCode.ERROR))) {
      LOGGER.warn(ProtocolCode.ERROR + " Restricted data access");
      dataPacket.getSocket()
              .sendDataPackets(
                      dataPacket.getRequest().getHost(),
                      dataPacket.getRequest().getPort(),
                      String.valueOf(ResponseCode.REQUESTED_ACTION_NOT_TAKEN));
      LOGGER.warn(ResponseCode.REQUESTED_ACTION_NOT_TAKEN + " Requested action not taken");
      valid = false;
    } else {
      processHandshake();
    }
    return valid;
  }

}
