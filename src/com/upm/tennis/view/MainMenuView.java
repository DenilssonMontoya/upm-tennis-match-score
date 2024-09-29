package com.upm.tennis.view;

import java.util.Optional;
import java.util.Scanner;

import com.upm.tennis.controller.GeneralTennisManagementController;
import com.upm.tennis.controller.HelpController;

public class MainMenuView extends MenuView {

  private final GeneralTennisManagementController generalTennisManagementController;

  private final HelpController helpController;

  public MainMenuView(GeneralTennisManagementController generalTennisManagementController, HelpController helpController) {
    this.generalTennisManagementController = generalTennisManagementController;
    this.helpController = helpController;
  }

  public void displayOptions() {
    Scanner scanner = new Scanner(System.in);
    String input;
    do {
      System.out.print("Enter command (type 'exit' to quit and 'help' for commands): ");
      input = scanner.nextLine();
      this.processCommand(input);
    } while (!input.equalsIgnoreCase("exit"));
    System.out.println("Finished");
  }

  private void processCommand(String input) {
    String command = getCommandNameFromInput(input);
    Optional<String> commandParameters = getCommandParametersFromInput(input);

    switch (command) {
      case "createReferee":
        commandParameters.ifPresent(this.generalTennisManagementController::createReferee);
        break;
      case "login":
        commandParameters.ifPresent(this.generalTennisManagementController::login);
        break;
      case "help":
        this.helpController.display();
        break;
      default:
        System.out.println("COMMAND NOT FOUND!");
    }
  }
}
