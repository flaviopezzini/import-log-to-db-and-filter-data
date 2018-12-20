package com.ef;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ValidParameterList {
  
  private static final String FILE_SIZE_EXCEEDS_MAX_SIZE =
      "Access Log file size exceeds the maximum size set in app.properties";
  private static final String ACCESS_LOG_FILE_DOES_NOT_EXIST = "Access Log file does not exist.";

  /**
   * Validates the console parameters.
   * @return
   */
  public InputParameterList validate(InputConsoleParameterList inputConsoleParameterList, 
      AppProperties loadedAppProperties) {
    String accessLogPath = inputConsoleParameterList.getAccessLog();
    Optional<File> accessLog;
    if (accessLogPath == null || accessLogPath.isEmpty()) {
      accessLog = Optional.empty();
    } else {
      File accessLogFile = new File(accessLogPath);
      if (!accessLogFile.exists()) {
        throw new IllegalArgumentException(ACCESS_LOG_FILE_DOES_NOT_EXIST);
      }
      if (loadedAppProperties.getAccessLog().getMaxSizeInBytes() < accessLogFile.length()) {
        throw new IllegalArgumentException(FILE_SIZE_EXCEEDS_MAX_SIZE);
      }
      accessLog = Optional.of(accessLogFile);
    }
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");
    LocalDateTime startDate =
        LocalDateTime.parse(inputConsoleParameterList.getStartDate(), dateTimeFormatter);
    Duration duration = Duration.valueOf(inputConsoleParameterList.getDuration().toUpperCase());
    int threshold = Integer.parseInt(inputConsoleParameterList.getThreshold());
    return new InputParameterList(accessLog, startDate, duration, threshold);
  }
}
