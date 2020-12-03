package functions;

import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class Route {
    public SendMessage sendRouteMsg(String chatId, List<String> routeList){
        SendMessage sendMessage = new SendMessage();        // routeList - лист с координатами вида: [широта долгота, широта долгота1, широта долгота2, ...]
        sendMessage.setChatId(chatId);
        sendMessage.setText("ROUTE");
        return sendMessage;
    }
}