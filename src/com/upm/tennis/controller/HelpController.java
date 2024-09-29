package com.upm.tennis.controller;

import com.upm.tennis.view.HelpView;

public class HelpController {

  private final HelpView helpView;

  public HelpController(HelpView helpView) {
    this.helpView = helpView;
  }

  public void display() {
    this.helpView.display();
  }
}
