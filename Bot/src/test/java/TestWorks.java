//import Validations.StatesValidator;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import systemStates.BotState;
//import systemStates.CreateBotStateMap;
//
//import java.util.HashMap;
//
//public class TestWorks {
//    private HashMap<String, BotState> botStateMap;
//    private StatesValidator statesValidator;
//
//    @Before
//    public void setup(){
//        botStateMap = new CreateBotStateMap().getBotStateMap();
//        statesValidator = new StatesValidator();
//    }
//
//    @Test
//    public void testGoodOrderRequest_1() {
//        var commandsSecond = "/n";
//        var botStateSecond = statesValidator.checkBotState(commandsSecond, BotState.ASK_WORKS, false, botStateMap);
//        Assert.assertEquals(botStateSecond, BotState.NEXT_ART);
//    }
//
//    @Test
//    public void testBadOrderRequest_1() {
//        String commandsSecond = "/p";
//        var botStateSecond = statesValidator.checkBotState(commandsSecond, BotState.ASK_WORKS, false, botStateMap);
//        Assert.assertEquals(botStateSecond, BotState.NONE);
//    }
//}
