package functions;
import constants.Constants;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import realizations.PhotoWorks;
import systemStates.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Works extends PhotoWorks {

    public SendPhoto createPhotoMsg(Map<String, String> dataLine, State state){
        String link = dataLine.get(Constants.IDS);
        return createPhotoObj(dataLine, state, link);
    }

    public List<SendPhoto> sendWorksMsg(State state, List<Map<String, String>> dataList) {

        state.setTotalPhotoWorks(Constants.COLWORKS);
        var totalArts = state.getTotalPhotoWorks();
        List<SendPhoto> sendPhotoList = new ArrayList<>();
        int buffer = Constants.BUFFER;
        if (state.getNumPhotoWorks() < totalArts){
            while (buffer != 0) {
                sendPhotoList.add(createPhotoMsg(dataList.get(state.getNumPhotoWorks() - 1), state));
                state.updateNumPhotoWorks();
                buffer --;
            }
        }
        return sendPhotoList;
    }
}






