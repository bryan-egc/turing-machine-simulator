package Logic;

import Gui.StateModel;
import Gui.TransitionFunctionModel;
import Logic.Inputs;
import javafx.collections.ObservableList;

/**
 * Created by Eno on 06/06/2017.
 */
public interface IController {
    Inputs getInputs();
    void setInputs(Inputs inputs);
    ObservableList<StateModel> getStateModels();
    void setStateModels(ObservableList<StateModel> stateModels) throws Exception;
    void setTapeAlphabet(String text) throws Exception;
    void setInputAlphabet(String text) throws Exception;
    String getTapeAlphabetForInputAlphabet(String tapeAlphabet);
    void setInitFinalRejectStates(String initStates, String finalStates, String rejectStates) throws Exception;
    ObservableList<String> getStatesObservableList();
    ObservableList<String> getAlphabetObservableList();
    ObservableList<TransitionFunctionModel> getTransitionFunctionModels();
    void setTransitionFunctionModels(ObservableList<TransitionFunctionModel> transitionFunctionModels) throws Exception;
    void reset();
}
