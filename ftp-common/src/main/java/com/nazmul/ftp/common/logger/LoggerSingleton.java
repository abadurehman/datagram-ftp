package com.nazmul.ftp.common.logger;

import org.apache.log4j.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Using Singleton design pattern for Logger
 *
 * LoggerSingleton properties:
 * control concurrent access to a shared resource
 * access to the resource will be requested from multiple, disparate parts of the system
 * there can be only one object
 *
 * Intent
 * Ensure a class has only one instance, and provide a global point of access to it.
 * Encapsulated "just-in-time initialization" or "initialization on first use".
 */
public class LoggerSingleton {

  private String LOG_EXTENSION = ".log";
  private static Logger rootLogger;
  private static String logFileName;
  private PatternLayout patternLayout;
  private static final Logger LOGGER = Logger.getLogger(LoggerSingleton.class);
  private static LoggerSingleton loggerInstance;

  private LoggerSingleton() {
    rootLogger = Logger.getRootLogger();
    rootLogger.setLevel(Level.DEBUG);
    patternLayout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - Status: %m%n");
    rootLogger.addAppender(new ConsoleAppender(patternLayout));
  }

  public static synchronized LoggerSingleton getLoggerInstance() {
    if (loggerInstance == null) {
      loggerInstance = new LoggerSingleton();
    }
    return loggerInstance;
  }

  public void setFileName(String directory, String fileName, int flag) {
    try {
      File dir = new File(directory);
      if (!(dir.isDirectory() && dir.exists())) {
        dir.mkdirs();
      }

      Date date = new Date();
      String format = "yyyy-MM-dd";
      if(flag == 1) {
        format = "yyyy-MM-dd_hh";
      }

      SimpleDateFormat dateFormat = new SimpleDateFormat(format);
      logFileName = directory + File.separator + fileName + File.separator + dateFormat.format(date)+ LOG_EXTENSION;

      RollingFileAppender fileAppender = new RollingFileAppender(patternLayout, logFileName);
      rootLogger.addAppender(fileAppender);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void info(String msg) {
    LOGGER.info(msg);
  }

  public void error(String msg) {
    LOGGER.error(msg);
  }

  public void debug(String msg) {
    LOGGER.debug(msg);
  }

  public void warn(String msg) {
    LOGGER.warn(msg);
  }

}
