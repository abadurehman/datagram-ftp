package com.nazmul.ftp.client.state;

import com.nazmul.ftp.client.ui.UiWindow;

/**
 * Logged out state
 */
public class LoggedOutState implements AuthenticationState {

  @Override
  public void authenticate(Authentication auth) {
    UiWindow.login();
    auth.setState(new LoggedInState());
  }
}
