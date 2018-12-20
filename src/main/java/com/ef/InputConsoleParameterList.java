package com.ef;

/**
 * Input console parameter list.
 */
public class InputConsoleParameterList {
  
  private final String accessLog;
  private final String startDate;
  private final String duration;
  private final String threshold;
  
  /**
   * Constructor.
   */
  public InputConsoleParameterList(String accessLog, String startDate, String duration,
      String threshold) {
    this.accessLog = accessLog;
    this.startDate = startDate;
    this.duration = duration;
    this.threshold = threshold;
  }

  public String getAccessLog() {
    return accessLog;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getDuration() {
    return duration;
  }

  public String getThreshold() {
    return threshold;
  }
  
}
