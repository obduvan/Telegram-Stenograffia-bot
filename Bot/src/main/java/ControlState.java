import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.concurrent.ConcurrentHashMap;

public class ControlState {
    private ConcurrentHashMap<Integer, StructureStates> statesMap = new ConcurrentHashMap<>();

    public void updateStatesMap(Integer userId, BotState botState, Message message) {
        var newState = new State(botState, message);
        if (!statesMap.containsKey(userId)){
            var newStructure = new StructureStates(newState);
            statesMap.put(userId, newStructure);
        }
        else{
            statesMap.get(userId).putState(newState);
        }
    }

    public State getStateUser(Integer userId){
        return statesMap.get(userId).getMainState();
        }
}
