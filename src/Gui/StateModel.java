package Gui;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Eno on 06/06/2017.
 */
public class StateModel {
    private SimpleStringProperty state;
    private String realStatus = "";

    public StateModel(SimpleStringProperty state) {

        this.state = state;
        setState(state.get());
    }

    public String getState() {
        return state.get();
    }

    public void setState(String fName) {
        if(fName != null && !fName.trim().isEmpty()){
            setRealStatus(fName.trim());
        }
        else {
            setRealStatus("");
        }
        state.set(fName);
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }

    public String getRealStatus() {
        return realStatus;
    }

    public void setRealStatus(String realStatus) {
        this.realStatus = realStatus;
    }

    @Override
    public String toString() {
        return getState();
    }
}
