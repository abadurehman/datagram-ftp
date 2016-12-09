package com.nazmul.ftp.client.transfer;

import com.nazmul.ftp.client.Constants;
import com.nazmul.ftp.client.socket.ClientHelper;
import com.nazmul.ftp.client.ui.UiWindow;
import com.nazmul.ftp.client.util.ClientUtils;
import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.io.FileEvent;
import com.nazmul.ftp.common.logger.LoggerSingleton;
import com.nazmul.ftp.common.protocol.ResponseCode;
import com.nazmul.ftp.common.util.CommonUtils;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Login operation
 */
public class UploadOperation implements Strategy {

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

      // Send request to write data
      logArea.append("Status: Sending a request to write data\n");
      responseCode = helper.sendMessageRequest(Constants.WRQ + username + password);
      // if data write is allowed
      if (responseCode.trim().equals(String.valueOf(ResponseCode.COMMAND_OKAY))) {
        LOGGER.info(ResponseCode.COMMAND_OKAY + " Ready to upload");
        // send data
        String sourcePath = uploadChooser.getSelectedFile().getAbsolutePath();
        String destinationPath = downloadChooser.getCurrentDirectory().getAbsolutePath();
        FileEvent event = CommonUtils.getFileEvent(sourcePath, destinationPath);
        logArea.append("Status: File upload has started\n");
        responseCode = helper.uploadDataPacket(event);
      }

    } catch (InvalidArgException | IOException inval) {
      logArea.append("Status: " + inval.getMessage() + "\n");
    } finally {
      // if file was successfully uploaded
      if (responseCode != null && !responseCode.isEmpty()) {
        UiWindow.onResponseCode(Short.parseShort(responseCode.trim()));
      }
    }
  }
}
