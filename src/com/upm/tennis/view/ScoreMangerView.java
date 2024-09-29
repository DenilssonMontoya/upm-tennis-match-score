package com.upm.tennis.view;

import java.util.HashMap;
import java.util.List;

import com.upm.tennis.model.Match;
import com.upm.tennis.model.Player;
import com.upm.tennis.model.ScoreBoard;

public class ScoreMangerView {

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
    List<HashMap<String, String>> scoreInfo = scoreBoard.getScoreInformation();
    System.out.println(formatRow(scoreInfo.get(0)));
    System.out.println(formatRow(scoreInfo.get(1)));
  }

  private String formatRow(HashMap<String, String> row) {
    String paddedName = String.format("%-10s", row.getOrDefault("name", ""));
    String paddedServiceIndicator = String.format("%-1s", row.getOrDefault("hasService", ""));

    return paddedServiceIndicator + " "
        + paddedName + " :  "
        + row.getOrDefault("currentGame", "0") + " "
        + getSetsString(row);
  }

  private String getSetsString(HashMap<String, String> row) {
    StringBuilder setString = new StringBuilder();
    for (int i = 1; i < 6; i++) {
      setString.append(row.getOrDefault("set" + i, "")).append(" ");
    }
    return setString.toString();
  }

  public void showNotMatchIsFinishedOrNotExits() {
    System.out.println("Match is finished or not found!");
  }
}
