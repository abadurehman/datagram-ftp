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
        int passIndex = str.indexOf('@') + 1;
        int lastIndex = str.lastIndexOf('!');
        return str.substring(passIndex, lastIndex);
    }

    public static boolean fieldStartsWith(String str, char ch) {
        return str.charAt(0) == ch;
    }

    public static boolean fieldEndsWith(String str, char ch) {
        return str.charAt(str.length()-1) == ch;
    }
}