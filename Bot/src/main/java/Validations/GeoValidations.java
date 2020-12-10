package Validations;

import org.telegram.telegrambots.meta.api.objects.Message;

public class GeoValidations {
    public boolean checkLocMsg(Message message) {

        return message.getLocation() != null;
    }
}
