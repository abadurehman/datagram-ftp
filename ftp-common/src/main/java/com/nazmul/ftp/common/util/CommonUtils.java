package com.nazmul.ftp.common.util;

import com.nazmul.ftp.common.exception.InvalidArgException;
import com.nazmul.ftp.common.io.FileEvent;
import com.nazmul.ftp.common.protocol.ResponseCode;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CommonUtils {

  public static short extractOpcode(String str) throws InvalidArgException {

    if (str == null || str.isEmpty()) {
      throw new InvalidArgException("Invalid string provided {CommonUtils}");
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

    return str.charAt(str.length() - 1) == ch;
  }

  public static void createAndWriteFile(FileEvent fileEvent, String username) throws IOException {

    String destinationPath = fileEvent.getDestinationDirectory() + "/" + username;
    String outputFile = destinationPath + "/" + fileEvent.getFilename();
    if (!new File(destinationPath).exists()) {
      new File(destinationPath).mkdirs();
    }
    try {
      File dstFile = new File(outputFile);
      FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
      fileOutputStream.write(fileEvent.getFileData());
      fileOutputStream.flush();
      fileOutputStream.close();
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException(String.valueOf(ResponseCode.REQUESTED_ACTION_NOT_TAKEN_FILE_NOT_FOUND));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static FileEvent getFileEvent(String sourceFilePath, String destinationPath) {

    FileEvent fileEvent = new FileEvent();
    String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
    String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
    fileEvent.setDestinationDirectory(destinationPath);
    fileEvent.setFilename(fileName);
    fileEvent.setSourceDirectory(sourceFilePath);
    File file = new File(sourceFilePath);
    if (file.isFile()) {
      try {
        DataInputStream diStream = new DataInputStream(new FileInputStream(file));
        Long len = file.length();
        byte[] fileBytes = new byte[len.intValue()];
        int read = 0;
        int numRead = 0;
        while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
          read += numRead;
        }
        fileEvent.setFileSize(len);
        fileEvent.setFileData(fileBytes);
        fileEvent.setStatus("Success");
      } catch (Exception e) {
        fileEvent.setStatus("Error");
      }
    } else {
      fileEvent.setStatus("Error");
    }
    return fileEvent;
  }
}
