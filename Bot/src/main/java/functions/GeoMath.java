package functions;

import constants.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GeoMath {
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

    public ArrayList<double[]> getOptimalWay(ArrayList<double[]> coords) {

        ArrayList<double[]> res = new ArrayList<>(coords);

        for (var i = 0; i < res.size() - 1; i++) {
            double minWay = Double.MAX_VALUE;
            int indexWithMinWay = 0;
            for (var j = i+1; j < res.size(); j++) {
                double way = getGeoPointsDistance(res.get(i)[0], res.get(j)[0], res.get(i)[1], res.get(j)[1]);
                if (way < minWay) {
                    minWay = way;
                    indexWithMinWay = j;
                }
            }
            Collections.swap(res, i + 1, indexWithMinWay);
        }
        return res;
    }
}
