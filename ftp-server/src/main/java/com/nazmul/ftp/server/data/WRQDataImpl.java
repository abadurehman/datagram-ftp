package com.nazmul.ftp.server.data;

import com.nazmul.ftp.server.io.IOEnum;
import com.nazmul.ftp.server.protocol.ProtocolCode;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class WRQDataImpl extends UDPPacket {

    private File localFile;
    private String storageName;
    private IOEnum mode;

    public WRQDataImpl() {
        super(ProtocolCode.WRQ);
    }

    public WRQDataImpl(File localFile, String storageName, IOEnum mode) {
        super(ProtocolCode.WRQ);
        this.localFile = localFile;
        this.storageName = storageName;
        this.mode = mode;
    }

    public File getLocalFile(){return localFile; }

    public String getStorageName() {
        return storageName;
    }

    public IOEnum getMode() {
        return mode;
    }

    public UDPPacket deserializePacket(byte... data) {
        //Cannot be sent by server
        return this;
    }

    @Override
    protected void fillRawPacket(DataOutputStream out) throws IOException {
        out.writeInt(reqcode);
        out.writeBytes(storageName);
        out.writeByte(0);
        out.writeBytes(mode.getValue());
        out.writeByte(0);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WRQDataImpl{");
        sb.append("localFile=").append(localFile);
        sb.append(", storageName='").append(storageName).append('\'');
        sb.append(", mode=").append(mode);
        sb.append('}');
        return sb.toString();
    }
}
