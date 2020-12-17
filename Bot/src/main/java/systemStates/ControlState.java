package systemStates;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ControlState {
    private ConcurrentHashMap<Integer, StructureStates> statesMap;

    public ControlState(){
        statesMap = new ConcurrentHashMap<>();
    }

    public void updateStatesMap(Integer userId, BotState botState, Message message) {
        var newState = new State(botState, message);
        if (!statesMap.containsKey(userId)){
            var newStructure = new StructureStates(newState);
            statesMap.put(userId, newStructure);
        }
        else{
            statesMap.get(userId).putState(newState);
//            statesMap.get(userId).clearUserRouteList();
            statesMap.get(userId).updateLongLang(newState);
        }
    }

    public void updateUserRouteList(Integer userId, String workCoordinates, boolean isAddWork){
        statesMap.get(userId).updateUserRouteList(workCoordinates, isAddWork);             // а если ключ не найдется?
    }


    public List<Double[]> getUserRouteList(Integer userId){
        return statesMap.get(userId).getRouteList();
    }

    public boolean existUser(Integer userId){
        return statesMap.containsKey(userId);
    }

    public State getStateUser(Integer userId){
        return statesMap.get(userId).getMainState();
    }

    public StructureStates getStructureUser(Integer userId){
        return statesMap.get(userId);
    }

    public List<BotState> getBotStateListUser(Integer userId){
        return statesMap.get(userId).getBotStateList();
    }
}
