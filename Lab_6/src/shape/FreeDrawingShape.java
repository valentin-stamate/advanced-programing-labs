package shape;

import javafx.scene.canvas.GraphicsContext;

public class FreeDrawingShape extends Shape{

    public FreeDrawingShape(ShapeValues shapeValues) {
        super(shapeValues);
        shapeValues.initializeFreePoints();
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(shapeValues.getFillColor());
        graphicsContext.setStroke(shapeValues.getStrokeColor());
        graphicsContext.setLineWidth(shapeValues.getStrokeValue());

        int[] freePointsX = shapeValues.getFreePointsX();
        int[] freePointsY = shapeValues.getFreePointsY();
        int freePointsLength = shapeValues.getFreePointsLength();

        for (int i = 0; i < freePointsLength; i++) {
            graphicsContext.fillRect(freePointsX[i], freePointsY[i], shapeValues.getStrokeValue(), shapeValues.getStrokeValue());
        }

    }
}
