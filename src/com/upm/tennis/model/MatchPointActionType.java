package com.upm.tennis.model;

public enum MatchPointActionType {
  LACK_SERVICE("lackService"),
  POINT_SERVICE("pointService"),
  REST_SERVICE("pointRest");

  private final String action;

  MatchPointActionType(String action) {
    this.action = action;
  }

  public static boolean isValidAction(String action) {
    for (MatchPointActionType type : values()) {
      if (type.getAction().equals(action)) {
        return true;
      }
    }
    return false;
  }

  public static MatchPointActionType fromString(String action) {
    for (MatchPointActionType type : values()) {
      if (type.getAction().equals(action)) {
        return type;
      }
    }
    return null;
  }

  public static String getAllPointActionList() {
    StringBuilder actionList = new StringBuilder();
    for (MatchPointActionType actionType : MatchPointActionType.values()) {
      if (!actionList.isEmpty()) {
        actionList.append(",");
      }
      actionList.append(actionType.getAction());
    }
    return actionList.toString();
  }

  public String getAction() {
    return action;
  }

}
