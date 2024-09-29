package com.upm.tennis.model;

public class Point {

  private final Player service;

  private final Player rest;

  private Player winner;

  private Integer lackOfServiceCount = 0;

  public Point(Player service, Player rest) {
    this.service = service;
    this.rest = rest;
  }

  public Player getWinner() {
    return winner;
  }

  public void incrementLackOfService() {
    this.lackOfServiceCount++;
  }

  public void addRestPlayerAsWinner() {
    this.winner = this.rest;
  }

  public void addServicePlayerAsWinner() {
    this.winner = this.service;
  }

  public Integer getLackOfServiceCount() {
    return lackOfServiceCount;
  }

  public Player getService() {
    return service;
  }

  public Player getRest() {
    return rest;
  }
}
