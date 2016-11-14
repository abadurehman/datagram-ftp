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

  public User findByUsername(String username) {

    for (User usr : registeredUsers) {
      if (usr.getUsername().equalsIgnoreCase(username)) {
        return usr;
      }
    }
    return null;
  }

  public List<User> getAllUsers() {

    return registeredUsers;
  }

  public User createUser(String username, String password) {

    User newUsr = new User(username, password, false);
    registeredUsers.add(newUsr);
    return newUsr;
  }
}
