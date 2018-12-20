package com.ef;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ValidLogLine {

  private static final int LINE_MAX_LENGTH = 400;
  private static final int DATE_MAX_LENGTH = 24;
  private static final int IP_MAX_LENGTH = 39;
  private static final int REQUEST_MAX_LENGTH = 100;
  private static final int STATUS_MAX_LENGTH = 5;
  private static final int USER_AGENT_MAX_LENGTH = 200;
  
  private static final int DATE_INDEX = 0;
  private static final int IP_INDEX = 1;
  private static final int REQUEST_INDEX = 2;
  private static final int STATUS_INDEX = 3;
  private static final int USER_AGENT_INDEX = 4;
  
  private static final String INVALID_CHARACTERS = 
      "Log line contains invalid characters and was discarded: \n%s\n%s";
  private static final String LINE_LENGTH_TOO_LONG = "This log line has a lenth exceeding %d: %s";
  private static final String INVALID_COLUMN_COUNT = "Log line does not contain 5 columns: %s";
  private static final String INVALID_COLUMN = "The column %s exceeds max length of %d: %s";
  
  private final LocalDateTime timestamp;
  private final String ipAddress;
  private final String request;
  private final String status;
  private final String userAgent;
  private final String raw;
  
  /**
   * Constructor.
   */
  public ValidLogLine(String line, AccessLogParameter accessLogParameter) 
      throws UnsafeLogLineException, InvalidLogLineException {
    String safe = SafeString.get(line).toString();
    if (!line.equals(safe)) {
      throw new UnsafeLogLineException(String.format(INVALID_CHARACTERS, line, safe));
    }
    
    if (safe.length() > LINE_MAX_LENGTH) {
      throw new InvalidLogLineException(String.format(LINE_LENGTH_TOO_LONG, LINE_MAX_LENGTH, safe));
    }

    String[] columns = safe.split(accessLogParameter.getSeparator());
    
    if (columns.length != 5) {
      throw new InvalidLogLineException(String.format(INVALID_COLUMN_COUNT, safe));
    }
    
    String dateColumn = checkColumn(columns, DATE_INDEX, "Date", DATE_MAX_LENGTH);
    DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern(accessLogParameter.getDateTimeFormat());
    timestamp = LocalDateTime.parse(dateColumn, formatter);
    ipAddress = checkColumn(columns, IP_INDEX, "Ip Address", IP_MAX_LENGTH);
    request = checkColumn(columns, REQUEST_INDEX, "Request", REQUEST_MAX_LENGTH);
    status = checkColumn(columns, STATUS_INDEX, "Status", STATUS_MAX_LENGTH);
    userAgent = checkColumn(columns, USER_AGENT_INDEX, "User Agent", USER_AGENT_MAX_LENGTH); 
    
    raw = safe;
  }

  private String checkColumn(String[] columns, int index, String name, int maxLength)
      throws InvalidLogLineException {
    String value = columns[index];
    if (value.length() > maxLength) {
      throw new InvalidLogLineException(String.format(INVALID_COLUMN, name, maxLength, value));
    }
    return value;
  }
  
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public String getRequest() {
    return request;
  }

  public String getStatus() {
    return status;
  }

  public String getUserAgent() {
    return userAgent;
  }
  
  public String getRaw() {
    return raw;
  }
}
