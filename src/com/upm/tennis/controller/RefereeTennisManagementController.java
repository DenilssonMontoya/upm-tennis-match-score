package com.upm.tennis.controller;

import java.util.List;
import java.util.Optional;

import com.upm.tennis.dto.MatchDTO;
import com.upm.tennis.model.Match;
import com.upm.tennis.model.Player;
import com.upm.tennis.model.Referee;
import com.upm.tennis.model.TennisManagement;
import com.upm.tennis.view.TennisManagementView;

public class RefereeTennisManagementController {

  private static final String SEMI_COLON_SYMBOL = ";";

  private static final String GREATER_THAN_SYMBOL = ">";

  private final TennisManagement tennisManagement;

  private final TennisManagementView tennisManagementView;

  public RefereeTennisManagementController(TennisManagement tennisManagement, TennisManagementView tennisManagementView) {
    this.tennisManagement = tennisManagement;
    this.tennisManagementView = tennisManagementView;
  }

  public void createPlayer(String commandParameters) {
    Optional<String> name = this.getParameterValueByKey(commandParameters, "name", SEMI_COLON_SYMBOL);
    if (name.isEmpty()) {
      this.tennisManagementView.showNotValidCommandMessage();
      return;
    }
    this.tennisManagement.addNewPlayer(name.get());
    this.tennisManagementView.showSuccessMessage();
  }

  public void readPlayers() {
    List<Player> players = this.tennisManagement.getPlayers();
    this.tennisManagementView.showPlayerList(players);
  }

  public void createMatch(String commandParameters, Referee referee) {
    Optional<String> numberOfSets = this.getParameterValueByKey(commandParameters, "sets", SEMI_COLON_SYMBOL);
    Optional<String> playerIds = this.getParameterValueByKey(commandParameters, "ids", SEMI_COLON_SYMBOL);

    if (numberOfSets.isEmpty() || playerIds.isEmpty()) {
      this.tennisManagementView.showNotValidCommandMessage();
      return;
    }

    Optional<String> playerOneId = this.getValueByPosition(playerIds.get().split(","), 0);
    Optional<String> playerTwoId = this.getValueByPosition(playerIds.get().split(","), 1);

    if (playerOneId.isEmpty() || playerTwoId.isEmpty()) {
      this.tennisManagementView.showNotValidCommandMessage();
      return;
    }

    MatchDTO matchDTO = new MatchDTO();
    matchDTO.setRefereeName(referee.getName());
    matchDTO.setPlayerOneId(Integer.valueOf(playerOneId.get()));
    matchDTO.setPlayerTwoId(Integer.valueOf(playerTwoId.get()));
    matchDTO.setNumberOfSets(Integer.valueOf(numberOfSets.get()));

    Optional<Match> newMatch = this.tennisManagement.addNewMatch(matchDTO);

    if (newMatch.isEmpty()) {
      this.tennisManagementView.showMatchNotCreated();
      return;
    }
    this.tennisManagementView.showMatchInfo(newMatch.get());
    this.tennisManagementView.showScoreBoard(newMatch.get().getScoreBoard());
  }

  public void addMatchPoint(String commandParameters) {
    Optional<String> matchId = this.getParameterValueByKey(commandParameters, "id", GREATER_THAN_SYMBOL);
    Optional<String> matchPointAction = this.getValueByPosition(commandParameters.split(GREATER_THAN_SYMBOL), 1);

    if (matchId.isEmpty() || matchPointAction.isEmpty()) {
      this.tennisManagementView.showNotValidCommandMessage();
      return;
    }

    Optional<Match> match = this.tennisManagement.addMatchPoint(Integer.valueOf(matchId.get()), matchPointAction.get());

    if (match.isEmpty()) {
      this.tennisManagementView.showMatchNotFound();
      return;
    }
    this.tennisManagementView.showScoreBoard(match.get().getScoreBoard());
  }

  private Optional<String> getParameterValueByKey(String commandParameters, String parameterKey, String separator) {
    String[] parameters = commandParameters.split(separator);
    for (String parameter : parameters) {
      String[] parameterKeyValue = parameter.split(":");
      if (this.isValidParameterKey(parameterKeyValue, parameterKey)) {
        return Optional.of(parameterKeyValue[1].trim());
      }
    }
    return Optional.empty();
  }

  private Optional<String> getValueByPosition(String[] values, Integer position) {
    if (position < values.length) {
      return Optional.of(values[position].trim());
    }
    return Optional.empty();
  }

  private boolean isValidParameterKey(String[] parameterKeyValue, String parameterKey) {
    return parameterKeyValue.length == 2 && parameterKeyValue[0].equals(parameterKey);
  }

}
