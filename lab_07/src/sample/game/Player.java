package sample.game;

import com.github.javafaker.Faker;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class Player implements Runnable {

    private final GameData gameData;
    private final GraphicsContext graphicsContext;
    private final int playerCount;
    private final Color playerColor;
    private int score = 0;
    private final String playerName;
    private final List<Token> playerTokenList;

    private boolean isWinner = false;
    private boolean isPicking = false;
    private final int timeToWait;

    private Thread playerThread;

    private final PlayerTokenPicker tokenPicker;

    public Player(int playerCount, GameData gameData, GraphicsContext graphicsContext, PlayerTokenPicker tokenPicker) {
        this.gameData = gameData;
        this.graphicsContext = graphicsContext;
        this.playerCount = playerCount;
        this.playerColor = GameData.getRandomColor();
        this.playerTokenList = new ArrayList<>();
        this.tokenPicker = tokenPicker;

        Faker faker = new Faker();
        this.playerName = faker.name().firstName();
        timeToWait = 500;
    }

    public void addToken(Token token) {
        playerTokenList.add(token);
        token.setPicked();
        score = calculateScore();
    }

    private int calculateScore() {
        int length = gameData.getPlayerNumber();
        int[][] G = initializeGraph();
        boolean[] visited = new boolean[length];

        int longestCycleLength = 0;
        for (int i = 0; i < length; i++) {
            if (!visited[i]) {
                int cycleLength = dfs(i, G, visited);
                longestCycleLength = Math.max(longestCycleLength, cycleLength);
            }
        }

        int tokenSum = getTokenSum();

        if (longestCycleLength == 0) {
            return tokenSum;
        }

        return tokenSum * (longestCycleLength / 3 + 1);
    }

    private int getTokenSum() {
        int tokenSum = 0;

        for (Token token : playerTokenList) {
            tokenSum += token.getI();
            tokenSum += token.getJ();
        }

        return tokenSum;
    }

    private int[][] initializeGraph() {
        int length = gameData.getPlayerNumber();
        int[][] G = new int[length][length];

        for (Token token : playerTokenList) {
            int i = token.getI();
            int j = token.getJ();

            G[i][j] = G[j][i] = 1;
        }

        return G;
    }

    private int dfs(int root, int[][] G, boolean[] visited) {
        int length = G.length;

        Stack<Integer> stack = new Stack<>();
        Stack<Integer> visitingOrder = new Stack<>();
        stack.add(root);

        int longestCycleLength = 0;

        while (!stack.empty()) {
            int n = stack.pop();
            visited[n] = true;
            visitingOrder.push(n);

            for (int j = 0; j < length; j++) {
                if (G[n][j] == 1) {
                    if (!visited[j]) {
                        stack.push(j);
                    } else {
                        Stack<Integer> visitingOrderCopy = new Stack<>();
                        Collections.copy(visitingOrderCopy, visitingOrder);

                        int cycleLength = 0;
                        while (!visitingOrderCopy.empty()) {
                            int visitedNode = visitingOrderCopy.pop();

                            cycleLength++;

                            if (visitedNode == j) {
                                break;
                            }
                        }

                        longestCycleLength = Math.max(longestCycleLength, cycleLength);
                    }
                }
            }
        }

        return longestCycleLength;
    }

    public int calculatePossibleScore(Token token) {
        playerTokenList.add(token);
        int newScore = calculateScore();
        playerTokenList.remove(token);
        return newScore;
    }

    public List<Token> getGoodTokens() {
        List<Token> goodTokens = new ArrayList<>();
        List<Token> tokenList = gameData.getTokenList();

        for (Token token : tokenList) {
            if (tokenIsGood(token)) {
                goodTokens.add(token);
            }
        }

        return goodTokens;
    }

    private boolean tokenIsGood(Token token) {
        HashSet<Integer> tokenHashSet = new HashSet<>();

        playerTokenList.forEach(tokenElement -> {
            tokenHashSet.add(tokenElement.getI());
            tokenHashSet.add(tokenElement.getJ());
        });

        return tokenHashSet.contains(token.getI()) || tokenHashSet.contains(token.getJ());
    }

    @Override
    public void run() {

        playerThread = Thread.currentThread();

        List<Token> tokenList = gameData.getTokenList();

        while (!gameData.isGameFinished() && tokenList.size() > 0) {

            Token randomToken = tokenPicker.pickToken();

            try {
                Thread.sleep(timeToWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (randomToken != null) {
                addToken(randomToken);
                tokenList.remove(randomToken);
            }
        }
    }

    public Thread getPlayerThread() {
        return playerThread;
    }

    public void draw() {

        graphicsContext.setFill(playerColor);

        if (isPicking) {
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

    public List<Token> getTokenList() {
        return playerTokenList;
    }
}
