import Validations.GeoValidations;

import bot.Bot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import systemStates.BotState;
import systemStates.CreateBotstateMap;
import systemStates.StatesValidator;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class TestStates {
    private GeoValidations geoValidations;
    private HashMap<String, BotState> botStateMap;
    private StatesValidator statesValidator;

    @Before
    public void setup() throws IOException {
        geoValidations = new GeoValidations();
        botStateMap = new CreateBotstateMap().getBotStateMap();
        statesValidator = new StatesValidator();
    }

    @Test
    public void testBadRequest() {
        String badText_1 = "fsfdfsd";
        String badText_2 = "/aaa";
        var checkBotState_1 = statesValidator.checkBotState(badText_1, BotState.NONE, false, botStateMap);
        var checkBotState_2 = statesValidator.checkBotState(badText_2, BotState.NONE, false, botStateMap);

        Assert.assertEquals(checkBotState_1, BotState.NONE);
        Assert.assertEquals(checkBotState_2, BotState.NONE);
    }

    @Test
    public void testGoodRequest() {
        String[] commands = new String[]{"/help", "/start", "/works", "/worksl", "/n", "/authors"};
        List<String> commandsList = Arrays.asList(commands);
        BotState[] states = new BotState[]{BotState.ASK_HELP, BotState.ASK_HELP, BotState.ASK_WORKS, BotState.WORKS_LOC_INIT, BotState.NEXT_ART, BotState.ASK_AUTHORS};
        int i = 0;
        for(String command : commandsList){
            var checkBotState = statesValidator.checkBotState(command, BotState.NONE, false, botStateMap);
            Assert.assertEquals(checkBotState,states[i]);
            i++;
        }

    }
}
