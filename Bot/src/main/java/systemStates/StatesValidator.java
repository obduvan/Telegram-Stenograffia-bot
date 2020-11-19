package systemStates;
import java.util.HashMap;


public class StatesValidator {

    public BotState checkBotState(String textMessage, BotState botStateLast, boolean isGeoMsg, HashMap<String, BotState> botStateMap ){
        String inputMsg = textMessage;
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

    public boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
