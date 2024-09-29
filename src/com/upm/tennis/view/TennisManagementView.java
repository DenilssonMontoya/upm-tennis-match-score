package com.upm.tennis.view;

import java.util.List;

import com.upm.tennis.model.Match;
import com.upm.tennis.model.Player;
import com.upm.tennis.model.ScoreBoard;

public class TennisManagementView {

  public void showNotValidCommandMessage() {
    System.out.println("Received command is not valid. See 'help'");
  }

  public void showSuccessMessage() {
    System.out.println("Command completed successfully!!");
  }

  public void showPlayerList(List<Player> players) {
    for (Player player : players) {
      System.out.println(player.toString());
    }
  }

  public void showFailedLoginMessage() {
    System.out.println("Not valid credential!!");
  }

  public void showMatchNotCreated() {
    System.out.println("Match not created, check parameters!!");
  }

  public void showMatchNotFound() {
    System.out.println("Match not found, check parameters!!");
  }

  public void showMatchInfo(Match match) {
    System.out.println("id: " + match.getId());
    System.out.println("date: " + match.getDate());
  }

  public void showScoreBoard(ScoreBoard scoreBoard) {
    System.out.println("SCORE BOARD");
  }

}
