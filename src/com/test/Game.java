package com.test;

import java.util.ArrayList;
import java.util.List;

import com.upm.model.Player;

public abstract class Game {

  protected List<Point> points;

  public Player getWinner() {
    return null;
  }

  public abstract boolean isFinished();

  public void addPointService() {
    Point currentPoint = this.getCurrentOrNewPoint();
    currentPoint.addServicePlayerAsWinner();
  }

  public void addPointRest() {
    Point currentPoint = this.getCurrentOrNewPoint();
    currentPoint.addRestPlayerAsWinner();
  }

  public void addLackService() {
    Point currentPoint = this.getCurrentOrNewPoint();
    currentPoint.incrementLackOfService();
  }

  private Point getCurrentOrNewPoint(){
    Point currentPoint = this.getCurrentPoint();
    if(currentPoint != null) {
      return currentPoint;
    }
    return this.addNewPoint();
  }

  private Point getCurrentPoint(){
    for (Point point: points){
      if(point.getWinner() == null){
        return point;
      }
    }
    return null;
  }

  private Point addNewPoint() {
    Point newPoint = new Point();
    points.add(newPoint);
    return newPoint;
  }


}
