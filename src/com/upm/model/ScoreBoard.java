package com.upm.model;

import java.util.*;

public class ScoreBoard {
    private List<HashMap<String, String>> scoreInformation;

    public ScoreBoard() {
        scoreInformation = new ArrayList<>();
    }

    public void initializeScoreBoard(Integer numberOfSets, Point currentPoint) {
        scoreInformation.add(initializePlayerRow(currentPoint.getService(), numberOfSets, "*"));
        scoreInformation.add(initializePlayerRow(currentPoint.getRest(), numberOfSets, ""));
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

    protected void update(List<Set> sets, Set currentSet, Player nextService) {
        String player1Name = scoreInformation.get(0).get("name");
        String player2Name = scoreInformation.get(1).get("name");
        updateCurrentGamePoints(currentSet, player1Name, player2Name);
        updateSets(sets, player1Name, player2Name);
        updateServices(player1Name, player2Name, nextService);
    }

    private void updateSets(List<Set> sets, String player1Name, String player2Name) {
        Integer setNumber = 1;
        for (Set set : sets) {
            updateSet(set, setNumber, player1Name, player2Name);
            setNumber++;
        }
    }

    private void updateCurrentGamePoints(Set currentSet, String player1Name, String player2Name) {
        Integer player1PointCount = getCurrentPointCount(player1Name, currentSet.getCurrentGame());
        Integer player2PointCount = getCurrentPointCount(player2Name, currentSet.getCurrentGame());
        scoreInformation.get(0).put("currentGame", getCalculatedPoints(player1PointCount, player2PointCount));
        scoreInformation.get(1).put("currentGame", getCalculatedPoints(player2PointCount, player1PointCount));
    }

    private void updateSet(Set set, Integer setNumber, String player1Name, String player2Name) {
        Map<Player, Integer> playerVictoriesMap = set.getWonGamesByPlayer();
        Integer player1VictoryCount = getPlayerVictoryCount(player1Name, playerVictoriesMap);
        Integer player2VictoryCount = getPlayerVictoryCount(player2Name, playerVictoriesMap);
        scoreInformation.get(0).put("set" + setNumber, String.valueOf(player1VictoryCount));
        scoreInformation.get(1).put("set" + setNumber, String.valueOf(player2VictoryCount));
    }

    private void updateServices(String player1Name, String player2Name, Player nextServicePlayer) {
        scoreInformation.get(0).put("hasService", hasService(player1Name, nextServicePlayer));
        scoreInformation.get(1).put("hasService", hasService(player2Name, nextServicePlayer));
    }

    private static Integer getPlayerVictoryCount(String player2Name, Map<Player, Integer> playerVictoriesMap) {
        return playerVictoriesMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getName().equals(player2Name))
                .map(Map.Entry::getValue).findFirst().orElse(0);
    }

    private Integer getCurrentPointCount(String playerName, Game currentGame) {
        Integer pointCount = 0;
        if (currentGame == null) return 0;
        for (Point point : currentGame.getPoints()) {
            if (point.getWinner() == null) continue;
            if (playerName.equals(point.getWinner().getName())) pointCount++;
        }
        return pointCount;
    }

    private String getCalculatedPoints(Integer pointCountPlayer1, Integer pointCountPlayer2) {
        if (pointCountPlayer1 == 0) return "0";
        if (pointCountPlayer1 == 1) return "15";
        if (pointCountPlayer1 == 2) return "30";
        if (pointCountPlayer1 == 3) return "40";
        if (pointCountPlayer1 >= 4 && pointCountPlayer1.equals(pointCountPlayer2)
                || pointCountPlayer1 < pointCountPlayer2) {
            return "40";
        }
        return "AD";
    }

    public void displayScoreBoard() {
        System.out.println(formatRow(scoreInformation.get(0)));
        System.out.println(formatRow(scoreInformation.get(1)));
    }

    private String formatRow(HashMap<String, String> row) {
        return row.getOrDefault("hasService", "") + " "
                + row.getOrDefault("name", "") + " :  "
                + row.getOrDefault("currentGame", "0") + " "
                + getSetsString(row);
    }

    private String getSetsString(HashMap<String, String> row) {
        String setString = "";
        for (int i = 1; i < 6; i++) {
            setString = setString + ' ' + row.getOrDefault("set" + i, "");
        }
        return setString;
    }

    private String hasService(String playerName, Player nextServicePlayer) {
        return Objects.equals(playerName, nextServicePlayer.getName()) ? "*" : "";
    }
}
