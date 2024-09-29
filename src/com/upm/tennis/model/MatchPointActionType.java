package com.upm.tennis.model;

public enum MatchPointActionType {
  LACK_SERVICE("lackService"),
  POINT_SERVICE("pointService"),
  REST_SERVICE("pointRest");

  private final String action;

  MatchPointActionType(String action) {
    this.action = action;
  }

  public static MatchPointActionType fromString(String action) {
    for (MatchPointActionType type : values()) {
      if (type.getAction().equals(action)) {
        return type;
      }
    }
    return null;
  }

  public String getAction() {
    return action;
  }

}
