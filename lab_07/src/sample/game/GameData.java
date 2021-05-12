package sample.game;

import javafx.scene.paint.Color;
import java.util.List;
import java.util.Map;

public class GameData {
    private boolean gameRunning = false;

    private int playerNumber = 10;
    private int playerBoxSize = 60;
    private final int canvasWidth;
    private final int canvasHeight;
    private final List<Token> tokenList;
    private List<Player> playerList;

    public GameData(int canvasWidth, int canvasHeight, List<Token> tokenList) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.tokenList = tokenList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void startGame() {
        gameRunning = true;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
        playerBoxSize = canvasWidth / playerNumber;
    }

    public int getPlayerBoxSize() {
        return playerBoxSize;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public static Color getRandomColor() {
        return Color.rgb(randomInt(0, 256), randomInt(0, 256), randomInt(0, 256));
    }

    public static int randomInt(int start, int end) {
        if (end - start == 0) {
            return 0;
        }
        int random = ((int)(Math.random() * 10000)) % (end - start);
        return random + start;
    }

    public boolean isGameFinished() {
        return !gameRunning;
    }

    public void finishGame() {
        gameRunning = false;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }
}
