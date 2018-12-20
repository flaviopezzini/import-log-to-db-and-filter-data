package com.ef;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Flags IP addresses with excessive accesses on the selected period.
 */
public class CompletedFlaggingOfIpAddresses {
  private static final String FINAL_RESULT_MESSAGE =
      "The following ip addresses have been flagged for having more than %d connections "
          + " in the selected start date %s and duration %s\n";
  private static final String IP_ALREADY_FLAGGED = 
      " This IP has already been flagged for this same reason\n";
  
  /**
   * Stores the flagged ip adresses on the database.
   */
  public String flag(Connection connection, InputParameterList inputParameterList) 
      throws SQLException {
    List<FlaggedIpAddress> flaggedIpAddresses =
        new FlaggedIpAddressList().getList(connection, inputParameterList.getStartDate(),
            inputParameterList.getDuration(), inputParameterList.getThreshold());
    StringBuilder retString = new StringBuilder();
    if (!flaggedIpAddresses.isEmpty()) {
      retString.append(String.format(FINAL_RESULT_MESSAGE, inputParameterList.getThreshold(),
          inputParameterList.getStartDate(), inputParameterList.getDuration()));
      for (FlaggedIpAddress flaggedIpAddress : flaggedIpAddresses) {
        retString.append(
            String.format("%s - %d. ", flaggedIpAddress.getNumber(), flaggedIpAddress.getCount()));
        boolean exists = new ExistingFlaggedIpAddress().exists(connection, flaggedIpAddress);
        if (exists) {
          retString.append(IP_ALREADY_FLAGGED);
          continue;
        }

        new StoredFlaggedIpAddress().store(connection, flaggedIpAddress);
        System.out.println();
      }
    }
    return retString.toString();
  }
}
