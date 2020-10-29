import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.Map;

public class Works {
    private Bot bot;

    public Works(Bot ibot) {
        bot = ibot;
    }

    public void sendMsg(Message message, Map<String, String> dataLine, State state){
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(message.getChatId().toString());

        String numPhoto = state.getNumPhotoWorks().toString();
        String title = dataLine.get(Constants.TITLE);
        String link = dataLine.get(Constants.IDS);
        String photo = dataLine.get(Constants.PHOTOS);

        sendPhotoRequest.setCaption(String.format("%s   %s/%s\n%s", title, numPhoto, Constants.NUMWORKS, link));
        sendPhotoRequest.setPhoto(photo);

        bot.sendMsg(sendPhotoRequest);
    }


    public void sendWorksMsg(State state) {
        int buffer = Constants.BUFFER;
        var works = new Works(bot);
        var dataList = bot.getDataList();
        if (state.getNumPhotoWorks() < Constants.NUMWORKS){
            while (buffer != 0) {
                works.sendMsg(state.getLastMessage(), dataList.get(state.getNumPhotoWorks() - 1), state);
                state.updateNumPhotoWorks(1);
                buffer -=1;
            }
        }
    }
}






