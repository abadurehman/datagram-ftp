package com.nazmul.ftp.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

/**
 * A subclass of DatagramSocket which contains
 * methods for sending and receiving messages
 *
 * @author M. L. Liu
 */
public class DataSocket extends AbstractSocket {

    public DataSocket() throws SocketException {
    }

    public DataSocket(int portNo) throws SocketException {
        super(portNo);
    }

    public Data receiveMessageAndSender() throws IOException {
        byte[] receiveBuffer = new byte[MAX_LEN];
        DatagramPacket datagram = new DatagramPacket(receiveBuffer, MAX_LEN);
        receive(datagram);
        // create a DatagramMessage object, to contain message
        //   received and sender's address
        Data returnVal = new Data();
        returnVal.putVal(new String(receiveBuffer), datagram.getAddress(), datagram.getPort());
        return returnVal;
    }

    public Data receiveCredentials() throws IOException {
        byte[] receiveBuffer = new byte[MAX_LEN];
        DatagramPacket datagram = new DatagramPacket(receiveBuffer, MAX_LEN);
        receive(datagram);

        Data returnVal = new Data();
        returnVal
                .putCredentials(
                        datagram.getAddress(),
                        datagram.getPort(),
                        new String(receiveBuffer));

        return returnVal;
    }


}
