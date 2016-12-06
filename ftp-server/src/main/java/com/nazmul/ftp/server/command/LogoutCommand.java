package com.nazmul.ftp.server.command;

import java.io.IOException;

/**
 * Logout command
 */
public class LogoutCommand implements Command {

  private final Authentication auth;

  public LogoutCommand(Authentication auth) {
    this.auth = auth;
  }

  @Override
  public void execute() throws IOException {
    auth.logout();
  }
}
