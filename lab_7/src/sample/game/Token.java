package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Token extends Thread{
    private final GameData gameData;
    private final GraphicsContext graphicsContext;
    private final int tokenCount;
    private final Color tokenColor;
    private final int i;
    private final int j;

    private boolean tokenPicked = false;

    public Token(int i, int j, int tokenCount, GameData gameData, GraphicsContext graphicsContext) {
        this.i = i;
        this.j = j;
        this.gameData = gameData;
        this.graphicsContext = graphicsContext;
        this.tokenCount = tokenCount;
        this.tokenColor = GameData.getRandomColor();
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setPicked() {
        tokenPicked = true;
    }

    public void draw() {

        if (tokenPicked) {
            graphicsContext.setFill(Color.rgb(170, 170, 170));
        } else {
            graphicsContext.setFill(tokenColor);
        }
        graphicsContext.setStroke(Color.WHITE);

        int x = tokenCount * gameData.getPlayerBoxSize();
        int y = 0;

        int boxSize = gameData.getPlayerBoxSize();

        graphicsContext.fillRect( x, y, boxSize, boxSize);
        graphicsContext.strokeRect( x, y, boxSize, boxSize);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("(" + i + ", " + j + ")", x, (int)(y + boxSize / 2));
    }
}
