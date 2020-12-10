package systemStates;

import constants.Constants;
import java.util.HashMap;


public class CreateBotStateMap {
    private static CreateBotStateMap instance;

    public static synchronized CreateBotStateMap getInstance(){
        if (instance == null){
            instance = new CreateBotStateMap();
        }
        return instance;
    }

    public HashMap<String, BotState> getBotStateMap()  {
        HashMap<String, BotState> botStateMap = new HashMap<>();
        botStateMap.put(Constants.START, BotState.ASK_HELP);
        botStateMap.put(Constants.GET_ROUTE, BotState.GET_ROUTE);
        botStateMap.put(Constants.HELP, BotState.ASK_HELP);
        botStateMap.put(Constants.AUTHORS, BotState.ASK_AUTHORS);
        botStateMap.put(Constants.WORKS, BotState.ASK_WORKS);
        botStateMap.put(Constants.NEXTART, BotState.NEXT_ART);
        botStateMap.put(Constants.WORKSLOC, BotState.WORKS_LOC_INIT);

        return botStateMap;
    }


}
