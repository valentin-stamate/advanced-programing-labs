package sample.game;

import com.github.javafaker.Faker;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Player implements Runnable {

    private final GameData gameData;
    private final GraphicsContext graphicsContext;
    private final int playerCount;
    private final Color playerColor;
    private int score = 0;
    private final String playerName;

    private boolean isWinner = false;

    private boolean isChoosing = false;

    private final List<Token> playerTokenList;

    private final int timeToWait;

    public Player(int playerCount, GameData gameData, GraphicsContext graphicsContext) {
        this.gameData = gameData;
        this.graphicsContext = graphicsContext;
        this.playerCount = playerCount;
        this.playerColor = GameData.getRandomColor();
        this.playerTokenList = new ArrayList<>();

        Faker faker = new Faker();
        this.playerName = faker.name().firstName();
        timeToWait = GameData.randomInt(100, 200);
    }

    public void addToken(Token token, boolean addScore) {
        playerTokenList.add(token);
        token.setPicked();
        if (addScore) {
            score += token.getI() + token.getJ();
        }
    }

    @Override
    public void run() {

        List<Token> tokenList = gameData.getTokenList();

        while (true) {
            if (gameData.isGameFinished()) {
                break;
            }

            try {
                Thread.sleep(timeToWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (tokenList.size() > 0 && !gameData.getPickingProgress()) {

                gameData.setPickingProgress(true);
                isChoosing = true;

                Token randomToken = pickSmartToken();

                if (randomToken == null) {
                    continue;
                }

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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

    private Token pickRandomToken() {
        List<Token> tokenList = gameData.getTokenList();
        int randomTokenIndex = GameData.randomInt(0, tokenList.size() - 1);

        Token tokenPicked = tokenList.get(randomTokenIndex);

        addToken(tokenPicked, true);

        return tokenPicked;
    }

    private Token pickSmartToken() {
        List<Token> tokenList = gameData.getTokenList();

        HashSet<Integer> nodeHashSet = new HashSet<>();

        playerTokenList.forEach(token -> {
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
            tokenPicked = tokenList.get(0);
            addToken(tokenPicked, false);
        } else {
            addToken(tokenPicked, true);
        }

        return tokenPicked;
    }

    public void draw() {

        graphicsContext.setFill(playerColor);

        if (isChoosing) {
            graphicsContext.setFill(Color.rgb(100, 100, 100));
        }

        if (isWinner) {
            graphicsContext.setFill(Color.rgb(20, 20, 20));
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

    public int getScore() {
        return score;
    }

    public void makeWinner() {
        isWinner = true;
    }
}
