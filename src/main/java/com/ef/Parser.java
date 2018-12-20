package com.ef;

import java.io.File;
import java.sql.Connection;
import java.util.Optional;

public final class Parser {
  
  /**
   * Private constructor.
   */
  private Parser() {}

  /**
   * Main method.
   */
  public static void main(String[] args) {

    try {
      AppProperties appProperties = AppProperties.getInstance();
      System.out.println("Validating input parameters...");
      InputParameterList inputParameterList =
          new ValidConsoleParameterList(new ValidParameterList()).validate(args, appProperties);
      Connection connection = null;
      System.out.println("Connecting to the database...");
      connection = new DatabaseConnection().open(appProperties.getDatabase());
      try {
        Optional<File> accessLogOptional = inputParameterList.getAccessLog();
        if (accessLogOptional.isPresent()) {
          File accessLog = accessLogOptional.get();
          System.out.println("Importing the access log file to the database...");
          String importResult = 
              new ImportedLogFile().importFile(connection, accessLog, appProperties.getAccessLog());
          System.out.println(importResult);
        }
        System.out.println("Finding log lines to flag...");
        String flagResult =
            new CompletedFlaggingOfIpAddresses().flag(connection, inputParameterList);
        System.out.println(flagResult);
        System.out.println("Operation complete.");
      } finally {
        connection.close();
      }
    } catch (Throwable e) {
      new HandledException().handle(e);
    }
  }
}
