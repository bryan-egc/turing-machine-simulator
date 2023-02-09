package Gui;

import Logic.IController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.util.Callback;

/**
 * Created by Eno on 06/06/2017.
 */
public class TransitionFunctionTable {
    private IController controller;
    private ObservableList<TransitionFunctionModel> data;
    private TableView<TransitionFunctionModel> tableView;
    private TableColumn currentStateColumn;
    private TableColumn currentValueColumn;
    private TableColumn newStateColumn;
    private TableColumn newValueColumn;
    private TableColumn moveColumn;
    private TableColumn numberCol;
    private Callback<TableColumn, TableCell> statusCellFactory;
    private Callback<TableColumn, TableCell> alphabetCellFactory;
    private Callback<TableColumn, TableCell> alphabetWithBlankCellFactory;
    private Callback<TableColumn, TableCell> moveCellFactory;
    private ContextMenu contextMenu;

    public TransitionFunctionTable(IController iController) {
        setController(iController);
        setData(getController().getTransitionFunctionModels());
        getTableView();
        if (getData().isEmpty()) {
            addRow(50);
        } else {
            addRow(5);
        }
    }

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public ObservableList<TransitionFunctionModel> getData() {
        return data;
    }

    public void setData(ObservableList<TransitionFunctionModel> data) {
        this.data = data;
    }

    public TableView<TransitionFunctionModel> getTableView() {
        if (tableView == null) {
            tableView = new TableView<TransitionFunctionModel>();
            tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            tableView.setEditable(true);
            tableView.getColumns().addAll(
                    getNumberCol(), getCurrentStateColumn(), getCurrentValueColumn()
                    , getNewStateColumn(), getNewValueColumn(), getMoveColumn());
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setPrefWidth(400);
            tableView.setItems(getData());
            tableView.setContextMenu(getContextMenu());
            tableView.setPrefHeight(200);
        }
        return tableView;
    }

    public void setTableView(TableView<TransitionFunctionModel> tableView) {
        this.tableView = tableView;
    }

    public void setEditable(boolean editable) {
        getTableView().setEditable(editable);
        if (editable) {
            getTableView().setContextMenu(getContextMenu());
        } else {
            getTableView().setContextMenu(null);
        }
    }

    public ContextMenu getContextMenu() {
        if (contextMenu == null) {
            final MenuItem addTenEmptyRowsMenuItem = new MenuItem("Add 10 Empty Rows");
            addTenEmptyRowsMenuItem.setOnAction(e -> {
                addRow(10);
            });

            final MenuItem removeMenuItem = new MenuItem("Delete Selected Rows");
            removeMenuItem.setOnAction(e -> {
                getData().removeAll(getTableView().getSelectionModel().getSelectedItems());
            });

            contextMenu = new ContextMenu(addTenEmptyRowsMenuItem, removeMenuItem);
        }
        return contextMenu;
    }

    public void setContextMenu(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
    }


