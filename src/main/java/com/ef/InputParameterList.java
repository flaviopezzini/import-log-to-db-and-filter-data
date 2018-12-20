package com.ef;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Input parameters.
 */
public class InputParameterList {
  
  private final Optional<File> accessLog;
  private final LocalDateTime startDate;
  private final Duration duration;
  private final int threshold;
  
  /**
   * Constructor.
   */
  public InputParameterList(Optional<File> accessLog, LocalDateTime startDate, 
      Duration duration, int threshold) {
    this.accessLog = accessLog;
    this.startDate = startDate;
    this.duration = duration;
    this.threshold = threshold;
  }
  
  public Optional<File> getAccessLog() {
    return accessLog;
  }
  
  public LocalDateTime getStartDate() {
    return startDate;
  }
  
  public Duration getDuration() {
    return duration;
  }
  
  public int getThreshold() {
    return threshold;
  }
}
