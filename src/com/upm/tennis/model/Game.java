package com.upm.tennis.model;

import java.util.HashMap;
import java.util.Map;

public abstract class Game {

  protected Map<Player, Integer> pointsMap;

  protected Player service;

  protected Player rest;

  private final Map<Player, Integer> lacksOfService;

  public Game() {
    this.lacksOfService = new HashMap<>();
  }

  public Player getRest() {
    return rest;
  }

  public abstract Player getWinner();

  public abstract boolean isFinished();

  protected void addPoint(Player player) {
    Integer currentScore = pointsMap.getOrDefault(player, 0);
    this.pointsMap.put(player, currentScore + 1);
  }

  protected void addLackService(Player service, Player rest) {
    int updatedLacksOfServiceCount = lacksOfService.getOrDefault(service, 0) + 1;
    lacksOfService.put(service, updatedLacksOfServiceCount);
    if (updatedLacksOfServiceCount >= 2) {
      lacksOfService.put(service, 0);
      addPoint(rest);
    }
  }

  protected Map<Player, Integer> getPointsMap() {
    return this.pointsMap;
  }

  public abstract Player getNextServicePlayerFromNonFirstPointInGame();

}
