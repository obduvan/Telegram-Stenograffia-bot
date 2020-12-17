package functions;

import constants.Constants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import realizations.CreatorSendMessage;

import java.util.ArrayList;
import java.util.Collections;

public class Route {
    private CreatorSendMessage creatorSendMessage;
    public Route(){
        creatorSendMessage = new CreatorSendMessage();
    }


    public SendMessage sendRouteMsg(String chatId, ArrayList<Double[]> routeList, double latitudeLast, double longtitudeLast, double latitude,  double longtitude){

        ArrayList<Double[]> coords = new ArrayList<>();
        Double[] startCoords = new Double[]{latitude, longtitude};
        Double[] finishCoords = new Double[]{latitudeLast, longtitudeLast};
        coords.add(startCoords);
        coords.addAll(routeList);

        final HttpClient httpClient = new HttpClient();

        String routeLink = getRouteLink(coords, finishCoords, httpClient);
        SendMessage sendMessage = creatorSendMessage.setMessage(chatId);
        sendMessage.setText(routeLink);

        return sendMessage;
    }

    public String getRouteLink(ArrayList<Double[]> coords, Double[] finishCoords, HttpClient httpClient) {
        ArrayList<Double[]> res = (ArrayList<Double[]>) coords.clone();

        for (var i = 0; i < res.size() - 1; i++) {
            double minWay = Double.MAX_VALUE;
            int indexWithMinWay = 0;
            for (var j = i+1; j < res.size(); j++) {
                var lat1 = res.get(i)[0];
                var lat2 = res.get(j)[0];
                var long1 = res.get(i)[1];
                var long2 = res.get(j)[1];

                var way = 0.0;
                String url = Constants.DISTANCE_MATRIX_URL_PATH + lat1 + "," + long1 +
                        Constants.DISTANCE_MATRIX_URL_DESTINATIONS + lat2 + "," + long2 +
                        Constants.DISTANCE_MATRIX_URL_KEY + System.getenv(Constants.SYS_API_KEY);

                final String response;
                try {
                    response = httpClient.post (url);
                    final JSONObject obj = new JSONObject(response);
                    way = (Integer) ((JSONObject)((JSONObject)((JSONArray)((JSONObject)((JSONArray)obj.get("rows")).get(0)).get("elements")).get(0)).get("distance")).get("value");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                if (way < minWay) {
                    minWay = way;
                    indexWithMinWay = j;
                }
            }
            Collections.swap(res, i + 1, indexWithMinWay);
        }

        res.add(finishCoords);

        return creatorRouteLink(res);
    }

    private String creatorRouteLink(ArrayList<Double[]> res){
        String resPath = Constants.PathYandexMapLoc;
        for (var value:res) {
            var currLatitude = value[0];
            var currLongtitude = value[1];
            resPath = resPath + currLatitude.toString() + Constants.YA_MAP_PATH_2C + currLongtitude.toString() + "~";
        }
        resPath = resPath.substring(0, resPath.length() - 1);
        resPath = resPath + Constants.YA_MAP_PATH_PART;
        return resPath;
    }
}