import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class PhotoWorks {
    public SendPhoto createPhotoMsg(Message message, Map<String, String> dataLine, State state){
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




}
