package com.nazmul.ftp.server.auth;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;

  private String password;

  private boolean authenticated;

  public User() {
    //needed for serialization
  }

  public User(String username, String password) {

    this.username = username;
    this.password = password;
  }

  public User(String username, String password, boolean authenticated) {

    this.username = username;
    this.password = password;
    this.authenticated = authenticated;
  }

  public String getUsername() {

    return username;
  }

  public void setUsername(String username) {

    this.username = username;
  }

  public String getPassword() {

    return password;
  }

  public void setPassword(String password) {

    this.password = password;
  }

  public boolean isAuthenticated() {

    return authenticated;
  }

  public void setAuthenticated(boolean authenticated) {

    this.authenticated = authenticated;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(username, user.username);
  }

  @Override
  public int hashCode() {

    return Objects.hash(username);
  }

  @Override
  public String toString() {

    final StringBuilder sb = new StringBuilder("User{");
    sb.append("username='").append(username).append('\'');
    sb.append(", authenticated=").append(authenticated);
    sb.append('}');
    return sb.toString();
  }
}
