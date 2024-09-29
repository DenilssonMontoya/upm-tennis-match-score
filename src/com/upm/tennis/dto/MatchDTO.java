package com.upm.tennis.dto;

public class MatchDTO {

  private Integer playerOneId;

  private Integer playerTwoId;

  private Integer numberOfSets;

  private String refereeName;

  public MatchDTO() {
  }

  public Integer getPlayerOneId() {
    return playerOneId;
  }

  public void setPlayerOneId(Integer playerOneId) {
    this.playerOneId = playerOneId;
  }

  public Integer getPlayerTwoId() {
    return playerTwoId;
  }

  public void setPlayerTwoId(Integer playerTwoId) {
    this.playerTwoId = playerTwoId;
  }

  public Integer getNumberOfSets() {
    return numberOfSets;
  }

  public void setNumberOfSets(Integer numberOfSets) {
    this.numberOfSets = numberOfSets;
  }

  public String getRefereeName() {
    return refereeName;
  }

  public void setRefereeName(String refereeName) {
    this.refereeName = refereeName;
  }
}
