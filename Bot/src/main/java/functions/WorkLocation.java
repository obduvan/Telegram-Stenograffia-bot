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

    public SendMessage setMessage(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        return sendMessage;
    }

    public SendMessage sendLocMsg(Message message) {
        var sendMessage = setMessage(message);
        sendMessage.setText("Отправьте геопозицию");
        return sendMessage;
    }


    public SendMessage sendRadMsg(Message message){
        this.currLocationLatitude = message.getLocation().getLatitude();
        this.currLocationLongtitude = message.getLocation().getLongitude();
        var sendMessage = setMessage(message);
        sendMessage.setText("Введите радиус (км), в пределах которого хотите увидеть работы");
        return sendMessage;
    }

    public SendMessage sendNoWorksMsg(Message message){
        var sendMessage = setMessage(message);
        sendMessage.setText("А в таком радиусе работ нет :( \nПопробуйте выбрать другой!");
        return sendMessage;
    }

    public SendPhoto sendMsg(Message message, Map<String, String> dataLine, State state,List<Map<String, String>> dataList){
        String[] workCoordinates = dataLine.get(Constants.COORDINATES).split(" ");
        String way = Constants.PathYandexMapLoc
                +this.currLocationLatitude.toString()+"%2C"+this.currLocationLongtitude.toString()+"~"
                +workCoordinates[0]+"%2C"+workCoordinates[1]
                +"&rtt=mt&ruri=~&z=12";

        return createPhotoObj(message, dataLine, state, dataList.size(),way);

    }

    public List<SendPhoto> sendWorksMsg(State state, List<Map<String, String>> dataList) {
        Message currMessage = state.getLastMessage();

        this.currRadius = Float.parseFloat(currMessage.getText());

        List<SendPhoto> sendPhotoList = new ArrayList<>();

        float deltaLatitude = (float) ((this.currRadius/1.8)/60);
        float deltaLongtitude = (float) ((this.currRadius/1.2)/60);

        int a = state.getNumPhotoWorks();

        int buffer = Constants.BUFFER;
        while (buffer != 0 && state.getNumPhotoWorks() < Constants.NUMWORKS) {

            Map<String, String> currDataLine = dataList.get(state.getNumPhotoWorks() - 1);
            String[] coords = currDataLine.get(Constants.COORDINATES).split(" ");


            if (Math.abs(this.currLocationLatitude - Float.parseFloat(coords[0])) < deltaLatitude &&
                    Math.abs(this.currLocationLongtitude - Float.parseFloat(coords[1])) < deltaLongtitude) {
                sendPhotoList.add(sendMsg(state.getLastMessage(), currDataLine , state, dataList));
                buffer --;
            }
            state.updateNumPhotoWorks(1);
        }
        return sendPhotoList;
    }

}
