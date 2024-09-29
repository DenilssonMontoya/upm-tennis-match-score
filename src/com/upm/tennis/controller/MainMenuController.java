package com.upm.tennis.controller;

import com.upm.tennis.view.MainMenuView;

public class MainMenuController {

  private final MainMenuView mainMenuView;

  public MainMenuController(MainMenuView mainMenuView) {
    this.mainMenuView = mainMenuView;
  }

  public void displayOptions() {
    this.mainMenuView.displayOptions();
  }
}
