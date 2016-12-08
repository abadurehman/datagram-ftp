package com.nazmul.ftp.server.command;

import java.io.IOException;

/**
 * Command pattern
 */
@FunctionalInterface
public interface Command {

  void execute() throws IOException, ClassNotFoundException;

}
