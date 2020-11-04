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
            for (int i = stateList.size() - 1; i >= 0; i--) {
                if (stateList.get(i).getStatus() == BotState.ASK_WORKS) {
                    mainState = stateList.get(i);
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
