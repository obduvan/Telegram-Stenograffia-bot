package functions;

import constants.Keys;
import constants.Constants;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

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

    public static ArrayList<double[]> getOptimalWay(ArrayList<double[]> coords) {

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=40.6655101,-73.89188969999998&destinations=41.6655101,-73.89188969999998&key=" + Keys.GOOGLE_API_KEY;

        final HttpClient httpClient = new HttpClient();

        ArrayList<double[]> res = new ArrayList<>(coords);

        final String response;
        try {
            response = httpClient.post (url);
            final JSONObject obj = new JSONObject(response);
            System.out.println (obj);
        } catch (IOException e) {
            e.printStackTrace ( );
        }

        for (var i = 0; i < res.size() - 1; i++) {
            double minWay = Double.MAX_VALUE;
            int indexWithMinWay = 0;
            for (var j = i+1; j < res.size(); j++) {
                var lat1 = res.get(i)[0];
                var lat2 = res.get(j)[0];
                var long1 = res.get(i)[1];
                var long2 = res.get(j)[1];


                var way = 0.0;



                if (way < minWay) {
                    minWay = way;
                    indexWithMinWay = j;
                }
            }
            Collections.swap(res, i + 1, indexWithMinWay);
        }
        return res;
    }

    public static void main(String[] args) {
        getOptimalWay(new ArrayList<double[]>());
    }
}
