package sample.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PlayerTokenPicker {

    private List<Player> playerList;
    private List<Token> tokenList;

    private long threadPickingId;
    private int playerPickingId = -1;

    public synchronized Token pickToken() {

        long currentThreadId = Thread.currentThread().getId();

        while (threadPickingId != currentThreadId) {
            try {
                wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Token token = pickGreedyToken();

        notifyRandomPlayer();
        return token;
    }

    private Token pickGreedyToken() {
        Player currentPlayer = playerList.get(playerPickingId);

        List<Token> bestTokens = new ArrayList<>();

        for (Player player : playerList) {
            if (player != currentPlayer) {
                List<Token> goodTokenList = player.getGoodTokens();

                int currentScore = player.getScore();
                Token playerBestToken = null;

                for (Token token : goodTokenList) {
                    int possibleScore = player.calculatePossibleScore(token);

                    if (possibleScore > currentScore) {
                        currentScore = possibleScore;
                        playerBestToken = token;
                    }
                }

                if (playerBestToken != null) {
                    bestTokens.add(playerBestToken);
                }
            }
        }

        List<Token> currentPlayerBestTokens = currentPlayer.getGoodTokens();

        for (Token token : currentPlayerBestTokens) {
            if (bestTokens.contains(token)) {
                return token;
            }
        }

        return null;
    }

    public void notifyRandomPlayer() {
        playerPickingId = GameData.randomInt(0, playerList.size());
        Thread playerThread = playerList.get(playerPickingId).getPlayerThread();
        threadPickingId = playerThread.getId();
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

}
