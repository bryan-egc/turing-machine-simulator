package Gui;

import Logic.IController;
import Logic.Inputs;
import Logic.TuringMachine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang.SerializationUtils;
import org.controlsfx.control.MasterDetailPane;

import java.io.File;
import java.io.IOException;


/**
 * Created by Eno on 06/06/2017.
 */
public class GuiManager extends BorderPane {
    private Stage stage;
    private static int BORDER_ALPHABET = 1;
    private static int BORDER_FINITE_STATES = 2;
    private static int BORDER_INIT_ACCEPT_REJECT_STATES = 3;
    private static int BORDER_FUNCTIONS = 4;
    private static int BORDER_TAPE = 5;

    private AlphabetPanel alphabetPanel;
    private FiniteStatesPanel finiteStatesPanel;
    private InitAcceptRejectStatesPanel initAcceptRejectStatesPanel;
    private TransitionFunctionsPanel transitionFunctionsPanel;
    private TapeGui tapeGui;

    private IController controller;
    private MasterDetailPane masterDetailPane;
    private VBox leftDetailBox;
    private Button nextButton;
    private Button previousButton;
    private int currentBorder = BORDER_ALPHABET;
    private MenuGui menuGui;

    public GuiManager(IController iController, Stage stage) {
        setController(iController);
        setStage(stage);
        this.setTop(getMenuGui().getMenuBar());
        this.setBottom(getBottomPane());
        this.setCenter(getMasterDetailPane());
        goToAlphabetBorderPane();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public MenuGui getMenuGui() {
        if (menuGui == null) {
            menuGui = new MenuGui(this);
        }
        return menuGui;
    }

    public void setMenuGui(MenuGui menuGui) {
        this.menuGui = menuGui;
    }

    public AlphabetPanel getAlphabetPanel() {
        if (alphabetPanel == null) {
            alphabetPanel = new AlphabetPanel(getController());
        }
        return alphabetPanel;
    }

    public void setAlphabetPanel(AlphabetPanel alphabetPanel) {
        this.alphabetPanel = alphabetPanel;
    }

    public FiniteStatesPanel getFiniteStatesPanel() {
        if (finiteStatesPanel == null) {
            finiteStatesPanel = new FiniteStatesPanel(getController());
        }
        return finiteStatesPanel;
    }

    public void setFiniteStatesPanel(FiniteStatesPanel finiteStatesPanel) {
        this.finiteStatesPanel = finiteStatesPanel;
    }

    public InitAcceptRejectStatesPanel getInitAcceptRejectStatesPanel() {
        if (initAcceptRejectStatesPanel == null) {
            initAcceptRejectStatesPanel = new InitAcceptRejectStatesPanel(getController());
        }
        return initAcceptRejectStatesPanel;
    }

    public void setInitAcceptRejectStatesPanel(InitAcceptRejectStatesPanel initAcceptRejectStatesPanel) {
        this.initAcceptRejectStatesPanel = initAcceptRejectStatesPanel;
    }

    public TransitionFunctionsPanel getTransitionFunctionsPanel() {
        if (transitionFunctionsPanel == null) {
            transitionFunctionsPanel = new TransitionFunctionsPanel(getController());
        }
        return transitionFunctionsPanel;
    }

    public void setTransitionFunctionsPanel(TransitionFunctionsPanel transitionFunctionsPanel) {
        this.transitionFunctionsPanel = transitionFunctionsPanel;
    }

    public TapeGui getTapeGui() {
        if (tapeGui == null) {
            TuringMachine turingMachine = new TuringMachine((Inputs) SerializationUtils.clone(getController().getInputs()));
            tapeGui = new TapeGui(turingMachine, this);
        }
        return tapeGui;
    }

    public void setTapeGui(TapeGui tapeGui) {
        this.tapeGui = tapeGui;
    }

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public MasterDetailPane getMasterDetailPane() {
        if (masterDetailPane == null) {
            masterDetailPane = new MasterDetailPane();
            //masterDetailPane.setMasterNode(getAlphabetBorderPane(true));
            masterDetailPane.setDetailNode(new ScrollPane(getLeftDetailBox()));
            masterDetailPane.setDetailSide(Side.LEFT);
            masterDetailPane.setShowDetailNode(true);
            masterDetailPane.getDetailNode().getStyleClass().add("-fx-max-width: 300px;");

        }
        return masterDetailPane;
    }

    public void setMasterDetailPane(MasterDetailPane masterDetailPane) {
        this.masterDetailPane = masterDetailPane;
    }

    public VBox getLeftDetailBox() {
        if (leftDetailBox == null) {
            leftDetailBox = new VBox();
            leftDetailBox.setSpacing(5);
            leftDetailBox.setAlignment(Pos.CENTER);
        }
        return leftDetailBox;
    }

    public void setLeftDetailBox(VBox leftDetailBox) {
        this.leftDetailBox = leftDetailBox;
    }

    public int getCurrentBorder() {
        return currentBorder;
    }

    public void setCurrentBorder(int currentBorder) {
        this.currentBorder = currentBorder;
    }

    public Button getNextButton() {
        if (nextButton == null) {
            nextButton = Utility.createButton("Next");
            nextButton.setGraphic(Utility.getImageView("next.png"));
            nextButton.setContentDisplay(ContentDisplay.RIGHT);
            nextButton.setOnAction(e -> {
                goNext();
            });
        }
        return nextButton;
    }

    public void setNextButton(Button nextButton) {
        this.nextButton = nextButton;
    }

    public Button getPreviousButton() {
        if (previousButton == null) {
            previousButton = Utility.createButton("Back");
            previousButton.setGraphic(Utility.getImageView("back.png"));
            previousButton.setContentDisplay(ContentDisplay.LEFT);
            previousButton.setVisible(false);
            previousButton.setOnAction(e -> {
                goBack();
            });
        }
        return previousButton;
    }

    public void setPreviousButton(Button previousButton) {
        this.previousButton = previousButton;
    }

    private BorderPane getBottomPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 20, 20, 20));
        borderPane.setLeft(getPreviousButton());
        borderPane.setRight(getNextButton());
        return borderPane;
    }

    private void addNodeToLeftDetailBox(Node node) {
        getLeftDetailBox().getChildren().add(node);
    }

    private void removeNodeToLeftDetailBox(Node node) {
        getLeftDetailBox().getChildren().remove(node);
    }

    private void goNext() {
        try {
            if (getCurrentBorder() == BORDER_ALPHABET) {
                saveAlphabets();
                setAlphabetBorderPaneOnDetailBox();
                goToFiniteStatesBorderPane();
            } else if (getCurrentBorder() == BORDER_FINITE_STATES) {
                saveFiniteStates();
                setFiniteStatesBorderPaneOnDetailBox();
                goToInitFinalRejectBorderPane();
            } else if (getCurrentBorder() == BORDER_INIT_ACCEPT_REJECT_STATES) {
                saveInitFinalRejectStatus();
                setInitFinalRejectBorderPaneOnDetailBox();
                goToTransitionFunctionsBorderPane();
            } else if (getCurrentBorder() == BORDER_FUNCTIONS) {
                saveTransitionFunctions();
                setTransitionFunctionsBorderPaneOnDetailBox();
                goToTapeGui();
            }
        } catch (Exception e) {
            Utility.waringForAction("Waring", e.getMessage(), "");
        }
    }

    private void goBack() {
        BorderPane attemptToGo = null;
        try {
            if (getCurrentBorder() == BORDER_FINITE_STATES) {
                attemptToGo = getAlphabetPanel();
                saveFiniteStates();
                goToAlphabetBorderPane();
            } else if (getCurrentBorder() == BORDER_INIT_ACCEPT_REJECT_STATES) {
                attemptToGo = getFiniteStatesPanel();
                saveInitFinalRejectStatus();
                goToFiniteStatesBorderPane();
            } else if (getCurrentBorder() == BORDER_FUNCTIONS) {
                attemptToGo = getInitAcceptRejectStatesPanel();
                saveTransitionFunctions();
                //removeNodeToLeftDetailBox(getTransitionFunctionsPanel());
                goToInitFinalRejectBorderPane();
            } else if (getCurrentBorder() == BORDER_TAPE) {
                goToStart();
            }
        } catch (Exception e) {
            if (Utility.getConfirmation(e.getMessage(), "Do you want to continue")) {
                if (attemptToGo == getAlphabetPanel()) {
                    goToAlphabetBorderPane();
                } else if (attemptToGo == getFiniteStatesPanel()) {
                    goToFiniteStatesBorderPane();
                } else if (attemptToGo == getInitAcceptRejectStatesPanel()) {
                    removeNodeToLeftDetailBox(getTransitionFunctionsPanel());
                    goToInitFinalRejectBorderPane();
                } else if (attemptToGo == getTransitionFunctionsPanel()) {
                    goToTransitionFunctionsBorderPane();
                }
            }
        }
    }

    private void saveFiniteStates() throws Exception {
        getController().setStateModels(getFiniteStatesPanel().getFiniteStatesTable().getData());
    }

    private void goToFiniteStatesBorderPane() {
        removeNodeToLeftDetailBox(getFiniteStatesPanel());
        fillFiniteStatesBorderPane();
        getFiniteStatesPanel().getFiniteStatesTable().setEditable(true);
        getMasterDetailPane().setMasterNode(getFiniteStatesPanel());
        getPreviousButton().setVisible(true);
        setCurrentBorder(BORDER_FINITE_STATES);
    }

    private void fillFiniteStatesBorderPane() {
        getController().getStateModels();
    }

    private void setFiniteStatesBorderPaneOnDetailBox() {
        fillFiniteStatesBorderPane();
        getFiniteStatesPanel().getFiniteStatesTable().setEditable(false);
        addNodeToLeftDetailBox(getFiniteStatesPanel());
    }


    private void saveAlphabets() throws Exception {
        String tapeAlphabet = getAlphabetPanel().getTapeAlphabetTextField().getText();
        String inputAlphabet = getController().getTapeAlphabetForInputAlphabet(tapeAlphabet) + getAlphabetPanel().getInputAlphabetTextField().getText();
        getController().setTapeAlphabet(tapeAlphabet);
        getController().setInputAlphabet(inputAlphabet);
    }

    private void goToAlphabetBorderPane() {
        removeNodeToLeftDetailBox(getAlphabetPanel());
        fillAlphabetBorderPane();
        getAlphabetPanel().getTapeAlphabetTextField().setEditable(true);
        getAlphabetPanel().getInputAlphabetTextField().setEditable(true);
        getAlphabetPanel().getAlphabetNoteLabel().setVisible(true);
        getPreviousButton().setVisible(false);
        getMasterDetailPane().setMasterNode(getAlphabetPanel());
        setCurrentBorder(BORDER_ALPHABET);
    }

    private void setAlphabetBorderPaneOnDetailBox() {
        fillAlphabetBorderPane();
        getAlphabetPanel().getTapeAlphabetTextField().setEditable(false);
        getAlphabetPanel().getInputAlphabetTextField().setEditable(false);
        getPreviousButton().setVisible(true);
        addNodeToLeftDetailBox(getAlphabetPanel());
    }

    private void fillAlphabetBorderPane() {
        String tapeAlphabet = Utility.transformArrayListInString(getController().getInputs().getTapeAlphabet());
        String tapeAlphabetForInputAlphabet = getController().getTapeAlphabetForInputAlphabet(tapeAlphabet);
        String inputAlphabet = Utility.transformArrayListInString(getController().getInputs().getInputAlphabet()).replace(tapeAlphabetForInputAlphabet, "");
        getAlphabetPanel().getTapeAlphabetTextField().setText(tapeAlphabet);
        getAlphabetPanel().getInputAlphabetFromTapeAlphabetTextField().setText(tapeAlphabetForInputAlphabet);
        getAlphabetPanel().getInputAlphabetTextField().setText(inputAlphabet);
    }

    private void saveInitFinalRejectStatus() throws Exception {
        getController().setInitFinalRejectStates(
                getInitAcceptRejectStatesPanel().getStartStateTextField().getText().trim(),
                getInitAcceptRejectStatesPanel().getAcceptStateTextField().getText().trim(),
                getInitAcceptRejectStatesPanel().getRejectStateTextField().getText().trim()
        );
    }

    private void fillInitFinalRejectBorderPane() {
        getInitAcceptRejectStatesPanel().getStartStateTextField().getTextField().setText(getController().getInputs().getInitialState());
        getInitAcceptRejectStatesPanel().getAcceptStateTextField().getTextField().setText(getController().getInputs().getFinalState());
        getInitAcceptRejectStatesPanel().getRejectStateTextField().getTextField().setText(getController().getInputs().getRejectState());
    }

    private void setInitFinalRejectBorderPaneOnDetailBox() {
        fillInitFinalRejectBorderPane();
        getInitAcceptRejectStatesPanel().getStartStateTextField().getTextField().setEditable(false);
        getInitAcceptRejectStatesPanel().getAcceptStateTextField().getTextField().setEditable(false);
        getInitAcceptRejectStatesPanel().getRejectStateTextField().getTextField().setEditable(false);

        addNodeToLeftDetailBox(getInitAcceptRejectStatesPanel());
    }

    private void goToInitFinalRejectBorderPane() {
        removeNodeToLeftDetailBox(getInitAcceptRejectStatesPanel());
        fillInitFinalRejectBorderPane();
        getInitAcceptRejectStatesPanel().getStartStateTextField().getTextField().setEditable(true);
        getInitAcceptRejectStatesPanel().getAcceptStateTextField().getTextField().setEditable(true);
        getInitAcceptRejectStatesPanel().getRejectStateTextField().getTextField().setEditable(true);
        getNextButton().setVisible(true);
        getMasterDetailPane().setMasterNode(getInitAcceptRejectStatesPanel());
        setCurrentBorder(BORDER_INIT_ACCEPT_REJECT_STATES);
    }

    private void saveTransitionFunctions() throws Exception {
        getController().setTransitionFunctionModels(getTransitionFunctionsPanel().getTransitionFunctionTable().getData());
    }

    private void goToTransitionFunctionsBorderPane() {
        removeNodeToLeftDetailBox(getTransitionFunctionsPanel());
        fillTransitionFunctionsBorderPane();
        getTransitionFunctionsPanel().getTransitionFunctionTable().setEditable(true);
        getMasterDetailPane().setMasterNode(getTransitionFunctionsPanel());
        getNextButton().setVisible(true);
        setCurrentBorder(BORDER_FUNCTIONS);
    }

    private void fillTransitionFunctionsBorderPane() {
        getTransitionFunctionsPanel().setTransitionFunctionTable(new TransitionFunctionTable(getController()));
    }

    private void setTransitionFunctionsBorderPaneOnDetailBox() {
        fillTransitionFunctionsBorderPane();
        getTransitionFunctionsPanel().getTransitionFunctionTable().setEditable(false);
        addNodeToLeftDetailBox(getTransitionFunctionsPanel());
    }

    public void goToTapeGui() {
        getNextButton().setVisible(false);
        setTapeGui(null);
        getMasterDetailPane().setMasterNode(getTapeGui());
        setCurrentBorder(BORDER_TAPE);
    }

    public void reset() {
        Scene scene = this.getScene();
        getController().reset();
        scene.setRoot(new GuiManager(getController(), getStage()));
    }

    public void goToStart() {
        Scene scene = this.getScene();
        Inputs inputs = getController().getInputs();
        getController().reset();
        getController().setInputs(inputs);
        scene.setRoot(new GuiManager(getController(), getStage()));
    }

    public void saveInputsOnFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Turing Machine");
        File file = fileChooser.showSaveDialog(getStage());
        if (file != null) {
            try {
                Utility.saveOnFile(getController().getInputs(), file);
                Utility.informForSuccessfulAction("Successful", "Successfully Save", "");
            } catch (Exception e) {
                Utility.waringForAction("Waring", "Can not be saved", "");
                e.printStackTrace();
            }
        }
    }

    public void openInputsFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Turing Machine");
        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            try {
                Inputs inputs = Utility.readInputsFromFile(file);
                Scene scene = this.getScene();
                getController().reset();
                getController().setInputs(inputs);
                scene.setRoot(new GuiManager(getController(), getStage()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                Utility.waringForAction("Waring", "Can not be opened", "");
                e.printStackTrace();
            }
        }
    }


}
