package inlineKeyboard;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardWork {
    private boolean addWorkRoute;
    private String workCoordinates;

    public EditMessageReplyMarkup getChangedKeyboard(Update update){
        String callData = update.getCallbackQuery().getData();

        var messageId = update.getCallbackQuery().getMessage().getMessageId();
        var chatId = update.getCallbackQuery().getMessage().getChatId();
        String inlineMessageId = update.getCallbackQuery().getInlineMessageId();

        setWorkCoordinates(callData);

        EditMessageReplyMarkup newMessage = new EditMessageReplyMarkup()
                .setChatId(chatId).setMessageId(messageId)
                .setInlineMessageId(inlineMessageId);

        InlineKeyboardButton button = new InlineKeyboardButton();

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        if (callData.startsWith("Добавить в маршрут")) {
            setAddWorkRoute(true);
            button.setText("Убрать из маршрута");
            button.setCallbackData(String.format("Убрать из маршрута#%s", workCoordinates));
            }
        else {
            setAddWorkRoute(false);
            button.setText("Добавить в маршрут");
            button.setCallbackData(String.format("Добавить в маршрут#%s", workCoordinates));
            }

        rowInline.add(button);
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        newMessage.setReplyMarkup(markupInline);
    return newMessage;
    }

    public  void setWorkCoordinates(String callData){
        var coordinates = callData.split("#")[1].split(" ");

        workCoordinates = String.format("%s %s", coordinates[0], coordinates[1]);
    }

    public String getWorkCoordinates(){
        return workCoordinates;
    }

    private void setAddWorkRoute(boolean exc){
        addWorkRoute = exc;
    }
    public boolean isAddWorkRoute(){
        return addWorkRoute;
    }
}
