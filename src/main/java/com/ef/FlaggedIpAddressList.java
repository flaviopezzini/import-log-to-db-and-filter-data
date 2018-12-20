package com.ef;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlaggedIpAddressList {

  private static final String QUERY_STATEMENT =
      "select ip_address, count(*) total from logline" + " where log_timestamp between ? and ? "
          + " group by ip_address" + " having total >= ? " + " order by 2 desc, ip_address";

  private static final String INVALID_DURATION =
      "Invalid duration while getting the list of flagged log lines. %s";

  /**
   * Returns a list of ip addresses to flag based on the input parameters.
   * 
   * @return
   */
  public List<FlaggedIpAddress> getList(Connection connection, LocalDateTime startTimestamp,
      Duration duration, int threshold) throws SQLException {
    ArrayList<FlaggedIpAddress> list = new ArrayList<>(0);

    try (PreparedStatement queryStatement = connection.prepareStatement(QUERY_STATEMENT)) {
      queryStatement.setTimestamp(1, Timestamp.valueOf(startTimestamp));

      LocalDateTime endTimestamp = null;
      if (Duration.HOURLY.equals(duration)) {
        endTimestamp = startTimestamp.plusHours(1L);
      } else if (Duration.DAILY.equals(duration)) {
        endTimestamp = startTimestamp.plusDays(1L);
      } else {
        throw new IllegalStateException(String.format(INVALID_DURATION, duration.toString()));
      }
      queryStatement.setTimestamp(2, Timestamp.valueOf(endTimestamp));
      queryStatement.setInt(3, threshold);

      try (ResultSet rs = queryStatement.executeQuery()) {
        while (rs.next()) {
          list.add(new FlaggedIpAddress(rs.getString("ip_address"), rs.getInt("total"), threshold,
              startTimestamp, endTimestamp));
        }
      }
    }

    return list;
  }

}
