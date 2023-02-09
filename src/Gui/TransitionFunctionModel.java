package Gui;

import Logic.TransitionFunction;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Eno on 07/06/2017.
 */
public class TransitionFunctionModel {
    private TransitionFunction transitionFunction;
    private SimpleStringProperty currentStatus = new SimpleStringProperty("");
    private SimpleStringProperty currentValue = new SimpleStringProperty("");
    private SimpleStringProperty newStatus = new SimpleStringProperty("");
    private SimpleStringProperty newValue = new SimpleStringProperty("");
    private SimpleStringProperty directionOfMove = new SimpleStringProperty("");

    public TransitionFunctionModel() {

    }

    public TransitionFunctionModel(TransitionFunction transitionFunction) {
        setTransitionFunction(transitionFunction);
        setCurrentStatus(getTransitionFunction().getCurrentStatus());
        setCurrentValue(getTransitionFunction().getCurrentValue());
        setNewStatus(getTransitionFunction().getNewStatus());
        setNewValue(getTransitionFunction().getNewValue());
        setDirectionOfMove(getTransitionFunction().getDirectionOfMove());
    }

    public String getCurrentStatus() {
        return currentStatus.get();
    }

    public SimpleStringProperty currentStatusProperty() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        getTransitionFunction().setCurrentStatus(currentStatus);
        this.currentStatus.set(currentStatus);
    }

    public String getCurrentValue() {
        return currentValue.get();
    }

    public SimpleStringProperty currentValueProperty() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        getTransitionFunction().setCurrentValue(currentValue);
        this.currentValue.set(currentValue);
    }

    public String getNewStatus() {
        return newStatus.get();
    }

    public SimpleStringProperty newStatusProperty() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        getTransitionFunction().setNewStatus(newStatus);
        this.newStatus.set(newStatus);
    }

    public String getNewValue() {
        return newValue.get();
    }

    public SimpleStringProperty newValueProperty() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        getTransitionFunction().setNewValue(newValue);
        this.newValue.set(newValue);
    }

    public String getDirectionOfMove() {
        return directionOfMove.get();
    }

    public SimpleStringProperty directionOfMoveProperty() {
        return directionOfMove;
    }

    public void setDirectionOfMove(String directionOfMove) {
        getTransitionFunction().setDirectionOfMove(directionOfMove);
        this.directionOfMove.set(directionOfMove);
    }

    public TransitionFunction getTransitionFunction() {
        if (transitionFunction == null) {
            transitionFunction = new TransitionFunction();
        }
        return transitionFunction;
    }

    public void setTransitionFunction(TransitionFunction transitionFunction) {
        this.transitionFunction = transitionFunction;
    }

    public boolean isEmpty(){
        if(getTransitionFunction().getCurrentStatus().isEmpty())return true;
        if(getTransitionFunction().getCurrentValue().isEmpty())return true;
        if(getTransitionFunction().getNewStatus().isEmpty())return true;
        if(getTransitionFunction().getNewValue().isEmpty())return true;
        if(getTransitionFunction().getDirectionOfMove().isEmpty())return true;
        return false;
    }
}
