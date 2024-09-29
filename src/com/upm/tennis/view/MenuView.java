package com.upm.tennis.view;

import java.util.Optional;

public abstract class MenuView {

  protected static String getCommandNameFromInput(String input) {
    String[] inputParts = input.split(" ");
    if (inputParts.length == 0) {
      return "Not valid command";
    }
    return inputParts[0];
  }

  protected static Optional<String> getCommandParametersFromInput(String input) {
    String[] inputParts = getCommandParts(input);
    if (inputParts.length == 0) {
      return Optional.empty();
    }
    return Optional.of(inputParts[1]);
  }

  private static String[] getCommandParts(String input) {
    String[] inputParts = input.split(" ");
    if (inputParts.length != 2) {
      return new String[0];
    }
    return inputParts;
  }

}
