package com.nazmul.ftp.server.user;

/**
 * Composite Pattern
 * <p>
 * Define a "lowest common denominator"
 */
public abstract class AbstractUser {

  public void add(AbstractUser abstractUser) {

    throw new UnsupportedOperationException();
  }

  public AbstractUser findByUsername(String username) {

    throw new UnsupportedOperationException();

  }

  public void remove(AbstractUser abstractUser) {

    throw new UnsupportedOperationException();
  }

  public String getUsername() {

    throw new UnsupportedOperationException();
  }

  public String getUserType() {

    throw new UnsupportedOperationException();
  }

  public void setUsername(String username) {

    throw new UnsupportedOperationException();
  }

  public String getPassword() {

    throw new UnsupportedOperationException();
  }

  public void setPassword(String password) {

    throw new UnsupportedOperationException();
  }

  public boolean isAuthenticated() {

    throw new UnsupportedOperationException();
  }

  public void setAuthenticated(boolean authenticated) {

    throw new UnsupportedOperationException();
  }

  public void print() {

    throw new UnsupportedOperationException();
  }
}
