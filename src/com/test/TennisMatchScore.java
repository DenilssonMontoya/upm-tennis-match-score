package com.test;

import java.util.List;

public class TennisMatchScore {

  public static void main(String[] args) {

    int numberOfSets = 3;
    Player playerOne = new Player("Rafael");
    Player playerTwo = new Player("Roger");
    Referee referee = new Referee("Luis");

    Match tennisMatch = new Match(numberOfSets, List.of(playerOne, playerTwo), referee);
    tennisMatch.startMatch();
  }

}
