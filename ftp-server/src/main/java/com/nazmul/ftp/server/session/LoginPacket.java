package com.nazmul.ftp.server.session;

import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.server.auth.User;
import com.nazmul.ftp.server.auth.service.UserServiceImpl;

public class LoginPacket {

    private final UserServiceImpl userService = new UserServiceImpl();

    public LoginPacket() {
    }

    public boolean authenticate(String username, String password) throws InvalidArgException {
        User checkUser =  userService.findByUsername(username);
        if(checkUser == null) {
            throw new InvalidArgException("User does not exist in the system");
        }

        // Authenticate user
        if (checkUser.getPassword().equals(password)) {
            checkUser.setAuthenticated(true);
            return true;
        }

        return false;
    }

}
