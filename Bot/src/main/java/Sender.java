import java.io.IOException;


public class Sender {
    private StandartFunctions standartFunctions;
    private Works works;
    private Bot bot;

    public Sender(Bot ibot){
        bot = ibot;
        standartFunctions = new StandartFunctions(bot);
        works = new Works(bot);
    }

    private void checkState(State state){
        if (state.getStatus() == BotState.ASK_WORKS)
            if (state.getNumPhotoWorks() >= 114){
                standartFunctions.sendEndedWorks(state.getLastMessage());
            }
    }

    public void action(State state) throws IOException {
        switch (state.getStatus()) {
            case ASK_HELP:
                standartFunctions.sendHelpMsg(state.getLastMessage());
                break;
            case ASK_AUTHORS:
                standartFunctions.sendAuthorsMsg(state.getLastMessage());
                break;
            case ASK_WORKS:
                works.sendWorksMsg(state);
                checkState(state);
                break;
        }
    }
}
