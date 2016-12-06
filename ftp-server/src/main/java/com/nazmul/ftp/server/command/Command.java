package com.nazmul.ftp.server.command;

import java.io.IOException;

/**
 * Command pattern
 */
public interface Command {

  void execute() throws IOException, ClassNotFoundException;

}
