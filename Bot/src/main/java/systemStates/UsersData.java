package systemStates;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UsersData {
    private ConcurrentHashMap<Integer, UserStructureStates> UsersDataMap;

    public UsersData() {
        UsersDataMap = new ConcurrentHashMap<>();
    }

    public void setNewStatesUsersMap(Integer userId) {
        var newStructure = new UserStructureStates();
        UsersDataMap.put(userId, newStructure);
    }

    public void updateStatesUserMap(Integer userId, BotState newBotState, Message message) {
        var newState = new State(newBotState, message);

        var userStructure = getStructureUser(userId);
        if (userStructure.getStateList().size() == 0) {
            userStructure.setNewState(newState);
        }
        else {
            UsersDataMap.get(userId).updateState(newState);
            UsersDataMap.get(userId).updateLongLang(newState);
        }
    }

    public void updateUserRouteList(Integer userId, String workCoordinates, boolean isAddWork) {
        UsersDataMap.get(userId).updateUserRouteList(workCoordinates, isAddWork);
    }

    public List<Double[]> getUserRouteList(Integer userId) {
        return UsersDataMap.get(userId).getRouteList();
    }

    public boolean existUser(Integer userId) {
        return UsersDataMap.containsKey(userId);
    }

    public State getStateUser(Integer userId) {
        return UsersDataMap.get(userId).getMainState();
    }

    public UserStructureStates getStructureUser(Integer userId) {
        return UsersDataMap.get(userId);
    }

    public List<BotState> getBotStateListUser(Integer userId) {
        return UsersDataMap.get(userId).getBotStateList();
    }
}
