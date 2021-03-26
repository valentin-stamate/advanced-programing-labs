package shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class PacmanShape extends Shape {

    private final int pacmanType;

    public PacmanShape(ShapeValues shapeValues) {
        super(shapeValues);
        pacmanType = ((int)(Math.random() * 1000)) % 2;
    }

    @Override
    public void draw(final GraphicsContext graphicsContext) {
        graphicsContext.setFill(shapeValues.getFillColor());
        graphicsContext.setStroke(shapeValues.getStrokeColor());
        graphicsContext.setLineWidth(shapeValues.getStrokeValue());

        int width = shapeValues.getWidthValue();

        int x = shapeValues.getX() - width / 2;
        int y = shapeValues.getY() - width / 2;

        if (pacmanType == 0) {
            graphicsContext.fillArc(x, y, shapeValues.getWidthValue(), width, 30, 290, ArcType.ROUND);
        } else {
            graphicsContext.strokeArc(x, y, shapeValues.getWidthValue(), width, 30, 290, ArcType.ROUND);
        }
    }

}
