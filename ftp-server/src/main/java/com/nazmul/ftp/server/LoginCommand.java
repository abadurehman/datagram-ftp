package com.nazmul.ftp.server;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocketImpl;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.server.auth.User;
import com.nazmul.ftp.server.session.LoginPacket;

import java.io.IOException;

/**
 * Switch Statement Smell -- converting switch cases to Command pattern
 *
 * Switch-statements are not an antipattern per se, but if you're coding object oriented you should
 * consider if the use of a switch is better solved with polymorphism
 * instead of using a switch statement.
 */
public class LoginCommand implements Command {

  @Override
  public User execute(short opcode,
                      String message,
                      Data request,
                      DataSocketImpl socket) throws IOException {

    LoginPacket loginPacket = new LoginPacket();
    return loginPacket.processAuthentication(ProtocolCode.LOGIN, message, request, socket);
  }
}
