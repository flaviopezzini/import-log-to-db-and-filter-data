package com.ef;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Import a file into the log table.
 */
public class ImportedLogFile {
  
  private static final String ERROR_READING_ACCESS_LOG_FILE = "Error reading the access log file";
  private static final String TOTAL_ROWS_MESSAGE = "Total rows imported: %d";
  private static final String FILE_ALREADY_IMPORTED =
      "This log file has already been imported. We'll proceed with your query.\n";

  /** 
   * Imports the access log file to the database.
   */
  public String importFile(Connection connection, File accessLog, 
      AccessLogParameter accessLogParameter) 
      throws UnsafeLogLineException, InvalidLogLineException, SQLException, IOException {
    StringBuilder retString = new StringBuilder();
    int rowCounter = 0;
    boolean neverSaved = true;
    String line = null;
    try (BufferedReader reader = Files.newBufferedReader(Paths.get(accessLog.getAbsolutePath()))) {
      while ((line = reader.readLine()) != null) {
        if (neverSaved && rowCounter == 0) {
          boolean exists = new ExistingLogLine().exists(connection, line);
          if (exists) {
            neverSaved = false;
            retString.append(FILE_ALREADY_IMPORTED);
            break;
          }
        }

        ValidLogLine validLogLine = new ValidLogLine(line, accessLogParameter);
        new StoredLogLine().store(connection, validLogLine);
        rowCounter++;
      }
    } catch (IOException ioe) {
      throw new IOException(ERROR_READING_ACCESS_LOG_FILE, ioe);
    }

    retString.append(String.format(TOTAL_ROWS_MESSAGE, rowCounter));
    
    return retString.toString();
  }
  
}
