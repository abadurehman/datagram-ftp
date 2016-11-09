package com.nazmul.ftp.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public abstract class AbstractSocket extends DatagramSocket implements IDataSocket {
    protected static final int MAX_LEN = 100;

    protected AbstractSocket() throws SocketException {
    }

    protected AbstractSocket(int portNo) throws SocketException {
        super(portNo);
    }

    public void sendMessage(InetAddress receiverHost,
                            int receiverPort,
                            String message)
            throws IOException {
        byte[] sendBuffer = message.getBytes();
        DatagramPacket datagram = new DatagramPacket(sendBuffer, sendBuffer.length, receiverHost, receiverPort);
        send(datagram);
    }

    public String receiveMessage()
            throws IOException {
        byte[] receiveBuffer = new byte[MAX_LEN];
        DatagramPacket datagram =
                new DatagramPacket(receiveBuffer, MAX_LEN);
        receive(datagram);
        String message = new String(receiveBuffer);
        return message;
    }

}
