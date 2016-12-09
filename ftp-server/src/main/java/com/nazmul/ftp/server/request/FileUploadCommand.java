package com.nazmul.ftp.server.request;

import java.io.IOException;

/**
 * File upload command
 */
public class FileUploadCommand implements Command {

  private final FileTransfer file;

  public FileUploadCommand(FileTransfer file) {
    this.file = file;
  }

  @Override
  public void execute() throws IOException, ClassNotFoundException {
    file.upload();
  }
}
