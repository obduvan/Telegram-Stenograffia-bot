package systemStates;

import constants.ConstantPath;
import constants.Constants;
import functions.GeoMath;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class CreateBotStateMap {
    private  static CreateBotStateMap instance;

    public static synchronized CreateBotStateMap getInstance(){
        if (instance == null){
            instance = new CreateBotStateMap();
        }
        return instance;
    }

    public  HashMap<String, BotState> getBotStateMap() throws IOException {
        HashMap<String, BotState> botStateMap = new HashMap<>();
        botStateMap.put(Constants.START, BotState.ASK_HELP);
        botStateMap.put(Constants.GET_ROUTE, BotState.GET_ROUTE);
        var botStates = BotState.values();
        Scanner s = new Scanner(new File(ConstantPath.commands));
        int k = 0;
        while (s.hasNext()) {
            botStateMap.put(s.next(), botStates[k]);
            k++;
        }
        s.close();


        return botStateMap;
    }


}
