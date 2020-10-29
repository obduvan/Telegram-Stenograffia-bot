import constants.ConstantPath;
import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


class StandartFunctions {
    private Bot bot;

    public StandartFunctions(Bot ibot){
        bot = ibot;
    }
    private SendMessage setMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        return sendMessage;
    }

    private String getDefaultMessage() throws IOException {
        return new String(Files.readAllBytes(Paths.get(ConstantPath.defaultMessage)));
    }

    public void sendHelpMsg(Message message) throws IOException {
        SendMessage sendMessage = setMessage(message);
        sendMessage.setText(getDefaultMessage());
        bot.sendMsg(sendMessage);
    }

    private String getNameAuthors() {
        return System.getenv(Constants.SYSAUTHORS);
    }

    public void sendAuthorsMsg(Message message) {
        var text = getNameAuthors();
        SendMessage sendMessage = setMessage(message);
        sendMessage.setText(text);
        bot.sendMsg(sendMessage);
    }

    public void sendEndedWorks(Message message){
        var text = Constants.ENDEDWORKSMSG;
        SendMessage sendMessage = setMessage(message);
        sendMessage.setText(text);
        bot.sendMsg(sendMessage);
    }
}
