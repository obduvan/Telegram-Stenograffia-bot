package realizations;

import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import systemStates.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PhotoWorks {

    private SendPhoto getPhotoObj(String chatId){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        return sendPhoto;
    }

    public SendPhoto createPhotoBoardObj(Map<String, String> dataLine, State state, String link, String workCoordinates){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText("Добавить в маршрут").setCallbackData(String.format("Добавить в маршрут#%s", workCoordinates));
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        keyboardButtonsRow.add(button);
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        SendPhoto sendPhoto = getPhotoObj(state.getChatId());

        String numPhoto = state.getNumPhotoWorks().toString();
        String title = dataLine.get(Constants.TITLE);
        String photo = dataLine.get(Constants.PHOTOS);
        var colPhotos = state.getTotalPhotoWorks();
        sendPhoto.setCaption(String.format("%s   %s/%s\n%s", title, numPhoto, colPhotos, link));
        sendPhoto.setPhoto(photo).setReplyMarkup(inlineKeyboardMarkup);
        return sendPhoto;
    }

    public SendPhoto createPhotoObj(Map<String, String> dataLine, State state, String link){
        SendPhoto sendPhoto = getPhotoObj(state.getChatId());

        String numPhoto = state.getNumPhotoWorks().toString();
        String title = dataLine.get(Constants.TITLE);
        String photo = dataLine.get(Constants.PHOTOS);
        var colPhotos = state.getTotalPhotoWorks();
        sendPhoto.setCaption(String.format("%s   %s/%s\n%s", title, numPhoto, colPhotos, link));
        sendPhoto.setPhoto(photo);
        return sendPhoto;
    }
}
