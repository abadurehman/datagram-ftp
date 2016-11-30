package com.nazmul.ftp.client.state;

import com.nazmul.ftp.client.ui.UiWindow;

/**
 * Logged in State
 */
public class LoggedInState implements AuthenticationState {

  @Override
  public void authenticate(Authentication auth) {
    UiWindow.logOut();
    auth.setState(new LoggedOutState());
  }
}
