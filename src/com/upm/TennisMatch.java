package com.upm;

import java.util.List;
import java.util.Scanner;

import com.upm.model.Game;
import com.upm.model.Match;
import com.upm.model.Player;
import com.upm.model.Point;
import com.upm.model.Referee;
import com.upm.model.Set;
import com.upm.model.Standard;
import com.upm.model.TieBreak;
import com.upm.repository.MatchRepository;
import com.upm.repository.PlayerRepository;

public class TennisMatch {

  public static void main(String[] args) {

    PlayerRepository playerRepository = new PlayerRepository();
    MatchRepository matchRepository = new MatchRepository();

    Referee referee = new Referee("Juan", "123");

    Player playerOne_ = new Player("Nadal");
    playerRepository.addPlayer(playerOne_);

    Player playerTwo_ = new Player("Rafael");
    playerRepository.addPlayer(playerTwo_);

    Player playerOne = playerRepository.findPlayerById(1);
    Player playerTwo = playerRepository.findPlayerById(2);
    Integer numberOfSets = 3;

    Match match_ = new Match(numberOfSets, playerOne, playerTwo,referee);
    Match persitedMatch =  matchRepository.addMatch(match_);

    Match foundMatch = matchRepository.findMatchById(persitedMatch.getId());


    Point currentPoint;


    Scanner scanner = new Scanner(System.in);
    String inputAction;
    do {
      System.out.print("Enter action [lackOfService, pointService, restService](type 'exit' to quit): ");
      inputAction = scanner.nextLine(); // Read user input

      Set currentSet = foundMatch.getCurrentSet();
      Game currentGame = currentSet.getCurrentGame();

      if(currentGame != null){
        currentPoint = currentGame.getCurrentPoint();
        if(currentPoint == null) {
          Player service = foundMatch.getNextServicePlayer();
          Player rest = (service.equals(foundMatch.getPlayers().get(0))) ?
              foundMatch.getPlayers().get(1) : foundMatch.getPlayers().get(0);

          currentPoint = new Point(rest, service);
          currentGame.getPoints().add(currentPoint);
        }
        switch (inputAction){
          case "lackOfService" : currentPoint.incrementLackOfService();break;
          case "pointService" : currentPoint.addServiceAsWinner(); break;
          case "restService" : currentPoint.addRestAsWinner(); break;
        }
      } else {
        Game newGame = currentSet.isSetTie()? new TieBreak() : new Standard();
        currentSet.getGames().add(newGame);

        Player service = foundMatch.getNextServicePlayer();
        Player rest = (service.equals(foundMatch.getPlayers().get(0))) ?
            foundMatch.getPlayers().get(1) : foundMatch.getPlayers().get(0);

        currentPoint = new Point(rest, service);
        switch (inputAction){
          case "lackOfService" : currentPoint.incrementLackOfService();break;
          case "pointService" : currentPoint.addServiceAsWinner(); break;
          case "restService" : currentPoint.addRestAsWinner(); break;
        }

        newGame.getPoints().add(currentPoint);
      }

    } while (!inputAction.equalsIgnoreCase("exit"));

   System.out.println("Finished");

  }

}
