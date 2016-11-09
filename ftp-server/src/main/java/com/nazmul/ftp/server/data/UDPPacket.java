package com.nazmul.ftp.server.data;

import com.nazmul.ftp.server.io.DataSerializer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Data packet holder
 */
public abstract class UDPPacket implements DataSerializer {

    protected int reqcode;

    protected UDPPacket(int reqcode) {
        this.reqcode = reqcode;
    }

    public int getReqcode() {
        return reqcode;
    }

    public byte[] serializePacket() throws IOException {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(bytes);

        fillRawPacket(out);

        final byte[] raw = bytes.toByteArray();

        out.close();

        return raw;
    }

    protected abstract void fillRawPacket(DataOutputStream out) throws IOException;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UDPPacket{");
        sb.append("reqcode=").append(reqcode);
        sb.append('}');
        return sb.toString();
    }
}
