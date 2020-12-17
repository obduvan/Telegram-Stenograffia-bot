package systemStates;
import bot.Bot;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;


public class StructureStates {
    private State mainState;
    private List<State> stateList;
    private List<String> routeList;
    private ArrayList<Double[]> routeListDouble;

    private List<BotState> botStateList;

    public StructureStates(State state){

        stateList  = new ArrayList<>(){};
        stateList.add(state);
        mainState = state;
        routeList = new ArrayList<>();
        routeListDouble = new ArrayList<>();
        botStateList = new ArrayList<>();
        botStateList.add(state.getBotState());
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
                        clearUserRouteList();
                        return;
                }
            }
        }
        else{
            stateList.add(newState);
            mainState = newState;
        }
        botStateList.add(mainState.getBotState());
    }

    public void clearUserRouteList(){
        if (mainState.getBotState() == BotState.ASK_WORKS || mainState.getBotState() == BotState.WORKS_LOC_INIT){
            routeList.clear();
            routeListDouble.clear();
        }

    }

    public void updateUserRouteList(String workCoordinates, boolean isAddWork){
        var coordsString = workCoordinates.split(" ");
        var currLatitude = Double.parseDouble(coordsString[0]);
        var currLongtitude = Double.parseDouble(coordsString[1]);
        var currCoords = new Double[]{currLatitude, currLongtitude};

        if (isAddWork){
            routeList.add(workCoordinates);
            routeListDouble.add(currCoords);
        }

        else{
            var remove_index = routeList.indexOf(workCoordinates);
            routeList.remove(remove_index);
            routeListDouble.remove(remove_index);
        }
//        System.out.println("---------------------------------------------");
//        for (String el : routeList){System.out.println(el);}
    }

    public List<Double[]> getRouteList(){
        return routeListDouble;
    }

    public List<State> getStateList(){
        return stateList;
    }

    public List<BotState> getBotStateList(){return botStateList; }

    public boolean containsState(State newState){
        return stateList.contains(newState);
    }
}
