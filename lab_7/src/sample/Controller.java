package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label playerNumberLabel;
    @FXML
    private Button startButton;
    @FXML
    private Canvas canvas;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.setOnAction(this::onStart);
    }

    private void onStart(ActionEvent actionEvent) {

    }


}
