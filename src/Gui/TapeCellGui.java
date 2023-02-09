package Gui;

import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;

import java.io.Serializable;

/**
 * Created by Eno on 08/06/2017.
 */
public class TapeCellGui extends Button implements Serializable {

    private String defaultStyle = "";
    private String selectedStyle = "-fx-border-color: deepskyblue;";
    public TapeCellGui(String text) {
        this.setText(text);
        this.setMinWidth(50);
        this.setMinHeight(50);
        this.setTextAlignment(TextAlignment.CENTER);
        this.defaultStyle = this.getStyle();
    }

    public void setDefaultStyle(){
        this.setStyle(defaultStyle);
    }

    public void setSelectedStyle(){
        this.setStyle(selectedStyle);
    }

}
