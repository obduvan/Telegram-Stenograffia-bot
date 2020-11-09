package realizations;

import constants.ConstantPath;
import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class StandardFunctions {

    private SendMessage setMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        return sendMessage;
    }

    private String getDefaultMessage() throws IOException {
        return new String(Files.readAllBytes(Paths.get(ConstantPath.defaultMessage)));
    }

    public SendMessage sendHelpMsg(Message message) throws IOException {
        SendMessage sendMessage = setMessage(message);
        sendMessage.setText(getDefaultMessage());
        return sendMessage;
    }

    private String getNameAuthors() throws IOException {
        return new String(Files.readAllBytes(Paths.get(ConstantPath.authorsMessage)));
    }

    public SendMessage sendAuthorsMsg(Message message) throws IOException {
        var text = getNameAuthors();
        SendMessage sendMessage = setMessage(message);
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage sendEndedWorks(Message message){
        var text = Constants.ENDEDWORKSMSG;
        SendMessage sendMessage = setMessage(message);
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage sendNoWorksMsg(Message message) {
        var sendMessage = setMessage(message);
        sendMessage.setText(Constants.NOARTINLOC);
        return sendMessage;
    }


    public SendMessage sendLocMsg(Message message) {
        var sendMessage = setMessage(message);
        sendMessage.setText(Constants.SENDLOC);
        return sendMessage;
    }
}
