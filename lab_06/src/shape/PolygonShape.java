package shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PolygonShape extends Shape{

    private final int polygonType;

    public PolygonShape(ShapeValues shapeValues) {
        super(shapeValues);

        int sides = shapeValues.getSides();
        double radius = shapeValues.getWidthValue() * 1.0 / 2;

        double[] pointsX = new double[sides];
        double[] pointsY = new double[sides];

        double alpha = 45;
        double angleStep = 360.0 / sides;

        int drawX = shapeValues.getX();
        int drawY = shapeValues.getY();

        int width = shapeValues.getWidthValue();
        shapeValues.setPosition(drawX, drawY);

        for (int i = 0; i < sides; i++) {

            double angleInRadius = toRadius(alpha);

            double xPlus = radius * Math.cos(angleInRadius);
            double yPlus = radius * Math.sin(angleInRadius);

            int x = drawX + (int)xPlus + width / 2;
            int y = drawY + (int)yPlus + width / 2;

            pointsX[i] = x;
            pointsY[i] = y;

            alpha += angleStep;
        }

        polygonType = ((int)(Math.random() * 1000)) % 2;

        shapeValues.setPoints(pointsX, pointsY);
        shapeValues.setShapeType("Polygon with " + sides + " sides");
    }

    private double toRadius(double alpha) {
        return (alpha * 2 * 3.141592) / 360.0;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(shapeValues.getFillColor());
        graphicsContext.setStroke(shapeValues.getStrokeColor());
        graphicsContext.setLineWidth(shapeValues.getStrokeValue());

        if (polygonType == 0) {
            graphicsContext.fillPolygon(shapeValues.getPointsX(), shapeValues.getPointsY(), shapeValues.getPointsX().length);
        } else {
            graphicsContext.strokePolygon(shapeValues.getPointsX(), shapeValues.getPointsY(), shapeValues.getPointsX().length);
        }

        graphicsContext.setLineWidth(1);
        graphicsContext.setStroke(Color.BLACK);

        if (shapeValues.getShowOutline()) {
            graphicsContext.strokeRect(shapeValues.getX(), shapeValues.getY(), shapeValues.getWidthValue(), shapeValues.getHeightValue());
        }
    }
}
