package com.ef;

import java.util.Properties;

/**
 * Wraps application parameters related to the access log file.
 */
public class AccessLogParameter {
  
  private final String separator;
  private final String dateTimeFormat;
  private final long maxSizeInBytes;
  
  /**
   * Constructor.
   */
  public AccessLogParameter(Properties appProperties) {
    this.separator = SafeProperty.get("access.log.separator", appProperties).toString(); 
    this.dateTimeFormat = SafeProperty.get("access.log.dateTimeFormat", appProperties).toString();
    String maxSizeInMbProperty = 
        SafeProperty.get("access.log.maxSizeInMb", appProperties).toString();
    this.maxSizeInBytes = new MaxFileSizeInBytes(maxSizeInMbProperty).get();
  }
  
  public String getSeparator() {
    return separator;
  }
  
  public String getDateTimeFormat() {
    return dateTimeFormat;
  }
  
  public long getMaxSizeInBytes() {
    return maxSizeInBytes;
  }
}
