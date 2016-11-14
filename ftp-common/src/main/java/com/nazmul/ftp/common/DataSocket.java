package com.nazmul.ftp.common;

import com.nazmul.ftp.common.io.FileEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.SocketException;

public class DataSocket extends AbstractSocket {

  public DataSocket() throws SocketException {

  }

  public DataSocket(int portNo) throws SocketException {

    super(portNo);
  }

  public Data receivePacketsWithSender() throws IOException {

    byte[] receiveBuffer = new byte[MAX_LEN];
    DatagramPacket datagram = new DatagramPacket(receiveBuffer, MAX_LEN);
    receive(datagram);
    Data returnVal = new Data();
    returnVal.putVal(new String(receiveBuffer), datagram.getAddress(), datagram.getPort());
    return returnVal;
  }

  public FileEvent receiveDataPacketsWithSender() throws IOException, ClassNotFoundException {

    byte[] incomingData = new byte[MAX_LEN * 1000 * 50];
    DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
    receive(incomingPacket);
    byte[] data = incomingPacket.getData();
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    ObjectInputStream is = new ObjectInputStream(in);
    return (FileEvent) is.readObject();
  }

  public Data receiveCredentials() throws IOException {

    return receivePacketsWithSender();
  }

}
