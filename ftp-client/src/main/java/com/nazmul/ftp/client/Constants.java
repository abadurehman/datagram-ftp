package com.nazmul.ftp.client;

import com.nazmul.ftp.common.protocol.ProtocolCode;

/**
 * Client Constants
 */
public enum Constants {
  ;
  public static final String LOGIN = String.valueOf(ProtocolCode.LOGIN);

  public static final String LOGOUT = String.valueOf(ProtocolCode.LOGOUT);

  public static final String WRQ = String.valueOf(ProtocolCode.WRQ);

  public static final String DATA = String.valueOf(ProtocolCode.DATA);

  public static final String DEFAULT_REMOTE_INPUT = "File name on the server";
}
