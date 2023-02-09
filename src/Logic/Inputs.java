package Logic;

import Logic.TransitionFunction;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Eno on 06/06/2017.
 */
public class Inputs implements Serializable {
    private ArrayList<String> states = new ArrayList<>();
    private ArrayList<String> tapeAlphabet = new ArrayList<>();
    private ArrayList<String> inputAlphabet = new ArrayList<>();
    private ArrayList<TransitionFunction> transitionFunctions = new ArrayList<>();
    private String initialState = "";
    private String finalState = "";
    private String rejectState = "";

    public ArrayList<String> getStates() {
        return states;
    }

    public void setStates(ArrayList<String> states) {
        this.states = states;
    }

    public ArrayList<String> getTapeAlphabet() {
        return tapeAlphabet;
    }

    public void setTapeAlphabet(ArrayList<String> tapeAlphabet) {
        this.tapeAlphabet = tapeAlphabet;
    }

    public ArrayList<String> getInputAlphabet() {
        return inputAlphabet;
    }

    public void setInputAlphabet(ArrayList<String> inputAlphabet) {
        this.inputAlphabet = inputAlphabet;
    }

    public ArrayList<TransitionFunction> getTransitionFunctions() {
        return transitionFunctions;
    }

    public void setTransitionFunctions(ArrayList<TransitionFunction> transitionFunctions) {
        this.transitionFunctions = transitionFunctions;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public String getFinalState() {
        return finalState;
    }

    public void setFinalState(String finalState) {
        this.finalState = finalState;
    }

    public String getRejectState() {
        return rejectState;
    }

    public void setRejectState(String rejectState) {
        this.rejectState = rejectState;
    }
}
