package com.upm.tennis.controller;

import java.util.Optional;

import com.upm.tennis.model.Referee;
import com.upm.tennis.model.ScoreManager;
import com.upm.tennis.view.RefereeMenuView;
import com.upm.tennis.view.ScoreMangerView;

public class PublicScoreManagerController {

  private final ScoreManager scoreManager;

  private final ScoreMangerView scoreMangerView;

  private final RefereeMenuView refereeMenuView;

  public PublicScoreManagerController(ScoreManager scoreManager, ScoreMangerView scoreMangerView,
      RefereeMenuView refereeMenuView) {
    this.scoreManager = scoreManager;
    this.scoreMangerView = scoreMangerView;
    this.refereeMenuView = refereeMenuView;
  }

  public void createReferee(String commandParameters) {
    Optional<String> name = this.getParameterValueByKey(commandParameters, "name");
    Optional<String> password = this.getParameterValueByKey(commandParameters, "password");

    if (name.isEmpty() || password.isEmpty()) {
      this.scoreMangerView.showNotValidCommandMessage();
      return;
    }
    this.scoreManager.addNewReferee(name.get(), password.get());
    this.scoreMangerView.showSuccessMessage();
  }

  public void login(String commandParameters) {
    Optional<String> name = this.getParameterValueByKey(commandParameters, "name");
    Optional<String> password = this.getParameterValueByKey(commandParameters, "password");

    if (name.isEmpty() || password.isEmpty()) {
      this.scoreMangerView.showNotValidCommandMessage();
      return;
    }
    Optional<Referee> loggedReferee = this.scoreManager.login(name.get(), password.get());

    if (loggedReferee.isEmpty()) {
      this.scoreMangerView.showFailedLoginMessage();
      return;
    }

    this.refereeMenuView.displayOptions(loggedReferee.get());
  }

  private Optional<String> getParameterValueByKey(String commandParameters, String parameterKey) {
    String[] parameters = commandParameters.split(";");
    for (String parameter : parameters) {
      String[] parameterKeyValue = parameter.split(":");
      if (this.isValidParameterKey(parameterKeyValue, parameterKey)) {
        return Optional.of(parameterKeyValue[1]);
      }
    }
    return Optional.empty();
  }

  private boolean isValidParameterKey(String[] parameterKeyValue, String parameterKey) {
    return parameterKeyValue.length == 2 && parameterKeyValue[0].equals(parameterKey);
  }

}
