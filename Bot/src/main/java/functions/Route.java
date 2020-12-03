package functions;

import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class Route {
    public SendMessage sendRouteMsg(String chatId, List<String> routeList){
        SendMessage sendMessage = new SendMessage();        // это все твоё
        sendMessage.setChatId(chatId);
        sendMessage.setText("ROUTE");
        return sendMessage;
    }
}