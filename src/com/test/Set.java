package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.upm.model.Player;

public class Set {

  private final List<Game> games;

  public Set() {
    this.games = new ArrayList<>();
  }

  public void addPointService(List<Player> players) {
    Game currentGame = this.getCurrentGame();
    currentGame.addPointService(players);
  }

  public void addLackService() {
    Game currentGame = this.getCurrentOrNewGame();
    currentGame.addLackService();
  }

  public void addPointRest() {
    Game currentGame = this.getCurrentOrNewGame();
    currentGame.addPointRest();
  }

  public boolean isFinished(){
    Map<Player, Integer> gameWinsByPlayer = getGameWinsByPlayer();
    return this.isThereSetWinner(gameWinsByPlayer);
  }

  private Game getCurrentOrNewGame(){
    Game currentGame = getCurrentGame();
    if( currentGame != null){
      return currentGame;
    }
    return addNewGame();
  }

  private Game getCurrentGame(){
    for (Game game : this.games){
      if(!game.isFinished()){
        return game;
      }
    }
    return null;
  }

  private Game addNewGame(){
    Game newGame = newGameType();
    this.games.add(newGame);
    return newGame;
  }

  private Game newGameType(){
    if(this.countAllFinishedGame()<12){
      return new Standard();
    }else{
      return new TieBreak();
    }
  }

  private int countAllFinishedGame(){
    int numberOfFinishedGames = 0;
    for (Game game : this.games){
      if(game.isFinished()){
        numberOfFinishedGames++;
      }
    }
    return numberOfFinishedGames;
  }

  private boolean isThereSetWinner(Map<Player, Integer> gameWinsByPlayer){
    if(gameWinsByPlayer.isEmpty()) return false;
    if(gameWinsByPlayer.size() == 1){
      Iterator<Entry<Player, Integer>> iterator = gameWinsByPlayer.entrySet().iterator();
      Entry<Player, Integer> playerEvaluated = iterator.next();
      return this.hasPlayerWonSet(playerEvaluated);
    } else {
      return this.hasAnyPlayerWonSet(gameWinsByPlayer);
    }
  }

  private boolean hasAnyPlayerWonSet(Map<Player, Integer> gameWinsByPlayer){
    Iterator<Entry<Player, Integer>> iterator = gameWinsByPlayer.entrySet().iterator();
    Entry<Player, Integer> playerEvaluatedA = iterator.next();
    Entry<Player, Integer> playerEvaluatedB = iterator.next();

    return hasPlayerWonSet(playerEvaluatedA,playerEvaluatedB.getValue()) ||
        hasPlayerWonSet(playerEvaluatedB,playerEvaluatedA.getValue());
  }

  private boolean hasPlayerWonSet(Entry<Player, Integer> evaluatedPlayer, int opponentPlayerWins) {
    int numberOfFinishedGames = evaluatedPlayer.getValue() + opponentPlayerWins;
    int numberOfEvaluatedPlayerWins = evaluatedPlayer.getValue();
    int numberOfEvaluatedPlayerWinsDifference = numberOfEvaluatedPlayerWins - opponentPlayerWins;

    if(numberOfFinishedGames < 6) return false;
    if(numberOfEvaluatedPlayerWins <= opponentPlayerWins) return false;

    return ((numberOfEvaluatedPlayerWinsDifference >= 2 && numberOfFinishedGames <= 12) ||
        (numberOfEvaluatedPlayerWinsDifference >= 1 && numberOfFinishedGames == 13))
        && numberOfEvaluatedPlayerWins >= 6;
  }

  private boolean hasPlayerWonSet(Entry<Player, Integer> playerEvaluated) {
    return this.hasPlayerWonSet(playerEvaluated, 0);
  }

  private Map<Player, Integer> getGameWinsByPlayer() {
    Map<Player, Integer> playerWinsMap = new HashMap<>();
    for (Game game: this.games){
      Player winner = game.getWinner();
      if(winner != null) {
        playerWinsMap.put(winner,playerWinsMap.getOrDefault(winner,0) + 1);
      }
    }
    return playerWinsMap;
  }

}
