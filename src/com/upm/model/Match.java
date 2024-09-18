package com.upm.model;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Match {

  private Integer id;

  private ScoreBoard scoreBoard;

  private final List<Set> sets;

  private final List<Player> players;

  private Referee referee;

  private LocalDateTime date;

  public Match() {
    this.sets = new ArrayList<>();
    this.players = new ArrayList<>();
    this.scoreBoard = new ScoreBoard();
    this.date = LocalDateTime.now();
  }

  public void populateMatchDetails(Integer numberOfSets, Player playerOne, Player playerTwo , Referee referee){

    addSets(numberOfSets);

    this.players.add(playerOne);
    this.players.add(playerTwo);
    this.referee = referee;

  }

  public Set getCurrentSet(){

    for (Set set : sets){
      if(set.getCurrentGame() != null){
        return set;
      }
    }
    return null;
  }

  public void updateScoreBoard(){
    //Implementar aqui logica para mostrar el resutlado actual del match.
  }


  private void addSets(Integer numberOfSets) {
    for (int i = 0; i < numberOfSets; i++) {
      this.sets.add(new Set());
    }
  }



}
