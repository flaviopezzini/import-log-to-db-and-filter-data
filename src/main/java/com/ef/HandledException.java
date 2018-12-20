package com.ef;

public class HandledException {
  private static final String ERROR_HAPPENED = "Uh Oh, something bad happened: %s -> %s ";

  public void handle(Throwable e) {
    System.err.println(String.format(ERROR_HAPPENED, e.getClass().getName(), e.getMessage()));
  }
}
