package com.upm.tennis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Game {

  protected List<Point> points;

  public Player getWinner() {
    return null;
  }

  public abstract boolean isFinished();

  protected void addPointService(Player service, Player rest) {
    Point currentPoint = this.getCurrentOrNewPoint(service, rest);
    currentPoint.addServicePlayerAsWinner();
  }

  protected void addPointRest(Player service, Player rest) {
    Point currentPoint = this.getCurrentOrNewPoint(service, rest);
    currentPoint.addRestPlayerAsWinner();
  }

  protected void addLackService(Player service, Player rest) {
    Point currentPoint = this.getCurrentOrNewPoint(service, rest);
    currentPoint.incrementLackOfService();
    if (currentPoint.getLackOfServiceCount() == 2) {
      currentPoint.addRestPlayerAsWinner();
    }
  }

  protected Point getCurrentOrNewPoint(Player service, Player rest) {
    Point currentPoint = this.getCurrentPoint();
    if (currentPoint != null) {
      return currentPoint;
    }
    return this.addNewPoint(service, rest);
  }

  private Point getCurrentPoint() {
    for (Point point : points) {
      if (point.getWinner() == null) {
        return point;
      }
    }
    return null;
  }

  private Point addNewPoint(Player service, Player rest) {
    Point newPoint = new Point(service, rest);
    points.add(newPoint);
    return newPoint;
  }

  protected Map<Player, Integer> getPointWinsByPlayer() {
    Map<Player, Integer> playerWinsMap = new HashMap<>();
    for (Point point : this.points) {
      if (point.getWinner() != null) {
        playerWinsMap.put(point.getWinner(),
            playerWinsMap.getOrDefault(point.getWinner(), 0) + 1);
      }
    }
    return playerWinsMap;
  }

  public Player getRestPlayerFromFirstPoint() {
    return this.points.get(0).getRest();
  }

  public abstract Player getNextServicePlayerFromNonFirstPointInGame();

  public Player getLastPointServicePlayer() {
    Player lastServicePlayer = null;
    for (Point point : points) {
      if (point.getWinner() != null) {
        lastServicePlayer = point.getService();
      }
    }

    return lastServicePlayer;
  }

  public Player getLastPointRestPlayer() {
    Player lastRestPlayer = null;
    for (Point point : points) {
      if (point.getWinner() != null) {
        lastRestPlayer = point.getRest();
      }
    }
    return lastRestPlayer;
  }

  public List<Point> getPoints() {
    return points;
  }
}
