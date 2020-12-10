package realizations;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CreatorSendMessage {
    public SendMessage setMessage(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        return sendMessage;
    }
}
