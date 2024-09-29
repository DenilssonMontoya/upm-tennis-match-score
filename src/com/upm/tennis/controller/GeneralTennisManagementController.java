package com.upm.tennis.controller;

import java.util.Optional;

import com.upm.tennis.model.Referee;
import com.upm.tennis.model.TennisManagement;
import com.upm.tennis.view.RefereeMenuView;
import com.upm.tennis.view.TennisManagementView;

public class GeneralTennisManagementController {

  private final TennisManagement tennisManagement;

  private final TennisManagementView tennisManagementView;

  private final RefereeMenuView refereeMenuView;

  public GeneralTennisManagementController(TennisManagement tennisManagement, TennisManagementView tennisManagementView,
      RefereeMenuView refereeMenuView) {
    this.tennisManagement = tennisManagement;
    this.tennisManagementView = tennisManagementView;
    this.refereeMenuView = refereeMenuView;
  }

  public void createReferee(String commandParameters) {
    Optional<String> name = this.getParameterValueByKey(commandParameters, "name");
    Optional<String> password = this.getParameterValueByKey(commandParameters, "password");

    if (name.isEmpty() || password.isEmpty()) {
      this.tennisManagementView.showNotValidCommandMessage();
      return;
    }
    this.tennisManagement.addNewReferee(name.get(), password.get());
    this.tennisManagementView.showSuccessMessage();
  }

  public void login(String commandParameters) {
    Optional<String> name = this.getParameterValueByKey(commandParameters, "name");
    Optional<String> password = this.getParameterValueByKey(commandParameters, "password");

    if (name.isEmpty() || password.isEmpty()) {
      this.tennisManagementView.showNotValidCommandMessage();
      return;
    }
    Optional<Referee> loggedReferee = this.tennisManagement.login(name.get(), password.get());

    if (loggedReferee.isEmpty()) {
      this.tennisManagementView.showFailedLoginMessage();
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
