package com.nazmul.ftp.server.protocol;

import com.nazmul.ftp.server.data.*;

public class PacketFactory {

    public static UDPPacket fromReqCode(int reqcode) {
        switch (reqcode) {
            case 100:
                return new RRQDataImpl();
            case 200:
                return new WRQDataImpl();
            case 300:
                return new BinaryDataImpl();
            case 400:
                return new ACKDataImpl();
            case 500:
                return new ERRORDataImpl();
//            case 600 : return new LoginDataImpl();
//            case 700 : return new LogoutDataImpl();

        }

        return null;
    }

    public static UDPPacket fromRawData(byte... data) {
        if (data.length <= 2) {
            throw new AssertionError("Not enough data");
        }

        byte lower = data[0];
        byte upper = data[1];

        int reqcode = lower + upper;

        return fromReqCode(reqcode);
    }
}
