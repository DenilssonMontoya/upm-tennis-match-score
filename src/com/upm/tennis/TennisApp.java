package com.upm.tennis;

import com.upm.tennis.controller.PublicScoreManagerController;
import com.upm.tennis.controller.HelpController;
import com.upm.tennis.controller.MainMenuController;
import com.upm.tennis.controller.RefereeScoreManagerController;
import com.upm.tennis.model.ScoreManager;
import com.upm.tennis.view.HelpView;
import com.upm.tennis.view.PublicMenuView;
import com.upm.tennis.view.RefereeMenuView;
import com.upm.tennis.view.ScoreMangerView;

public class TennisApp {

  public static void main(String[] args) {
    MainMenuController mainMenuController = initializeMainMenu();
    mainMenuController.displayOptions();
  }

  private static MainMenuController initializeMainMenu() {

    ScoreManager scoreManager = new ScoreManager();
    ScoreMangerView scoreMangerView = new ScoreMangerView();
    HelpView helpView = new HelpView();
    HelpController helpController = new HelpController(helpView);
    RefereeScoreManagerController refereeScoreManagerController =
        new RefereeScoreManagerController(scoreManager, scoreMangerView);
    RefereeMenuView refereeMenuView = new RefereeMenuView(refereeScoreManagerController, helpController);
    PublicScoreManagerController publicScoreManagerController =
        new PublicScoreManagerController(scoreManager, scoreMangerView, refereeMenuView);

    PublicMenuView menuView = new PublicMenuView(publicScoreManagerController, helpController);
    return new MainMenuController(menuView);
  }

}
