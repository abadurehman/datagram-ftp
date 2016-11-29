package com.nazmul.ftp.server.session;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.io.FileEvent;
import com.nazmul.ftp.common.logger.LoggerSingleton;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.CommonUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

public class WRQPacket {

  private static final LoggerSingleton LOGGER = LoggerSingleton.INSTANCE;

  public void writeDataOnServer(Data request, DataSocket socket, String username)
          throws IOException, ClassNotFoundException {

    boolean dataWritten = true;

    FileEvent fileEvent = socket.receiveDataPacketsWithSender();
    if ("Error".equalsIgnoreCase(fileEvent.getStatus())) {
      dataWritten = false;
      socket
              .sendMessage(
                      request.getHost(),
                      request.getPort(),
                      String.valueOf(ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN));
    }

    try {
      CommonUtils.createAndWriteFile(fileEvent, username);

    } catch (FileNotFoundException file) {
      dataWritten = false;
      socket
              .sendMessage(
                      request.getHost(),
                      request.getPort(),
                      String.valueOf(ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN));

      LOGGER.warn(ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN + " Writing data was unsuccessful");
      LOGGER.warn(file.getMessage());

    } catch (InvalidArgException e) {
      dataWritten = false;
      socket.sendMessage(request.getHost(), request.getPort(), e.getMessage());

      LOGGER.warn(e.getMessage() + " Writing data was unsuccessful");

    } finally {
      // If file was created successfully
      if (dataWritten) {
        LOGGER.info(ResponseCode.CLOSING_DATA_CONNECTION + " Successfully written data");
        socket
                .sendMessage(
                        request.getHost(),
                        request.getPort(),
                        String.valueOf(ResponseCode.CLOSING_DATA_CONNECTION));
      }
    }
  }

  public void writeDataOnClient(Data request, DataSocket socket, String username)
          throws IOException, ClassNotFoundException {

    writeDataOnServer(request, socket, username);
  }
}
