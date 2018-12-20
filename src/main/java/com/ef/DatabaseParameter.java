package com.ef;

import java.util.Properties;

/**
 * Encapsulates the database connection parameters.
 *
 */
public class DatabaseParameter {
  
  private final String driver;
  private final String url;
  private final String user;
  private final String password;
  
  /**
   * Constructor.
   * @param appProperties - application properties
   */
  public DatabaseParameter(Properties appProperties) {
    this.driver = SafeProperty.get("database.driver", appProperties).toString();
    this.url = SafeProperty.get("database.url", appProperties).toString();
    this.user = SafeProperty.get("database.user", appProperties).toString(); 
    this.password = SafeProperty.get("database.password", appProperties).toString();
  }
  
  public String getDriver() {
    return driver;
  }
  
  public String getUrl() {
    return url;
  }
  
  public String getUser() {
    return user;
  }
  
  public String getPassword() {
    return password;
  }
}
