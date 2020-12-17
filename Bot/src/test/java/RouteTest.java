import functions.GeoMath;
import functions.Route;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import constants.Constants;

import java.lang.invoke.ConstantBootstraps;
import java.util.ArrayList;
import java.util.List;

public class RouteTest {
    private Route route;

    @Before
    public void setup() {
        route = new Route();
    }

    @Test
    public void testLink() {
        String chatId = "test";
        var intermediatePoints = new ArrayList<Double[]>();
        intermediatePoints.add(new Double[]{56.803640796185675, 60.556411815291796}); // потом эта
        intermediatePoints.add(new Double[]{56.84375148104052, 60.573062968730625}); // сначала эта
        SendMessage actual = route.sendRouteMsg(chatId, intermediatePoints, 56.83201392329678, 60.583362651270114, 56.84046533579498, 60.653743815289964);
        SendMessage expected = new SendMessage();

        String expectedUrl = Constants.PathYandexMapLoc + "56.84046533579498" + Constants.YA_MAP_PATH_2C + "60.653743815289964" + "~" +
                "56.84375148104052" + Constants.YA_MAP_PATH_2C + "60.573062968730625" + "~" +
                "56.803640796185675" + Constants.YA_MAP_PATH_2C + "60.556411815291796" + "~" +
                "56.83201392329678" + Constants.YA_MAP_PATH_2C + "60.583362651270114" + Constants.YA_MAP_PATH_PART;

        expected.setChatId(chatId);
        expected.setText(expectedUrl);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testLinkWithoutIntermediateValues() {
        String chatId = "test";
        var intermediatePoints = new ArrayList<Double[]>();
        SendMessage actual = route.sendRouteMsg(chatId, intermediatePoints, 56.83201392329678, 60.583362651270114, 56.84046533579498, 60.653743815289964);
        SendMessage expected = new SendMessage();

        String expectedUrl = Constants.PathYandexMapLoc + "56.84046533579498" + Constants.YA_MAP_PATH_2C + "60.653743815289964" + "~" +
                "56.83201392329678" + Constants.YA_MAP_PATH_2C + "60.583362651270114" + Constants.YA_MAP_PATH_PART;

        expected.setChatId(chatId);
        expected.setText(expectedUrl);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testLinkWithEqualValues() {
        String chatId = "test";
        var intermediatePoints = new ArrayList<Double[]>();
        SendMessage actual = route.sendRouteMsg(chatId, intermediatePoints, 56.84046533579498, 60.653743815289964, 56.84046533579498, 60.653743815289964);
        SendMessage expected = new SendMessage();

        String expectedUrl = Constants.PathYandexMapLoc + "56.84046533579498" + Constants.YA_MAP_PATH_2C + "60.653743815289964" + "~" +
                "56.84046533579498" + Constants.YA_MAP_PATH_2C + "60.653743815289964" + Constants.YA_MAP_PATH_PART;

        expected.setChatId(chatId);
        expected.setText(expectedUrl);
        Assert.assertEquals(actual, expected);
    }

}
