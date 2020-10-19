import org.apache.commons.io.IOUtils;
import org.checkerframework.checker.units.qual.C;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    private SendMessage setMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        return sendMessage;
    }


    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = setMessage(message);
        sendMessage.setText(text);


        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private String[] parsePhotos(String photos) {
        return photos.split(",");
    }


    public void sendMsgWorks(Message message) throws SQLException {
        ConnectDataBase dataBase = new ConnectDataBase();
        ResultSet rs = dataBase.state.executeQuery("select * from ARTS;");

        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(message.getChatId().toString());

        for (int i = 0; i < 8; i++) {
            rs.next();
            String title = rs.getString("TITLE");
            String[] photos = parsePhotos(rs.getString("PHOTOS"));
            String coordinates = String.format("\n%s", rs.getString("COORDINATES"));


            sendPhotoRequest.setCaption(String.format("%s %s", title, coordinates));
            sendPhotoRequest.setPhoto(photos[0].strip());

            try {
                execute(sendPhotoRequest);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        dataBase.close();
    }


    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                case "/start":
                    try {
                        String defaultMsg = getDefaultMessage();
                        sendMsg(message, defaultMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/authors":
                    sendMsg(message, System.getenv("AUTHORS"));
                    break;
                case "/works":
                    try {
                        sendMsgWorks(message);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }
        }

    }

    public String getDefaultMessage() throws IOException {
        return new String(Files.readAllBytes(Paths.get("src\\main\\resources\\default message\\defaultMessage.md")));
    }

    public String getBotUsername() {
        return System.getenv("NAMEBOT");
    }

    public String getBotToken() {
        return System.getenv("TOKEN");
    }
}
