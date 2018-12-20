package com.ef;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A database connection.
 */
public class DatabaseConnection {
  
  /**
   * Opens the connection to the database.
   */
  public Connection open(DatabaseParameter databaseParameter)
      throws SQLException {
    
    try {
      Class.forName(databaseParameter.getDriver()).getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new SQLException(e);
    }
    
    return DriverManager.getConnection(
        databaseParameter.getUrl(), databaseParameter.getUser(), databaseParameter.getPassword()); 
  }
}
