package com.test;

import java.util.ArrayList;

public class Standard extends Game{

  public Standard() {
    this.points = new ArrayList<>();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
