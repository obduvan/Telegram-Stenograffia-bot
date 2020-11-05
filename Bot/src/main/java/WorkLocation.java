import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class WorkLocation {
    public SendMessage setMessage(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        return sendMessage;
    }

    public SendMessage sendLocMsg(Message message) {
        var sendMessage = setMessage(message);
        sendMessage.setText("Отправьте геопозицию");
        return sendMessage;
    }


    public SendMessage sendRadMsg(Message message){
        var sendMessage = setMessage(message);
        sendMessage.setText("Введите радиус (км), в пределах которого хотите увидеть работы");
        return sendMessage;
    }

    public SendMessage sendMsg(Message message){
        var sendMessage = setMessage(message);
        sendMessage.setText("работы....");
        return sendMessage;
    }
}
