import org.telegram.telegrambots.meta.api.objects.Message;
import constants.Constants;

import java.util.Date;


public class State {
    private BotState status;
    private Message lastMessage;
    private Integer numPhotoWorks = 1;
    private Date timeWorkLoc;
    private Integer totalLocationPhotoWorks;

    public State(BotState status, Message message){
        updateStatus(status, message);
    }

    public Message getLastMessage(){
        return lastMessage;
    }

    public void updateNumPhotoWorks(Integer num, Integer arrLen){
        if (numPhotoWorks < arrLen)
            numPhotoWorks += num;
    }

    public Integer getNumPhotoWorks(){
        return numPhotoWorks;
    }

    public void updateStatus(BotState status, Message lastMessage) {
        this.status = status;
        this.lastMessage = lastMessage;
    }
    public void setTimeWorkLoc(){
        timeWorkLoc = new Date();
    }
    public Date getTimeWorkLoc(){
        return timeWorkLoc;
    }


    public BotState getStatus() {
        return this.status;
    }
}
