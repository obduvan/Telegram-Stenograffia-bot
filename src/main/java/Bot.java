import org.apache.commons.io.IOUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Bot extends TelegramLongPollingBot {


    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                case "/start":
                    String defaultMsg = null;
                    try {
                        defaultMsg = getDefaultMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sendMsg(message, defaultMsg);
                    break;


                case "/works":
                    sendMsg(message, "ща");

                case "/authors":
                    sendMsg(message, Constants.AUTHORS);
                default:
            }
        }

    }

    public String getDefaultMessage() throws IOException {
        String content;
        content = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\default message\\defaultMessage.md")));
        return content;
    }

    public String getBotUsername() {
        return Constants.USERNAME;
    }

    public String getBotToken() {
        return Constants.TOKEN;
    }
}
