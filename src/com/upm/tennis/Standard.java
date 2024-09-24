package com.upm.tennis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Standard extends Game{

  public Standard() {
    this.points = new ArrayList<>();
  }

  @Override
  public boolean isFinished() {
    Map<Player, Integer> pointWinsByPlayer = this.getPointWinsByPlayer();
    return this.isThereGameWinner(pointWinsByPlayer);
  }

  @Override
  public Player getNextServicePlayerFromNonFirstPointInGame() {
    return this.points.get(0).getService();
  }

  private boolean isThereGameWinner(Map<Player, Integer> pointWinsByPlayer){
    if(pointWinsByPlayer.isEmpty()) return false;
    if(pointWinsByPlayer.size() == 1){
      Iterator<Entry<Player, Integer>> iterator = pointWinsByPlayer.entrySet().iterator();
      Entry<Player, Integer> playerEvaluated = iterator.next();
      return this.hasPlayerWonGame(playerEvaluated);
    } else {
      return this.hasAnyPlayerWonGame(pointWinsByPlayer);
    }
  }

  private boolean hasAnyPlayerWonGame(Map<Player, Integer> gameWinsByPlayer){
    Iterator<Entry<Player, Integer>> iterator = gameWinsByPlayer.entrySet().iterator();
    Entry<Player, Integer> playerEvaluatedA = iterator.next();
    Entry<Player, Integer> playerEvaluatedB = iterator.next();

    return hasPlayerWonGame(playerEvaluatedA,playerEvaluatedB.getValue()) ||
        hasPlayerWonGame(playerEvaluatedB,playerEvaluatedA.getValue());
  }

  private boolean hasPlayerWonGame(Entry<Player, Integer> evaluatedPlayer, int opponentPlayerWins) {
    int numberOfTotalWonPoints = evaluatedPlayer.getValue() + opponentPlayerWins;
    int numberOfEvaluatedPlayerWins = evaluatedPlayer.getValue();
    int numberOfEvaluatedPlayerWinsDifference = numberOfEvaluatedPlayerWins - opponentPlayerWins;

    if(numberOfTotalWonPoints < 4) return false;
    if(numberOfEvaluatedPlayerWins <= opponentPlayerWins) return false;

    return numberOfEvaluatedPlayerWinsDifference >= 2 && numberOfEvaluatedPlayerWins >= 4;
  }

  private boolean hasPlayerWonGame(Entry<Player, Integer> playerEvaluated) {
    return this.hasPlayerWonGame(playerEvaluated, 0);
  }
}
