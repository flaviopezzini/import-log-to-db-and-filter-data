package com.ef;

import java.time.LocalDateTime;

/**
 * A flagged IP address.
 */
public class FlaggedIpAddress {
  private final String number;
  private final int count;
  private final int threshold;
  private final LocalDateTime startTimestamp;
  private final LocalDateTime endTimestamp;
  
  /**
   * Constructor.
   */
  public FlaggedIpAddress(String number, int count, int threshold, 
      LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
    this.number = number;
    this.count = count;
    this.threshold = threshold;
    this.startTimestamp = startTimestamp;
    this.endTimestamp = endTimestamp;
  }
  
  public String getNumber() {
    return number;
  }
  
  public int getCount() {
    return count;
  }
  
  public String getReason() {
    return String.format("IP has been banned due to making over %d connections from %s to %s.", 
        threshold, startTimestamp, endTimestamp);
  }
}
