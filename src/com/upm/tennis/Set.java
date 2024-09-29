package com.upm.tennis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Set {

    private final List<Game> games;

    public Set() {
        this.games = new ArrayList<>();
    }

    public void addPointService(Player service, Player rest) {
        Game currentGame = this.getCurrentOrNewGame(service, rest);
        currentGame.addPoint(service);
    }

    public void addLackService(Player service, Player rest) {
        Game currentGame = this.getCurrentOrNewGame(service, rest);
        currentGame.addLackService(service, rest);
    }

    public void addPointRest(Player service, Player rest) {
        Game currentGame = this.getCurrentOrNewGame(service, rest);
        currentGame.addPoint(rest);
    }

    public boolean isFinished() {
        Map<Player, Integer> gameWinsByPlayer = getGameWinsByPlayer();
        return this.isThereSetWinner(gameWinsByPlayer);
    }

    public boolean hasCurrentSetHaveGames() {
        return !this.games.isEmpty();
    }

    private Game getCurrentOrNewGame(Player service, Player rest) {
        Game currentGame = getCurrentGame();
        if (currentGame != null) {
            return currentGame;
        }
        return addNewGame(service, rest);
    }

    public Game getCurrentGame() {
        for (Game game : this.games) {
            if (!game.isFinished()) {
                return game;
            }
        }
        return null;
    }

    private Game addNewGame(Player service, Player rest) {
        Game newGame = newGameType(service, rest);
        this.games.add(newGame);
        return newGame;
    }

    private Game newGameType(Player service, Player rest) {
        if (this.countAllFinishedGame() < 12) {
            return new Standard(service, rest);
        } else {
            return new TieBreak(service, rest);
        }
    }

    private int countAllFinishedGame() {
        int numberOfFinishedGames = 0;
        for (Game game : this.games) {
            if (game.isFinished()) {
                numberOfFinishedGames++;
            }
        }
        return numberOfFinishedGames;
    }

    private boolean isThereSetWinner(Map<Player, Integer> gameWinsByPlayer) {
        if (gameWinsByPlayer.isEmpty()) return false;
        if (gameWinsByPlayer.size() == 1) {
            Iterator<Entry<Player, Integer>> iterator = gameWinsByPlayer.entrySet().iterator();
            Entry<Player, Integer> playerEvaluated = iterator.next();
            return this.hasPlayerWonSet(playerEvaluated);
        } else {
            return this.hasAnyPlayerWonSet(gameWinsByPlayer);
        }
    }

    private boolean hasAnyPlayerWonSet(Map<Player, Integer> gameWinsByPlayer) {
        Iterator<Entry<Player, Integer>> iterator = gameWinsByPlayer.entrySet().iterator();
        Entry<Player, Integer> playerEvaluatedA = iterator.next();
        Entry<Player, Integer> playerEvaluatedB = iterator.next();

        return hasPlayerWonSet(playerEvaluatedA, playerEvaluatedB.getValue()) ||
                hasPlayerWonSet(playerEvaluatedB, playerEvaluatedA.getValue());
    }

    private boolean hasPlayerWonSet(Entry<Player, Integer> evaluatedPlayer, int opponentPlayerWins) {
        int numberOfFinishedGames = evaluatedPlayer.getValue() + opponentPlayerWins;
        int numberOfEvaluatedPlayerWins = evaluatedPlayer.getValue();
        int numberOfEvaluatedPlayerWinsDifference = numberOfEvaluatedPlayerWins - opponentPlayerWins;

        if (numberOfFinishedGames < 6) return false;
        if (numberOfEvaluatedPlayerWins <= opponentPlayerWins) return false;

        return ((numberOfEvaluatedPlayerWinsDifference >= 2 && numberOfFinishedGames <= 12) ||
                (numberOfEvaluatedPlayerWinsDifference >= 1 && numberOfFinishedGames == 13))
                && numberOfEvaluatedPlayerWins >= 6;
    }

    private boolean hasPlayerWonSet(Entry<Player, Integer> playerEvaluated) {
        return this.hasPlayerWonSet(playerEvaluated, 0);
    }

    public Map<Player, Integer> getGameWinsByPlayer() {
        Map<Player, Integer> playerWinsMap = new HashMap<>();
        for (Game game : this.games) {
            Player winner = game.getWinner();
            if (winner != null) {
                playerWinsMap.put(winner, playerWinsMap.getOrDefault(winner, 0) + 1);
            }
        }
        return playerWinsMap;
    }

    public boolean doesSetHasTieBreakGame() {
        return this.games.size() == 13;
    }

    public Player getRestPlayerFromFirstPointInLastFinishedGame() {
        Game lastFinishedGame = this.getLastFinishedGame();
        if (lastFinishedGame == null) return null;
        return lastFinishedGame.getRestPlayerFromFirstPoint();
    }

    private Game getLastFinishedGame() {
        Game lastFinishedGame = null;
        for (Game game : this.games) {
            if (game.isFinished()) {
                lastFinishedGame = game;
            }
        }
        return lastFinishedGame;
    }

    public Player getNexServicePlayerFromCurrentGame() {
        Game currentGame = this.getCurrentGame();
        Player nextServicePlayer = null;

        if (currentGame != null) {
            nextServicePlayer = currentGame.getNextServicePlayerFromNonFirstPointInGame();
        }
        return nextServicePlayer;
    }

}
