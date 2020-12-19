import Validations.StatesValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import systemStates.BotState;
import systemStates.CreateBotStateMap;

import java.util.ArrayList;
import java.util.HashMap;

public class TestRouteState {
    private HashMap<String, BotState> botStateMap;
    private StatesValidator statesValidator;

    @Before
    public void setup(){
        botStateMap = new CreateBotStateMap().getBotStateMap();
        statesValidator = new StatesValidator();
    }

    @Test
    public void testGoodLinkCommand_1() {
        var routeList = new ArrayList<Double[]>();
        routeList.add(new Double[]{56.84046533579498, 60.653743815289964});
        routeList.add(new Double[]{56.84375148104052, 60.573062968730625});
        routeList.add(new Double[]{56.803640796185675, 60.556411815291796});
        var firstBotState = BotState.NONE;
        var secondCommand = "/route";

        var secondBotState = statesValidator.checkBotState(secondCommand, firstBotState, false, botStateMap, routeList);
        Assert.assertEquals(secondBotState, BotState.GET_ROUTE);
    }

    @Test
    public void testBadLinkCommand_1() {
        var routeList = new ArrayList<Double[]>();
        var firstBotState = BotState.NONE;
        var secondCommand = "/route";

        var secondBotState = statesValidator.checkBotState(secondCommand, firstBotState, false, botStateMap, routeList);
        Assert.assertEquals(secondBotState, BotState.MSG_NO_ART_IN_LIST);
    }
}
