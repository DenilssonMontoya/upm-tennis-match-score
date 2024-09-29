package com.upm.tennis.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class TieBreak extends Game {

  public TieBreak(Player service, Player rest) {
    super();
    this.pointsMap = new HashMap<>();
    this.service = service;
    this.rest = rest;
  }

  @Override
  public Player getWinner() {
    Map<Player, Integer> pointWinsByPlayer = this.getPointsMap();
    if (pointWinsByPlayer.isEmpty()) {
      return null;
    }

    if (pointWinsByPlayer.size() == 1) {
      return this.getWinnerFromSinglePlayerWithPoints(pointWinsByPlayer);
    }
    return this.getGameWinnerFromTwoPlayersWithPoints(pointWinsByPlayer);
  }

  @Override
  public boolean isFinished() {
    Map<Player, Integer> pointWinsByPlayer = this.getPointsMap();
    return this.isThereGameWinner(pointWinsByPlayer);
  }

  @Override
  public Player getNextServicePlayerFromNonFirstPointInGame() {
    Integer pointsCountInGame = this.pointsMap.values()
        .stream()
        .reduce(Integer::sum)
        .orElse(0);

    if (pointsCountInGame % 2 == 0) {
      return this.rest;
    } else {
      return this.service;
    }
  }

  private Player getWinnerFromSinglePlayerWithPoints(Map<Player, Integer> pointWinsByPlayer) {
    Iterator<Entry<Player, Integer>> iterator = pointWinsByPlayer.entrySet().iterator();
    Entry<Player, Integer> playerEvaluated = iterator.next();
    return this.hasPlayerWonGame(playerEvaluated) ? playerEvaluated.getKey() : null;
  }

  private Player getGameWinnerFromTwoPlayersWithPoints(Map<Player, Integer> pointWinsByPlayer) {
    Iterator<Entry<Player, Integer>> iterator = pointWinsByPlayer.entrySet().iterator();
    Entry<Player, Integer> playerEvaluatedA = iterator.next();
    Entry<Player, Integer> playerEvaluatedB = iterator.next();

    if (hasPlayerWonGame(playerEvaluatedA, playerEvaluatedB.getValue())) {
      return playerEvaluatedA.getKey();
    }

    if (hasPlayerWonGame(playerEvaluatedB, playerEvaluatedA.getValue())) {
      return playerEvaluatedB.getKey();
    }
    return null;
  }

  private boolean isThereGameWinner(Map<Player, Integer> pointWinsByPlayer) {
    if (pointWinsByPlayer.isEmpty()) {
      return false;
    }
    if (pointWinsByPlayer.size() == 1) {
      Iterator<Entry<Player, Integer>> iterator = pointWinsByPlayer.entrySet().iterator();
      Entry<Player, Integer> playerEvaluated = iterator.next();
      return this.hasPlayerWonGame(playerEvaluated);
    } else {
      return this.hasAnyPlayerWonGame(pointWinsByPlayer);
    }
  }

  private boolean hasAnyPlayerWonGame(Map<Player, Integer> gameWinsByPlayer) {
    Iterator<Entry<Player, Integer>> iterator = gameWinsByPlayer.entrySet().iterator();
    Entry<Player, Integer> playerEvaluatedA = iterator.next();
    Entry<Player, Integer> playerEvaluatedB = iterator.next();

    return hasPlayerWonGame(playerEvaluatedA, playerEvaluatedB.getValue()) ||
        hasPlayerWonGame(playerEvaluatedB, playerEvaluatedA.getValue());
  }

  private boolean hasPlayerWonGame(Entry<Player, Integer> evaluatedPlayer, int opponentPlayerWins) {
    int numberOfTotalWonPoints = evaluatedPlayer.getValue() + opponentPlayerWins;
    int numberOfEvaluatedPlayerWins = evaluatedPlayer.getValue();
    int numberOfEvaluatedPlayerWinsDifference = numberOfEvaluatedPlayerWins - opponentPlayerWins;

    if (numberOfTotalWonPoints < 6) {
      return false;
    }
    if (numberOfEvaluatedPlayerWins <= opponentPlayerWins) {
      return false;
    }

    return numberOfEvaluatedPlayerWinsDifference >= 2 && numberOfEvaluatedPlayerWins >= 6;
  }

  private boolean hasPlayerWonGame(Entry<Player, Integer> playerEvaluated) {
    return this.hasPlayerWonGame(playerEvaluated, 0);
  }

}
