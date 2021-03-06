package systemStates;
import org.telegram.telegrambots.meta.api.objects.Message;


public class State {
    private BotState botState;
    private Message lastMessage;
    private Integer numPhotoWorks = 1;
    private Integer totalPhotoWorks;
    private TypeMessage typeMessage;
    private String chatId;
    private double latitude;
    private double longtitude;
    private double latitudeLast;
    private double longtitudeLast;


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

    public void updateLongLang(double latitude, double longtitude){
        this.latitude = latitude;
        this.longtitude = longtitude;
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

    public double getLatitudeLast(){return latitudeLast;}

    public double getLongtitudeLast(){return longtitudeLast;}

    public double getLatitude(){return  latitude; }

    public double getLongtitude(){return longtitude; }

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

    public void updateBotState(BotState status, Message lastMessage) {
       this.botState = status;
       this.lastMessage = lastMessage;
    }

    public BotState getBotState() {
        return botState;
    }
}
