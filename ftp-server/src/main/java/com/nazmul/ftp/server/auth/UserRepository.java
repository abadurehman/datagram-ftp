package com.nazmul.ftp.server.auth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * UserRepository implements the "lowest common denominator"
 */
public class UserRepository extends AbstractUser {

  private List<AbstractUser> registeredUserList = new ArrayList<>();

  private String userType;

  public UserRepository() {

    registeredUserList = populateUsers();
  }

  public UserRepository(String userType) {

    this.userType = userType;
  }

  private List<AbstractUser> populateUsers() {

    List<AbstractUser> users = new CopyOnWriteArrayList<>();
    users.add(new RegisteredUser("admin", "admin", false));
    users.add(new RegisteredUser("user", "user", false));
    users.add(new RegisteredUser("demo", "demo", false));
    return users;
  }

  @Override
  public void add(AbstractUser abstractUser) {

    registeredUserList.add(abstractUser);
  }

  @Override
  public AbstractUser findByUsername(String username) {

    return registeredUserList
            .stream()
            .filter(usr ->
                    usr.getUsername()
                            .equals(username))
            .findFirst()
            .orElse(null);
  }

  @Override
  public void remove(AbstractUser abstractUser) {

    registeredUserList.remove(abstractUser);
  }

  @Override
  public String getUserType() {

    return userType;
  }

  @Override
  public void print() {

    System.out.print("\n" + getUsername());
    System.out.println(", " + isAuthenticated());
    System.out.println("---------------------");

    Iterator<AbstractUser> iterator = registeredUserList.iterator();
    while (iterator.hasNext()) {
      AbstractUser abstractUser = iterator.next();
      abstractUser.print();
    }
  }
}
