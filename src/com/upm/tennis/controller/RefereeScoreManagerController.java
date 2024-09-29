package com.upm.tennis.controller;

import java.util.List;
import java.util.Optional;

import com.upm.tennis.dto.MatchDTO;
import com.upm.tennis.model.Match;
import com.upm.tennis.model.Player;
import com.upm.tennis.model.Referee;
import com.upm.tennis.model.ScoreManager;
import com.upm.tennis.view.ScoreMangerView;

public class RefereeScoreManagerController {

  private static final String SEMI_COLON_SYMBOL = ";";

  private static final String GREATER_THAN_SYMBOL = ">";

  private final ScoreManager scoreManager;

  private final ScoreMangerView scoreMangerView;

  public RefereeScoreManagerController(ScoreManager scoreManager, ScoreMangerView scoreMangerView) {
    this.scoreManager = scoreManager;
    this.scoreMangerView = scoreMangerView;
  }

  public void createPlayer(String commandParameters) {
    Optional<String> name = this.getParameterValueByKey(commandParameters, "name", SEMI_COLON_SYMBOL);
    if (name.isEmpty()) {
      this.scoreMangerView.showNotValidCommandMessage();
      return;
    }
    this.scoreManager.addNewPlayer(name.get());
    this.scoreMangerView.showSuccessMessage();
  }

  public void readPlayers() {
    List<Player> players = this.scoreManager.getPlayers();
    this.scoreMangerView.showPlayerList(players);
  }

  public void createMatch(String commandParameters, Referee referee) {
    Optional<String> numberOfSets = this.getParameterValueByKey(commandParameters, "sets", SEMI_COLON_SYMBOL);
    Optional<String> playerIds = this.getParameterValueByKey(commandParameters, "ids", SEMI_COLON_SYMBOL);

    if (numberOfSets.isEmpty() || playerIds.isEmpty()) {
      this.scoreMangerView.showNotValidCommandMessage();
      return;
    }

    Optional<String> playerOneId = this.getValueByPosition(playerIds.get().split(","), 0);
    Optional<String> playerTwoId = this.getValueByPosition(playerIds.get().split(","), 1);

    if (playerOneId.isEmpty() || playerTwoId.isEmpty()) {
      this.scoreMangerView.showNotValidCommandMessage();
      return;
    }

    MatchDTO matchDTO = new MatchDTO();
    matchDTO.setRefereeName(referee.getName());
    matchDTO.setPlayerOneId(Integer.valueOf(playerOneId.get()));
    matchDTO.setPlayerTwoId(Integer.valueOf(playerTwoId.get()));
    matchDTO.setNumberOfSets(Integer.valueOf(numberOfSets.get()));

    Optional<Match> newMatch = this.scoreManager.addNewMatch(matchDTO);

    if (newMatch.isEmpty()) {
      this.scoreMangerView.showMatchNotCreated();
      return;
    }
    this.scoreMangerView.showMatchInfo(newMatch.get());
    this.scoreMangerView.showScoreBoard(newMatch.get().getScoreBoard());
  }

  public void addMatchPoint(String commandParameters) {
    Optional<String> matchId = this.getParameterValueByKey(commandParameters, "id", GREATER_THAN_SYMBOL);
    Optional<String> matchPointAction = this.getValueByPosition(commandParameters.split(GREATER_THAN_SYMBOL), 1);

    if (matchId.isEmpty() || matchPointAction.isEmpty()) {
      this.scoreMangerView.showNotValidCommandMessage();
      return;
    }
    boolean isMatchFinishedOrNotExist = this.scoreManager.isMatchByIdFinishedOrNotExists(Integer.valueOf(matchId.get()));

    if (isMatchFinishedOrNotExist) {
      this.scoreMangerView.showNotMatchIsFinishedOrNotExits();
      return;
    }

    Optional<Match> match = this.scoreManager.addMatchPoint(Integer.valueOf(matchId.get()), matchPointAction.get());

    if (match.isEmpty()) {
      this.scoreMangerView.showMatchNotFound();
      return;
    }
    match.get().updateScoreBoard();
    this.scoreMangerView.showScoreBoard(match.get().getScoreBoard());
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
