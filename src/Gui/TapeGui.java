package Gui;

import Logic.TransitionFunction;
import Logic.TuringMachine;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Eno on 05/06/2017.
 */
public class TapeGui extends BorderPane implements Serializable {
    private TuringMachine turingMachine;
    private HBox tape;
    private Label cursorLabel;
    private Button leftButton;
    private Button rightButton;
    private ScrollPane scrollPaneTape;
    private Pane paneTape;
    private HBox hBox;
    private ArrayList<TapeCellGui> tapeCells = new ArrayList<>();
    private ProgressIndicator progressIndicator;
    private Label nrOfMoveLabel;
    private Label currentTransitionFunctionLabel;
    private int nrOfMove = 0;
    private Button autoRunButton;
    private Button manualRunButton;
    private Button resetButton;
    private TextArea consoleTextField;
    private Slider slider;
    private GuiManager guiManager;

    public TapeGui(TuringMachine turingMachine, GuiManager guiManager) {
        setTuringMachine(turingMachine);
        setGuiManager(guiManager);
        createTapeCells();
        createGui();
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public TuringMachine getTuringMachine() {
        return turingMachine;
    }

    public void setTuringMachine(TuringMachine turingMachine) {
        this.turingMachine = turingMachine;
    }

    public ArrayList<TapeCellGui> getTapeCells() {
        return tapeCells;
    }

    public void setTapeCells(ArrayList<TapeCellGui> tapeCells) {
        this.tapeCells = tapeCells;
    }

    public ProgressIndicator getProgressIndicator() {
        if (progressIndicator == null) {
            progressIndicator = new ProgressIndicator(-1);
            progressIndicator.setVisible(false);
        }
        return progressIndicator;
    }

    public void setProgressIndicator(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    public Slider getSlider() {
        if (slider == null) {
            slider = new Slider();
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMin(1);
            slider.setMax(50);
            slider.setBlockIncrement(10);
            slider.setMinWidth(100);
            slider.setValue(1);
        }
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public TextArea getConsoleTextField() {
        if (consoleTextField == null) {
            consoleTextField = new TextArea();
            consoleTextField.setEditable(false);
            consoleTextField.setStyle("-fx-control-inner-background: dimgray;-fx-text-fill: white;-fx-font-weight: bold;-fx-font-size: 17");
            consoleTextField.setPrefHeight(300);
        }
        return consoleTextField;
    }

    public void setConsoleTextField(TextArea consoleTextField) {
        this.consoleTextField = consoleTextField;
    }

    public Label getNrOfMoveLabel() {
        if (nrOfMoveLabel == null) {
            nrOfMoveLabel = Utility.createLabel(getNrOfMove() + " Moves");
        }
        return nrOfMoveLabel;
    }

    public void setNrOfMoveLabel(Label nrOfMoveLabel) {
        this.nrOfMoveLabel = nrOfMoveLabel;
    }

    public Label getCurrentTransitionFunctionLabel() {
        if (currentTransitionFunctionLabel == null) {
            currentTransitionFunctionLabel = Utility.createLabel(getTuringMachine().getCurrentTransitionFunction().toString());
            currentTransitionFunctionLabel.setTextAlignment(TextAlignment.CENTER);
        }
        return currentTransitionFunctionLabel;
    }

    public void setCurrentTransitionFunctionLabel(Label currentTransitionFunctionLabel) {
        this.currentTransitionFunctionLabel = currentTransitionFunctionLabel;
    }

    public int getNrOfMove() {
        return nrOfMove;
    }

    public void setNrOfMove(int nrOfMove) {
        this.nrOfMove = nrOfMove;
    }

    public HBox getTape() {
        if (tape == null) {
            tape = new HBox();
            tape.getChildren().addAll(getTapeCells());
            tape.setLayoutX(50000);
        }
        return tape;
    }

    public void setTape(HBox tape) {
        this.tape = tape;
    }

    public Label getCursorLabel() {
        if (cursorLabel == null) {
            cursorLabel = Utility.createLabel("^\n" + getTuringMachine().getCurrentState());
            cursorLabel.setTextAlignment(TextAlignment.CENTER);
        }
        return cursorLabel;
    }

    public void setCursorLabel(Label cursorLabel) {
        this.cursorLabel = cursorLabel;
    }

    public Button getLeftButton() {
        if (leftButton == null) {
            leftButton = new Button("<");
            leftButton.setMinWidth(50);
            leftButton.setMinHeight(50);
            leftButton.setOnAction(e -> {
                moveTape(1, 1);
            });
        }
        return leftButton;
    }

    public void setLeftButton(Button leftButton) {
        this.leftButton = leftButton;
    }

    public Button getRightButton() {
        if (rightButton == null) {
            rightButton = new Button(">");
            rightButton.setMinWidth(50);
            rightButton.setMinHeight(50);
            rightButton.setOnAction(e -> {
                moveTape(-1, 1);
            });
        }
        return rightButton;
    }

    public void setRightButton(Button rightButton) {
        this.rightButton = rightButton;
    }

    public ScrollPane getScrollPaneTape() {
        if (scrollPaneTape == null) {
            scrollPaneTape = new ScrollPane();
            scrollPaneTape.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPaneTape.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        }
        return scrollPaneTape;
    }

    public void setScrollPaneTape(ScrollPane scrollPaneTape) {
        this.scrollPaneTape = scrollPaneTape;
    }

    public Pane getPaneTape() {
        if (paneTape == null) {
            paneTape = new Pane();
            paneTape.setPrefWidth(100000);
        }
        return paneTape;
    }

    public void setPaneTape(Pane paneTape) {
        this.paneTape = paneTape;
    }

    public HBox gethBox() {
        if (hBox == null) {
            hBox = new HBox();
            hBox.setSpacing(10);
        }
        return hBox;
    }

    public void sethBox(HBox hBox) {
        this.hBox = hBox;
    }

    private void createTapeCells() {
        for (String s : getTuringMachine().getTape()) {
            getTapeCells().add(new TapeCellGui(s));
        }
    }

    public Button getAutoRunButton() {
        if (autoRunButton == null) {
            autoRunButton = Utility.createButton("Auto Run");
            autoRunButton.setGraphic(Utility.getImageView("auto.png"));
            autoRunButton.setOnAction(e -> {
                setButtonsStatus(true);
                fixTapePos();
                new Thread(autoRun((long) getSlider().getValue())).start();
            });
        }
        return autoRunButton;
    }

    public void setAutoRunButton(Button autoRunButton) {
        this.autoRunButton = autoRunButton;
    }

    public Button getManualRunButton() {
        if (manualRunButton == null) {
            manualRunButton = Utility.createButton("Next");
            manualRunButton.setGraphic(Utility.getImageView("manual.png"));
            manualRunButton.setOnAction(e -> {
                //setButtonsStatus(true);
                fixTapePos();
                new Thread(manualRun((long) getSlider().getValue())).start();
            });
        }
        return manualRunButton;
    }

    public void setManualRunButton(Button manualRunButton) {
        this.manualRunButton = manualRunButton;
    }

    public Button getResetButton() {
        if (resetButton == null) {
            resetButton = Utility.createButton("Reset");
            resetButton.setGraphic(Utility.getImageView("reset.png"));
            resetButton.setOnAction(e -> {
                getGuiManager().goToTapeGui();
            });
        }
        return resetButton;
    }

    public void setResetButton(Button resetButton) {
        this.resetButton = resetButton;
    }

    private void setButtonsStatus(boolean disable) {
//        getAutoRunButton().setDisable(disable);
//        getManualRunButton().setDisable(disable);
//        getResetButton().setDisable(disable);
        getAutoRunButton().setVisible(!disable);
        getManualRunButton().setVisible(!disable);
        getResetButton().setVisible(!disable);
    }


    private void createGui() {
        getPaneTape().getChildren().add(getTape());
        getScrollPaneTape().setContent(getPaneTape());
        getScrollPaneTape().setHvalue(0.5);
        BorderPane tapeLeftRightBorderPane = new BorderPane();
        tapeLeftRightBorderPane.setTop(getScrollPaneTape());
        tapeLeftRightBorderPane.setCenter(getCursorLabel());
        gethBox().getChildren().addAll(getLeftButton(), tapeLeftRightBorderPane, getRightButton());
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gethBox());
        borderPane.setTop(createTopBorder());
        this.setTop(borderPane);
        BorderPane borderPaneBottom = new BorderPane();
        borderPaneBottom.setTop(createBottomBorderPane());
        borderPaneBottom.setBottom(createConsoleBorderPane());
        this.setCenter(borderPaneBottom);
    }

    private BorderPane createBottomBorderPane() {
        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox(Utility.createLabel("Speed"), getSlider());
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setAlignment(Pos.CENTER);
        borderPane.setLeft(hBox);
        borderPane.setRight(createBottomButtonsHBox());
        return borderPane;
    }

    private BorderPane createTopBorder() {
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(getProgressIndicator());
        borderPane.setRight(getNrOfMoveLabel());
        borderPane.setCenter(getCurrentTransitionFunctionLabel());
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        return borderPane;
    }

    private HBox createBottomButtonsHBox() {
        HBox hBox = new HBox(getResetButton(), getManualRunButton(), getAutoRunButton());
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(30);
        hBox.setPadding(new Insets(50, 20, 20, 20));
        return hBox;
    }

    private BorderPane createConsoleBorderPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(Utility.createLabel("Console"));
        borderPane.setCenter(getConsoleTextField());
        return borderPane;
    }

    private void beforeMove(TransitionFunction transitionFunction) {
        Platform.runLater(new Runnable() {
            public void run() {
                getProgressIndicator().setVisible(true);
                setNrOfMove(getNrOfMove() + 1);
                getNrOfMoveLabel().setText(getNrOfMove() + " moves");
                getTapeCells().get(getTuringMachine().getCurrentCell()).setSelectedStyle();
                getCurrentTransitionFunctionLabel().setText(getTuringMachine().getCurrentTransitionFunction() != null ? getTuringMachine().getCurrentTransitionFunction().toString() : "");
                getTapeCells().get(getTuringMachine().getCurrentCell()).setText(transitionFunction.getNewValue());
                getTuringMachine().getTape().set(getTuringMachine().getCurrentCell(), transitionFunction.getNewValue());
                getCursorLabel().setText("^\n" + getTuringMachine().getCurrentState());
                getConsoleTextField().appendText(getTuringMachine().getCurrentTransitionFunction().toString() + "\n");
            }
        });
    }

    private void afterMove(TransitionFunction transitionFunction, int move) {
        getTuringMachine().setCurrentState(transitionFunction.getNewStatus());
        getTuringMachine().setCurrentCell(getTuringMachine().getCurrentCell() + (-1 * move));
        getTuringMachine().setCurrentTransitionFunction(getTuringMachine().getTransitionFunctionForCurrentStates());
        getAutoRunButton().setDisable(false);
        getManualRunButton().setDisable(false);
        getResetButton().setDisable(false);
        if (getTuringMachine().isLastCell()) {
            Platform.runLater(new Runnable() {
                public void run() {
                    addBlankElementOnTape();
                }
            });
        }
    }

    private int getMove(TransitionFunction transitionFunction) {
        int move;
        if (transitionFunction.getDirectionOfMove().equalsIgnoreCase(TransitionFunction.LEFT_DIRECTION)) {
            move = 1;
        } else if (transitionFunction.getDirectionOfMove().equalsIgnoreCase(TransitionFunction.RIGHT_DIRECTION)) {
            move = -1;
        } else {
            move = 0;
        }
        return move;
    }

    private void action(long speedMillis) {
        TransitionFunction transitionFunction = getTuringMachine().getCurrentTransitionFunction();
        int move = getMove(transitionFunction);
        beforeMove(transitionFunction);
        moveTape(move, speedMillis);
        afterMove(transitionFunction, move);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Task<Integer> manualRun(long speedMillis) {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                new Thread() {
                    public void run() {

                        if (!getTuringMachine().isAcceptState(getTuringMachine().getCurrentState())
                                && !getTuringMachine().isRejectState(getTuringMachine().getCurrentState())
                                && !getTuringMachine().isOutTape()
                                && getTuringMachine().getCurrentTransitionFunction() != null) {
                            action(speedMillis);
                        }
                        Platform.runLater(new Runnable() {
                            public void run() {
                                getProgressIndicator().setVisible(false);
                                getEndMessage();
                            }
                        });
                    }
                }.start();
                return 1;
            }

        };
    }


