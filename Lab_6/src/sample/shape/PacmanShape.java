package sample.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class PacmanShape extends Shape {

    public PacmanShape(ShapeValues shapeValues) {
        super(shapeValues);
    }

    @Override
    public void draw(final GraphicsContext graphicsContext) {
        graphicsContext.setFill(shapeValues.getFillColor());
        graphicsContext.setStroke(shapeValues.getStrokeColor());
        graphicsContext.setLineWidth(shapeValues.getStrokeValue());

        int width = shapeValues.getWidthValue();

        int x = shapeValues.getX() - width / 2;
        int y = shapeValues.getY() - width / 2;

        int randomNumber = ((int)(Math.random() * 1000)) % 2;

        if (randomNumber == 0) {
            graphicsContext.fillArc(x, y, shapeValues.getWidthValue(), width, 45, 240, ArcType.ROUND);
        } else {
            graphicsContext.strokeArc(x, y, shapeValues.getWidthValue(), width, 45, 240, ArcType.ROUND);
        }
    }

}
