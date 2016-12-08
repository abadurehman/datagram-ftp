package com.nazmul.ftp.server.command;

import java.io.IOException;

/**
 * Process by operation code
 */
public class Operation {

  Command opcode;

  public Operation() {
    //needed
  }

  public void setOpcode(Command command) {
    opcode = command;
  }

  public void operationCodeRequested() throws IOException, ClassNotFoundException {
    opcode.execute();
  }

}
