package shape;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ShapeValues {
    public static int DEFAULT_WIDTH = 100;
    public static int DEFAULT_HEIGHT = 100;
    public static int DEFAULT_SIDES = 4;
    public static int DEFAULT_STROKE = 2;

    private int widthValue = DEFAULT_WIDTH;
    private int heightValue = DEFAULT_HEIGHT;
    private int strokeValue = DEFAULT_STROKE;
    private int sidesValue = DEFAULT_SIDES;

    private final int[] position = new int[]{0, 0};

    private final Color fillColor;
    private final Color strokeColor;

    private double[] pointsX = new double[]{0};
    private double[] pointsY = new double[]{0};

    private Image image;

    private int[] freePointsX;
    private int[] freePointsY;
    private int freePointsNumber = 0;

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

    public void initializeFreePoints() {
        freePointsX = new int[10000];
        freePointsY = new int[10000];
    }

    public int[] getFreePointsX() {
        return freePointsX;
    }

    public int[] getFreePointsY() {
        return freePointsY;
    }

    public int getFreePointsLength() {
        return freePointsNumber;
    }

    public ShapeValues getCopy() {
        ShapeValues newShapeValues = new ShapeValues();
        newShapeValues.updateValues(widthValue, heightValue, strokeValue, sidesValue);

        return newShapeValues;
    }

    public void setPoints(double[] pointsX, double[] pointsY) {
        this.pointsX = pointsX;
        this.pointsY = pointsY;
    }

    public double[] getPointsX() {
        return pointsX;
    }

    public double[] getPointsY() {
        return pointsY;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void updateValues(int widthValue, int heightValue, int strokeValue, int sidesValue) {
        setSize(widthValue, heightValue);
        this.sidesValue = sidesValue;
        this.strokeValue = strokeValue;
    }

    public int getSides() {
        return sidesValue;
    }

    public void setSize(int width, int height) {
        this.widthValue = width;
        this.heightValue = height;
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
                ", strokeValue=" + strokeValue +
                '}';
    }

    public void addFreeShapePoint(int x, int y) {
        freePointsX[freePointsNumber] = x;
        freePointsY[freePointsNumber] = y;
        freePointsNumber++;
    }
}
