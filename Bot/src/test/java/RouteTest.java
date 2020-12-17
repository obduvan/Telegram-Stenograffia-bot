import functions.HttpClient;
import functions.Route;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import constants.Constants;

import java.util.ArrayList;


public class RouteTest {
    private Route route;
    private HttpClient httpClient;

    @Before
    public void setup() {
        route = new Route();
        httpClient = new HttpClient();
    }

    @Test
    public void testLink() {
        var intermediatePoints = new ArrayList<Double[]>();
        intermediatePoints.add(new Double[]{56.84046533579498, 60.653743815289964});
        intermediatePoints.add(new Double[]{56.84375148104052, 60.573062968730625});
        intermediatePoints.add(new Double[]{56.803640796185675, 60.556411815291796});

        Double[] finishCoords = new Double[]{56.83201392329678, 60.583362651270114};

        String actualUrl = route.getRouteLink(intermediatePoints, finishCoords, httpClient);

        String expectedUrl = Constants.PathYandexMapLoc + "56.84046533579498" + Constants.YA_MAP_PATH_2C + "60.653743815289964" + "~" +
                "56.84375148104052" + Constants.YA_MAP_PATH_2C + "60.573062968730625" + "~" +
                "56.803640796185675" + Constants.YA_MAP_PATH_2C + "60.556411815291796" + "~" +
                "56.83201392329678" + Constants.YA_MAP_PATH_2C + "60.583362651270114" + Constants.YA_MAP_PATH_PART;


        Assert.assertEquals(actualUrl, expectedUrl);
    }

    @Test
    public void testLinkWithoutIntermediateValues() {
        var intermediatePoints = new ArrayList<Double[]>();
        intermediatePoints.add(new Double[]{56.84046533579498, 60.653743815289964});
        Double[] finishCoords = new Double[]{56.83201392329678, 60.583362651270114};

        String actualUrl = route.getRouteLink(intermediatePoints, finishCoords, httpClient);

        String expectedUrl = Constants.PathYandexMapLoc + "56.84046533579498" + Constants.YA_MAP_PATH_2C + "60.653743815289964" + "~" +
                "56.83201392329678" + Constants.YA_MAP_PATH_2C + "60.583362651270114" + Constants.YA_MAP_PATH_PART;

        Assert.assertEquals(actualUrl, expectedUrl);
    }

    @Test
    public void testLinkWithEqualValues() {
        var intermediatePoints = new ArrayList<Double[]>();
        intermediatePoints.add((new Double[]{56.84046533579498, 60.653743815289964}));
        Double[] finishCoords = new Double[]{56.84046533579498, 60.653743815289964};

        String actualUrl = route.getRouteLink(intermediatePoints, finishCoords, httpClient);

        String expectedUrl = Constants.PathYandexMapLoc + "56.84046533579498" + Constants.YA_MAP_PATH_2C + "60.653743815289964" + "~" +
                "56.84046533579498" + Constants.YA_MAP_PATH_2C + "60.653743815289964" + Constants.YA_MAP_PATH_PART;
        Assert.assertEquals(actualUrl, expectedUrl);
    }
}