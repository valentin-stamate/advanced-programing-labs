package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.game.GameData;
import sample.game.Player;
import sample.game.Token;
import sample.timekeeper.TimeKeeper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label playerNumberLabel, gameTimeLabel;
    @FXML
    private TextField playersTextField;
    @FXML
    private Button startButton;
    @FXML
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private List<Player> playerList;
    private List<Token> tokenList;
    private List<Token> tokenListDraw;

    private GameData gameData;

    private TimeKeeper timeKeeper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicsContext = canvas.getGraphicsContext2D();
        playerList = new ArrayList<>();
        tokenList = new ArrayList<>();
        tokenListDraw = new ArrayList<>();
        gameData = new GameData((int)canvas.getWidth(), (int)canvas.getHeight(), tokenList);

        gameData.startGame();

        timeKeeper = new TimeKeeper(gameTimeLabel, gameData, 120);

        startButton.setOnAction(this::onStart);
    }

    private void onStart(ActionEvent actionEvent) {

        int playerNumberString = 10;

        try {
            playerNumberString = Integer.parseInt(playersTextField.getText());
        } catch (Exception ignored) { }


        setPlayers(playerNumberString);

        playerList = new ArrayList<>();

        int playerNumber = gameData.getPlayerNumber();

        for (int i = 0; i < playerNumber; i++) {
            playerList.add(new Player(i, gameData, graphicsContext));
        }

        for (int i = 0; i < playerNumber * 3; i++) {
            int randomI = GameData.randomInt(0, playerNumber);
            int randomJ = GameData.randomInt(0, playerNumber);
            Token token = new Token(randomI, randomJ, i, gameData, graphicsContext);

            tokenList.add(token);
            tokenListDraw.add(token);
        }

        playerList.forEach(player -> {
            new Thread(player).start();
        });

        gameData.startGame();

        new Thread(timeKeeper).start();
        new Thread(this::drawShapes).start();
    }

    private void setPlayers(int playerNumber) {
        gameData.setPlayerNumber(playerNumber);

        if (playerNumber > 20) {
            gameData.setPlayerNumber(20);
        }

        if (playerNumber < 10) {
            gameData.setPlayerNumber(10);
        }

        this.playerNumberLabel.setText("Players: " + gameData.getPlayerNumber());
        startButton.setDisable(true);
    }

    int frameCount = 0;
    private void drawShapes() {

        while (true) {
            clearCanvas();

            tokenListDraw.forEach(Token::draw);
            playerList.forEach(Player::draw);

            if (tokenList.isEmpty()) {
                gameData.finishGame();
                finishGame();
                break;
            }

            if (gameData.isGameFinished()) {
                System.out.println("Game finished: Time exceeded");
                break;
            }

            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            frameCount++;

        }

    }

    private void finishGame() {

        Player winner = playerList.get(0);

        for (Player player : playerList) {
            if (winner.getScore() < player.getScore()) {
                winner = player;
            }
        }

        winner.makeWinner();

        tokenListDraw.forEach(Token::draw);
        playerList.forEach(Player::draw);

        System.out.println("Game finished");
    }

    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
