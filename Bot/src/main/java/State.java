import org.telegram.telegrambots.meta.api.objects.Message;
import constants.Constants;


public class State {
    private BotState status;
    private Message lastMessage;
    private Integer numPhotoWorks = 1;

    public State(BotState status, Message message){
        updateStatus(status, message);
    }

    public Message getLastMessage(){
        return lastMessage;
    }

    public void updateNumPhotoWorks(Integer num){
        if (numPhotoWorks < Constants.NUMWORKS)
            numPhotoWorks += num;
    }

    public Integer getNumPhotoWorks(){
        return numPhotoWorks;
    }

    public void updateStatus(BotState status, Message lastMessage) {
        this.status = status;
        this.lastMessage = lastMessage;
    }

    public BotState getStatus() {
        return this.status;
    }
}
