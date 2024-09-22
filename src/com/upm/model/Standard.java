package com.upm.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Standard extends Game{


  @Override
  public boolean isFinished() {
    Map<Integer, Integer> playerWinsMap = new HashMap<>();

    //Rule: Not minimum Points Played
    if(this.getPoints().stream().filter(point -> point.getWinner() != null).count() < 4) return false;
    //Rule One Player All victories
    for (Point point: this.points){
      if(point.getWinner() == null) continue;
      Integer playerId = point.getWinner().getId();
      playerWinsMap.put(playerId,playerWinsMap.getOrDefault(playerId,0) + 1);
    }

    if(playerWinsMap.size() == 1){
      return true;
    } else if (playerWinsMap.size() == 2) {
      //Rule 2 player minimum 6s point with 2 points difference.
      Iterator<Entry<Integer, Integer>> iterator = playerWinsMap.entrySet().iterator();
      Integer playerOnePoints = iterator.next().getValue();
      Integer playerTwoPoints = iterator.next().getValue();
      int pointsDifference = Math.abs(playerOnePoints - playerTwoPoints);

      return (playerOnePoints >= 4 || playerTwoPoints >= 4) && pointsDifference >= 2;
    }
    return false;
  }

  @Override
  public Player getWinner() {
    Map<Player, Integer> playerWinsMap = new HashMap<>();

    for (Point point: this.points){
      Player winner = point.getWinner();
      if(winner != null) {
        playerWinsMap.put(winner,playerWinsMap.getOrDefault(winner,0) + 1);
      }
    }
    //No winner
    if(playerWinsMap.isEmpty()) return null;

    Iterator<Entry<Player, Integer>> iterator = playerWinsMap.entrySet().iterator();
    Entry<Player, Integer> playerOne = iterator.next();

    //All 4 points won by One Player
    if(playerWinsMap.size() == 1 && playerOne.getValue() >= 4) {
      return playerOne.getKey();
    } else if (playerWinsMap.size() == 2) {

      Entry<Player, Integer> playerTwo = iterator.next();
      //Winner A by 2 points difference
      if(playerOne.getValue() - playerTwo.getValue() >= 2 && playerOne.getValue() >= 4){
        return playerOne.getKey();
      }
      //Winner B by 2 points difference
      if(playerTwo.getValue() - playerOne.getValue() >= 2 && playerTwo.getValue() >= 4){
        return playerTwo.getKey();
      }
    }

    return null;
  }
}
