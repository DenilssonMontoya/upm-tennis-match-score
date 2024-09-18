package com.upm.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

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






}
