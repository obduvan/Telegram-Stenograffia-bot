import constants.ConstantPath;
import constants.Constants;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;


public class Bot extends TelegramLongPollingBot {
    private ControlState controlState;
    private List<Map<String, String>> dataList;
    private Works works;
    private StandardFunctions standardFunctions;

    public Bot(List<Map<String, String>> idataList) {
        dataList = idataList;
        controlState = new ControlState();
        works = new Works();
        standardFunctions = new StandardFunctions();
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            try {
                var userId = message.getFrom().getId();
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

    private void handleInputMessage(Message message, Integer userId) throws IOException {
        String inputMsg = message.getText();
        var botStateMap = getBotStateMap();
        var botState = botStateMap.getOrDefault(inputMsg, BotState.NONE);

        controlState.updateStatesMap(userId, botState, message);

    }

    private void action(Integer userId) throws IOException {
        SendMessage sendMessage;
        var state = controlState.getStateUser(userId);
        switch (state.getStatus()) {
            case ASK_HELP:
                sendMessage = standardFunctions.sendHelpMsg(state.getLastMessage());
                sendMsg(sendMessage);
                break;
            case ASK_AUTHORS:
                sendMessage = standardFunctions.sendAuthorsMsg(state.getLastMessage());
                sendMsg(sendMessage);
                break;
            case ASK_WORKS:
                List<SendPhoto> sendPhotoList = works.sendWorksMsg(state, dataList);
                sendMsg(sendPhotoList);
                if (checkState(state))
                    sendMsg(standardFunctions.sendEndedWorks(state.getLastMessage()));
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
        if (state.getStatus() == BotState.ASK_WORKS)
            return state.getNumPhotoWorks() >= Constants.NUMWORKS;
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





