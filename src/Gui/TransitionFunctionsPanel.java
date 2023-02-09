package Gui;

import Logic.IController;
import javafx.scene.layout.BorderPane;

/**
 * Created by Eno on 27/06/2017.
 */
public class TransitionFunctionsPanel extends BorderPane {
    private IController controller;
    private TransitionFunctionTable transitionFunctionTable;

    public TransitionFunctionsPanel(IController iController){
        setController(iController);
        createGui();
    }

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public TransitionFunctionTable getTransitionFunctionTable() {
        if(transitionFunctionTable == null){
            transitionFunctionTable = new TransitionFunctionTable(getController());
        }
        return transitionFunctionTable;
    }

    public void setTransitionFunctionTable(TransitionFunctionTable transitionFunctionTable) {
        this.transitionFunctionTable = transitionFunctionTable;
    }

    private void createGui(){
        this.setCenter(getTransitionFunctionTable().getTableView());
    }


}
