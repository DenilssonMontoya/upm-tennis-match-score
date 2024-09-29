package com.upm.tennis;

import com.upm.tennis.controller.GeneralTennisManagementController;
import com.upm.tennis.controller.HelpController;
import com.upm.tennis.controller.MainMenuController;
import com.upm.tennis.controller.RefereeTennisManagementController;
import com.upm.tennis.model.TennisManagement;
import com.upm.tennis.view.HelpView;
import com.upm.tennis.view.MainMenuView;
import com.upm.tennis.view.RefereeMenuView;
import com.upm.tennis.view.TennisManagementView;

public class TennisApp {

  public static void main(String[] args) {
    MainMenuController mainMenuController = initializeMainMenu();
    mainMenuController.displayOptions();
  }

  private static MainMenuController initializeMainMenu() {

    TennisManagement tennisManagement = new TennisManagement();
    TennisManagementView tennisManagementView = new TennisManagementView();
    HelpView helpView = new HelpView();
    HelpController helpController = new HelpController(helpView);
    RefereeTennisManagementController refereeTennisManagementController =
        new RefereeTennisManagementController(tennisManagement, tennisManagementView);
    RefereeMenuView refereeMenuView = new RefereeMenuView(refereeTennisManagementController, helpController);
    GeneralTennisManagementController generalTennisManagementController =
        new GeneralTennisManagementController(tennisManagement, tennisManagementView, refereeMenuView);

    MainMenuView menuView = new MainMenuView(generalTennisManagementController, helpController);
    return new MainMenuController(menuView);
  }

}
