package Gui;

import Logic.IController;
import javafx.scene.layout.BorderPane;

/**
 * Created by Eno on 27/06/2017.
 */
public class FiniteStatesPanel extends BorderPane {
    private IController controller;
    private StateTable finiteStatesTable;

    public FiniteStatesPanel(IController iController) {
        setController(iController);
        createGui();
    }

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public StateTable getFiniteStatesTable() {
        if (finiteStatesTable == null) {
            finiteStatesTable = new StateTable(getController());
            finiteStatesTable.getStateColumn().setText("Finite States");
        }
        return finiteStatesTable;
    }

    public void setFiniteStatesTable(StateTable finiteStatesTable) {
        this.finiteStatesTable = finiteStatesTable;
    }

    private void createGui(){
        this.setCenter(getFiniteStatesTable().getTableView());
    }
}
