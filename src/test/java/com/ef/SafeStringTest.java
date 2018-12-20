package com.ef;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Safe String unit tests.
 */
public class SafeStringTest {

  private static final String SAFE_PART = "safepart";
  
  @Test
  public void shouldCleanNullChar() {
    String unsafe = SAFE_PART + Character.toString('\0');
    String result = SafeString.get(unsafe).toString();
    assertEquals(SAFE_PART, result);
  }
  
  @Test
  public void shouldCleanControlChars() {
    String unsafe = SAFE_PART + "\b\t\n\f\r";
    String result = SafeString.get(unsafe).toString();
    assertEquals(SAFE_PART, result);
  }
}
