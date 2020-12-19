package bot;

import Validations.GeoValidations;
import Validations.StatesValidator;
import constants.Constants;
import functions.Route;
import functions.StandardFunctions;
import functions.WorkLocation;
import functions.Works;
import inlineKeyboard.InlineKeyboardWork;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import systemStates.*;

import java.util.*;
import java.util.function.*;


public class Bot extends TelegramLongPollingBot {
    private UsersData usersData;
    private List<Map<String, String>> dataList;
    private Works works;
    private StandardFunctions standardFunctions;
    private WorkLocation workLocation;
    private StatesValidator statesValidator;
    private GeoValidations geoValidations;
    private HashMap<BotState, Function<String, SendMessage>> actionMapText;
    private HashMap<BotState, BiFunction<State, List<Map<String, String>>, List<SendPhoto>>> actionMapPhoto;
    private HashMap<String, BotState> botStateMap;
    private InlineKeyboardWork changeInlineKeyboard;
    private Route route;

    public Bot(List<Map<String, String>> idataList) {
        dataList = idataList;
        usersData = new UsersData();
        works = new Works();
        actionMapText = new HashMap<>();
        workLocation = new WorkLocation();
        standardFunctions = new StandardFunctions();
        actionMapPhoto = new HashMap<>();
        statesValidator = new StatesValidator();
        geoValidations = new GeoValidations();
        botStateMap = new HashMap<>();
        route = new Route();

        changeInlineKeyboard = new InlineKeyboardWork();
        botStateMap = CreateBotStateMap.getInstance().getBotStateMap();

        initMapsMsg();
    }

    private void initMapsMsg() {
        createMapWorkMessage();
        createMapTextMessage();
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null) {
            var userId = message.getFrom().getId();
            handleInputMessage(message, userId);
            action(userId);
        }
        else if (update.hasCallbackQuery()) {
            var newMessage = changeInlineKeyboard.getChangedKeyboard(update);
            updateUserRouteList(update.getCallbackQuery().getFrom().getId());
            try {
                execute(newMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUserRouteList(Integer userId) {
        var workCoordinates = changeInlineKeyboard.getWorkCoordinates();
        var isAddWork = changeInlineKeyboard.isAddWorkRoute();
        usersData.updateUserRouteList(userId, workCoordinates, isAddWork);
    }

    private BotState getLastState(Integer userId) {
        BotState lastState = BotState.NONE;
        if (usersData.existUser(userId)) {
            var listStates = usersData.getStructureUser(userId).getStateList();
            for (int i = listStates.size() - 1; i >= 0; i--) {
                var state = listStates.get(i);
                if (state.getBotState() != BotState.NONE) {
                    lastState = state.getBotState();
                    break;
                }
            }
        }

        return lastState;
    }

    private void setNewUser(Integer userId) {
        if (!usersData.existUser(userId))
            usersData.setNewStatesUsersMap(userId);
    }

    private BotState getNewBotState(Message message, Integer userId) {
        BotState botStateLast = getLastState(userId);
        boolean isGeoMsg = geoValidations.checkLocMsg(message);
        var routeList = usersData.getUserRouteList(userId);

        return statesValidator.checkBotState(message.getText(), botStateLast, isGeoMsg, botStateMap, routeList);

    }

    private void updateUserBotState(BotState newBotState, Integer userId, Message message) {
        usersData.updateStatesUserMap(userId, newBotState, message);
    }

    private void handleInputMessage(Message message, Integer userId) {
        System.out.println(message);
        setNewUser(userId);

        BotState newBotState = getNewBotState(message, userId);
        updateUserBotState(newBotState, userId, message);
    }

    private void createMapTextMessage() {
        actionMapText.put(BotState.NONE, standardFunctions::sendNoneMsg);
        actionMapText.put(BotState.ASK_HELP, standardFunctions::sendHelpMsg);
        actionMapText.put(BotState.ASK_AUTHORS, standardFunctions::sendAuthorsMsg);
        actionMapText.put(BotState.WORKS_LOC_INIT, standardFunctions::sendLocMsg);
        actionMapText.put(BotState.GET_ROUTE, standardFunctions::sendLocRouteMsg);
        actionMapText.put(BotState.MSG_NO_ART_IN_LIST, standardFunctions::sendNoArtInListMsg);
    }

    private void createMapWorkMessage() {
        actionMapPhoto.put(BotState.WORKS_LOC_GET, workLocation::sendWorksMsg);
        actionMapPhoto.put(BotState.ASK_WORKS, works::sendWorksMsg);
    }

    private SendMessage getSendMessage(State state) {
        return actionMapText.get(state.getBotState()).apply(state.getChatId());
    }

    private List<SendPhoto> getListPhotoMessage(State state) {
        return actionMapPhoto.get(state.getBotState()).apply(state, dataList);
    }

    private void checkEmpty(List<SendPhoto> sendLocationPhotoList, State state) {
        SendMessage sendMessage;
        if (!sendLocationPhotoList.isEmpty()) {
            sendMsg(sendLocationPhotoList);
            if (checkState(state))
                sendMsg(standardFunctions.sendEndedWorks(state.getChatId()));
        } else {
            sendMessage = standardFunctions.sendNoWorksMsg(state.getChatId());
            sendMsg(sendMessage);
        }
    }

    private void action(Integer userId) {
        var state = usersData.getStateUser(userId);
        SendMessage sendMessage;

        switch (state.getTypeMessage()) {
            case IS_TEXT:
                if (state.getBotState() == BotState.WORKS_LOC_RAD) {
                    sendMessage = workLocation.sendRadMsg(state.getLatitude(), state.getLongtitude(), state.getChatId());
                }
                else if (state.getBotState() == BotState.GET_ROUTE_URL) {
                    var routeList = usersData.getUserRouteList(userId);
                    var latitudeLast = state.getLatitudeLast();
                    var longtitudeLast = state.getLongtitudeLast();
                    var latitude = state.getLatitude();
                    var longtitude = state.getLongtitude();
                    sendMessage = route.sendRouteMsg(state.getChatId(), (ArrayList<Double[]>) routeList, latitudeLast, longtitudeLast, latitude, longtitude);
                }
                else {
                    sendMessage = getSendMessage(state);
                }
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
            return state.getNumPhotoWorks() > state.getTotalPhotoWorks();
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





