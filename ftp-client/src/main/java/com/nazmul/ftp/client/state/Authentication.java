package com.nazmul.ftp.client.state;

/**
 * Authentication wrapper
 */
public class Authentication {

  private AuthenticationState state;

  public Authentication() {
    state = new LoggedOutState();
  }

  public void setState(AuthenticationState state) {
    this.state = state;
  }

  public void authenticate() {
    state.authenticate(this);
  }

  public AuthenticationState getState() {
    return state;
  }

}