    public Task<Integer> autoRun(long speedMillis) {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                new Thread() {
                    public void run() {
                        while (!getTuringMachine().isAcceptState(getTuringMachine().getCurrentState())
                                && !getTuringMachine().isRejectState(getTuringMachine().getCurrentState())
                                && !getTuringMachine().isOutTape()
                                && getTuringMachine().getCurrentTransitionFunction() != null) {
                            action(speedMillis);
                        }
                        Platform.runLater(new Runnable() {
                            public void run() {
                                getProgressIndicator().setVisible(false);
                                getEndMessage();
                            }
                        });
                    }
                }.start();
                return 1;
            }

        };
    }


    private void getEndMessage() {
        if (getTuringMachine().isAcceptState(getTuringMachine().getCurrentState())) {
            Utility.informForSuccessfulAction("Succssfull",
                    "You have reached an acceptable state",
                    "State : " + getTuringMachine().getCurrentState());
            setButtonsStatus(false);
        } else if (getTuringMachine().isRejectState(getTuringMachine().getCurrentState())) {
            Utility.waringForAction("Waring",
                    "You have reached a reject state",
                    "State : " + getTuringMachine().getCurrentState());
            setButtonsStatus(false);
        } else if (getTuringMachine().isOutTape()) {
            Utility.waringForAction("Waring",
                    "Out Tape", null);
            setButtonsStatus(false);
        } else if (getTuringMachine().getCurrentTransitionFunction() == null) {
            Utility.waringForAction("Waring",
                    "Tansition Function missing", null);
            setButtonsStatus(false);
        }
    }

    private void moveTape(int move, long speedMillis) {
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(speedMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(new Runnable() {
                public void run() {
                    getTape().setLayoutX(getTape().getLayoutX() + move);
                }
            });
        }
    }

    private void fixTapePos() {
        //// TODO: 28/06/2017 duhet rregulluar po nuk kam nerva :p
//        double pos = getTape().getLayoutX();
//        System.out.println("pos = " + pos);
//        double posAfterMove = getTuringMachine().getCurrentCell() * 50;
//        System.out.println("posAfterMove = " + posAfterMove);
//        System.out.println("pos + posAfterMove = " +( pos + posAfterMove));
//        if( (pos != ( 50000 + posAfterMove))){
//            getTape().setLayoutX(50000 + posAfterMove);
//        }
    }

    private void addBlankElementOnTape() {
        getTuringMachine().addBlankElementOnTape(10);
        for (int i = 0; i < 10; i++) {
            TapeCellGui tapeCell = new TapeCellGui("@");
            getTapeCells().add(tapeCell);
            getTape().getChildren().add(tapeCell);
            getTuringMachine().getTape().add("@");
        }
    }
}


