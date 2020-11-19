package systemStates;

import constants.ConstantPath;
import constants.Constants;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class StatesValidator {

    public BotState checkBotState(String textMessage, BotState botStateLast, boolean isGeoMsg ) throws IOException {
        String inputMsg = textMessage;
        var botStateMap = getBotStateMap();
        BotState botState;

        if (inputMsg == null || !botStateMap.containsKey(inputMsg)) {
            if (isGeoMsg) {
                botState = BotState.WORKS_LOC_RAD;
            } else if (isFloat(inputMsg) && (botStateLast.equals(BotState.WORKS_LOC_RAD) || botStateLast.equals(BotState.WORKS_LOC_GET))) {
                botState = BotState.WORKS_LOC_GET;
            } else {
                botState = (botStateLast.equals(BotState.WORKS_LOC_RAD) || botStateLast.equals(BotState.WORKS_LOC_GET)) ? BotState.WORKS_LOC_RAD : BotState.NONE;
            }
        } else {
            botState = botStateMap.get(inputMsg);
        }
        return botState;
    }

    private ConcurrentHashMap<String, BotState> getBotStateMap() throws IOException {
        ConcurrentHashMap<String, BotState> botStateMap = new ConcurrentHashMap<>() {};
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

    public boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


}
