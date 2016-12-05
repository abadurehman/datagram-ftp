package com.nazmul.ftp.server;

import com.nazmul.ftp.common.Data;
import com.nazmul.ftp.common.DataSocketImpl;
import com.nazmul.ftp.server.auth.User;

import java.io.IOException;

/**
 * Intent
 *  Encapsulate a request as an object, thereby letting you parametrize clients with different
 * requests, queue or log requests, and support undoable operations.
 *  Promote "invocation of a method on an object" to full object status
 *  An object-oriented callback
 */
public interface Command {
  User execute(short opcode,
               String message,
               Data request,
               DataSocketImpl socket) throws IOException;
}
