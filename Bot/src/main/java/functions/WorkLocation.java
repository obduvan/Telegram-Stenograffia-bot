package functions;
import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import realizations.PhotoWorks;
import systemStates.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkLocation extends PhotoWorks {
    private double currLocationLatitude;
    private double currLocationLongtitude;
    private double currRadius;
    private SendMessage sendMessage;
    private GeoMath geoMath;

    public WorkLocation() {
        sendMessage = new SendMessage();
        geoMath = GeoMath.getInstance();
    }

    public SendMessage setMessage(String chatId) {
        sendMessage.setChatId(chatId);
        return sendMessage;
    }

    public SendMessage sendRadMsg(double latitude, double longtitude, String chatId) {
        currLocationLatitude = latitude;
        currLocationLongtitude = longtitude;
        var sendMessage = setMessage(chatId);
        sendMessage.setText(Constants.SENDRADMSG);
        return sendMessage;
    }

    public SendPhoto sendMsg(Map<String, String> dataLine, State state) {
        String[] workCoordinates = dataLine.get(Constants.COORDINATES).split(" ");

        String forRoadCoordinates = String.format("%s %s",workCoordinates[0], workCoordinates[1]);

        String way = Constants.PathYandexMapLoc
                + Double.toString(currLocationLatitude) + Constants.YA_MAP_PATH_2C + Double.toString(currLocationLongtitude) + "~"
                + workCoordinates[0] + Constants.YA_MAP_PATH_2C + workCoordinates[1]
                + Constants.YA_MAP_PATH_PART;
        return createPhotoBoardObj(dataLine, state, way, forRoadCoordinates);
    }

    public List<SendPhoto> sendWorksMsg(State state, List<Map<String, String>> dataList) {
        Message currMessage = state.getLastMessage();
        currRadius = Float.parseFloat(currMessage.getText());

        ArrayList<Map<String, String>> dataLines = new ArrayList<>();

        for (int i = 0; i < Constants.COLWORKS; i++) {
            Map<String, String> currDataLine = dataList.get(i);
            String[] coords = currDataLine.get(Constants.COORDINATES).split(" ");

            Double distance = geoMath.getGeoPointsDistance(Double.parseDouble(coords[0]), (double)currLocationLatitude,
                    Double.parseDouble(coords[1]), (double)currLocationLongtitude);

            if (distance < currRadius) {
                dataLines.add(currDataLine);
            }
        }
        return getPhotoList(state, dataLines);
    }


    private List<SendPhoto> getPhotoList(State state, ArrayList<Map<String, String>> dataLines) {
        var totalWorksCount = dataLines.size();

        state.setTotalPhotoWorks(totalWorksCount);
        int buffer = Constants.BUFFER;
        List<SendPhoto> sendPhotoList = new ArrayList<>();

        while (buffer != 0 && state.getNumPhotoWorks() <= totalWorksCount) {
            sendPhotoList.add(sendMsg(dataLines.get(state.getNumPhotoWorks() - 1),state));
            buffer--;
            state.updateNumPhotoWorks();
        }
        return sendPhotoList;
    }
}
