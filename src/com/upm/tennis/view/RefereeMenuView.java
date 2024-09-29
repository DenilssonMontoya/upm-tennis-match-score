package com.upm.tennis.view;

import java.util.Optional;
import java.util.Scanner;

import com.upm.tennis.controller.HelpController;
import com.upm.tennis.controller.RefereeScoreManagerController;
import com.upm.tennis.model.Referee;

public class RefereeMenuView extends MenuView {

  private final RefereeScoreManagerController refereeScoreManagerController;

  private final HelpController helpController;

  public RefereeMenuView(RefereeScoreManagerController refereeScoreManagerController, HelpController helpController) {
    this.refereeScoreManagerController = refereeScoreManagerController;
    this.helpController = helpController;
  }

  public void displayOptions(Referee referee) {
    Scanner scanner = new Scanner(System.in);
    String input;
    do {
      System.out.print("Hi " + referee.getName() + ", please enter command (type 'logout' to quit and 'help' for commands): ");
      input = scanner.nextLine();
      this.processCommand(input, referee);
    } while (!input.equalsIgnoreCase("logout"));
    System.out.println("Finished");
  }

  private void processCommand(String input, Referee referee) {
    String command = getCommandNameFromInput(input);
    Optional<String> commandParameters = getCommandParametersFromInput(input);

    switch (command) {
      case "createPlayer":
        commandParameters.ifPresent(this.refereeScoreManagerController::createPlayer);
        break;
      case "readPlayers":
        this.refereeScoreManagerController.readPlayers();
        break;
      case "createMatch":
        commandParameters.ifPresent(parameters -> this.refereeScoreManagerController.createMatch(parameters, referee));
        break;
      case "match":
        commandParameters.ifPresent(this.refereeScoreManagerController::addMatchPoint);
        break;
      case "readPlayer", "readMatch":
        System.out.println("NOT IMPLEMENTED");
        break;
      case "help":
        this.helpController.display();
        break;
      default:
        System.out.println("COMMAND NOT FOUND!");
    }
  }

}
