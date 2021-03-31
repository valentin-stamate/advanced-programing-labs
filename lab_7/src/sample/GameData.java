package sample;

import javafx.scene.paint.Color;

public class GameData {
    private boolean gameRunning = false;

    private int playerNumber = 10;
    private int playerBoxSize = 60;
    private final int canvasWidth;
    private final int canvasHeight;

    public GameData(int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    public void startGame() {
        gameRunning = true;
    }

    public boolean getRunningStatus() {
        return gameRunning;
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

    private static int randomInt(int start, int end) {
        int random = ((int)(Math.random() * 10000)) % (end - start);
        return random + start;
    }

}
