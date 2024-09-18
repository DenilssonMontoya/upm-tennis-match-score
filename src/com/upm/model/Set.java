package com.upm.model;

import java.util.ArrayList;
import java.util.List;

public class Set {

  private final List<Game> games;

  public Set() {
    this.games = new ArrayList<>();
  }

  public List<Game> getGames() {
    return games;
  }

  public Game getCurrentGame(){
    for (Game game: games){
      if(game.getCurrentPoint() != null) {
        return game;
      }
    }
    return null;
  }

}
