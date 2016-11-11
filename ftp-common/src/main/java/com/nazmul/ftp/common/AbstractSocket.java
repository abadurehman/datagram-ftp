package com.nazmul.ftp.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public abstract class AbstractSocket extends DatagramSocket implements IDataSocket {
    protected static final int MAX_LEN = 1024;

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

    public void login(InetAddress host,
                      int port,
                      String opcode,
                      String username,
                      String password)
            throws IOException {
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

}
