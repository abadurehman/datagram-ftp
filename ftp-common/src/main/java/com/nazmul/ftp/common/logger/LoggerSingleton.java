package com.nazmul.ftp.common.logger;

import org.apache.log4j.Logger;

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
  private static Logger logger;

  private LoggerSingleton() {
  }

  public static synchronized Logger getLogger(Class<Object> clazz) {
    if(logger != null) {
      return logger;
    }
    logger = Logger.getLogger(clazz);

    return logger;
  }
}
