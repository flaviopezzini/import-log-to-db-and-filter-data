package com.ef;

public class MaxFileSizeInBytes {
  private static final String INVALID_MAX_SIZE = 
      "Invalid max size for the access log file. Make sure the size is set in mega bytes.";
  private static final long MEGA_BYTE = 1024L * 1024L;
  private static final long MAX_VALID_PARAMETER = Long.MAX_VALUE / MEGA_BYTE;
  
  private final long maxSizeInBytes;
  
  /**
   * Constructor.
   */
  public MaxFileSizeInBytes(String maxSizeInMbParameter) {
    long maxSizeInMb;
    try {
      maxSizeInMb = Long.parseLong(maxSizeInMbParameter);
      if (isOverLongLimit(maxSizeInMb)) {
        throw new IllegalArgumentException(INVALID_MAX_SIZE);
      }
      
      maxSizeInBytes = MEGA_BYTE * maxSizeInMb;
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException(INVALID_MAX_SIZE, nfe);
    }
  }
  
  private boolean isOverLongLimit(long value) {
    return MAX_VALID_PARAMETER < value;
  }
  
  public long get() {
    return maxSizeInBytes;
  }
}
