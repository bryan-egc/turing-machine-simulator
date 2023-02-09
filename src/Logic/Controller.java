package Logic;

import Gui.StateModel;
import Gui.TransitionFunctionModel;
import Gui.Utility;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Eno on 06/06/2017.
 */
public class Controller implements IController {

    private static Controller instance;
    private ObservableList<StateModel> stateModels = FXCollections.observableArrayList();
    private ObservableList<TransitionFunctionModel> transitionFunctionModels = FXCollections.observableArrayList();
    ObservableList<String> statesObservableList = FXCollections.observableArrayList();
    ObservableList<String> alphabetObservableList = FXCollections.observableArrayList();
    private Inputs inputs = new Inputs();

    private Controller() {
    }

    public Inputs getInputs() {
        return inputs;
    }

    @Override
    public void setInputs(Inputs inputs) {
        this.inputs = inputs;
    }

    public static synchronized Controller getInstance() {
        if (instance == null) {
            try {
                instance = new Controller();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }

    public ObservableList<StateModel> getStateModels() {
        stateModels.clear();
        for (String s : getInputs().getStates()) {
            stateModels.add(new StateModel(new SimpleStringProperty(s)));
        }
        return stateModels;
    }

    public void setStateModels(ObservableList<StateModel> stateModels) throws Exception {
        ArrayList<String> arrayList = new ArrayList<>();
        for (StateModel stateModel : stateModels) {
            if (!stateModel.getRealStatus().isEmpty()) {
                if (arrayList.indexOf(stateModel.getRealStatus()) < 0) {
                    arrayList.add(stateModel.getRealStatus());
                }
            }
        }
        if(arrayList.isEmpty()){
            throw new Exception("Finite states are empty");
        }
        getInputs().setStates(arrayList);
        this.stateModels = stateModels;
    }

    public ObservableList<TransitionFunctionModel> getTransitionFunctionModels() {
        transitionFunctionModels.clear();
        for(TransitionFunction transitionFunction : getInputs().getTransitionFunctions()){
            transitionFunctionModels.add(new TransitionFunctionModel(transitionFunction));
        }
        return transitionFunctionModels;
    }

    public void setTransitionFunctionModels(ObservableList<TransitionFunctionModel> transitionFunctionModels) throws Exception {
        ArrayList<TransitionFunction> arrayList = new ArrayList<>();
        for(TransitionFunctionModel transitionFunctionModel : transitionFunctionModels){
            if(!transitionFunctionModel.isEmpty() &&
                    (transitionFunctionModel.getTransitionFunction().getDirectionOfMove().
                            equalsIgnoreCase(TransitionFunction.LEFT_DIRECTION) ||
                            transitionFunctionModel.getTransitionFunction().getDirectionOfMove().
                                    equalsIgnoreCase(TransitionFunction.RIGHT_DIRECTION) ||
                            transitionFunctionModel.getTransitionFunction().getDirectionOfMove().
                                    equalsIgnoreCase(TransitionFunction.NO_MOVE)) ){
                if(arrayList.indexOf(transitionFunctionModel.getTransitionFunction()) < 0){
                    arrayList.add(transitionFunctionModel.getTransitionFunction());
                }
            }
        }

        if(arrayList.isEmpty()){
            throw new Exception("Transition Function Is Empty");
        }
        getInputs().setTransitionFunctions(arrayList);
        this.transitionFunctionModels = transitionFunctionModels;
    }

    @Override
    public void setTapeAlphabet(String text) throws Exception {
        validateTapeAlphabet(text);
        getInputs().getTapeAlphabet().clear();
        text = Utility.clearTextFromWhiteSpace(text);
        getInputs().getTapeAlphabet().addAll(Utility.transformStringInArrayList(text));
    }

    @Override
    public void setInputAlphabet(String text) throws Exception {
        validateInputAlphabet(text);
        getInputs().getInputAlphabet().clear();
        text = Utility.clearTextFromWhiteSpace(text);
        text = text.replaceAll("\\@", "");
        ArrayList arrayList = Utility.transformStringInArrayList(text);
        getInputs().setInputAlphabet(Utility.getArrayListWithUniqueElement(arrayList));
    }

    private void validateTapeAlphabet(String tapeAlphabet) throws Exception {
        if (tapeAlphabet == null || tapeAlphabet.trim().isEmpty()) {
            throw new Exception("Tape alphabet is empty");
        }
    }

    private void validateInputAlphabet(String inputAlphabet) throws Exception {
        if (inputAlphabet == null || inputAlphabet.trim().isEmpty()) {
            throw new Exception("Input alphabet is empty");
        }
    }

    public String getTapeAlphabetForInputAlphabet(String tapeAlphabet) {
        tapeAlphabet = Utility.clearTextFromWhiteSpace(tapeAlphabet);
        ArrayList<String> arrayList = Utility.transformStringInArrayList(tapeAlphabet);
        arrayList = Utility.getArrayListWithUniqueElement(arrayList);
        tapeAlphabet = Utility.transformArrayListInString(arrayList);
        return tapeAlphabet.replaceAll("\\@", "");
    }

    public void setInitFinalRejectStates(String initStates, String finalStates, String rejectStates) throws Exception {
        validateInitStates(initStates);
        validateFinalStates(finalStates);
        validateRejectStates(rejectStates);
        if(finalStates.equals(rejectStates)){
            throw new Exception("Final Status Is Equale With Reject Status");
        }
        getInputs().setInitialState(initStates);
        getInputs().setFinalState(finalStates);
        getInputs().setRejectState(rejectStates);
    }

    private void validateInitStates(String initStates) throws Exception {
        if(!isValidState(initStates)){
            throw new Exception("Start Status Incorrect");
        }
    }

    private void validateFinalStates(String finalStates) throws Exception {
        if(!isValidState(finalStates)){
            throw new Exception("Final Status Incorrect");
        }
    }

    private void validateRejectStates(String rejectStates) throws Exception {
        if(!isValidState(rejectStates)){
            throw new Exception("Reject Status Incorrect");
        }
    }

    public boolean isValidState(String state){
        return getInputs().getStates().indexOf(state) < 0 ? false : true;
    }

    public ObservableList<String> getStatesObservableList(){
        statesObservableList.clear();
        statesObservableList.addAll(getInputs().getStates());
        return statesObservableList;
    }

    public ObservableList<String> getAlphabetObservableList(){
        alphabetObservableList.clear();
        alphabetObservableList.addAll(getInputs().getInputAlphabet());
        alphabetObservableList.addAll("@");
        return alphabetObservableList;
    }

    @Override
    public void reset(){
        stateModels = FXCollections.observableArrayList();
        transitionFunctionModels = FXCollections.observableArrayList();
        statesObservableList = FXCollections.observableArrayList();
        alphabetObservableList = FXCollections.observableArrayList();
        inputs = new Inputs();
    }

}
