package functions;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class Route {
    public SendMessage sendRouteMsg(String chatId, List<String> routeList, float latitudeLast, float longtitudeLast){
        SendMessage sendMessage = new SendMessage();        // routeList - лист с координатами вида: [широта долгота, широта долгота1, широта долгота2, ...]
        sendMessage.setChatId(chatId);
        sendMessage.setText("ROUTE");
        System.out.println(latitudeLast);
        System.out.println(longtitudeLast);
        for (String el: routeList){System.out.println(el);}
        return sendMessage;
    }



}