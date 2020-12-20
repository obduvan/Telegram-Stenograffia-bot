import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import systemStates.BotState;
import systemStates.CreateBotStateMap;
import Validations.StatesValidator;
import java.util.ArrayList;
import java.util.HashMap;


public class TestStates {
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
    public void testBadCommands() {
        String[] badCommands = new String[]{"fsfdfsd", "/aaa", "/worksd", "What is your Mr.Credo?"};
        for(String command : badCommands){
            var botState = statesValidator.checkBotState(command, BotState.NONE, false, botStateMap, routeList);
            Assert.assertEquals(botState, BotState.NONE);
        }
    }

    @Test
    public void testGoodCommands() {
        String[] commands = new String[]{"/help", "/start", "/works", "/worksl", "/n", "/authors", "/route"};
        BotState[] expectedStates = new BotState[]{BotState.ASK_HELP, BotState.ASK_HELP, BotState.ASK_WORKS, BotState.WORKS_LOC_INIT, BotState.NEXT_ART, BotState.ASK_AUTHORS, BotState.MSG_NO_ART_IN_LIST};

        var i = 0;
        for(String command : commands){
            var botState = statesValidator.checkBotState(command, BotState.NONE, false, botStateMap, routeList);
            Assert.assertEquals(botState, expectedStates[i]);
            i++;
        }
    }
}
