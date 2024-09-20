package com.upm.model;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class Match {

  private Integer id;

  private ScoreBoard scoreBoard;

  private final List<Set> sets;

  private final List<Player> players;

  private Referee referee;

  private LocalDateTime date;

  public Match(Integer numberOfSets, Player playerOne, Player playerTwo , Referee referee) {
    this.sets = new ArrayList<>();
    this.players = new ArrayList<>();
    this.scoreBoard = new ScoreBoard();
    this.date = LocalDateTime.now();

    this.addSets(numberOfSets);
    this.players.add(playerOne);
    this.players.add(playerTwo);
    this.referee = referee;
  }

  public Set getCurrentSet(){
    for (Set set : sets) {
      if (!set.isFinished()) {
        return set;
      }
    }
    return sets.get(0);
  }

  public List<Player> getPlayersRandomOrder(){
    Random randomBoolean = new Random();
    List<Player> randomPlayers = new ArrayList<>();

    var randomPlayerOne = (randomBoolean.nextBoolean())? players.get(0) : players.get(1);
    var randomPlayerTwo = (randomPlayerOne.equals(players.get(0)))? players.get(1) : players.get(0);
    randomPlayers.add(randomPlayerOne);
    randomPlayers.add(randomPlayerTwo);

    return randomPlayers;
  }

  public Player getRandomPlayer(){
    Random randomBoolean = new Random();
    return (randomBoolean.nextBoolean())? players.get(0) : players.get(1);
  }


  public Player getNextServicePlayer(){

    Set currentSet = this.getCurrentSet();

    //En el primer juego de un set posterior a un tie break del anterior set, el
    //servicio del primer juego será para el jugador que resto el primer punto de dicho tie break
    if((currentSet.getGames().isEmpty()||
        (currentSet.getGames().size()==1 && currentSet.getCurrentGame().getPoints().isEmpty()))
        && currentSet.isLastClosedGameATieBreak()){
      return currentSet.getRestPlayerFromFirstPointInTieBreakGame();
    }

    //El jugador con servicio del primer juego será aleatorio
    if(currentSet.getGames().isEmpty() || (currentSet.getGames().size()==1 && currentSet.getCurrentGame().getPoints().isEmpty())){
      return this.getRandomPlayer();
    }

    if(currentSet.getCurrentGame() instanceof Standard){
      // - El servicio en un juego estándar será de un jugador durante todos los puntos del juego en curso
      // - Alternando al jugador que resta para el siguiente juego

      if(currentSet.getGames().size() == 1){
        return currentSet.getCurrentGame().getLastPointServicePlayer();
      } else if (currentSet.getGames().size() > 1) {
        return currentSet.getRestPlayerFromLastClosedStandardGame();
      }
      return this.getRandomPlayer();
    }

    if(currentSet.getCurrentGame() instanceof TieBreak){
      //servicio en un tie break comenzará el primer punto con el jugador que restó el juego anterior
      //posteriormente alternará cada 2 puntos entre ambos jugadores
      if(currentSet.getCurrentGame().getPoints().isEmpty()){
        return currentSet.getRestPlayerFromLastClosedStandardGame();
      } else {
        if(currentSet.getCurrentGame().getPoints().size() % 2 == 0){
          return currentSet.getCurrentGame().getLastRestServicePlayer();
        }
        return currentSet.getCurrentGame().getLastPointServicePlayer();
      }
    }
    return this.getRandomPlayer();
  }

  public Player getMostRecentServicePlayerInMatch(){
    List<Point> points = sets.stream().flatMap(set -> set.getGames().stream())
            .filter(game -> game != null)
            .flatMap(game -> game.getPoints().stream())
            .filter(point -> point != null).filter(point -> point.getService() != null)
            .toList();
    if(points.isEmpty()) return null;
    return points.get(points.size() - 1).getService();
  }

  public void initializeScoreBoard(Point currentPoint){
    scoreBoard.initializeScoreBoard(this.sets.size(), currentPoint);
  }

  public void updateScoreBoard(){
    scoreBoard.update(sets, getCurrentSet(), getMostRecentServicePlayerInMatch());
  }

  private void addSets(Integer numberOfSets) {
    for (int i = 0; i < numberOfSets; i++) {
      this.sets.add(new Set());
    }
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public ScoreBoard getScoreBoard() {
    return scoreBoard;
  }
}
