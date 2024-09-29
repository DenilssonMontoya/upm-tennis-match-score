package com.upm.tennis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Game {

    protected List<Point> points;
    Map<Player, Integer> pointsMap;
    private Map<Player, Integer> lacksOfService;
    Player service;
    Player rest;

    public Game() {
        this.lacksOfService = new HashMap<Player, Integer>();
    }

    public Player getWinner() {
        return null;
    }

    public abstract boolean isFinished();


    protected void addPoint(Player player) {
        Integer currentScore = pointsMap.getOrDefault(player, 0);
        this.pointsMap.put(player, currentScore + 1);
    }

  /*protected void addLackService(Player service, Player rest) {
    Point currentPoint = this.getCurrentOrNewPoint(service, rest);
    currentPoint.incrementLackOfService();
    if(currentPoint.getLackOfServiceCount() == 2){
      currentPoint.addRestPlayerAsWinner();
    }
  }*/

    protected void addLackService(Player service, Player rest) {
        int updatedLacksOfServiceCount = lacksOfService.getOrDefault(service, 0) + 1;
        lacksOfService.put(service, updatedLacksOfServiceCount);
        if (updatedLacksOfServiceCount >= 2) {
            lacksOfService.put(service, 0);
            addPoint(rest);
        }
    }
/*
  protected Point getCurrentOrNewPoint(Player service, Player rest){
    Point currentPoint = this.getCurrentPoint();
    if(currentPoint != null) {
      return currentPoint;
    }
    return this.addNewPoint(service, rest);
  }*/

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

    protected Map<Player, Integer> getPointsMap() {
        return this.pointsMap;
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
