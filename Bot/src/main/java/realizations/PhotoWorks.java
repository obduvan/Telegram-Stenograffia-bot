package realizations;

import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import systemStates.State;
import java.util.Map;

public class PhotoWorks {
    public SendPhoto createPhotoObj(Map<String, String> dataLine, State state,Integer colPhotos, String link){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(state.getChatId());

        String numPhoto = state.getNumPhotoWorks().toString();
        String title = dataLine.get(Constants.TITLE);
        String photo = dataLine.get(Constants.PHOTOS);

        sendPhoto.setCaption(String.format("%s   %s/%s\n%s", title, numPhoto, colPhotos, link));
        sendPhoto.setPhoto(photo);
        return sendPhoto;
    }

}
