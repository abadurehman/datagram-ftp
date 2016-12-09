package com.nazmul.ftp.client.transfer;

import com.nazmul.ftp.client.Constants;
import com.nazmul.ftp.client.socket.ClientHelper;
import com.nazmul.ftp.client.ui.UiWindow;
import com.nazmul.ftp.client.util.ClientUtils;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.io.FileEvent;
import com.nazmul.ftp.common.logger.LoggerSingleton;
import com.nazmul.ftp.common.protocol.ProtocolCode;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.CommonUtils;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Logout operation
 */
public class DownloadOperation implements Strategy {

  private static final LoggerSingleton LOGGER = LoggerSingleton.INSTANCE;

  @Override
  public void doOperation(JTextField serverInput,
                          JTextField portInput,
                          JTextField userInput,
                          JPasswordField passwordInput,
                          JFileChooser uploadChooser,
                          JFileChooser downloadChooser,
                          JTextArea logArea,
                          ClientHelper helper) {

    String responseCode = "";
    try {
      String username = ClientUtils.validUsername(userInput);
      String password = ClientUtils.validPassword(passwordInput);

      String curDirName = downloadChooser.getCurrentDirectory().getName();
      String sysUsername = CommonUtils.extractUsername(username);
      boolean validDirectory = ClientUtils.isValidDirectory(curDirName, sysUsername);

      logArea.append("Status: Sending a request to download data\n");

      if (validDirectory) {
        // Send request to download data
        responseCode = helper.sendMessageRequest(Constants.DATA + username + password);

        if (responseCode.trim().equals(String.valueOf(ResponseCode.COMMAND_OKAY))) {
          // send data source
          String sourcePath = downloadChooser.getSelectedFile().getAbsolutePath();
          String destinationPath = uploadChooser.getCurrentDirectory().getAbsolutePath();
          FileEvent event = CommonUtils.getFileEvent(sourcePath, destinationPath);
          logArea.append("Status: File download has started\n");
          responseCode = helper.downloadDataPacket(event);
        }

      } else {
        LOGGER.info(ProtocolCode.ERROR + " Restricted data access requested");
        responseCode = helper.sendMessageRequest(Constants.DATA + username + password + ProtocolCode.ERROR);
      }

    } catch (InvalidArgException | IOException inval) {
      logArea.append("Status: " + inval.getMessage() + "\n");
    } finally {
      // if file was successfully downloaded
      if (responseCode != null && !responseCode.isEmpty()) {
        UiWindow.onResponseCode(Short.parseShort(responseCode.trim()));
      }
    }
  }
}
