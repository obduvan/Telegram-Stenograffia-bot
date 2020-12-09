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

    public static ArrayList<Double[]> getOptimalWay(ArrayList<Double[]> a) {

        ArrayList<Double[]> coords = new ArrayList<Double[]>();
        Double[] m = new Double[]{56.82969129413337, 60.62512613127236};
        Double[] x = new Double[]{56.82925978317406, 60.62307280380156};
        Double[] y = new Double[]{56.82775752677078, 60.61883864119887};
        Double[] z = new Double[]{56.822429366931985, 60.55912993340787};
        Double[] n = new Double[]{56.81689455893772, 60.54017628175199};
        coords.add(m);
        coords.add(n);
        coords.add(z);
        coords.add(x);
        coords.add(y);

//        String url = "47.6655101,-75.89188969999998&destinations=41.6655101,-73.89188969999998&key=" + Keys.GOOGLE_API_KEY;

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
                    System.out.println(abc);
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
        for (var value:res) {
            System.out.println(Arrays.toString(value));
        }
        return res;
    }

    public static void main(String[] args) {
        getOptimalWay(new ArrayList<Double[]>());
    }
}
