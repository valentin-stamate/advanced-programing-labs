package shape;

import javafx.scene.canvas.GraphicsContext;

public class ImageShape extends Shape {

    public ImageShape(ShapeValues shapeValues) {
        super(shapeValues);
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(shapeValues.getImage(), 0, 0);
    }
}
