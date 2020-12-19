package Validations;

import systemStates.BotState;

import java.util.HashMap;
import java.util.List;


public class StatesValidator {

    public BotState checkBotState(String textMessage, BotState botStateLast, boolean isGeoMsg, HashMap<String, BotState> botStateMap, List<Double[]> routeList){

        BotState botState;
        if (textMessage == null || !botStateMap.containsKey(textMessage)) {
            if (isGeoMsg) {
                if (botStateLast.equals(BotState.GET_ROUTE))
                    botState = BotState.GET_ROUTE_URL;
                else
                    botState = BotState.WORKS_LOC_RAD;
            } else if (isFloat(textMessage) && (botStateLast.equals(BotState.WORKS_LOC_RAD) || botStateLast.equals(BotState.WORKS_LOC_GET))) {
                botState = BotState.WORKS_LOC_GET;
            } else {
                botState = (botStateLast.equals(BotState.WORKS_LOC_RAD) || botStateLast.equals(BotState.WORKS_LOC_GET)) ? BotState.WORKS_LOC_RAD : BotState.NONE;
            }
        }
        else {
            botState = botStateMap.get(textMessage);
        }

        botState = validateGetRouteState(botState, routeList);
        return botState;
    }

    public BotState validateGetRouteState(BotState currentBotState, List<Double[]> routeList){
        if (currentBotState == BotState.GET_ROUTE){
            if (routeList.size() == 0) currentBotState = BotState.MSG_NO_ART_IN_LIST;
        }
        return currentBotState;
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
