package com.nazmul.ftp.common.protocol;

public enum ResponseCode {
    ;
    public static final short REQUESTED_ACTION_INITIATED                        = 100;
    public static final short RESTART_MARKER_REPLAY                             = 110;
    public static final short SERVICE_READY_IN_NNN_MINUTES                      = 120;
    public static final short DATA_CONNECTION_ALREADY_OPEN_XFER_STARTING        = 125;
    public static final short FILE_STATUS_OK_ABOUT_TO_OPEN_CONNECTION           = 150;

    public static final short COMMAND_OKAY                                      = 200;
    public static final short COMMAND_NOT_IMPLEMENTED_AT_THIS_SITE              = 202;
    public static final short SYSTEM_STATUS_OR_HELP_REPLY                       = 211;
    public static final short DIRECTORY_STATUS                                  = 212;
    public static final short FILE_STATUS                                       = 213;
    public static final short HELP_MESSAGE                                      = 214;
    public static final short NAME_SYSTEM_TYPE                                  = 215;
    public static final short SERVICE_READ_FOR_NEW_USER                         = 220;
    public static final short SERVICE_CLOSING_CONTROL_CONNECTION                = 221;
    public static final short DATA_CONNECTION_OPEN_NO_XFER_IN_PROGRESS          = 225;
    public static final short CLOSING_DATA_CONNECTION                           = 226;
    public static final short ENTERING_PASSIVE_MODE                             = 227;
    public static final short ENTERING_LONG_PASSIVE_MODE                        = 228;
    public static final short ENTERING_EXTENDED_PASSIVE_MODE                    = 229;
    public static final short USER_LOGGED_IN_PROCEED                            = 230;
    public static final short USER_LOGGED_OUT_SERVICE_TERMINATED                = 231;
    public static final short LOGOUT_COMMAND_NOTED_WILL_COMPLETE_WHEN_XFER_DONE = 232;
    public static final short REQUESTED_FILE_ACTION_OKAY_COMPLETED              = 250;
    public static final short PATHNAME_CREATED                                  = 257;

    public static final short USERNAME_OK_NEED_PASSWORD                         = 331;
    public static final short NEED_ACCOUNT_FOR_LOGIN                            = 332;
    public static final short REQUESTED_FILE_ACTION_PENDING_FURTHER_INFO        = 350;

    public static final short SERVICE_NOT_AVAILABLE                             = 421;
    public static final short CANT_OPEN_DATA_CONNECTION                         = 425;
    public static final short CONNECTION_CLOSED_XFER_ABORTED                    = 426;
    public static final short INVALID_USERNAME_OR_PASSWORD                      = 430;
    public static final short REQUESTED_HOST_UNAVAILABLE                        = 434;
    public static final short REQUESTED_FILE_ACTION_NOT_TAKEN                   = 450;
    public static final short REQUESTED_ACTION_ABORTED                          = 451;
    public static final short REQUESTED_ACTION_NOT_TAKEN_INSUFFICIENT_STORAGE   = 452;

    public static final short SYNTAX_ERROR_COMMAND_UNRECOGNIZED                 = 500;
    public static final short SYNTAX_ERROR_IN_PARAMETERS                        = 501;
    public static final short COMMAND_NOT_IMPLEMENTED                           = 502;
    public static final short BAD_SEQUENCE_OF_COMMANDS                          = 503;
    public static final short COMMAND_NOT_IMPLEMENTED_FOR_PARAMETER             = 504;
    public static final short NOT_LOGGED_IN                                     = 530;
    public static final short NEED_ACCOUNT_FOR_STORING_FILES                    = 532;
    public static final short REQUESTED_ACTION_NOT_TAKEN_FILE_NOT_FOUND         = 550;
    public static final short REQUESTED_ACTION_ABORTED_PAGE_TYPE_UNKNOWN        = 551;
    public static final short ACTION_ABORTED_EXCEEDED_STORAGE_ALLOCATION        = 552;
    public static final short ACTION_NOT_TAKEN_FILENAME_NOT_ALLOWED             = 553;
}
