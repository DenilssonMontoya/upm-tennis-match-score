package com.upm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Set {

  private final List<Game> games;

  public Set() {
    this.games = new ArrayList<>();
    Game standard = new Standard();
    this.games.add(standard);
  }

  public List<Game> getGames() {
    return games;
  }

  public Game getCurrentGame(){
    for (Game game: games){
      if(!game.isFinished()) {
        return game;
      }
    }
    return null;
  }

  public Player getLastServicePlayerFromClosedGame(){
    Game lastClosedGame = null;
    for (Game game: games){
      if(game.isFinished()) {
        lastClosedGame = game;
        break;
      }
    }

    if(lastClosedGame == null) return null;

    return lastClosedGame.getLastPointServicePlayer();
  }

  public Player getRestPlayerFromLastClosedStandardGame(){
    Game lastClosedGame = null;
    for (Game game: games){
      if(game.isFinished() && game instanceof Standard) {
        lastClosedGame = game;
      }
    }

    if(lastClosedGame == null) return null;

    return lastClosedGame.getLastRestServicePlayer();
  }

  public Player getRestPlayerFromFirstPointInTieBreakGame(){
    Game lastClosedGame = null;
    for (Game game: games){
      if(game.isFinished() && game instanceof TieBreak) {
        lastClosedGame = game;
        break;
      }
    }

    if(lastClosedGame == null) return null;

    return lastClosedGame.getPoints().get(0).getRest();
  }


  public boolean isLastClosedGameATieBreak(){
    Game lastClosedGame = null;
    for (Game game: games){
      if(game.isFinished()) {
        lastClosedGame = game;
      }
    }

    if(lastClosedGame == null) return false;
    if(!(lastClosedGame instanceof TieBreak)) return false;

    return true;
  }

  public Player getWinner(){
    if(games.size()< 6) return null;

    Map<Player, Integer> playerWinsMap = new HashMap<>();

    for (Game game: this.games){
      Player winner = game.getWinner();
      if(winner != null) {
        playerWinsMap.put(winner,playerWinsMap.getOrDefault(winner,0) + 1);
      }
    }
    //No winner
    if(playerWinsMap.isEmpty()) return null;

    Iterator<Entry<Player, Integer>> iterator = playerWinsMap.entrySet().iterator();
    Entry<Player, Integer> playerOne = iterator.next();

    if(playerWinsMap.size() == 1 && playerOne.getValue() >= 6) {
      return playerOne.getKey();
    } else if (playerWinsMap.size() == 2) {
      Entry<Player, Integer> playerTwo = iterator.next();

      int playerOneDifference = playerOne.getValue() - playerTwo.getValue();
      int playerTwoDifference = playerTwo.getValue() - playerOne.getValue();

      if(((playerOneDifference >= 2 && games.size() <= 12) ||
          (playerOneDifference >= 1 && games.size() == 13))
          && playerOne.getValue() >= 6){
        return playerOne.getKey();
      }

      if(((playerTwoDifference >= 2 && games.size() <= 12) ||
          (playerTwoDifference >= 1 && games.size() == 13))
          && playerTwo.getValue() >= 6){
        return playerTwo.getKey();
      }
    }

    return null;
  }

  public boolean isFinished(){
    if(games.size()< 6) return false;

    Map<Player, Integer> playerWinsMap = new HashMap<>();

    for (Game game: this.games){
      Player winner = game.getWinner();
      if(winner != null) {
        playerWinsMap.put(winner,playerWinsMap.getOrDefault(winner,0) + 1);
      }
    }
    //No winner
    if(playerWinsMap.isEmpty()) return false;

    Iterator<Entry<Player, Integer>> iterator = playerWinsMap.entrySet().iterator();
    Entry<Player, Integer> playerOne = iterator.next();

    if(playerWinsMap.size() == 1 && playerOne.getValue() >= 6) {
      return true;
    } else if (playerWinsMap.size() == 2) {
      Entry<Player, Integer> playerTwo = iterator.next();

      int playerOneDifference = playerOne.getValue() - playerTwo.getValue();
      int playerTwoDifference = playerTwo.getValue() - playerOne.getValue();

      if(((playerOneDifference >= 2 && games.size() <= 12) ||
          (playerOneDifference >= 1 && games.size() == 13))
          && playerOne.getValue() >= 6){
        return true;
      }

      if(((playerTwoDifference >= 2 && games.size() <= 12) ||
          (playerTwoDifference >= 1 && games.size() == 13))
          && playerTwo.getValue() >= 6){
        return true;
      }
    }

    return false;
  }

  public boolean isSetTie(){
    return games.size() >= 12;
  }

  public Map<Player, Integer> getWonGamesByPlayer(){
    Map<Player, Integer> gamesVictoryMap = new HashMap<>();
    for(Game game: this.games){
      if(game.getWinner() != null) {
        gamesVictoryMap.put(game.getWinner(), gamesVictoryMap.getOrDefault(game.getWinner(), 0) + 1);
      }
    }
    return gamesVictoryMap;
  }

}
