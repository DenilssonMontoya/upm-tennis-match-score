package com.upm.tennis.controller;

import com.upm.tennis.view.PublicMenuView;

public class MainMenuController {

  private final PublicMenuView publicMenuView;

  public MainMenuController(PublicMenuView publicMenuView) {
    this.publicMenuView = publicMenuView;
  }

  public void displayOptions() {
    this.publicMenuView.displayOptions();
  }
}
