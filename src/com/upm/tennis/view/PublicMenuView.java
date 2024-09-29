package com.upm.tennis.view;

import java.util.Optional;
import java.util.Scanner;

import com.upm.tennis.controller.PublicScoreManagerController;
import com.upm.tennis.controller.HelpController;

public class PublicMenuView extends MenuView {

  private final PublicScoreManagerController publicScoreManagerController;

  private final HelpController helpController;

  public PublicMenuView(PublicScoreManagerController publicScoreManagerController, HelpController helpController) {
    this.publicScoreManagerController = publicScoreManagerController;
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
        commandParameters.ifPresent(this.publicScoreManagerController::createReferee);
        break;
      case "login":
        commandParameters.ifPresent(this.publicScoreManagerController::login);
        break;
      case "help":
        this.helpController.display();
        break;
      default:
        System.out.println("COMMAND NOT FOUND!");
    }
  }
}
