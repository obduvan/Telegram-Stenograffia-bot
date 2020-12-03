package systemStates;
import org.telegram.telegrambots.meta.api.objects.Message;


public class State {
    private BotState botState;
    private Message lastMessage;
    private Integer numPhotoWorks = 1;
    private Integer totalPhotoWorks;
    private TypeMessage typeMessage;
    private String chatId;
    private float latitude;
    private float longtitude;
    private float latitudeLast;
    private float longtitudeLast;


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
        else if (botState == BotState.GET_ROUTE_URL){
            latitudeLast = message.getLocation().getLatitude();
            longtitudeLast = message.getLocation().getLongitude();
        }
    }

    public void  setTotalPhotoWorks(Integer col){
        totalPhotoWorks = col;
    }
    public Integer getTotalPhotoWorks(){
        return totalPhotoWorks;
    }
    public float getLatitudeLast(){return latitudeLast;}
    public float getLongtitudeLast(){return longtitudeLast;}

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
