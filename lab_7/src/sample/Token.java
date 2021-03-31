package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Token {
    private final GameData gameData;
    private final GraphicsContext graphicsContext;
    private final int tokenCount;
    private final Color tokenColor;

    public Token(int tokenCount, GameData gameData, GraphicsContext graphicsContext) {
        this.gameData = gameData;
        this.graphicsContext = graphicsContext;
        this.tokenCount = tokenCount;
        this.tokenColor = GameData.getRandomColor();
    }


    public void draw() {
        graphicsContext.setFill(tokenColor);
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.fillRect( tokenCount * gameData.getPlayerBoxSize(), 0, gameData.getPlayerBoxSize(), gameData.getPlayerBoxSize());
        graphicsContext.strokeRect( tokenCount * gameData.getPlayerBoxSize(), 0, gameData.getPlayerBoxSize(), gameData.getPlayerBoxSize());
    }
}
