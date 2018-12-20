package com.ef;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Encapsulates logic to retrieve application parameters and abstracts away how that is done.
 *
 */
public final class AppProperties {


  private static final String APP_PROPERTIES_FILE_NAME = "app.properties";
  private static final String ERROR_LOADING_APP_PROPERTIES_FILE =
      "Error loading App properties file.";
  private static final String APP_PROPERTIES_FILE_NOT_FOUND =
      "App properties file should be there.";

  private DatabaseParameter databaseParameter;
  private AccessLogParameter accessLogParameter;

  private static class Holder {
    private static final AppProperties INSTANCE;
    
    static {
      try {
        INSTANCE = new AppProperties();
      } catch (FileNotFoundException e) {
        throw new IllegalStateException(APP_PROPERTIES_FILE_NOT_FOUND, e);
      } catch (IOException e) {
        throw new IllegalStateException(ERROR_LOADING_APP_PROPERTIES_FILE, e);
      }
    }
  }

  /**
   * Returns the singleton instance.
   */
  public static AppProperties getInstance() {
    return Holder.INSTANCE;
  }

  private AppProperties() throws FileNotFoundException, IOException {
    String rootPath = System.getProperty("user.dir");
    String appConfigPath = String.format("%s/%s", rootPath, APP_PROPERTIES_FILE_NAME);
    Properties appProperties = new Properties();
    try (InputStream fis = Files.newInputStream(Paths.get(appConfigPath))) {
      appProperties.load(fis);
      databaseParameter = new DatabaseParameter(appProperties);
      accessLogParameter = new AccessLogParameter(appProperties);
    }
  }

  public DatabaseParameter getDatabase() {
    return databaseParameter;
  }

  public AccessLogParameter getAccessLog() {
    return accessLogParameter;
  }
}
