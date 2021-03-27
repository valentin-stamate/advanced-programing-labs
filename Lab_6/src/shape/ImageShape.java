package shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ImageShape extends Shape {

    public ImageShape(ShapeValues shapeValues) {
        super(shapeValues);
        Image image =  shapeValues.getImage();
        shapeValues.setSize((int)image.getWidth(), (int)image.getHeight());
        shapeValues.setShapeType("Image Shape");
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(shapeValues.getImage(), 0, 0);

        graphicsContext.setLineWidth(1);
        graphicsContext.setStroke(Color.BLACK);

        if (shapeValues.getShowOutline()) {
            graphicsContext.strokeRect(shapeValues.getX(), shapeValues.getY(), shapeValues.getWidthValue(), shapeValues.getHeightValue());
        }
    }
}
