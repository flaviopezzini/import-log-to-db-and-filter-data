package com.ef;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExistingFlaggedIpAddress {

  private static final String QUERY_STATEMENT = 
      "select count(*) as total from flaggedipaddress where ip_address = ? and reason = ?";

  /**
   * Returns whether this flagged log line already exists.
   * @return
   */
  public boolean exists(Connection connection, FlaggedIpAddress flaggedIpAddress) 
      throws SQLException {
    int total = 0;
    
    try (PreparedStatement queryStatement = connection.prepareStatement(QUERY_STATEMENT)) {
      int index = 1;
      queryStatement.setString(index++, flaggedIpAddress.getNumber());
      queryStatement.setString(index++, flaggedIpAddress.getReason());
      try (ResultSet rs = queryStatement.executeQuery()) {
        if (rs.next()) {
          total = rs.getInt("total");
        }
      }
    }
    
    return total > 0;
  }
}
