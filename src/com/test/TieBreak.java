package com.test;

import java.util.ArrayList;

public class TieBreak extends Game{

  public TieBreak() {
    this.points = new ArrayList<>();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
