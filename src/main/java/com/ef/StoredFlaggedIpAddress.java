package com.ef;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Store the flagged IP address to the table.
 */
public class StoredFlaggedIpAddress {

  private static final String INSERT_STATEMENT =
      "insert into flaggedipaddress (id, ip_address, reason) values (?, ?, ?)";

  /**
   * Stores the flagged IP address to the database.
   */
  public void store(Connection connection, FlaggedIpAddress flaggedIpAddress) throws SQLException {
    try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_STATEMENT)) {
      int index = 1;
      insertStatement.setString(index++, UUID.randomUUID().toString());
      insertStatement.setString(index++, flaggedIpAddress.getNumber());
      insertStatement.setString(index++, flaggedIpAddress.getReason());
      insertStatement.executeUpdate();
    }
  }
}
