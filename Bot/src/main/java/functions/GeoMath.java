package functions;

import constants.Keys;
import constants.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GeoMath {
    public static Double getGeoPointsDistance(Double lat1, Double lat2, Double long1, Double long2) {

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

    public static String getOptimalWay(ArrayList<Double[]> intermediatePoints, double latitudeLast, double longtitudeLast, double latitude,  double longtitude) {

        var a = new Double[]{56.829033195152725, 60.59481518586104};
        var b = new Double[]{56.82957622260527, 60.588090194751565};

        intermediatePoints.add(b);
        intermediatePoints.add(a);

        ArrayList<Double[]> coords = new ArrayList<Double[]>();
        Double[] startCoords = new Double[]{(double)latitude, (double)longtitude};
        Double[] finishCoords = new Double[]{(double)latitudeLast, (double)longtitudeLast};
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
                    JSONArray abc = (JSONArray)obj.get("rows");
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
        return resPath;
    }

    public static void main(String[] args) {
        System.out.println(getOptimalWay(new ArrayList<Double[]>(), 56.829560433755184, 60.61502394563033, 56.828968612920335, 60.60232025086067));
    }
}
