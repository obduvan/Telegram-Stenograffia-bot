package systemStates;
import java.util.ArrayList;
import java.util.List;


public class StructureStates {
    private State mainState;
    private List<State> stateList;
    private List<Double[]> routeList;

    public StructureStates(State state){
        stateList  = new ArrayList<>(){};
        mainState = state;
        routeList = new ArrayList<>();
        stateList.add(state);
    }

    public State getMainState(){
        return mainState;
    }

    public void updateLongLang(State newState){
        if (newState.getBotState() == BotState.GET_ROUTE_URL){
            for (int i = stateList.size() - 1; i >= 0; i--) {
                var state = stateList.get(i);
                if (state.getBotState() == BotState.WORKS_LOC_RAD){
                    newState.updateLongLang(state.getLatitude(), state.getLongtitude());
                    break;
                }
            }
        }
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

    public void clearUserRouteList(){
        if (mainState.getBotState() == BotState.ASK_WORKS || mainState.getBotState() == BotState.WORKS_LOC_INIT)
            routeList.clear();
    }

    public void updateUserRouteList(String workCoordinates, boolean isAddWork){
        var latitude = workCoordinates.split(" ")[0];
        var longtitude = workCoordinates.split(" ")[1];
        var coord = new Double[]{Double.parseDouble(latitude), Double.parseDouble(longtitude)};
        if (isAddWork)
            routeList.add(coord);
        else
            routeList.remove(coord);
//        System.out.println("---------------------------------------------");
//        for (String el : routeList){System.out.println(el);}
    }

    public List<Double[]> getRouteList(){
        return routeList;
    }

    public List<State> getStateList(){
        return stateList;
    }

    public boolean containsState(State newState){
        return stateList.contains(newState);
    }
}
