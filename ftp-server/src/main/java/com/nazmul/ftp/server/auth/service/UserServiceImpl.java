package com.nazmul.ftp.server.auth.service;

import com.nazmul.ftp.server.auth.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserServiceImpl implements UserService {

  private static final List<User> registeredUsers;

  static {
    registeredUsers = populateUsers();
  }

  private static List<User> populateUsers() {

    List<User> users = new CopyOnWriteArrayList<User>();
    users.add(new User("admin", "admin", false));
    users.add(new User("user", "user", false));
    users.add(new User("demo", "demo", false));
    return users;
  }

  @Override
  public User findByUsername(String username) {

    return registeredUsers
            .stream()
            .filter(usr ->
                    usr.getUsername()
                            .equals(username))
            .findFirst()
            .orElse(null);
  }

  @Override
  public List<User> getAllUsers() {

    return registeredUsers;
  }

  @Override
  public User createUser(String username, String password) {

    User newUsr = new User(username, password, false);
    registeredUsers.add(newUsr);
    return newUsr;
  }
}
