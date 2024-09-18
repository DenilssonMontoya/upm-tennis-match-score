package com.upm.model;

public class Referee extends Person{

  private String password;

  public Referee(String name, String password) {
    super(name);
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
