import Validations.StatesValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import systemStates.BotState;
import systemStates.CreateBotStateMap;

import java.util.ArrayList;
import java.util.HashMap;

public class TestWorkStates {
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
        var firstBotState = BotState.ASK_WORKS;
        var secondCommand = "/n";

        var secondBotState = statesValidator.checkBotState(secondCommand, firstBotState, false, botStateMap, routeList);
        Assert.assertEquals(secondBotState, BotState.NEXT_ART);
    }

    @Test
    public void testBadLinkCommand_1() {
        var firstBotState = BotState.ASK_WORKS;
        var secondCommand = "/invalid commands";

        var secondBotState = statesValidator.checkBotState(secondCommand, firstBotState, false, botStateMap, routeList);
        Assert.assertEquals(secondBotState, BotState.NONE);
    }

    @Test
    public void testGoodLinkCommand_2() {
        var goodWorksLinkCommands = new String[]{"/works", "/n", "/help", "/n"};
        BotState[] expectedStates = new BotState[]{BotState.ASK_WORKS, BotState.NEXT_ART,BotState.ASK_HELP, BotState.NEXT_ART};

        var i = 0 ;
        var botState = BotState.NONE;
        for (String command: goodWorksLinkCommands){
            botState = statesValidator.checkBotState(command, botState, false, botStateMap, routeList);
            Assert.assertEquals(botState, expectedStates[i]);
            i++;
        }
    }
}