//    public Task<Integer> autoRun2(long speedMillis) {
//        return new Task<Integer>() {
//            @Override
//            protected Integer call() throws Exception {
//                new Thread() {
//                    public void run() {
//                        int move = -1;
//                        while (!getTuringMachine().isAcceptState(getTuringMachine().getCurrentState())
//                                && !getTuringMachine().isRejectState(getTuringMachine().getCurrentState())
//                                && !getTuringMachine().isOutTape()
//                                && getTuringMachine().getCurrentTransitionFunction() != null) {
//                            TransitionFunction transitionFunction = getTuringMachine().getCurrentTransitionFunction();
//                            if (transitionFunction.getDirectionOfMove().equalsIgnoreCase(TransitionFunction.LEFT_DIRECTION)) {
//                                move = 1;
//                            } else if (transitionFunction.getDirectionOfMove().equalsIgnoreCase(TransitionFunction.RIGHT_DIRECTION)) {
//                                move = -1;
//                            } else {
//                                move = 0;
//                            }
//                            Platform.runLater(new Runnable() {
//                                public void run() {
//                                    getProgressIndicator().setVisible(true);
//                                    setNrOfMove(getNrOfMove() + 1);
//                                    getNrOfMoveLabel().setText(getNrOfMove() + " moves");
//                                    getTapeCells().get(getTuringMachine().getCurrentCell()).setSelectedStyle();
//                                    getCurrentTransitionFunctionLabel().setText(getTuringMachine().getCurrentTransitionFunction() != null ? getTuringMachine().getCurrentTransitionFunction().toString() : "");
//                                    getTapeCells().get(getTuringMachine().getCurrentCell()).setText(transitionFunction.getNewValue());
//                                    getTuringMachine().getTape().set(getTuringMachine().getCurrentCell(), transitionFunction.getNewValue());
//                                    getCursorLabel().setText("^\n" + getTuringMachine().getCurrentState());
//                                    getConsoleTextField().appendText(getTuringMachine().getCurrentTransitionFunction().toString() + "\n");
//                                }
//                            });
//                            moveTape(move, speedMillis);
//                            getTuringMachine().setCurrentState(transitionFunction.getNewStatus());
//                            getTuringMachine().setCurrentCell(getTuringMachine().getCurrentCell() + (-1 * move));
//                            getTuringMachine().setCurrentTransitionFunction(getTuringMachine().getTransitionFunctionForCurrentStates());
//                            if (getTuringMachine().isLastCell()) {
//                                Platform.runLater(new Runnable() {
//                                    public void run() {
//                                        addBlankElementOnTape();
//                                    }
//                                });
//                            }
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        Platform.runLater(new Runnable() {
//                            public void run() {
//                                getProgressIndicator().setVisible(false);
//                                getEndMessage();
//                            }
//                        });
//                    }
//                }.start();
//                return 1;
//            }
//
//        };
//    }


//    public Task<Integer> manualRun2(long speedMillis) {
//        return new Task<Integer>() {
//            @Override
//            protected Integer call() throws Exception {
//                new Thread() {
//                    public void run() {
//                        int move = -1;
//                        if (!getTuringMachine().isAcceptState(getTuringMachine().getCurrentState())
//                                && !getTuringMachine().isRejectState(getTuringMachine().getCurrentState())
//                                && !getTuringMachine().isOutTape()
//                                && getTuringMachine().getCurrentTransitionFunction() != null) {
//                            TransitionFunction transitionFunction = getTuringMachine().getCurrentTransitionFunction();
//                            if (transitionFunction.getDirectionOfMove().equalsIgnoreCase(TransitionFunction.LEFT_DIRECTION)) {
//                                move = 1;
//                            } else if (transitionFunction.getDirectionOfMove().equalsIgnoreCase(TransitionFunction.RIGHT_DIRECTION)) {
//                                move = -1;
//                            } else {
//                                move = 0;
//                            }
//                            Platform.runLater(new Runnable() {
//                                public void run() {
//                                    getProgressIndicator().setVisible(true);
//                                    setNrOfMove(getNrOfMove() + 1);
//                                    getNrOfMoveLabel().setText(getNrOfMove() + " moves");
//                                    getTapeCells().get(getTuringMachine().getCurrentCell()).setSelectedStyle();
//                                    getCurrentTransitionFunctionLabel().setText(getTuringMachine().getCurrentTransitionFunction() != null ? getTuringMachine().getCurrentTransitionFunction().toString() : "");
//                                    getTapeCells().get(getTuringMachine().getCurrentCell()).setText(transitionFunction.getNewValue());
//                                    getTuringMachine().getTape().set(getTuringMachine().getCurrentCell(), transitionFunction.getNewValue());
//                                    getCursorLabel().setText("^\n" + getTuringMachine().getCurrentState());
//                                    getConsoleTextField().appendText(getTuringMachine().getCurrentTransitionFunction().toString() + "\n");
//                                }
//                            });
//                            moveTape(move, speedMillis);
//                            getTuringMachine().setCurrentState(transitionFunction.getNewStatus());
//                            getTuringMachine().setCurrentCell(getTuringMachine().getCurrentCell() + (-1 * move));
//                            getTuringMachine().setCurrentTransitionFunction(getTuringMachine().getTransitionFunctionForCurrentStates());
//                            if (getTuringMachine().isLastCell()) {
//                                Platform.runLater(new Runnable() {
//                                    public void run() {
//                                        addBlankElementOnTape();
//                                    }
//                                });
//                            }
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        Platform.runLater(new Runnable() {
//                            public void run() {
//                                getProgressIndicator().setVisible(false);
//                                getEndMessage();
//                            }
//                        });
//                    }
//                }.start();
//                return 1;
//            }
//
//        };
//    }
