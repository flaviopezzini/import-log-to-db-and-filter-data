package com.ef;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A log line already imported.
 */
public class ExistingLogLine {

  private static final String QUERY_STATEMENT = 
      "select count(*) as total from logline where raw = ?";

  /**
   * Returns whether this log exists on the log table.
   */
  public boolean exists(Connection connection, String line) throws SQLException {
    int total = 0;
    try (PreparedStatement queryStatement = connection.prepareStatement(QUERY_STATEMENT)) {
      int index = 1;
      queryStatement.setString(index, line);
      try (ResultSet rs = queryStatement.executeQuery()) {
        if (rs.next()) {
          total = rs.getInt("total");
        }
      }
    }
    return total > 0;
  }
}
