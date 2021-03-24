package sample.shape;

import javafx.scene.paint.Color;

public class ShapeValues {
    public static int DEFAULT_WIDTH = 100;
    public static int DEFAULT_HEIGHT = 100;
    public static int DEFAULT_SIDES = 4;
    public static int DEFAULT_STROKE = 2;

    private int widthValue = DEFAULT_WIDTH = 100;
    private int heightValue = DEFAULT_HEIGHT = 100;
    private int sidesValue = DEFAULT_SIDES = 4;
    private int strokeValue = DEFAULT_STROKE = 2;

    private final int[] position = new int[]{0, 0};

    private final Color fillColor;
    private final Color strokeColor;

    private final int[] pointsX = new int[]{0};
    private final int[] pointsY = new int[]{0};

    public ShapeValues() {
        this.fillColor = getRandomColor();
        this.strokeColor = getRandomColor();
    }

    private static Color getRandomColor() {
        return Color.rgb(randomInt(0, 256), randomInt(0, 256), randomInt(0, 256));
    }

    private static int randomInt(int start, int end) {
        int random = ((int)(Math.random() * 10000)) % (end - start);
        return random + start;
    }

    public ShapeValues getCopy() {
        ShapeValues newShapeValues = new ShapeValues();
        newShapeValues.updateValues(widthValue, heightValue, sidesValue, strokeValue);

        return newShapeValues;
    }

    public void updateValues(int widthValue, int heightValue, int sidesValue, int strokeValue) {
        this.widthValue = widthValue;
        this.heightValue = heightValue;
        this.sidesValue = sidesValue;
        this.strokeValue = strokeValue;
    }

    public void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public int getWidthValue() {
        return widthValue;
    }

    public int getHeightValue() {
        return heightValue;
    }

    public int getSidesValue() {
        return sidesValue;
    }

    public int getStrokeValue() {
        return strokeValue;
    }

    public int getX() {
        return position[0];
    }

    public int getY() {
        return position[1];
    }

    @Override
    public String toString() {
        return "ShapeValues{" +
                "widthValue=" + widthValue +
                ", heightValue=" + heightValue +
                ", sidesValue=" + sidesValue +
                ", strokeValue=" + strokeValue +
                '}';
    }
}
