package Gui;

import Logic.IController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Created by Eno on 27/06/2017.
 */
public class AlphabetPanel extends BorderPane {
    private IController controller;
    private TextField tapeAlphabetTextField;
    private TextField inputAlphabetFromTapeAlphabetTextField;
    private TextField inputAlphabetTextField;
    private Label alphabetNoteLabel;

    public AlphabetPanel(IController iController) {
        setController(iController);
        createGui();
    }

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public TextField getTapeAlphabetTextField() {
        if (tapeAlphabetTextField == null) {
            tapeAlphabetTextField = Utility.createTextField("", 200);
            tapeAlphabetTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (tapeAlphabetTextField.isEditable()) {
                        String text = tapeAlphabetTextField.getText().trim();
                        boolean isTextEmpty = text == null || text.isEmpty();
                        if (isTextEmpty) {
                            getInputAlphabetFromTapeAlphabetTextField().setText("");
                        } else {
                            tapeAlphabetTextField.setText(text);
                            getInputAlphabetFromTapeAlphabetTextField().setText(getController().getTapeAlphabetForInputAlphabet(text));
                        }
                    }
                }
            });
        }
        tapeAlphabetTextField.setEffect(null);
        return tapeAlphabetTextField;
    }

    public void setTapeAlphabetTextField(TextField tapeAlphabetTextField) {
        this.tapeAlphabetTextField = tapeAlphabetTextField;
    }

    public TextField getInputAlphabetFromTapeAlphabetTextField() {
        if (inputAlphabetFromTapeAlphabetTextField == null) {
            inputAlphabetFromTapeAlphabetTextField = Utility.createTextField("", 100);
            inputAlphabetFromTapeAlphabetTextField.setEditable(false);
            inputAlphabetFromTapeAlphabetTextField.setDisable(true);
        }
        return inputAlphabetFromTapeAlphabetTextField;
    }

    public void setInputAlphabetFromTapeAlphabetTextField(TextField inputAlphabetFromTapeAlphabetTextField) {
        this.inputAlphabetFromTapeAlphabetTextField = inputAlphabetFromTapeAlphabetTextField;
    }

    public TextField getInputAlphabetTextField() {
        if (inputAlphabetTextField == null) {
            inputAlphabetTextField = Utility.createTextField("", 100);
        }
        return inputAlphabetTextField;
    }

    public void setInputAlphabetTextField(TextField inputAlphabetTextField) {
        this.inputAlphabetTextField = inputAlphabetTextField;
    }

    public Label getAlphabetNoteLabel() {
        if (alphabetNoteLabel == null) {
            alphabetNoteLabel = new Label("Use '@' as blank symbol.");
            alphabetNoteLabel.setFont(Font.font(12));
            alphabetNoteLabel.setPadding(new Insets(5, 0, 0, 30));
        }
        return alphabetNoteLabel;
    }

    public void setAlphabetNoteLabel(Label alphabetNoteLabel) {
        this.alphabetNoteLabel = alphabetNoteLabel;
    }

    private void createGui(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(3);
        gridPane.setVgap(4);
        Label tapeLabel = Utility.createLabel("Tape Alphabet");
        gridPane.add(tapeLabel, 0, 0);
        gridPane.add(getTapeAlphabetTextField(), 1, 0);
        gridPane.add(Utility.createLabel("Input Alphabet"), 0, 2);
        gridPane.add(
                new HBox(getInputAlphabetFromTapeAlphabetTextField(), Utility.createLabel("+"), getInputAlphabetTextField())
                , 1, 2);
        gridPane.add(getAlphabetNoteLabel(), 1, 3);

        tapeLabel.setPadding(new Insets(0, 30, 0, 0));
        this.setCenter(gridPane);
    }
}
