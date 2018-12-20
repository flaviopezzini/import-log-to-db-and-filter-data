package com.ef;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ValidConsoleParameterList {
  
  private static final String THRESHOLD_OPTION = "threshold";
  private static final String DURATION_OPTION = "duration";
  private static final String START_DATE_OPTION = "startDate";
  private static final String ACCESS_LOG_OPTION = "accesslog";
  private final ValidParameterList validParameterList;
  
  public ValidConsoleParameterList(ValidParameterList validParameterList) {
    super();
    this.validParameterList = validParameterList;
  }

  /**
   * Validates the input parameters.
   * @return
   */
  public InputParameterList validate(String[] args, AppProperties loadedAppProperties) 
      throws ParseException {
    Options options = new Options();
    
    options.addOption(createOption("a", ACCESS_LOG_OPTION, true, "access log file name", false));
    options.addOption(createOption("s", START_DATE_OPTION, true, "Date to start filtering", true));
    options.addOption(createOption("d", DURATION_OPTION, true, "Period to filter", true));
    options.addOption(createOption("t", THRESHOLD_OPTION, true, "Threshold to filter", true));

    CommandLineParser parser = new DefaultParser();
    HelpFormatter helpFormatter = new HelpFormatter();
    CommandLine cmd = null;
    
    try {
      cmd = parser.parse(options, args);
      String accessLog = cmd.getOptionValue(ACCESS_LOG_OPTION);
      String startDate = cmd.getOptionValue(START_DATE_OPTION);
      String duration = cmd.getOptionValue(DURATION_OPTION);
      String threshold = cmd.getOptionValue(THRESHOLD_OPTION);
      InputConsoleParameterList inputConsoleParameterList = 
          new InputConsoleParameterList(accessLog, startDate, duration, threshold);
      return validParameterList.validate(inputConsoleParameterList, loadedAppProperties);
    } catch (ParseException e) {
      helpFormatter.printHelp("Parser", options);
      throw e;
    }
  }
  
  /**
   * create an option.
   */
  private Option createOption(String shortVersion, String longVersion, boolean hasArgument, 
      String description, boolean required) {
    Option option = new Option(shortVersion, longVersion, hasArgument, description);
    option.setRequired(required);
    return option;
  }
}
