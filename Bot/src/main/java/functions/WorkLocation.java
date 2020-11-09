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

    public Float currLocationLatitude = null;
    public Float currLocationLongtitude = null;
    public Float currRadius = null;
    private Works works = new Works();

    public SendMessage setMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        return sendMessage;
    }

    public SendMessage sendRadMsg(Message message) {
        currLocationLatitude = message.getLocation().getLatitude();
        currLocationLongtitude = message.getLocation().getLongitude();
        var sendMessage = setMessage(message);
        sendMessage.setText(Constants.SENDRADMSG);
        return sendMessage;
    }


    public SendPhoto sendMsg(Message message, Map<String, String> dataLine, State state, Integer numOfWorks) {
        String[] workCoordinates = dataLine.get(Constants.COORDINATES).split(" ");

        String way = Constants.PathYandexMapLoc
                + currLocationLatitude.toString() + "%2C" + currLocationLongtitude.toString() + "~"
                + workCoordinates[0] + "%2C" + workCoordinates[1]
                + "&rtt=mt&ruri=~&z=12";

        return createPhotoObj(message, dataLine, state, numOfWorks, way);

    }

    public List<SendPhoto> sendWorksMsg(State state, List<Map<String, String>> dataList) {

        Message currMessage = state.getLastMessage();

        currRadius = Float.parseFloat(currMessage.getText());


        float deltaLatitude = (float) ((currRadius / 1.8) / 60);
        float deltaLongtitude = (float) ((currRadius / 1.2) / 60);

        ArrayList<Map<String, String>> dataLines = new ArrayList<>();

        for (int i = 0; i < Constants.NUMWORKS; i++) {
            Map<String, String> currDataLine = dataList.get(i);
            String[] coords = currDataLine.get(Constants.COORDINATES).split(" ");

            if (Math.abs(currLocationLatitude - Float.parseFloat(coords[0])) < deltaLatitude &&
                    Math.abs(currLocationLongtitude - Float.parseFloat(coords[1])) < deltaLongtitude) {
                dataLines.add(currDataLine);

            }
        }
        return getPhotoList(state, dataLines);

    }


    private List<SendPhoto> getPhotoList(State state, ArrayList<Map<String, String>> dataLines) {
        var totalWorksCount = dataLines.size();

        state.setTotalLocationPhotoWorks(totalWorksCount);
        int buffer = Constants.BUFFER;
        List<SendPhoto> sendPhotoList = new ArrayList<>();

        while (buffer != 0 && state.getNumPhotoWorks() <= totalWorksCount) {
            sendPhotoList.add(sendMsg(state.getLastMessage(), dataLines.get(state.getNumPhotoWorks() - 1),
                    state,
                    dataLines.size()));
            buffer--;
            state.updateNumPhotoWorks();
        }
        return sendPhotoList;
    }
}
