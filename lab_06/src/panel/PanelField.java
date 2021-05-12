package panel;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PanelField {

    private final Label label;
    private final TextField textField;

    boolean textFiledDisables = false;

    public PanelField(Label label, TextField textField) {
        this.label = label;
        this.textField = textField;
    }

    public void setDisabledStatus(boolean status) {
        this.textFiledDisables = status;
        updateDisabledStatus();
    }

    public void updateDisabledStatus() {
        textField.setDisable(textFiledDisables);
    }

    public void updateTextFieldText(String name) {
        textField.setText(name);
    }

    public int getValue() {
        int value = 0;

        try {
            value = Integer.parseInt(textField.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public void updateText(String string) {
        textField.setText(string);
    }
}
