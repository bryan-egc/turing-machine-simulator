package Gui;

import Logic.IController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * Created by Eno on 06/06/2017.
 */
public class StateTable {
    private IController controller;
    private ObservableList<StateModel> data;
    private TableView<StateModel> tableView;
    private TableColumn stateColumn;
    private TableColumn numberCol;
    private Callback<TableColumn, TableCell> cellFactory;
    private ContextMenu contextMenu;

    public StateTable(IController iController) {
        setController(iController);
        setData(getController().getStateModels());
        getTableView();
        if(getData().isEmpty()){
            addRow(50);
        }
        else {
            addRow(5);
        }
    }

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public ObservableList<StateModel> getData() {
        return data;
    }

    public void setData(ObservableList<StateModel> data) {
        this.data = data;
    }

    public TableView<StateModel> getTableView() {
        if (tableView == null) {
            tableView = new TableView<StateModel>();
            tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            tableView.setEditable(true);
            tableView.getColumns().addAll(getNumberCol(), getStateColumn());
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setPrefWidth(210);
            tableView.setItems(getData());
            tableView.setContextMenu(getContextMenu());
            tableView.setPrefHeight(200);
        }
        return tableView;
    }

    public void setTableView(TableView<StateModel> tableView) {
        this.tableView = tableView;
    }

    public void setEditable(boolean editable){
        getTableView().setEditable(editable);
        if(editable){
            getTableView().setContextMenu(getContextMenu());
        }
        else {
            getTableView().setContextMenu(null);
        }
    }

    public ContextMenu getContextMenu() {
        if(contextMenu == null){
            final MenuItem addTenEmptyRowsMenuItem = new MenuItem("Add 10 Empty Rows");
            addTenEmptyRowsMenuItem.setOnAction(e -> {
                addRow(10);
            });

            final MenuItem removeMenuItem = new MenuItem("Delete Selected Rows");
            removeMenuItem.setOnAction(e -> {
                getData().removeAll(getTableView().getSelectionModel().getSelectedItems());
            });

            final MenuItem addInputAlphabetElementAsStates = new MenuItem("Add Input Alphabet Element As States");
            addInputAlphabetElementAsStates.setOnAction(e -> {
                addInputAlphabetElementAsStates();
            });

            contextMenu = new ContextMenu(addTenEmptyRowsMenuItem, removeMenuItem, new SeparatorMenuItem(), addInputAlphabetElementAsStates);
        }
        return contextMenu;
    }

    public void setContextMenu(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
    }

    private void addInputAlphabetElementAsStates() {
        for(String s : getController().getInputs().getInputAlphabet()){
            getData().add(0, new StateModel(new SimpleStringProperty("Q."+s)));
        }
    }

    public TableColumn getStateColumn() {
        if (stateColumn == null) {
            stateColumn = new TableColumn("");
            stateColumn.setStyle("-fx-alignment: CENTER;");
            stateColumn.setPrefWidth(180);
            stateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StateModel, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<StateModel, String> p) {
                    return p.getValue().stateProperty();
                }
            });
            stateColumn.setCellFactory(getCellFactory());
            stateColumn.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<StateModel, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<StateModel, String> t) {
                            StateModel row = ((StateModel) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            row.setState(t.getNewValue());
                            getTableView().requestFocus();
                        }
                    });
        }
        return stateColumn;
    }

    public void setStateColumn(TableColumn stateColumn) {
        this.stateColumn = stateColumn;
    }

    public TableColumn getNumberCol() {
        if (numberCol == null) {
            numberCol = new TableColumn("#");
            numberCol.setMaxWidth(25);
            numberCol.setMinWidth(25);
            numberCol.setStyle("-fx-alignment: CENTER;");
            numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StateModel, StateModel>, ObservableValue<StateModel>>() {
                @Override
                public ObservableValue<StateModel> call(TableColumn.CellDataFeatures<StateModel, StateModel> p) {
                    return new ReadOnlyObjectWrapper(p.getValue());
                }
            });
            numberCol.setCellFactory(new Callback<TableColumn<StateModel, StateModel>, TableCell<StateModel, StateModel>>() {
                @Override
                public TableCell<StateModel, StateModel> call(TableColumn<StateModel, StateModel> param) {
                    return new TableCell<StateModel, StateModel>() {
                        @Override
                        protected void updateItem(StateModel item, boolean empty) {
                            super.updateItem(item, empty);

                            if (this.getTableRow() != null && item != null) {
                                setText(this.getTableRow().getIndex() + 1 + "");
                            } else {
                                setText("");
                            }
                        }
                    };
                }
            });
            numberCol.setSortable(false);
        }
        return numberCol;
    }

    public void setNumberCol(TableColumn numberCol) {
        this.numberCol = numberCol;
    }

    public Callback<TableColumn, TableCell> getCellFactory() {
        if (cellFactory == null) {
            cellFactory = new Callback<TableColumn, TableCell>() {
                        public TableCell call(TableColumn p) {
                            return new EditingCell();
                        }
                    };
        }
        return cellFactory;
    }

    public void setCellFactory(Callback<TableColumn, TableCell> cellFactory) {
        this.cellFactory = cellFactory;
    }

    public void addRow(int nrOfRows) {
        for (int i = 0; i < nrOfRows; i++) {
            getTableView().getItems().add(new StateModel(new SimpleStringProperty("")));
        }
    }

    class EditingCell extends TableCell<StateModel, String> {

        private TextField textField;

        public EditingCell() {
            setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    startFullDrag();
                    getTableColumn().getTableView().getSelectionModel().select(getIndex(), getTableColumn());
                }
            });
            setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {

                @Override
                public void handle(MouseDragEvent event) {
                    getTableColumn().getTableView().getSelectionModel().select(getIndex(), getTableColumn());
                }
            });
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            textField.setText(getString());
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.requestFocus();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem());
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
                    textField.setText(getString());
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
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
            textField = new TextField(getString());
            textField.setStyle("-fx-alignment: CENTER;");
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                                    Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(textField.getText());
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}
