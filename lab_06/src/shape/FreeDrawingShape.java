package shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

        graphicsContext.setLineWidth(1);
        graphicsContext.setStroke(Color.BLACK);

        if (shapeValues.getShowOutline()) {
            graphicsContext.strokeRect(shapeValues.getX(), shapeValues.getY(), shapeValues.getWidthValue(), shapeValues.getHeightValue());
        }

    }

    public void finishFreeDrawing() {
        setFreeDrawingWidthAndHeight();
        showOutline(true);
        shapeValues.setShapeType(detectShapeType());
    }

    private String detectShapeType() {
        String shapeType = "Unknown type";

        if (this.isLine()) {
            shapeType = "Line Shape";
        } else if (this.isSquare()) {
            int sizeDifference = Math.abs(shapeValues.getWidthValue() - shapeValues.getHeightValue());

            shapeType = sizeDifference <= 40 ? "Square Shape" : "Rectangle Shape";
        }

        return shapeType;
    }

    private boolean isLine() {
        int lineLimit = 7;

        int width = shapeValues.getWidthValue();
        int height = shapeValues.getHeightValue();

        return width < lineLimit|| height < lineLimit;
    }

    private boolean isSquare() {

        int xStart = shapeValues.getX();
        int yStart = shapeValues.getY();

        int xEnd = xStart + shapeValues.getWidthValue();
        int yEnd = yStart + shapeValues.getHeightValue();

        int width = shapeValues.getWidthValue();
        int height = shapeValues.getHeightValue();

        int[] pointsX = shapeValues.getFreePointsX();
        int[] pointsY = shapeValues.getFreePointsY();
        int pointsNumber = shapeValues.getFreePointsLength();

        int startCount = 0;
        int topCount = 0;
        int rightCount = 0;
        int bottomCount = 0;

        int squareDeviation = 20;
        double percentageAllowance = 0.5;

        if (width <= squareDeviation * 2 + 10 || height <= squareDeviation * 2 + 10) {
            return false;
        }

        for (int i = 0; i < pointsNumber; i++) {
            int x = pointsX[i];
            int y = pointsY[i];

            /* START SIDE */
            if (x >= xStart &&  x <= xStart + squareDeviation && y >= yStart && y <= yEnd) {
                startCount++;
            }

            /* TOP SIDE */
            if (x >= xStart && x <= xEnd && y >= yStart && y <= yStart + squareDeviation) {
                topCount++;
            }

            /* RIGHT SIDE */
            if (x <= xEnd && x >= xEnd - squareDeviation && y >= yStart && y <= yEnd) {
                rightCount++;
            }

            /* BOTTOM SIDE */
            if (x >= xStart && x <= xEnd && y <= yEnd && y >= yEnd - squareDeviation) {
                bottomCount++;
            }

        }

        return percentageAllowance * width <= topCount &&
                percentageAllowance * width <= bottomCount &&
                percentageAllowance * height <= startCount &&
                percentageAllowance * height <= rightCount;
    }

}
