package sample;

import com.github.javafaker.Faker;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Player extends Thread{

    private final GameData gameData;
    private final GraphicsContext graphicsContext;
    private final int playerCount;
    private Color playerColor;
    private int score = 0;
    private final String playerName;

    private boolean isChoosing = false;

    private final List<Token> tokenList;

    private final int timeToWait;

    public Player(int playerCount, GameData gameData, GraphicsContext graphicsContext) {
        this.gameData = gameData;
        this.graphicsContext = graphicsContext;
        this.playerCount = playerCount;
        this.playerColor = GameData.getRandomColor();
        this.tokenList = new ArrayList<>();

        Faker faker = new Faker();
        this.playerName = faker.name().firstName();
        timeToWait = GameData.randomInt(100, 200);
    }

    public void addToken(Token token) {
        tokenList.add(token);
        token.setPicked();
        score += token.getI() + token.getJ();
    }

    @Override
    public void run() {
        super.run();

        List<Token> tokenList = gameData.getTokenList();

        while (true) {

            try {
                Thread.sleep(timeToWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (tokenList.size() > 0 && !gameData.getPickingProgress()) {

                gameData.setPickingProgress(true);
                isChoosing = true;

                System.out.println("Player " + playerCount);


                int randomTokenIndex = GameData.randomInt(0, tokenList.size() - 1);

                Token randomToken = tokenList.get(randomTokenIndex);

                if (randomToken == null) {
                    continue;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                addToken(randomToken);
                tokenList.remove(randomToken);

                isChoosing = false;
                gameData.setPickingProgress(false);

            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void draw() {

        graphicsContext.setFill(playerColor);

        if (isChoosing) {
            graphicsContext.setFill(Color.rgb(100, 100, 100));
        }
        graphicsContext.setStroke(Color.WHITE);

        int x = playerCount * gameData.getPlayerBoxSize();
        int y = gameData.getCanvasHeight() - gameData.getPlayerBoxSize();

        int boxSize = gameData.getPlayerBoxSize();

        graphicsContext.fillRect(x, y, boxSize, boxSize);
        graphicsContext.strokeRect(x, y, boxSize, boxSize);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("" + score, x + 10, (int)(y + boxSize / 2));
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillText(playerName, x, y - 10);
    }
}
