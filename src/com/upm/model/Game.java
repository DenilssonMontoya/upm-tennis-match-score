package com.upm.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {

  protected final List<Point> points;

  public Game() {
    this.points = new ArrayList<>();
  }

  public List<Point> getPoints() {
    return points;
  }

  public Point getCurrentPoint(){

    for (Point point : points) {
      if (point.getWinner() == null){
        return point;
      }
    }

    return null;
  }

  public Player getLastPointServicePlayer(){
    Player lastServicePlayer = null;
    for (Point point : points) {
      if (point.getWinner() != null){
        lastServicePlayer = point.getService();
      }
    }

    return lastServicePlayer;
  }

  public Player getLastRestServicePlayer(){
    Player lastRestPlayer = null;
    for (Point point : points) {
      if (point.getWinner() != null){
        lastRestPlayer = point.getRest();
      }
    }

    return lastRestPlayer;
  }

  abstract boolean isFinished();

  abstract Player getWinner();





}
