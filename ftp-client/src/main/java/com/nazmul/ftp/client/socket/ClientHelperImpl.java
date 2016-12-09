package com.nazmul.ftp.client.socket;

import com.nazmul.ftp.common.DataSocketImpl;
import com.nazmul.ftp.common.io.FileEvent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientHelperImpl implements ClientHelper {

  private final DataSocketImpl mySocket;

  private final InetAddress serverHost;

  private final int serverPort;

  public ClientHelperImpl(String hostName, String portNum)
          throws SocketException, UnknownHostException {

    serverHost = InetAddress.getByName(hostName);
    serverPort = Integer.parseInt(portNum);
    mySocket = new DataSocketImpl();
  }


  @Override
  public String authenticate(String opcode, String user, String pass)
          throws IOException {

    mySocket.sendCredentials(serverHost, serverPort, opcode, user, pass);
    return mySocket.receiveConfirmationMessage();
  }

  @Override
  public String sendMessageRequest(String message)
          throws IOException {

    mySocket.sendDataPackets(serverHost, serverPort, message);
    return mySocket.receiveConfirmationMessage();
  }

  @Override
  public String uploadDataPacket(FileEvent event)
          throws IOException {

    mySocket.sendFilePackets(event, serverHost, serverPort);
    return mySocket.receiveConfirmationMessage();
  }

  @Override
  public String downloadDataPacket(FileEvent event)
          throws IOException {

    return uploadDataPacket(event);
  }

  @Override
  public void closeSocket() throws SocketException {

    mySocket.close();
  }
}
