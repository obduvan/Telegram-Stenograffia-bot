import java.util.ArrayList;
import java.util.List;


public class StructureStates {
    private State mainState;
    private List<State> stateList = new ArrayList<>(){};

    public StructureStates(State state){
        mainState = state;
        stateList.add(state);
    }

    public State getMainState(){
        return mainState;
    }

    public List<State> getStateList(){
        return stateList;
    }

    public boolean containsState(State newState){
        return stateList.contains(newState);
    }

    public void putState(State newState){
        if (newState.getStatus() == BotState.NEXT_ART){
            for(State state : stateList){
                if (state.getStatus() == BotState.ASK_WORKS) {
                    mainState = state;
                    break;
                }
            }
        }
        else{
            stateList.add(newState);
            mainState = newState;
        }
    }
}
