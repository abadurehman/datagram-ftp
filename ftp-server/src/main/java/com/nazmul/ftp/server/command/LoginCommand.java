package com.nazmul.ftp.server.command;

import java.io.IOException;

/**
 * Login command
 */
public class LoginCommand implements Command {

  private final Authentication auth;

  public LoginCommand(Authentication auth) {
    this.auth = auth;
  }

  @Override
  public void execute() throws IOException {
    auth.login();
  }
}
