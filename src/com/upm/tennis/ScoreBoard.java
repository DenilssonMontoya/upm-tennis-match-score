package com.upm.tennis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ScoreBoard {
    private final List<HashMap<String, String>> scoreInformation;

    public ScoreBoard() {
        scoreInformation = new ArrayList<>();
    }

    public void initialize(Integer numberOfSets, Player service, Player rest) {
        scoreInformation.add(initializePlayerRow(service, numberOfSets, "*"));
        scoreInformation.add(initializePlayerRow(rest, numberOfSets, ""));
    }

    public void display() {
        System.out.println(formatRow(scoreInformation.get(0)));
        System.out.println(formatRow(scoreInformation.get(1)));
    }

    public void update(List<Set> sets, Set currentSet, Player nextService) {
        String playerOneName = scoreInformation.get(0).get("name");
        String playerTwoName = scoreInformation.get(1).get("name");
        updateCurrentGamePoints(currentSet, playerOneName, playerTwoName);
        updateSets(sets, playerOneName, playerTwoName);
        updateServices(playerOneName, playerTwoName, nextService);
    }

    private HashMap<String, String> initializePlayerRow(Player player, Integer numberOfSets, String service) {
        return new HashMap<String, String>() {{
            put("hasService", service);
            put("name", player.getName());
            put("currentGame", "0");
            for (int i = 1; i < numberOfSets + 1; i++) {
                put("set" + i, "_");
            }
        }};
    }

    private void updateSets(List<Set> sets, String playerOneName, String playerTwoName) {
        Integer setNumber = 1;
        for (Set set : sets) {
            updateSet(set, setNumber, playerOneName, playerTwoName);
            setNumber++;
        }
    }

    private void updateCurrentGamePoints(Set currentSet, String playerOneName, String playerTwoName) {
        Integer playerOnePointCount = getCurrentPointCount(playerOneName, currentSet.getCurrentGame());
        Integer playerTwoPointCount = getCurrentPointCount(playerTwoName, currentSet.getCurrentGame());
        scoreInformation.get(0).put("currentGame", getCalculatedPoints(playerOnePointCount, playerTwoPointCount));
        scoreInformation.get(1).put("currentGame", getCalculatedPoints(playerTwoPointCount, playerOnePointCount));
    }

    private void updateSet(Set set, Integer setNumber, String playerOneName, String playerTwoName) {
        Map<Player, Integer> playerVictoriesMap = set.getGameWinsByPlayer();
        Integer playerOneVictoryCount = getPlayerVictoryCount(playerOneName, playerVictoriesMap);
        Integer playerTwoVictoryCount = getPlayerVictoryCount(playerTwoName, playerVictoriesMap);
        scoreInformation.get(0).put("set" + setNumber, String.valueOf(playerOneVictoryCount));
        scoreInformation.get(1).put("set" + setNumber, String.valueOf(playerTwoVictoryCount));
    }

    private void updateServices(String playerOneName, String playerTwoName, Player nextServicePlayer) {
        scoreInformation.get(0).put("hasService", hasService(playerOneName, nextServicePlayer));
        scoreInformation.get(1).put("hasService", hasService(playerTwoName, nextServicePlayer));
    }

    private static Integer getPlayerVictoryCount(String playerName, Map<Player, Integer> playerVictoriesMap) {
        return playerVictoriesMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey()
                    .getName()
                    .equals(playerName))
                .map(Map.Entry::getValue)
            .findFirst()
            .orElse(0);
    }

    private Integer getCurrentPointCount(String playerName, Game currentGame) {
        Map<Player, Integer> pointsByPlayerMapInThisGame = currentGame.getPointsMap();
        return pointsByPlayerMapInThisGame.entrySet()
                .stream()
                .filter(entry -> entry.getKey()
                        .getName()
                        .equals(playerName))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(0);
    }

    private String getCalculatedPoints(Integer pointCountEvaluatedPlayer, Integer pointCountOpponentPlayer) {
        if (pointCountEvaluatedPlayer == 0) return "0";
        if (pointCountEvaluatedPlayer == 1) return "15";
        if (pointCountEvaluatedPlayer == 2) return "30";
        if (pointCountEvaluatedPlayer == 3) return "40";
        if (pointCountEvaluatedPlayer >= 4 && pointCountEvaluatedPlayer.equals(pointCountOpponentPlayer)
                || pointCountEvaluatedPlayer < pointCountOpponentPlayer) {
            return "40";
        }
        return "AD";
    }

    private String formatRow(HashMap<String, String> row) {
        return row.getOrDefault("hasService", "") + " "
                + row.getOrDefault("name", "") + " :  "
                + row.getOrDefault("currentGame", "0") + " "
                + getSetsString(row);
    }

    private String getSetsString(HashMap<String, String> row) {
        StringBuilder setString = new StringBuilder();
        for (int i = 1; i < 6; i++) {
            setString.append(row.getOrDefault("set" + i, ""));
        }
        return setString.toString();
    }

    private String hasService(String playerName, Player nextServicePlayer) {
        return Objects.equals(playerName, nextServicePlayer.getName()) ? "*" : "";
    }
}
