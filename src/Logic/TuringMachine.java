package Logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Eno on 08/06/2017.
 */
public class TuringMachine implements Serializable {
    private Inputs inputs;
    private ArrayList<String> tape = new ArrayList<>();
    private String currentState = "";
    private int currentCell = -1;
    private TransitionFunction currentTransitionFunction;

    public TuringMachine(Inputs inputs) {
        setInputs(inputs);
        setTape(getInputs().getTapeAlphabet());
        setCurrentState(inputs.getInitialState());
        setCurrentCell(0);
        addBlankElementOnTape(50);
        setCurrentTransitionFunction(getTransitionFunctionForCurrentStates());
    }

    public Inputs getInputs() {
        return inputs;
    }

    public void setInputs(Inputs inputs) {
        this.inputs = inputs;
    }

    public ArrayList<String> getTape() {
        return tape;
    }

    public void setTape(ArrayList<String> tape) {
        this.tape = tape;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public int getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(int currentCell) {
        this.currentCell = currentCell;
    }

    public TransitionFunction getCurrentTransitionFunction() {
        return currentTransitionFunction;
    }

    public void setCurrentTransitionFunction(TransitionFunction currentTransitionFunction) {
        this.currentTransitionFunction = currentTransitionFunction;
    }

    public void addBlankElementOnTape(int nrOfBlankElement) {
        for (int i = 0; i < nrOfBlankElement; i++) {
            getTape().add("@");
        }
    }

    public boolean isAcceptState(String state){
        return getInputs().getFinalState().equals(state);
    }

    public boolean isRejectState(String state){
        return getInputs().getRejectState().equals(state);
    }

    public boolean isOutTape(){
        return getCurrentCell() == -1 || getCurrentCell() > (getTape().size() - 1);
    }

    public boolean isLastCell(){
        return (getTape().size() - 1) < getCurrentCell();
    }

    public TransitionFunction getTransitionFunctionForCurrentStates(){
        if(isOutTape()){
            return null;
        }
        for(TransitionFunction transitionFunction : getInputs().getTransitionFunctions()){
            if(transitionFunction.getCurrentStatus().equals(getCurrentState())
                    && transitionFunction.getCurrentValue().equals(getTape().get(getCurrentCell()))){
                return transitionFunction;
            }
        }
        return null;
    }

}
