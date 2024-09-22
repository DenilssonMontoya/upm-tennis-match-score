package com.test;

public class Point {

  private final Player rest;

  private final Player service;

  private Player winner;

  private Integer lackOfServiceCount = 0;

  public Point(Player rest, Player service) {
    this.rest = rest;
    this.service = service;
  }

  public Player getWinner() {
    return winner;
  }

  public void incrementLackOfService(){
    if(this.winner != null) return;

    this.lackOfServiceCount++;
    if(this.lackOfServiceCount == 2){
      this.addRestPlayerAsWinner();
    }
  }

  public void addRestPlayerAsWinner(){
    this.winner = this.rest;
  }

  public void addServicePlayerAsWinner(){
    this.winner = this.service;
  }

}
