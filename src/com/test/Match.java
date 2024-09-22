package com.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Match {

  private List<Set> sets;

  private List<Player> players;

  private LocalDateTime date;

  private Integer numberOfSets;

  private Referee referee;


  public Match(int numberOfSets, List<Player> players, Referee referee) {
    this.sets = new ArrayList<>();
    this.players = players;
    this.date = LocalDateTime.now();
    this.numberOfSets = numberOfSets;
    this.referee = referee;
  }

  public void startMatch() {
    String inputAction;

    Scanner scanner = new Scanner(System.in);
    do {
      System.out.print("Enter action [lackService, pointService, pointRest](type 'exit' to quit): ");
      inputAction = scanner.nextLine();
      this.processAction(inputAction);

    } while (!inputAction.equalsIgnoreCase("exit"));

    System.out.println("Finished");
  }

  private void addPointService(Set currentSet){
    Objects.requireNonNull(currentSet);
    currentSet.addPointService();
  }

  private void addPointRest(Set currentSet){
    Objects.requireNonNull(currentSet);
    currentSet.addPointRest();
  }

  private void addLackService(Set currentSet){
    Objects.requireNonNull(currentSet);
    currentSet.addLackService();
  }

  private boolean isFinished(){
    if(this.sets.size() < this.numberOfSets) return false;
    Set lastSet = this.sets.get(this.sets.size()-1);
    return lastSet.isFinished();
  }

  public Set getCurrentSet(){
    for (Set set : sets) {
      if (!set.isFinished()) {
        return set;
      }
    }
    return null;
  }

  private void printMatchFinishedMessage(){
    System.out.println("Match is Finished");
  }

  private void processAction(String action) {
    Objects.requireNonNull(action);
    MatchPointActionType actionType = MatchPointActionType.fromString(action);
    Objects.requireNonNull(actionType);
    Set currentSet = getCurrentOrNewSet();
    switch (actionType){
      case LACK_SERVICE -> this.addLackService(currentSet);
      case POINT_SERVICE -> this.addPointService(currentSet);
      case REST_SERVICE -> this.addPointRest(currentSet);
    }

    if(this.isFinished()){
      this.printMatchFinishedMessage();
    }
  }

  private Set getCurrentOrNewSet() {
    Set currentSet = this.getCurrentSet();
    if(currentSet!= null){
      return currentSet;
    }
    return addNewSet();
  }

  private Set addNewSet() {
    Set newSet = new Set();
    this.sets.add(newSet);
    return newSet;
  }

}
