package com.nazmul.ftp.server.data;

import com.nazmul.ftp.server.exception.InvalidFormatException;
import com.nazmul.ftp.server.protocol.ProtocolCode;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ACKDataImpl extends UDPPacket {

    private int blockNumber;

    public ACKDataImpl() {
        super(ProtocolCode.ACK);
    }

    public ACKDataImpl(int blockNumber){
        super(ProtocolCode.ACK);
        this.blockNumber = blockNumber;
    }

    @Override
    protected void fillRawPacket(DataOutputStream out) throws IOException {
        out.writeInt(reqcode);
        out.writeInt(blockNumber);
    }

    public UDPPacket deserializePacket(byte... data) throws InvalidFormatException, IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));

        if(in.readInt() != ProtocolCode.ACK) {
            throw new InvalidFormatException("Request code is invalid");
        }
        blockNumber = in.readInt();

        in.close();

        return this;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ACKDataImpl{");
        sb.append("blockNumber=").append(blockNumber);
        sb.append('}');
        return sb.toString();
    }
}
