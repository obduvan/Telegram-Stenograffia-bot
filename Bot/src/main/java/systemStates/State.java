package systemStates;
import org.telegram.telegrambots.meta.api.objects.Message;


public class State {
    private BotState botState;
    private Message lastMessage;
    private Integer numPhotoWorks = 1;
    private Integer totalLocationPhotoWorks;
    private TypeMessage typeMessage;
    private String chatId;
    private float latitude;
    private float longtitude;

    public State(BotState status, Message message){
        updateBotState(status, message);
        setTypeMessage(botState);
        setFields(message);
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

    private void setFields(Message message){
        chatId = message.getChatId().toString();
        if (botState == BotState.WORKS_LOC_RAD){
            latitude = message.getLocation().getLatitude();
            longtitude = message.getLocation().getLongitude();
        }
    }

    public void setTotalLocationPhotoWorks(Integer col){
        totalLocationPhotoWorks = col;
    }

    public Integer getTotalLocationPhotoWorks(){
        return totalLocationPhotoWorks;
    }

    public float getLatitude(){return  latitude; }

    public float getLongtitude(){return longtitude; }

    public String getChatId(){ return chatId; }

    public TypeMessage getTypeMessage(){
        return typeMessage;
    }

    public Message getLastMessage(){
        return lastMessage;
    }

    public void updateNumPhotoWorks(){
        numPhotoWorks ++;
    }

    public Integer getNumPhotoWorks(){
        return numPhotoWorks;
    }

    public void updateBotState(BotState istatus, Message ilastMessage) {
        botState = istatus;
        lastMessage = ilastMessage;
    }

    public BotState getBotState() {
        return botState;
    }
}
