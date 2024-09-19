package com.upm.repository;

import java.util.ArrayList;
import java.util.List;

import com.upm.model.Player;

public class PlayerRepository {

  private final List<Player> players = new ArrayList<>();

  public void addPlayer(Player player){
    player.setId(players.size() + 1);
    players.add(player);
  }

  public List<Player> getAllPlayers(){
    return players;
  }

  public Player findPlayerById(Integer id){

    for (Player player : players){
      if(player.getId().equals(id)){
        return  player;
      }
    }
    return null;
  }

}
