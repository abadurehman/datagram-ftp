package com.nazmul.ftp.server.auth.service;

import com.nazmul.ftp.server.auth.User;

import java.util.List;

public interface UserService {

    User findByUsername(String name);

    List<User> getAllUsers();

    User createUser(String name, String password);
}
