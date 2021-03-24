package sample.shape;

import javafx.scene.canvas.GraphicsContext;

public abstract class Shape {
    protected final ShapeValues shapeValues;

    public Shape(ShapeValues shapeValues) {
        this.shapeValues = shapeValues;
    }

    public abstract void draw(final GraphicsContext graphicsContext);
}
