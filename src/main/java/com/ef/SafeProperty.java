package com.ef;

import java.util.Properties;

/**
 * Encapsulates all clean up necessary to ensure safety of application properties.
 */
public final class SafeProperty {
  
  /**
   * Private Constructor.
   */
  private SafeProperty() {}

  /**
   * Clean up the application property.
   */
  public static String get(String key, Properties appProperties) {
    return SafeString.get(appProperties.getProperty(key)).toString().trim();
  }

}
