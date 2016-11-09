package com.nazmul.ftp.server.data;


import com.nazmul.ftp.server.exception.InvalidFormatException;
import com.nazmul.ftp.server.protocol.ProtocolCode;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BinaryDataImpl extends UDPPacket implements IDataPacketAck {

    private int blockNumber;
    private byte[] data;
    private int offset;
    private int length;

    public BinaryDataImpl() {
        super(ProtocolCode.DATA);
    }

    public BinaryDataImpl(int blockNumber, byte[] data, int offset, int length) {
        super(ProtocolCode.DATA);
        this.blockNumber = blockNumber;
        this.data = data;
        this.offset = offset;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public int getBlockNumber() {
        return blockNumber;
    }


    /**
     * !!!!! WARNING : Length must be defined before deserialize packet !!!!!
     */
    public UDPPacket deserializePacket(byte... data) throws InvalidFormatException, IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));

        if (in.readInt() != ProtocolCode.DATA) throw new InvalidFormatException("Request code is invalid");

        blockNumber = in.readInt();
        this.data = new byte[data.length - 4]; //4 first bytes (reqcode / blockNumber / data)

        System.arraycopy(data, 4, this.data, 0, length);

        in.close();

        return this;
    }

    @Override
    protected void fillRawPacket(DataOutputStream out) throws IOException {
        out.writeInt(reqcode);
        out.writeInt(blockNumber);
        out.write(data, offset, length);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BinaryDataImpl{");
        sb.append("blockNumber=").append(blockNumber);
        sb.append(", length=").append(length);
        sb.append('}');
        return sb.toString();
    }
}
