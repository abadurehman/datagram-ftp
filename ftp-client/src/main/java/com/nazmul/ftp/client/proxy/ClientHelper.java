package com.nazmul.ftp.client.proxy;

import com.nazmul.ftp.common.io.FileEvent;

import java.io.IOException;
import java.net.SocketException;

/**
 * Proxy Pattern
 * To support plug-compatibility of wrapper and target, create an interface
 *
 */
public interface ClientHelper {

  String authenticate(String opcode, String user, String pass) throws IOException;

  String sendMessageRequest(String message) throws IOException;

  String uploadDataPacket(FileEvent event) throws IOException;

  String downloadDataPacket(FileEvent event) throws IOException;

  void closeSocket() throws SocketException;
}
