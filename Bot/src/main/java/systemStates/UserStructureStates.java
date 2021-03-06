package systemStates;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserStructureStates {
    private State mainState;
    private List<State> stateList;
    private Map<String, Double[]> routeMap;
    private List<BotState> botStateList;

    public UserStructureStates() {
        stateList = new ArrayList<>(){};
        botStateList = new ArrayList<>();
        routeMap = new HashMap<>();
    }

    public State getMainState() {
        return mainState;
    }

    public void setNewState(State state) {
        stateList.add(state);
        mainState = state;
        botStateList.add(state.getBotState());
    }

    public void updateLongLang(State newState) {
        if (newState.getBotState() == BotState.GET_ROUTE_URL) {
            for (int i = stateList.size() - 1; i >= 0; i--) {
                var state = stateList.get(i);
                if (state.getBotState() == BotState.WORKS_LOC_RAD) {
                    newState.updateLongLang(state.getLatitude(), state.getLongtitude());
                    break;
                }
            }
        }
    }

    public void updateState(State newState) {
        if (newState.getBotState() == BotState.NEXT_ART) {
            for (int i = stateList.size() - 1; i >= 0; i--) {
                switch (stateList.get(i).getBotState()) {
                    case ASK_WORKS:
                    case WORKS_LOC_GET:{
                        mainState = stateList.get(i);
                        return;
                    }

                }
            }
        }
        else {
            stateList.add(newState);
            mainState = newState;
        }

        clearUserRouteList();
        botStateList.add(mainState.getBotState());
    }

    public void clearUserRouteList() {
        if (mainState.getBotState() == BotState.ASK_WORKS || mainState.getBotState() == BotState.WORKS_LOC_INIT) {
            routeMap.clear();
        }
    }

    public void updateUserRouteList(String workCoordinates, boolean isAddWork) {
        var coordsString = workCoordinates.split(" ");
        var currLatitude = Double.parseDouble(coordsString[0]);
        var currLongtitude = Double.parseDouble(coordsString[1]);
        var currCoords = new Double[]{currLatitude, currLongtitude};

        if (isAddWork) {
            routeMap.put(workCoordinates, currCoords);
        }
        else routeMap.remove(workCoordinates);

    }

    public List<Double[]> getRouteList() {
        return new ArrayList<>(routeMap.values());

    }

    public List<State> getStateList() {
        return stateList;
    }

    public List<BotState> getBotStateList() {
        return botStateList;
    }

    public boolean containsState(State newState) {
        return stateList.contains(newState);
    }
}
