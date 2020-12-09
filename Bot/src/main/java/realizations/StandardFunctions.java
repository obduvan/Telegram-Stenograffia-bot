package realizations;

import constants.ConstantPath;
import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import systemStates.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class StandardFunctions {

    private SendMessage setMessage(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        return sendMessage;
    }

    private String getDefaultMessage() throws IOException {
        return new String(Files.readAllBytes(Paths.get(ConstantPath.defaultMessage)));
    }

    public SendMessage sendHelpMsg(String chatId)  {
        SendMessage sendMessage = setMessage(chatId);
        try {
            sendMessage.setText(getDefaultMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sendMessage;
    }

    private String getNameAuthors() throws IOException {
        return new String(Files.readAllBytes(Paths.get(ConstantPath.authorsMessage)));
    }

    public SendMessage sendAuthorsMsg(String chatId) {
        String text = null;
        try {
            text = getNameAuthors();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SendMessage sendMessage = setMessage(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage sendNoneMsg(String chatId){
        var text = Constants.NONE_MSG;
        SendMessage sendMessage = setMessage(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage sendEndedWorks(String chatId){
        var text = Constants.ENDEDWORKSMSG;
        SendMessage sendMessage = setMessage(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage sendNoWorksMsg(String chatId) {
        var sendMessage = setMessage(chatId);
        sendMessage.setText(Constants.NOARTINLOC);
        return sendMessage;
    }

    public SendMessage sendLocMsg(String chatId) {
        var sendMessage = setMessage(chatId);
        sendMessage.setText(Constants.SENDLOC);
        return sendMessage;
    }
    
    public SendMessage sendLocRouteMsg(String chatId){
        var sendMessage = setMessage(chatId);
        sendMessage.setText(Constants.GET_LAST_LOC);
        return sendMessage;
    }

    public SendMessage sendNoArtInListMsg(String chatId){
        var sendMessage = setMessage(chatId);
        sendMessage.setText(Constants.NO_ART_IN_LIST);
        return sendMessage;
    }
}
