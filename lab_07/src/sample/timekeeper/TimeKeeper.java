package sample.timekeeper;

import javafx.application.Platform;
import javafx.scene.control.Label;
import sample.game.GameData;

public class TimeKeeper implements Runnable {

    private long time;

    private final Label gameTimeLabel;

    private final GameData gameData;

    public TimeKeeper(Label gameTimeLabel, GameData gameData, int time) {
        this.gameData = gameData;
        this.gameTimeLabel = gameTimeLabel;
        this.time = time;
        updateTime();
    }

    @Override
    public void run() {

        while (true) {

            if (gameData.isGameFinished()) {
                break;
            }

            if (time < 0) {
                gameData.finishGame();
            }

            Platform.runLater(this::updateTime);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time--;
        }

    }

    public void updateTime() {
        gameTimeLabel.setText(secondsToTime());
    }

    private String secondsToTime() {
        return "" + time / 60 + ":" + time % 60;
    }

}
