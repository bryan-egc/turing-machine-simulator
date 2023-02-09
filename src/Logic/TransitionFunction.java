package Logic;

import java.io.Serializable;

/**
 * Created by Dell on 06/06/2017.
 */
public class TransitionFunction implements Serializable {
    public static final String LEFT_DIRECTION = "L";
    public static final String RIGHT_DIRECTION = "R";
    public static final String NO_MOVE = "-";


    private String currentStatus = "";
    private String currentValue = "";
    private String newStatus = "";
    private String newValue = "";
    private String directionOfMove = "";

    public TransitionFunction() {
    }

    public TransitionFunction(String currentStatus, String currentValue, String newStatus, String newValue, String directionOfMove) {
        setCurrentStatus(currentStatus);
        setCurrentValue(currentValue);
        setNewStatus(newStatus);
        setNewValue(newValue);
        setDirectionOfMove(directionOfMove);
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getDirectionOfMove() {
        return directionOfMove;
    }

    public void setDirectionOfMove(String directionOfMove) {
        this.directionOfMove = directionOfMove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransitionFunction that = (TransitionFunction) o;

        if (!currentStatus.equals(that.currentStatus)) return false;
        if (!currentValue.equals(that.currentValue)) return false;
        if (!newStatus.equals(that.newStatus)) return false;
        if (!newValue.equals(that.newValue)) return false;
        return directionOfMove.equals(that.directionOfMove);

    }

    @Override
    public int hashCode() {
        int result = currentStatus.hashCode();
        result = 31 * result + currentValue.hashCode();
        result = 31 * result + newStatus.hashCode();
        result = 31 * result + newValue.hashCode();
        result = 31 * result + directionOfMove.hashCode();
        return result;
    }

    public String toString(){
        return "( " + getCurrentStatus() + " , " + getCurrentValue() + " , " +
                getNewStatus() + " , " + getNewValue() + " , " + getDirectionOfMove() + " )";
    }
}
