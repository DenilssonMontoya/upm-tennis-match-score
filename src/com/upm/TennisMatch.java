package com.upm;

import com.upm.model.Game;
import com.upm.model.Match;
import com.upm.model.Player;
import com.upm.model.Point;
import com.upm.model.Referee;
import com.upm.model.Set;
import com.upm.repository.PlayerRepository;

public class TennisMatch {

  public static void main(String[] args) {

    PlayerRepository playerRepository = new PlayerRepository();


    Referee referee = new Referee("Juan", "123");
    System.out.println(referee.getName());

    Player playerOne_ = new Player("Nadal");
    playerRepository.addPlayer(playerOne_);

    Player playerTwo_ = new Player("Rafael");
    playerRepository.addPlayer(playerTwo_);

    playerRepository.getAllPlayers()
        .forEach(player -> System.out.println(player.toString()));

    Match match = new Match();

    Player playerOne = playerRepository.findPlayerById(1);
    Player playerTwo = playerRepository.findPlayerById(2);
    match.populateMatchDetails(3, playerOne, playerTwo,referee);

    Set currentSet = match.getCurrentSet();
    Game currentGame = currentSet.getCurrentGame();
    Point currentPoint = currentGame.getCurrentPoint();
    //---

    currentPoint.incrementLackOfService();
    currentPoint.addRestAsWinner();
    currentPoint.addServiceAsWinner();





    //lackOfService
    //match->set0->game0->point0




  }

}
