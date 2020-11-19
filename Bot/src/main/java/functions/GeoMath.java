package functions;

import constants.Constants;

public class GeoMath {
    public Double getGeoPointsDistance(Double lat1, Double lat2, Double long1, Double long2) {

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
}
