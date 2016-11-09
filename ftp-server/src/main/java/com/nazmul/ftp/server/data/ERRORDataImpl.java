package com.nazmul.ftp.server.data;

import com.nazmul.ftp.server.exception.InvalidFormatException;
import com.nazmul.ftp.server.protocol.ProtocolCode;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class ERRORDataImpl extends UDPPacket {

    private short errno;
    private String errorMsg;

    public ERRORDataImpl() {
        super(ProtocolCode.ERROR);
    }

    public ERRORDataImpl(short errno, String errorMsg){
        super(ProtocolCode.ERROR);

        this.errno = errno;
        this.errorMsg = errorMsg;
    }

    public short getErrno() {
        return errno;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    protected void fillRawPacket(DataOutputStream out) throws IOException {
        out.writeShort(reqcode);
        out.writeShort(errno);
        out.write(errorMsg.getBytes(Charset.forName("ASCII")));
        out.write(0);
    }

    public UDPPacket deserializePacket(byte... data) throws InvalidFormatException, IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));

        if(in.readShort() != ProtocolCode.ERROR) throw new InvalidFormatException("Request code is invalid");

        errno = in.readShort();

        byte[] errorMsgRaw = new byte[data.length - 5]; // [Op, errno, errorMsg, 0]

        System.arraycopy(data, 4, errorMsgRaw, 0, errorMsgRaw.length);
        errorMsg = new String(errorMsgRaw, Charset.forName("ASCII"));

        in.close();

        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ERRORDataImpl{");
        sb.append("errno=").append(errno);
        sb.append(", errorMsg='").append(errorMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
