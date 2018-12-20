package com.ef;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class StoredLogLine {

  private static final String INSERT_STATEMENT =
      "insert into logline (id, log_timestamp, ip_address, request, status, user_agent, raw) "
          + " values (?, ?, ?, ?, ?, ?, ?)";

  /**
   * Stores this log line in the database.
   */
  public void store(Connection connection, ValidLogLine validLogLine) throws SQLException {
    try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_STATEMENT)) {
      int index = 1;
      insertStatement.setString(index++, UUID.randomUUID().toString());
      insertStatement.setTimestamp(index++, Timestamp.valueOf(validLogLine.getTimestamp()));
      insertStatement.setString(index++, validLogLine.getIpAddress());
      insertStatement.setString(index++, validLogLine.getRequest());
      insertStatement.setString(index++, validLogLine.getStatus());
      insertStatement.setString(index++, validLogLine.getUserAgent());
      insertStatement.setString(index++, validLogLine.getRaw());
      insertStatement.executeUpdate();
    }
  }
}
