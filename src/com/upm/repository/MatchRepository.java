package com.upm.repository;

import java.util.ArrayList;
import java.util.List;

import com.upm.model.Match;
import com.upm.model.Player;

public class MatchRepository {

  private final List<Match> matches = new ArrayList<>();

  public Match addMatch(Match match){
    match.setId(matches.size() + 1);
    matches.add(match);
    return match;
  }

  public List<Match> getAllMatches(){
    return matches;
  }

  public Match findMatchById(Integer id){

    for (Match match : matches){
      if(match.getId().equals(id)){
        return  match;
      }
    }
    return null;
  }

}
