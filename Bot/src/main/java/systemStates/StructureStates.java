package systemStates;
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

    public void putState(State newState){
        if (newState.getBotState() == BotState.NEXT_ART){
            for (int i = stateList.size() - 1; i >= 0; i--) {
                switch (stateList.get(i).getBotState()){
                    case ASK_WORKS:
                    case WORKS_LOC_GET:
                        mainState = stateList.get(i);
                        return;
                }
            }
        }
        else{
            stateList.add(newState);
            mainState = newState;
        }
    }

    public List<State> getStateList(){
        return stateList;
    }

    public boolean containsState(State newState){
        return stateList.contains(newState);
    }
}
