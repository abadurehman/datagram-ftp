package com.nazmul.ftp.common;

import com.nazmul.ftp.common.io.FileEvent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class DataSocket extends DatagramSocket implements IDataSocket {
  private static final int MAX_LEN = 1024;

  public DataSocket() throws SocketException {
  }

  public DataSocket(int portNo) throws SocketException {
    super(portNo);
  }

  @Override
  public void sendDataPackets(InetAddress receiverHost,
                              int receiverPort,
                              String message)
          throws IOException {

    byte[] sendBuffer = message.getBytes();
    DatagramPacket datagram =
            new DatagramPacket(sendBuffer, sendBuffer.length, receiverHost, receiverPort);
    send(datagram);
  }

  @Override
  public String receiveConfirmationMessage()
          throws IOException {

    byte[] receiveBuffer = new byte[MAX_LEN];
    DatagramPacket datagram = new DatagramPacket(receiveBuffer, MAX_LEN);
    receive(datagram);
    return new String(receiveBuffer);
  }

  @Override
  public Data receiveDataPacketsWithSender() throws IOException {

    byte[] receiveBuffer = new byte[MAX_LEN];
    DatagramPacket datagram = new DatagramPacket(receiveBuffer, MAX_LEN);
    receive(datagram);
    Data returnVal = new Data();
    returnVal.putVal(new String(receiveBuffer), datagram.getAddress(), datagram.getPort());
    return returnVal;
  }

  @Override
  public void sendCredentials(InetAddress host,
                              int port,
                              String opcode,
                              String username,
                              String password) throws IOException {

    byte[] code = opcode.getBytes();
    byte[] user = username.getBytes();
    byte[] pass = password.getBytes();
    byte[] sendBuffer = new byte[code.length + user.length + pass.length];
    System.arraycopy(code, 0, sendBuffer, 0, code.length);
    System.arraycopy(user, 0, sendBuffer, code.length, user.length);
    System.arraycopy(pass, 0, sendBuffer, code.length + user.length, pass.length);
    DatagramPacket datagram = new DatagramPacket(sendBuffer, sendBuffer.length, host, port);
    send(datagram);
  }

  @Override
  public Data receiveCredentials() throws IOException {

    return receiveDataPacketsWithSender();
  }

  @Override
  public void sendFilePackets(FileEvent event, InetAddress host, int port) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectOutputStream os = new ObjectOutputStream(outputStream);
    os.writeObject(event);
    byte[] data = outputStream.toByteArray();
    DatagramPacket sendPacket = new DatagramPacket(data, data.length, host, port);
    send(sendPacket);
  }

  @Override
  public FileEvent receiveFilePacketsWithSender() throws IOException, ClassNotFoundException {

    byte[] incomingData = new byte[MAX_LEN * 1000 * 50];
    DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
    receive(incomingPacket);
    byte[] data = incomingPacket.getData();
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    ObjectInputStream is = new ObjectInputStream(in);
    return (FileEvent) is.readObject();
  }

}
