package systemStates;

import constants.ConstantPath;
import constants.Constants;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class CreateBotstateMap {
    public  HashMap<String, BotState> getBotStateMap() throws IOException {
        HashMap<String, BotState> botStateMap = new HashMap<>();
        botStateMap.put(Constants.START, BotState.ASK_HELP);
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
