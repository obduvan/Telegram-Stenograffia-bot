import Validations.StatesValidator;
import bot.Bot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import systemStates.BotState;
import systemStates.CreateBotStateMap;

import java.util.ArrayList;
import java.util.HashMap;

public class TestLocationStates {
    private HashMap<String, BotState> botStateMap;
    private StatesValidator statesValidator;
    private ArrayList<Double[]> routeList;

    @Before
    public void setup(){
        botStateMap = new CreateBotStateMap().getBotStateMap();
        statesValidator = new StatesValidator();
        routeList = new ArrayList<>();
    }

    @Test
    public void testGoodLinkCommand_1() {
        var firstBotState = BotState.WORKS_LOC_RAD;
        var secondCommand = "4";            // expected radius in kilometers

        var secondBotState = statesValidator.checkBotState(secondCommand, firstBotState, false, botStateMap, routeList);
        Assert.assertEquals(secondBotState, BotState.WORKS_LOC_GET);
    }

    @Test
    public void testBadLinkCommand_1() {
        var firstBotState = BotState.WORKS_LOC_RAD;
        var secondCommand = "4ffffff";            // expected radius in kilometers

        var secondBotState = statesValidator.checkBotState(secondCommand, firstBotState, false, botStateMap, routeList);
        Assert.assertEquals(secondBotState, BotState.NONE);
    }

    @Test
    public void testGoodLinkCommand_2() {
        var firstBotState = BotState.WORKS_LOC_RAD;
        var goodWorksLinkCommands = new String[]{"4", "/n", "/help", "/n"};
        BotState[] expectedStates = new BotState[]{BotState.WORKS_LOC_GET, BotState.NEXT_ART,BotState.ASK_HELP, BotState.NEXT_ART};

        var i = 0 ;
        var botState = firstBotState;
        for (String command: goodWorksLinkCommands){
            botState = statesValidator.checkBotState(command, botState, false, botStateMap, routeList);
            Assert.assertEquals(botState, expectedStates[i]);
            i++;
        }
    }


}
