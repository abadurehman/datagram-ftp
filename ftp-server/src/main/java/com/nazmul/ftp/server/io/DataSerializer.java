package com.nazmul.ftp.server.io;

import com.nazmul.ftp.server.data.UDPPacket;
import com.nazmul.ftp.server.exception.InvalidFormatException;

import java.io.IOException;


public interface DataSerializer {

    byte[] serializePacket() throws IOException;

    UDPPacket deserializePacket(byte... data) throws InvalidFormatException, IOException;
}
