package com.nazmul.ftp.server.user;

/**
 * RegisteredUser implements the "lowest common denominator"
 */
public class RegisteredUser extends AbstractUser {

  private String username;

  private String password;

  private boolean authenticated;

  public RegisteredUser() {

  }

  public RegisteredUser(String username, String password, boolean authenticated) {

    this.username = username;
    this.password = password;
    this.authenticated = authenticated;
  }

  @Override
  public String getUsername() {

    return username;
  }

  @Override
  public void setUsername(String username) {

    this.username = username;
  }

  @Override
  public String getPassword() {

    return password;
  }

  @Override
  public void setPassword(String password) {

    this.password = password;
  }

  @Override
  public boolean isAuthenticated() {

    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean authenticated) {

    this.authenticated = authenticated;
  }

  @Override
  public void print() {

    final StringBuilder sb = new StringBuilder("RegisteredUser{");
    sb.append("username='").append(username).append('\'');
    sb.append(", authenticated=").append(authenticated);
    sb.append('}');

    System.out.println(sb);
  }

}
