package com.nazmul.ftp.common.util;

import com.nazmul.ftp.common.exception.InvalidArgException;

public class Utils {

    public static short extractOpcode(String str) throws InvalidArgException {
        if (str == null || str.isEmpty()) {
            throw new InvalidArgException("Invalid string provided {Utils}");
        }

        int opIndex = str.indexOf('!');
        return Short.parseShort(str.substring(0, opIndex));
    }

    public static String extractUsername(String str) {
        int opIndex = str.indexOf('!') + 1;
        int passIndex = str.indexOf('@');
        return str.substring(opIndex, passIndex);
    }

    public static String extractPassword(String str) {
        int passIndex = str.indexOf('@');
        return str.substring(passIndex, str.length()-1);
    }
}