    public TableColumn getCurrentStateColumn() {
        if (currentStateColumn == null) {
            currentStateColumn = new TableColumn();
            currentStateColumn.setSortable(false);
            Label currentStateLabel = new Label("C.S");
            currentStateLabel.setTooltip(new Tooltip("Current State"));
            currentStateColumn.setGraphic(currentStateLabel);
            currentStateColumn.setStyle("-fx-alignment: CENTER;");
            currentStateColumn.setPrefWidth(40);
            currentStateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransitionFunctionModel, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<TransitionFunctionModel, String> p) {
                    return p.getValue().currentStatusProperty();
                }
            });
            currentStateColumn.setCellFactory(getStatusCellFactory());
            currentStateColumn.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<TransitionFunctionModel, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<TransitionFunctionModel, String> t) {
                            TransitionFunctionModel row = ((TransitionFunctionModel) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            row.setCurrentStatus(t.getNewValue());
                            getTableView().requestFocus();
                        }
                    });
        }
        return currentStateColumn;
    }

    public void setCurrentStateColumn(TableColumn currentStateColumn) {
        this.currentStateColumn = currentStateColumn;
    }

    public TableColumn getCurrentValueColumn() {
        if (currentValueColumn == null) {
            currentValueColumn = new TableColumn();
            currentValueColumn.setSortable(false);
            Label label = new Label("C.V");
            label.setTooltip(new Tooltip("Current Value"));
            currentValueColumn.setGraphic(label);
            currentValueColumn.setStyle("-fx-alignment: CENTER;");
            currentValueColumn.setPrefWidth(30);
            currentValueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransitionFunctionModel, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<TransitionFunctionModel, String> p) {
                    return p.getValue().currentValueProperty();
                }
            });
            currentValueColumn.setCellFactory(getAlphabetWithBlankCellFactory());
            currentValueColumn.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<TransitionFunctionModel, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<TransitionFunctionModel, String> t) {
                            TransitionFunctionModel row = ((TransitionFunctionModel) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            row.setCurrentValue(t.getNewValue());
                            getTableView().requestFocus();
                        }
                    });
        }
        return currentValueColumn;
    }

    public void setCurrentValueColumn(TableColumn currentValueColumn) {
        this.currentValueColumn = currentValueColumn;
    }

    public TableColumn getNewStateColumn() {
        if (newStateColumn == null) {
            newStateColumn = new TableColumn();
            newStateColumn.setSortable(false);
            Label label = new Label("N.S");
            label.setTooltip(new Tooltip("New State"));
            newStateColumn.setGraphic(label);
            newStateColumn.setStyle("-fx-alignment: CENTER;");
            newStateColumn.setPrefWidth(40);
            newStateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransitionFunctionModel, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<TransitionFunctionModel, String> p) {
                    return p.getValue().newStatusProperty();
                }
            });
            newStateColumn.setCellFactory(getStatusCellFactory());
            newStateColumn.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<TransitionFunctionModel, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<TransitionFunctionModel, String> t) {
                            TransitionFunctionModel row = ((TransitionFunctionModel) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            row.setNewStatus(t.getNewValue());
                            getTableView().requestFocus();
                        }
                    });
        }
        return newStateColumn;
    }

    public void setNewStateColumn(TableColumn newStateColumn) {
        this.newStateColumn = newStateColumn;
    }

    public TableColumn getNewValueColumn() {
        if (newValueColumn == null) {
            newValueColumn = new TableColumn();
            newValueColumn.setSortable(false);
            Label label = new Label("N.V");
            label.setTooltip(new Tooltip("New Value"));
            newValueColumn.setGraphic(label);
            newValueColumn.setStyle("-fx-alignment: CENTER;");
            newValueColumn.setPrefWidth(30);
            newValueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransitionFunctionModel, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<TransitionFunctionModel, String> p) {
                    return p.getValue().newValueProperty();
                }
            });
            newValueColumn.setCellFactory(getAlphabetCellFactory());
            newValueColumn.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<TransitionFunctionModel, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<TransitionFunctionModel, String> t) {
                            TransitionFunctionModel row = ((TransitionFunctionModel) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            row.setNewValue(t.getNewValue());
                            getTableView().requestFocus();
                        }
                    });
        }
        return newValueColumn;
    }

    public void setNewValueColumn(TableColumn newValueColumn) {
        this.newValueColumn = newValueColumn;
    }

    public TableColumn getMoveColumn() {
        if (moveColumn == null) {
            moveColumn = new TableColumn();
            moveColumn.setSortable(false);
            Label label = new Label("M");
            label.setTooltip(new Tooltip("Move"));
            moveColumn.setGraphic(label);
            moveColumn.setStyle("-fx-alignment: CENTER;");
            moveColumn.setPrefWidth(30);
            moveColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransitionFunctionModel, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<TransitionFunctionModel, String> p) {
                    return p.getValue().directionOfMoveProperty();
                }
            });
            moveColumn.setCellFactory(getMoveCellFactory());
            moveColumn.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<TransitionFunctionModel, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<TransitionFunctionModel, String> t) {
                            TransitionFunctionModel row = ((TransitionFunctionModel) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            row.setDirectionOfMove(t.getNewValue());
                            getTableView().requestFocus();
                        }
                    });
        }
        return moveColumn;
    }

    public void setMoveColumn(TableColumn moveColumn) {
        this.moveColumn = moveColumn;
    }

    public TableColumn getNumberCol() {
        if (numberCol == null) {
            numberCol = new TableColumn("#");
            numberCol.setMaxWidth(25);
            numberCol.setMinWidth(25);
            numberCol.setStyle("-fx-alignment: CENTER;");
            numberCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransitionFunctionModel, TransitionFunctionModel>, ObservableValue<TransitionFunctionModel>>() {
                @Override
                public ObservableValue<TransitionFunctionModel> call(TableColumn.CellDataFeatures<TransitionFunctionModel, TransitionFunctionModel> p) {
                    return new ReadOnlyObjectWrapper(p.getValue());
                }
            });
            numberCol.setCellFactory(new Callback<TableColumn<TransitionFunctionModel, TransitionFunctionModel>, TableCell<TransitionFunctionModel, TransitionFunctionModel>>() {
                @Override
                public TableCell<TransitionFunctionModel, TransitionFunctionModel> call(TableColumn<TransitionFunctionModel, TransitionFunctionModel> param) {
                    return new TableCell<TransitionFunctionModel, TransitionFunctionModel>() {
                        @Override
                        protected void updateItem(TransitionFunctionModel item, boolean empty) {
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

    public Callback<TableColumn, TableCell> getStatusCellFactory() {
        if (statusCellFactory == null) {
            statusCellFactory = new Callback<TableColumn, TableCell>() {
                public TableCell call(TableColumn p) {
                    return new EditingCellSuggestion(getController().getStatesObservableList());
                }
            };
        }
        return statusCellFactory;
    }

    public void setStatusCellFactory(Callback<TableColumn, TableCell> statusCellFactory) {
        this.statusCellFactory = statusCellFactory;
    }

    public Callback<TableColumn, TableCell> getAlphabetCellFactory() {
        if (alphabetCellFactory == null) {
            alphabetCellFactory = new Callback<TableColumn, TableCell>() {
                public TableCell call(TableColumn p) {
                    return new EditingCellSuggestion(getController().getAlphabetObservableList());
                }
            };
        }
        return alphabetCellFactory;
    }

    public void setAlphabetCellFactory(Callback<TableColumn, TableCell> alphabetCellFactory) {
        this.alphabetCellFactory = alphabetCellFactory;
    }

    public Callback<TableColumn, TableCell> getAlphabetWithBlankCellFactory() {
        if (alphabetWithBlankCellFactory == null) {
            alphabetWithBlankCellFactory = new Callback<TableColumn, TableCell>() {
                public TableCell call(TableColumn p) {
                    return new EditingCellSuggestion(getController().getAlphabetObservableList());
                }
            };
        }
        return alphabetWithBlankCellFactory;
    }

    public void setAlphabetWithBlankCellFactory(Callback<TableColumn, TableCell> alphabetWithBlankCellFactory) {
        this.alphabetWithBlankCellFactory = alphabetWithBlankCellFactory;
    }

    public Callback<TableColumn, TableCell> getMoveCellFactory() {
        if (moveCellFactory == null) {
            ObservableList<String> stringObservableList = FXCollections.observableArrayList();
            stringObservableList.addAll("L", "l", "R", "r", "-");
            moveCellFactory = new Callback<TableColumn, TableCell>() {
                public TableCell call(TableColumn p) {
                    return new EditingCellSuggestion(stringObservableList);
                }
            };
        }
        return moveCellFactory;
    }

    public void setMoveCellFactory(Callback<TableColumn, TableCell> moveCellFactory) {
        this.moveCellFactory = moveCellFactory;
    }

    public void addRow(int nrOfRows) {
        for (int i = 0; i < nrOfRows; i++) {
            getTableView().getItems().add(new TransitionFunctionModel());
        }
    }

}
