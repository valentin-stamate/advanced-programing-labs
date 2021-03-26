package shape;

import javafx.scene.canvas.GraphicsContext;

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

        for (int i = 0; i < sides; i++) {

            double angleInRadius = toRadius(alpha);

            double xPlus = radius * Math.cos(angleInRadius);
            double yPlus = radius * Math.sin(angleInRadius);

            int x = shapeValues.getX() + (int)xPlus;
            int y = shapeValues.getY() + (int)yPlus;

            pointsX[i] = x;
            pointsY[i] = y;

            alpha += angleStep;
        }

        polygonType = ((int)(Math.random() * 1000)) % 2;

        shapeValues.setPoints(pointsX, pointsY);

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
    }
}
