package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.shape.PacmanShape;
import sample.shape.Shape;
import sample.shape.ShapeValues;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private final String RESOURCE_PATH = "res";

    @FXML
    private Button loadButton, saveButton, resetButton, exitButton;
    @FXML
    private Button updateValuesButton;

    @FXML
    private TextField widthTextField, heightTextField, strokeTextField, sidesTextFiled;

    @FXML
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private ShapeValues shapeValues;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shapeValues = new ShapeValues();
        graphicsContext = canvas.getGraphicsContext2D();

        loadButton.setOnAction(this::onLoad);
        saveButton.setOnAction(this::onSave);
        resetButton.setOnAction(this::onReset);
        exitButton.setOnAction(this::onExit);

        widthTextField.setText("" + ShapeValues.DEFAULT_WIDTH);
        heightTextField.setText("" + ShapeValues.DEFAULT_HEIGHT);
        sidesTextFiled.setText("" + ShapeValues.DEFAULT_SIDES);
        strokeTextField.setText("" + ShapeValues.DEFAULT_STROKE);

        updateValuesButton.setOnAction(this::updateValues);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onCanvasClick);
    }

    private void onCanvasClick(MouseEvent mouseEvent) {
        drawShape((int)mouseEvent.getX(), (int)mouseEvent.getY());

        System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
    }

    private void drawShape(int x, int y) {
        ShapeValues newShapeValues = shapeValues.getCopy();
        newShapeValues.setPosition(x, y);

        Shape shape = new PacmanShape(newShapeValues);
        shape.draw(graphicsContext);
    }

    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void updateValues(ActionEvent actionEvent) {

        int withValue;
        int heightValue;
        int sidesValue;
        int strokeValue;

        try {
            withValue = Integer.parseInt(widthTextField.getText());
            heightValue = Integer.parseInt(heightTextField.getText());
            sidesValue = Integer.parseInt(sidesTextFiled.getText());
            strokeValue = Integer.parseInt(strokeTextField.getText());
        } catch (Exception e) {
            System.out.println("Invalid input");
            return;
        }

        shapeValues.updateValues(withValue, heightValue, sidesValue, strokeValue);
        System.out.println(shapeValues);
    }

    private void onLoad(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(RESOURCE_PATH));

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
        fileChooser.setTitle("Load Image To Canvas");

        File imageFile = fileChooser.showOpenDialog(getStageFromEvent(actionEvent));

        if (imageFile == null) {
            return;
        }

        Image image = new Image(imageFile.toURI().toString());
        graphicsContext.drawImage(image, 0, 0);
    }

    private void onSave(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(RESOURCE_PATH));

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
        fileChooser.setTitle("Save Canvas");

        Stage stage = getStageFromEvent(actionEvent);

        File imageFile = fileChooser.showSaveDialog(stage);
        WritableImage wi = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null, wi), null), "png", imageFile);
        } catch (Exception ignored) {}

    }

    private Stage getStageFromEvent(ActionEvent actionEvent) {
        return (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
    }

    private void onReset(ActionEvent actionEvent) {
        clearCanvas();
    }

    private void onExit(ActionEvent actionEvent) {
        getStageFromEvent(actionEvent).close();
    }

}
