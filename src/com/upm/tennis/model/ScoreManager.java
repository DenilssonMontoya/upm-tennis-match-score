package com.upm.tennis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.upm.tennis.dto.MatchDTO;

public class ScoreManager {

  private final List<Player> players;

  private final List<Referee> referees;

  private final List<Match> matches;

  public ScoreManager() {
    this.matches = new ArrayList<>();
    this.players = new ArrayList<>();
    this.referees = new ArrayList<>();
  }

  public void addNewPlayer(String name) {
    Integer id = this.players.size() + 1;
    Player player = new Player(id, name);
    this.players.add(player);
  }

  public void addNewReferee(String name, String password) {
    Referee referee = new Referee(name, password);
    this.referees.add(referee);
  }

  public List<Player> getPlayers() {
    return this.players;
  }

  public Optional<Referee> login(String name, String password) {
    for (Referee referee : referees) {
      if (referee.matchCredentials(name, password)) {
        return Optional.of(referee);
      }
    }
    return Optional.empty();
  }

  public Optional<Match> addNewMatch(MatchDTO matchDTO) {
    Integer id = this.matches.size() + 1;
    Optional<Player> playerOne = this.findPlayerById(matchDTO.getPlayerOneId());
    Optional<Player> playerTwo = this.findPlayerById(matchDTO.getPlayerTwoId());
    Optional<Referee> referee = this.findRefereeByName(matchDTO.getRefereeName());

    if (playerOne.isEmpty() || playerTwo.isEmpty() || referee.isEmpty()) {
      return Optional.empty();
    }

    Match match = new Match(id, matchDTO.getNumberOfSets(),
        List.of(playerOne.get(), playerTwo.get()),
        referee.get());
    this.matches.add(match);

    return Optional.of(match);
  }

  public Optional<Match> addMatchPoint(Integer matchId, String actionPoint) {
    Optional<Match> foundMatch = this.findMatchById(matchId);
    foundMatch.ifPresent(match -> match.processAction(actionPoint));
    return foundMatch;
  }

  public boolean isMatchByIdFinishedOrNotExists(Integer id) {
    Optional<Match> foundMatch = this.findMatchById(id);
    return foundMatch.map(Match::isFinished).orElse(true);
  }

  private Optional<Player> findPlayerById(Integer id) {
    for (Player player : this.players) {
      if (player.getId().equals(id)) {
        return Optional.of(player);
      }
    }
    return Optional.empty();
  }

  private Optional<Referee> findRefereeByName(String name) {
    for (Referee referee : this.referees) {
      if (referee.getName().equals(name)) {
        return Optional.of(referee);
      }
    }
    return Optional.empty();
  }

  private Optional<Match> findMatchById(Integer id) {
    for (Match match : this.matches) {
      if (match.getId().equals(id)) {
        return Optional.of(match);
      }
    }
    return Optional.empty();
  }

}
