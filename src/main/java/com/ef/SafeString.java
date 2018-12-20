package com.ef;

/**
 * Clean up all special and null characters.
 */
public final class SafeString {
  
  /**
   * Private Constructor.
   */
  private SafeString() {}
  
  public static String get(String unsafe) {
    return unsafe.replaceAll("[^A-Za-z0-9()-:;_& =\\\\?\"|\\[\\]]", "");
  }
}
