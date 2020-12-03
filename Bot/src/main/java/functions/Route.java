package functions;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class Route {
    public SendMessage sendRouteMsg(String chatId, List<Double[]> routeList, double latitudeLast, double longtitudeLast, double latitude,  double longtitude){
        SendMessage sendMessage = new SendMessage();        // routeList - лист с координатами вида: [широта долгота, широта долгота1, широта долгота2, ...]
        sendMessage.setChatId(chatId);
        sendMessage.setText("ROUTE");
        System.out.println(latitude);
        System.out.println(longtitude);
        System.out.println(latitudeLast);
        System.out.println(longtitudeLast);
        for (Double[] el: routeList){System.out.println(el);}
        return sendMessage;
    }



}