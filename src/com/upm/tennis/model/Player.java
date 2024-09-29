package com.upm.tennis.model;

public class Player extends Person {

  private final Integer id;

  public Player(Integer id, String name) {
    super(name);
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "name:" + this.name + " ; id:" + this.id;
  }
}
