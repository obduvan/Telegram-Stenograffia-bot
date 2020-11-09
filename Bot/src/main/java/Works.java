import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Works {

    public SendPhoto sendMsg(Message message, Map<String, String> dataLine, State state){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());

        String numPhoto = state.getNumPhotoWorks().toString();
        String title = dataLine.get(Constants.TITLE);
        String link = dataLine.get(Constants.IDS);
        String photo = dataLine.get(Constants.PHOTOS);

        sendPhoto.setCaption(String.format("%s   %s/%s\n%s", title, numPhoto, Constants.NUMWORKS, link));
        sendPhoto.setPhoto(photo);

        return sendPhoto;
    }


    public List<SendPhoto> sendWorksMsg(State state, List<Map<String, String>> dataList) {
        List<SendPhoto> sendPhotoList = new ArrayList<>();
        int buffer = Constants.BUFFER;
        if (state.getNumPhotoWorks() < Constants.NUMWORKS){
            while (buffer != 0) {
                sendPhotoList.add(sendMsg(state.getLastMessage(), dataList.get(state.getNumPhotoWorks() - 1), state));
                state.updateNumPhotoWorks(1, Constants.NUMWORKS);
                buffer --;
            }
        }
        return sendPhotoList;
    }
}






