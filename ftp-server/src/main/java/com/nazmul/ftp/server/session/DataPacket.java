package com.nazmul.ftp.server.session;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocket;
import com.nazmul.ftp.common.util.CommonUtils;

public class DataPacket {

  private short opcode;

  private String message;

  private Data request;

  private DataSocket socket;

  public DataPacket(short opcode, String message, Data request, DataSocket socket) {

    this.opcode = opcode;
    this.message = message;
    this.request = request;
    this.socket = socket;
  }

  public short getOpcode() {

    return opcode;
  }

  public void setOpcode(short opcode) {

    this.opcode = opcode;
  }

  public String getMessage() {

    return message;
  }

  public void setMessage(String message) {

    this.message = message;
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
    return CommonUtils.extractUsername(message);
  }

  public String getPassword() {
    return CommonUtils.extractPassword(message);

  }
}
