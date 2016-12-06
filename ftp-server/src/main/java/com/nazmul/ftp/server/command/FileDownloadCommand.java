package com.nazmul.ftp.server.command;

import java.io.IOException;

/**
 * File download
 */
public class FileDownloadCommand implements Command {
  private final FileTransfer file;

  public FileDownloadCommand(FileTransfer file) {
    this.file = file;
  }

  @Override
  public void execute() throws IOException, ClassNotFoundException {
    file.download();
  }
}
