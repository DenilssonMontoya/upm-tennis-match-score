package com.upm.model;

public class Player extends Person{

  private Integer id;

  public Player(String name) {
    super(name);
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Player name: " + this.getName() +
        " id: " + this.id;
  }
}
