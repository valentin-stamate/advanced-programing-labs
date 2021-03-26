package shape;

import javafx.scene.canvas.GraphicsContext;

public abstract class Shape {
    public final static int PACMAN_SHAPE = 1;
    public final static int POLYGON_SHAPE = 2;
    public static final int FREE_SHAPE = 3;

    protected final ShapeValues shapeValues;

    public Shape(ShapeValues shapeValues) {
        this.shapeValues = shapeValues;
    }

    public abstract void draw(final GraphicsContext graphicsContext);

    public boolean onSelectEvent(int x, int y) {

        int width = shapeValues.getWidthValue();
        int height = shapeValues.getHeightValue();

        int shapeX = shapeValues.getX() - width / 2;
        int shapeY = shapeValues.getY() - height / 2;

        return (shapeX < x && shapeX + width > x) && (shapeY < y && shapeY + height > y);
    }

    public void addFreeShapePoint(int x, int y) {
        shapeValues.addFreeShapePoint(x, y);
    }

    public void setFreeDrawingWidthAndHeight() {
        int minX = 1000, maxX = 0;
        int minY = 1000, maxY = 0;

        int[] freePointsX = shapeValues.getFreePointsX();
        int[] freePointsY = shapeValues.getFreePointsY();
        int freePointsLength = shapeValues.getFreePointsLength();

        for (int i = 0; i < freePointsLength; i++) {
            int x = freePointsX[i];
            int y = freePointsY[i];

            minX = Math.min(x, minX);
            maxX = Math.max(x, maxX);

            minY = Math.min(y, minY);
            maxY = Math.max(y, maxY);
        }

       shapeValues.setSize(maxX - minX, maxY - minY);

    }
}
