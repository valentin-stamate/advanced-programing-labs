package sample.game;

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

        HashSet<Integer> nodeHashSet = new HashSet<>();

        currentPlayer.getTokenList().forEach(token -> {
            nodeHashSet.add(token.getI());
            nodeHashSet.add(token.getJ());
        });

        Token tokenPicked = null;

        for (Token token : tokenList) {
            if (nodeHashSet.contains(token.getI()) || nodeHashSet.contains(token.getJ())) {
                tokenPicked = token;
                break;
            }
        }

        if (tokenPicked == null) {
            tokenPicked = tokenList.get(GameData.randomInt(0, tokenList.size()));
        }

        return tokenPicked;
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
