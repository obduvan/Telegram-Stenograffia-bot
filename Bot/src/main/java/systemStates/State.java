package systemStates;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Date;



public class State {
    private BotState botState;
    private Message lastMessage;
    private Integer numPhotoWorks = 1;
    private Date timeWorkLoc;
    private Integer totalLocationPhotoWorks;
    private TypeMessage typeMessage;

    public State(BotState status, Message message){
        updateBotState(status, message);
        setTypeMessage(botState);
    }

    public void setTotalLocationPhotoWorks(Integer col){
        totalLocationPhotoWorks = col;
    }
    public Integer getTotalLocationPhotoWorks(){
        return totalLocationPhotoWorks;
    }

    public void setTypeMessage(BotState botState){
        switch (botState){
            case WORKS_LOC_GET:
            case ASK_WORKS:
                typeMessage = TypeMessage.IS_PHOTO;
                break;
            default:
                typeMessage = TypeMessage.IS_TEXT;
                break;
        }
    }

    public TypeMessage getTypeMessage(){
        return typeMessage;
    }


    public Message getLastMessage(){
        return lastMessage;
    }

    public void updateNumPhotoWorks(){
        numPhotoWorks += 1;
    }

    public Integer getNumPhotoWorks(){
        return numPhotoWorks;
    }

    public void updateBotState(BotState istatus, Message ilastMessage) {
        botState = istatus;
        lastMessage = ilastMessage;
    }

    public void setTimeWorkLoc(){
        timeWorkLoc = new Date();
    }
    public Date getTimeWorkLoc(){
        return timeWorkLoc;
    }

    public BotState getBotState() {
        return botState;
    }
}
