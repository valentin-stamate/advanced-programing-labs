package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import panel.Panel;
import panel.PanelField;
import shape.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private final String RESOURCE_PATH = "res";

    @FXML
    private Button loadButton, saveButton, resetButton, exitButton, toggleDrawingModeButton;
    @FXML
    private Button updateValuesButton;

    @FXML
    private Label widthLabel, heightLabel, strokeLabel, sidesLabel;
    @FXML
    private TextField widthTextField, heightTextField, strokeTextField, sidesTextField;

    @FXML
    private MenuItem pacmanMenuItem, polygonMenuItem, freeShapeMenuItem;
    private int shapeType = Shape.PACMAN_SHAPE;

    @FXML
    private SplitMenuButton shapeMenuButton;

    private Panel panel;

    @FXML
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private ShapeValues shapeValues;

    private boolean pauseDrawing = false;

    private boolean drawingThreadRunning = true;

    List<Shape> shapes;
    Shape lastFreeDrawingShape;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shapeValues = new ShapeValues();
        graphicsContext = canvas.getGraphicsContext2D();
        shapes = new ArrayList<>();

        loadButton.setOnAction(this::onLoad);
        saveButton.setOnAction(this::onSave);
        toggleDrawingModeButton.setOnAction(this::onToggleDrawingMode);
        resetButton.setOnAction(this::onReset);
        exitButton.setOnAction(this::onExit);

        updateValuesButton.setOnAction(this::updateValues);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onCanvasClick);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onCanvasDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, this::onCanvasReleased);

        panel = new Panel(
                new PanelField(widthLabel, widthTextField),
                new PanelField(heightLabel, heightTextField),
                new PanelField(strokeLabel, strokeTextField),
                new PanelField(sidesLabel, sidesTextField),
                shapeMenuButton
        );

        Runnable runnable = this::drawShapes;
        Thread drawingThread = new Thread(runnable);
        drawingThread.start();

        panel.setPacmanPanel();

        pacmanMenuItem.setOnAction(e -> onPacmanItemPressed());
        polygonMenuItem.setOnAction(e -> onPolygonPressed());
        freeShapeMenuItem.setOnAction(e -> onFreeShapePressed());

    }

    private void onFreeShapePressed() {
        shapeType = Shape.FREE_SHAPE;
        panel.setFreeShapePanel();
    }

    private void onPolygonPressed() {
        shapeType = Shape.POLYGON_SHAPE;
        panel.setPolygonPanel();
    }

    private void onPacmanItemPressed() {
        shapeType = Shape.PACMAN_SHAPE;
        panel.setPacmanPanel();
    }

    private void onToggleDrawingMode(ActionEvent actionEvent) {
        pauseDrawing = !pauseDrawing;

        String modeText = pauseDrawing ? "Start Drawing" : "Pause Drawing";

        toggleDrawingModeButton.setText(modeText);
    }

    private void onCanvasClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && !pauseDrawing) {
            addShape((int)mouseEvent.getX(), (int)mouseEvent.getY());
        }

        if (mouseEvent.getButton() == MouseButton.MIDDLE) {
            onDetectShapeAction((int)mouseEvent.getX(), (int)mouseEvent.getY());
        }

        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            onDeleteAction((int)mouseEvent.getX(), (int)mouseEvent.getY());
        }
    }

    private void onCanvasDragged(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && shapeType == Shape.FREE_SHAPE) {
            lastFreeDrawingShape.addFreeShapePoint((int)mouseEvent.getX(), (int)mouseEvent.getY());
        }
    }

    private void onCanvasReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && shapeType == Shape.FREE_SHAPE) {
            lastFreeDrawingShape.setFreeDrawingWidthAndHeight();
        }
    }

    private void onDeleteAction(int x, int y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.onSelectEvent(x, y)) {
                shapes.remove(shape);
                break;
            }
        }
    }

    private void onDetectShapeAction(int x, int y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.onSelectEvent(x, y)) {
                showShapeType(shape);
                break;
            }
        }
    }

    private void showShapeType(Shape shape) {
        if (shape instanceof PacmanShape) {
            System.out.println("Pacman Shape");
        } else if (shape instanceof ImageShape) {
            System.out.println("Image Shape");
        } else if (shape instanceof PolygonShape) {
            System.out.println("Polygon Shape");
        } else if (shape instanceof FreeDrawingShape) {
            System.out.println("Free Shape");
        }
    }

    private void addShape(int x, int y) {
        ShapeValues newShapeValues = shapeValues.getCopy();
        newShapeValues.setPosition(x, y);

        Shape shape;

        switch (shapeType) {
            case Shape.PACMAN_SHAPE:
                shape = new PacmanShape(newShapeValues);
                break;
            case Shape.FREE_SHAPE:
                shape = new FreeDrawingShape(newShapeValues);
                lastFreeDrawingShape = shape;
                break;
            default:
                shape = new PolygonShape(newShapeValues);
        }

        shapes.add(shape);
    }

    private void drawShapes() {

        while (drawingThreadRunning) {
            clearCanvas();

            shapes.forEach(shape -> shape.draw(graphicsContext));

            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void updateValues(ActionEvent actionEvent) {
        shapeValues = panel.updateValues();
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

        ShapeValues shapeValues = new ShapeValues();
        shapeValues.setImage(image);
        shapeValues.setSize((int)image.getWidth(), (int)image.getHeight());

        Shape imageShape = new ImageShape(shapeValues);

        shapes.add(0, imageShape);
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
        shapes.clear();
    }

    private void onExit(ActionEvent actionEvent) {
        drawingThreadRunning = false;
        getStageFromEvent(actionEvent).close();
    }

}
