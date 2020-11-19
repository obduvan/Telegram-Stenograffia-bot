import Validations.GeoValidations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import systemStates.BotState;
import systemStates.CreateBotstateMap;
import systemStates.StatesValidator;
import java.io.IOException;
import java.util.HashMap;


public class TestStates {
    private HashMap<String, BotState> botStateMap;
    private StatesValidator statesValidator;

    @Before
    public void setup() throws IOException {
        botStateMap = new CreateBotstateMap().getBotStateMap();
        statesValidator = new StatesValidator();
    }

    @Test
    public void testBadRequest() {
        String[] badCommands = new String[]{"fsfdfsd", "/aaa", "/worksd", "What is your Mr.Credo?"};
        for(String command : badCommands){
            var checkBotState = statesValidator.checkBotState(command, BotState.NONE, false, botStateMap);
            Assert.assertEquals(checkBotState, BotState.NONE);
        }
    }

    @Test
    public void testGoodRequest() {
        String[] commands = new String[]{"/help", "/start", "/works", "/worksl", "/n", "/authors"};
        BotState[] states = new BotState[]{BotState.ASK_HELP, BotState.ASK_HELP, BotState.ASK_WORKS, BotState.WORKS_LOC_INIT, BotState.NEXT_ART, BotState.ASK_AUTHORS};
        int i = 0;
        for(String command : commands){
            var checkBotState = statesValidator.checkBotState(command, BotState.NONE, false, botStateMap);
            Assert.assertEquals(checkBotState,states[i]);
            i++;
        }
    }
}
