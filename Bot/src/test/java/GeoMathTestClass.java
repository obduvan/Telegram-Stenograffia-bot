import functions.GeoMath;
import org.junit.Assert;
import org.junit.Test;


public class GeoMathTestClass {
    @Test
    public void testZeroDistance() {
        GeoMath geoMath = new GeoMath();
        double actual = geoMath.getGeoPointsDistance(60.000000, 60.000000, 50.000000, 50.000000);
        Assert.assertEquals(0, actual, 1e-5);
    }

    @Test
    public void testRealDistance() {
        GeoMath geoMath = new GeoMath();
        double actual = geoMath.getGeoPointsDistance(0.000000, 0.000000, 10.000000, 11.000000);
        Assert.assertEquals(111.3213, actual, 0.6);
    }

    @Test
    public void testWrongData() {
        // Latitude > 90deg or < -90deg
        // Longtitude > 180deg or < -180deg
        GeoMath geoMath = new GeoMath();
        double actual = geoMath.getGeoPointsDistance(100.000000, -100.000000, 200.000000, -200.000000);
        Assert.assertEquals(-1, actual, 1e-5);
    }

}
