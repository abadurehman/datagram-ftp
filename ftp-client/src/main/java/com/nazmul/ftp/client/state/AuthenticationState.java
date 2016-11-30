package com.nazmul.ftp.client.state;

/**
 * Intent
 * Allow an object to alter its behavior when its internal state changes.
 * The object will appear to change its class.
 * An object-oriented state machine
 * wrapper + polymorphic wrappee + collaboration
 */
public interface AuthenticationState {

  void authenticate(Authentication auth);

}
