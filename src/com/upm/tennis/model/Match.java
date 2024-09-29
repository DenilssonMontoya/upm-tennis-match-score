package com.upm.tennis.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Match {

  private final Integer id;

  private final List<Set> sets;

  private final List<Player> players;

  private final ScoreBoard scoreBoard;

  private final LocalDate date;

  private Integer numberOfSets;

  private Referee referee;

  public Match(Integer id, int numberOfSets, List<Player> players, Referee referee) {
    this.id = id;
    this.sets = new ArrayList<>();
    this.players = players;
    this.numberOfSets = numberOfSets;
    this.referee = referee;
    this.scoreBoard = new ScoreBoard();
    this.date = LocalDate.now();
  }

  private void addPointService(Set currentSet, Player service, Player rest) {
    Objects.requireNonNull(currentSet);
    currentSet.addPointService(service, rest);
  }

  private void addPointRest(Set currentSet, Player service, Player rest) {
    Objects.requireNonNull(currentSet);
    currentSet.addPointRest(service, rest);
  }

  private void addLackService(Set currentSet, Player service, Player rest) {
    Objects.requireNonNull(currentSet);
    currentSet.addLackService(service, rest);
  }

  public Set getCurrentSet() {
    for (Set set : sets) {
      if (!set.isFinished()) {
        return set;
      }
    }
    return null;
  }

  public void processAction(String action) {
    Objects.requireNonNull(action);
    MatchPointActionType actionType = MatchPointActionType.fromString(action);
    Objects.requireNonNull(actionType);
    Set currentSet = getCurrentOrNewSet();
    Player service = this.getNextServicePlayer();
    Player rest = this.getOpponentPlayer(service);
    switch (actionType) {
      case LACK_SERVICE -> this.addLackService(currentSet, service, rest);
      case POINT_SERVICE -> this.addPointService(currentSet, service, rest);
      case REST_SERVICE -> this.addPointRest(currentSet, service, rest);
    }
  }

  public ScoreBoard getScoreBoard() {
    return scoreBoard;
  }

  public LocalDate getDate() {
    return date;
  }

  private Set getCurrentOrNewSet() {
    Set currentSet = this.getCurrentSet();
    if (currentSet != null) {
      return currentSet;
    }
    return addNewSet();
  }

  private Set addNewSet() {
    Set newSet = new Set();
    this.sets.add(newSet);
    return newSet;
  }

  private Player getOpponentPlayer(Player evaluatedPlayer) {
    return evaluatedPlayer.equals(this.players.get(0)) ?
        this.players.get(1) : this.players.get(0);
  }

  public Player getRandomPlayer() {
    Random randomBoolean = new Random();
    return (randomBoolean.nextBoolean()) ? this.players.get(0) : this.players.get(1);
  }

  public Integer getId() {
    return id;
  }

  private Player getNextServicePlayer() {
    if (this.isFirstGameInCurrentOrNewSet()) {
      return getRandomPlayer();
    }

    if (this.isFirstGameInNonFirstSet() && this.doesLastFinishedSetHaveTieBreak()) {
      return this.getLastFinishedSet().getRestPlayerFromFirstPointInLastFinishedGame();
    } else if (this.isFirstGameInNonFirstSet() && !this.doesLastFinishedSetHaveTieBreak()) {
      return getRandomPlayer();
    }

    if (existsCurrentSet()) {
      return this.getServicePlayerFromCurrentSet();
    }

    return getRandomPlayer();
  }

  private Player getServicePlayerFromCurrentSet() {
    Set currentSet = this.getCurrentSet();
    return currentSet.getNexServicePlayerFromCurrentGame();
  }

  private boolean isFirstGameInCurrentOrNewSet() {
    Set currentSet = this.getCurrentSet();
    if (currentSet != null) {
      return !currentSet.hasCurrentSetHaveGames();
    }
    return true;
  }

  private boolean existsFinishedSets() {
    for (Set set : sets) {
      if (set.isFinished()) {
        return true;
      }
    }
    return false;
  }

  private Set getLastFinishedSet() {
    Set lastFinishedSet = null;
    for (Set set : sets) {
      if (set.isFinished()) {
        lastFinishedSet = set;
      }
    }
    return lastFinishedSet;
  }

  private boolean existsCurrentSet() {
    Set currentSet = this.getCurrentSet();
    return currentSet != null;
  }

  private boolean isFirstGameInNonFirstSet() {
    return this.existsFinishedSets() && !this.existsCurrentSet();
  }

  private boolean doesLastFinishedSetHaveTieBreak() {
    Set lastFinishedSet = this.getLastFinishedSet();
    if (lastFinishedSet != null) {
      return lastFinishedSet.doesSetHasTieBreakGame();
    }
    return false;
  }

}
