package com.upm.tennis.model;

public class Referee extends Person {

  private final String password;

  public Referee(String name, String password) {
    super(name);
    this.password = password;
  }

  public boolean matchCredentials(String name, String password) {
    return this.name.equals(name) && this.password.equals(password);
  }

}
