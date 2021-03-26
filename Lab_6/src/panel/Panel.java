package panel;

import javafx.scene.control.SplitMenuButton;
import shape.ShapeValues;

public class Panel {

    private final PanelField widthPanelField;
    private final PanelField heightPanelField;
    private final PanelField sidesPanelField;
    private final PanelField strokePanelField;

    private final SplitMenuButton splitMenuButton;

    public Panel(PanelField widthPanelFiled, PanelField heightPanelField, PanelField strokePanelField, PanelField sidesPanelField, SplitMenuButton splitMenuButton) {
        this.widthPanelField = widthPanelFiled;
        this.heightPanelField = heightPanelField;
        this.strokePanelField = strokePanelField;
        this.sidesPanelField = sidesPanelField;
        this.splitMenuButton = splitMenuButton;

        widthPanelField.updateText("" + ShapeValues.DEFAULT_WIDTH);
        heightPanelField.updateText("" + ShapeValues.DEFAULT_HEIGHT);
        strokePanelField.updateText("" + ShapeValues.DEFAULT_STROKE);
        sidesPanelField.updateText("" + ShapeValues.DEFAULT_SIDES);
    }

    public ShapeValues updateValues() {
        ShapeValues shapeValues = new ShapeValues();

        shapeValues.updateValues(
                widthPanelField.getValue(),
                heightPanelField.getValue(),
                strokePanelField.getValue(),
                sidesPanelField.getValue()
        );

        return shapeValues;
    }

    public void setPacmanPanel() {
        updatePanel(false, true, false, true);
    }

    public void setPolygonPanel() {
        updatePanel(false, true, false, false);
    }

    public void setFreeShapePanel() {
        updatePanel(true, true, false, true);
    }

    private void updatePanel(boolean widthStatus, boolean heightStatus, boolean strokeStatus, boolean sidesStatus) {
        widthPanelField.setDisabledStatus(widthStatus);
        heightPanelField.setDisabledStatus(heightStatus);
        strokePanelField.setDisabledStatus(strokeStatus);
        sidesPanelField.setDisabledStatus(sidesStatus);
    }
}
