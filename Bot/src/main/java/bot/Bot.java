package bot;
import constants.ConstantPath;
import constants.Constants;
import functions.WorkLocation;
import functions.Works;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import realizations.StandardFunctions;
import systemStates.BotState;
import systemStates.ControlState;
import systemStates.State;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;


public class Bot extends TelegramLongPollingBot {
    private ControlState controlState;
    private List<Map<String, String>> dataList;
    private Works works;
    private StandardFunctions standardFunctions;
    private WorkLocation workLocation;
    private HashMap<BotState, Function<Message, SendMessage>> actionMapText;
    private HashMap<BotState, BiFunction<State, List<Map<String, String>>, List<SendPhoto>>> actionMapPhoto;

    public Bot(List<Map<String, String>> idataList) {
        dataList = idataList;
        controlState = new ControlState();
        works = new Works();
        actionMapText = new HashMap<>();
        workLocation = new WorkLocation();
        standardFunctions = new StandardFunctions();
        actionMapPhoto = new HashMap<>();
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null) {
            try {
                var userId = message.getFrom().getId();
                initMapsMsg();
                handleInputMessage(message, userId);
                action(userId);
            } catch (IOException throwables) {
                throwables.printStackTrace();
            }
        }
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

    private boolean checkLocMsg(Message message) {
        return message.getLocation() != null;
    }

    public boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private BotState checkBotState(Message message, Integer userId) throws IOException {
        String inputMsg = message.getText();
        var botStateMap = getBotStateMap();
        BotState botState;
        BotState botStateLast;
        botStateLast = controlState.existUser(userId) ? controlState.getStateUser(userId).getBotState() : BotState.NONE;

        if (inputMsg == null || !botStateMap.containsKey(inputMsg)) {
            if (checkLocMsg(message)) {
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

    private void handleInputMessage(Message message, Integer userId) throws IOException {
        System.out.println(message);
        var botState = checkBotState(message, userId);
        controlState.updateStatesMap(userId, botState, message);
    }

    private void initMapsMsg(){
        createMapPhotoMessage();
        createMapTextMessage();
    }

    private void createMapTextMessage() {
        actionMapText.put(BotState.ASK_HELP, standardFunctions::sendHelpMsg);
        actionMapText.put(BotState.ASK_AUTHORS, standardFunctions::sendAuthorsMsg);
        actionMapText.put(BotState.WORKS_LOC_RAD, workLocation::sendRadMsg);
        actionMapText.put(BotState.WORKS_LOC_INIT, standardFunctions::sendLocMsg);
    }

    private void createMapPhotoMessage() {
        actionMapPhoto.put(BotState.WORKS_LOC_GET, workLocation::sendWorksMsg);
        actionMapPhoto.put(BotState.ASK_WORKS, works::sendWorksMsg);
    }

    private SendMessage getSendMessage(State state) {
        return actionMapText.get(state.getBotState()).apply(state.getLastMessage());
    }

    private List<SendPhoto> getListPhotoMessage(State state) {
        return actionMapPhoto.get(state.getBotState()).apply(state, dataList);
    }

    private void checkEmpty(List<SendPhoto> sendLocationPhotoList, State state) {
        SendMessage sendMessage;
        if (!sendLocationPhotoList.isEmpty()) {
            sendMsg(sendLocationPhotoList);
            if (checkState(state))
                sendMsg(standardFunctions.sendEndedWorks(state.getLastMessage()));
        } else {
            sendMessage = standardFunctions.sendNoWorksMsg(state.getLastMessage());
            sendMsg(sendMessage);
        }
    }

    private void action(Integer userId) {
        var state = controlState.getStateUser(userId);

        switch (state.getTypeMessage()) {
            case IS_TEXT:
                SendMessage sendMessage = getSendMessage(state);
                sendMsg(sendMessage);
                break;
            case IS_PHOTO:
                List<SendPhoto> sendLocationPhotoList = getListPhotoMessage(state);
                checkEmpty(sendLocationPhotoList, state);
                break;
        }
    }

    public void sendMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(List<SendPhoto> sendPhotoList) {
        try {
            for (SendPhoto sendPhoto : sendPhotoList)
                execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private boolean checkState(State state) {
        if (state.getBotState() == BotState.NEXT_ART || state.getBotState() == BotState.WORKS_LOC_GET)
            return state.getNumPhotoWorks() > state.getTotalLocationPhotoWorks();
        return false;
    }

    @Override
    public String getBotToken() {
        return System.getenv(Constants.SYSTOKEN);
    }

    @Override
    public String getBotUsername() {
        return System.getenv(Constants.SYSNAMEBOT);
    }
}





