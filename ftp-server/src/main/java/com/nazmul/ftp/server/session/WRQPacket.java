package com.nazmul.ftp.server.session;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.io.FileEvent;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.CommonUtils;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;

public class WRQPacket {

  static final Logger LOGGER = Logger.getLogger(WRQPacket.class);

  public void writeDataOnServer(Data request, DataSocket socket, String username) throws IOException, ClassNotFoundException {

    boolean dataWritten = true;

    FileEvent fileEvent = socket.receiveDataPacketsWithSender();
    if ("Error".equalsIgnoreCase(fileEvent.getStatus())) {
      dataWritten = false;
      socket.sendMessage(request.getHost(), request.getPort(), String.valueOf(ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN));
    }

    try {
      CommonUtils.createAndWriteFile(fileEvent, username);

    } catch (FileNotFoundException file) {
      dataWritten = false;
      socket.sendMessage(request.getHost(), request.getPort(), String.valueOf(ResponseCode.REQUESTED_FILE_ACTION_NOT_TAKEN));
    }

    // If file was created successfully
    if (dataWritten) {
      socket.sendMessage(request.getHost(), request.getPort(), String.valueOf(ResponseCode.CLOSING_DATA_CONNECTION));
    }
  }

  public void writeDataOnClient(Data request, DataSocket socket, String username) throws IOException, ClassNotFoundException {

    writeDataOnServer(request, socket, username);
  }
}
