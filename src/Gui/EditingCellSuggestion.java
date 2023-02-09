package Gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import np.com.ngopal.control.AutoFillTextBox;

/**
 * Created by Eno on 07/06/2017.
 */
class EditingCellSuggestion extends TableCell<String, String> {

    private AutoFillTextBox textField;
    private String text = "";
    private ObservableList data;
    private String style;

    public EditingCellSuggestion(ObservableList data) {
        this.data = data;
        this.style = getStyle();
    }

    @Override
    public void startEdit() {

        super.startEdit();
        if (textField == null) {
            createTextField();
        }
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        //textField.getTextField().selectAll();
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(validateInput(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (textField != null) {
                textField.getTextField().setText(getString());
            }
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {

            setText(validateInput(getString()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    @Override
    public void commitEdit(String item) {

        if (isEditing()) {
            super.commitEdit(item);
        } else {
            final TableView table = getTableView();
            if (table != null) {
                TablePosition position = new TablePosition(getTableView(), getTableRow().getIndex(), getTableColumn());
                TableColumn.CellEditEvent editEvent = new TableColumn.CellEditEvent(table, position, TableColumn.editCommitEvent(), item);
                Event.fireEvent(getTableColumn(), editEvent);
            }
            updateItem(item, false);
            if (table != null) {
                table.edit(-1, null);
            }
        }
    }

    private void createTextField() {
        textField = new AutoFillTextBox(data);
        textField.setFilterMode(true);
        textField.setListLimit(100);
        textField.setFilterStyle(AutoFillTextBox.FilterStyle.CONTAINS);
        textField.getTextField().setText(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if ((t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.TAB) && !textField.getText().isEmpty()) {
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });

        textField.getTextField().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && textField != null) {
                    commitEdit(textField.getText());
                }
            }
        });
    }

    private String validateInput(String input) {
        setStyle(style);
        if(this.data.indexOf(input.replaceAll("\\s","")) < 0){
            setStyle("-fx-background-color:rgba(255,0,0,0.3);");
        }
        else{
            setStyle(style);
        }
        return input.replaceAll("\\s","");
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

}

