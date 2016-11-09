package com.nazmul.ftp.server.data;

import com.nazmul.ftp.server.exception.InvalidFormatException;
import com.nazmul.ftp.server.io.IOEnum;
import com.nazmul.ftp.server.protocol.ProtocolCode;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class RRQDataImpl extends UDPPacket {

    private File localFile;
    private String remoteFileName;
    private IOEnum mode;

    public RRQDataImpl() {
        super(ProtocolCode.RRQ);
    }

    public RRQDataImpl(File localFile, String remoteFileName, IOEnum mode) {
        super(ProtocolCode.RRQ);

        this.localFile = localFile;
        this.remoteFileName = remoteFileName;
        this.mode = mode;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public File getLocalFile() {
        return localFile;
    }

    public IOEnum getMode() {
        return mode;
    }

    public UDPPacket deserializePacket(byte... data) throws IOException, InvalidFormatException {
        //Cannot be sent by server
        return this;
    }

    @Override
    protected void fillRawPacket(DataOutputStream out) throws IOException {
        out.writeInt(reqcode);
        out.writeBytes(remoteFileName);
        out.writeByte(0);
        out.writeBytes(mode.getValue());
        out.writeByte(0);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RRQDataImpl{");
        sb.append("remoteFileName='").append(remoteFileName).append('\'');
        sb.append(", mode=").append(mode);
        sb.append('}');
        return sb.toString();
    }
}
