package com.upm.model;

public class Point {

  private Player rest;

  private Player service;

  private Player winner;

  private Integer lackOfServiceCount = 0;

  public Point(Player rest, Player service) {
    this.rest = rest;
    this.service = service;
  }

  public void addRestAsWinner(){
    this.winner = this.rest;
  }

  public void addServiceAsWinner(){
    this.winner = this.service;
  }

  public Player getWinner() {
    return winner;
  }

  public Player getRest() {
    return rest;
  }

  public Player getService() {
    return service;
  }

  public void setService(Player service) {
    this.service = service;
  }

  public void setRest(Player rest) {
    this.rest = rest;
  }

  public void incrementLackOfService(){
    this.lackOfServiceCount++;
    if(this.lackOfServiceCount == 2){
      this.addRestAsWinner();
    }
  }




}
