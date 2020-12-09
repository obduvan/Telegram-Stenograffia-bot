package functions;

import constants.Keys;
import constants.Constants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GeoMath {
    private  static GeoMath instance;

    public static synchronized GeoMath getInstance(){
        if (instance == null){
            instance = new GeoMath();
        }
        return instance;
    }

    public Double getGeoPointsDistance(Double lat1, Double lat2, Double long1, Double long2) {

        if (lat1 > 90 || lat1 < -90 || lat2 > 90 || lat2 < -90 || long1 > 180 || long1 < -180 || long2 > 180 || long2 < -180) return -1.0;

        double currLocationLatitudeRads = Math.toRadians(lat2);
        double currLocationLongtitudeRads = Math.toRadians(long2);

        double artLatitude = Math.toRadians(lat1);
        double artLongtitude = Math.toRadians(long1);

        double halfDeltaLatitude = (artLatitude - currLocationLatitudeRads) / 2;
        double halfDeltaLongtitude = (artLongtitude - currLocationLongtitudeRads) / 2;

        double distance = Constants.EARTH_DIAMETER*Math.asin(Math.sqrt(
                Math.sin(halfDeltaLatitude) * Math.sin(halfDeltaLatitude) +
                Math.cos(artLatitude) * Math.cos(currLocationLatitudeRads) *
                        Math.sin(halfDeltaLongtitude) * Math.sin(halfDeltaLongtitude)));

        return distance;
    }

    public SendMessage getOptimalWay(String chatId, List<String> intermediatePointsList, double latitudeLast, double longtitudeLast, double latitude, double longtitude) {

        ArrayList<Double[]> intermediatePoints = new ArrayList<Double[]>();
        for (var latLong:intermediatePointsList) {
            var coordsString = latLong.split(" ");
            var currLatitude = Double.parseDouble(coordsString[0]);
            var currLongtitude = Double.parseDouble(coordsString[1]);
            var currCoords = new Double[]{currLatitude, currLongtitude};
            intermediatePoints.add(currCoords);
        }

        ArrayList<Double[]> coords = new ArrayList<Double[]>();
        Double[] startCoords = new Double[]{latitude, longtitude};
        Double[] finishCoords = new Double[]{latitudeLast, longtitudeLast};
        coords.add(startCoords);
        coords.addAll(intermediatePoints);

        final HttpClient httpClient = new HttpClient();

        ArrayList<Double[]> res = (ArrayList<Double[]>)coords.clone();

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
                        Constants.DISTANCE_MATRIX_URL_KEY + Keys.GOOGLE_API_KEY;

                final String response;
                try {
                    response = httpClient.post (url);
                    final JSONObject obj = new JSONObject(response);
//                    JSONArray abc = (JSONArray)obj.get("rows");
//                    System.out.println(abc);
                    way = (Integer) ((JSONObject)((JSONObject)((JSONArray)((JSONObject)((JSONArray)obj.get("rows")).get(0)).get("elements")).get(0)).get("distance")).get("value");
                } catch (IOException e) {
                    e.printStackTrace ( );
                }

                if (way < minWay) {
                    minWay = way;
                    indexWithMinWay = j;
                }
            }
            Collections.swap(res, i + 1, indexWithMinWay);
        }

        res.add(finishCoords);

        String resPath = Constants.PathYandexMapLoc;

        for (var value:res) {
            var currLatitude = value[0];
            var currLongtitude = value[1];
            resPath = resPath + currLatitude.toString() + Constants.YA_MAP_PATH_2C + currLongtitude.toString() + "~";
        }
        resPath = resPath.substring(0, resPath.length() - 1);
        resPath = resPath + Constants.YA_MAP_PATH_PART;

        var resMsg = new SendMessage();
        resMsg.setChatId(chatId);
        resMsg.setText(resPath);

        return resMsg;
    }
}
