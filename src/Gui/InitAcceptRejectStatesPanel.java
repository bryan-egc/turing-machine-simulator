package Gui;

import Logic.IController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import np.com.ngopal.control.AutoFillTextBox;

/**
 * Created by Eno on 27/06/2017.
 */
public class InitAcceptRejectStatesPanel extends BorderPane{
    private IController controller;
    private AutoFillTextBox<StateModel> startStateTextField;
    private AutoFillTextBox<StateModel> acceptStateTextField;
    private AutoFillTextBox<StateModel> rejectStateTextField;

    public InitAcceptRejectStatesPanel(IController iController){
        setController(iController);
        createGui();
    }

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public AutoFillTextBox<StateModel> getStartStateTextField() {
        if (startStateTextField == null) {
            startStateTextField = Utility.createTextFieldWithFiniteStatesSuggestion(getController().getStateModels());
        }
        return startStateTextField;
    }

    public void setStartStateTextField(AutoFillTextBox<StateModel> startStateTextField) {
        this.startStateTextField = startStateTextField;
    }

    public AutoFillTextBox<StateModel> getAcceptStateTextField() {
        if (acceptStateTextField == null) {
            acceptStateTextField = Utility.createTextFieldWithFiniteStatesSuggestion(getController().getStateModels());
        }
        return acceptStateTextField;
    }

    public void setAcceptStateTextField(AutoFillTextBox<StateModel> acceptStateTextField) {
        this.acceptStateTextField = acceptStateTextField;
    }

    public AutoFillTextBox<StateModel> getRejectStateTextField() {
        if (rejectStateTextField == null) {
            rejectStateTextField = Utility.createTextFieldWithFiniteStatesSuggestion(getController().getStateModels());
        }
        return rejectStateTextField;
    }

    public void setRejectStateTextField(AutoFillTextBox<StateModel> rejectStateTextField) {
        this.rejectStateTextField = rejectStateTextField;
    }

    private void createGui(){
        Label startStateLabel = Utility.createLabel("Start State");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(3);
        gridPane.setVgap(4);
        gridPane.add(startStateLabel, 0, 0);
        gridPane.add(getStartStateTextField(), 1, 0);
        gridPane.add(Utility.createLabel("Accept State"), 0, 1);
        gridPane.add(getAcceptStateTextField(), 1, 1);
        gridPane.add(Utility.createLabel("Reject State"), 0, 2);
        gridPane.add(getRejectStateTextField(), 1, 2);

        startStateLabel.setPadding(new Insets(0, 30, 0, 0));
        this.setCenter(gridPane);
    }
}
