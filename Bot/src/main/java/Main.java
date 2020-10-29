import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        ApiContextInitializer.init();
        TelegramBotsApi telegram = new TelegramBotsApi();
        ConnectDataBase connectDataBase = new ConnectDataBase();
        var dataList = connectDataBase.getDataList();
        Bot bot = new Bot(dataList);

        try {
            telegram.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
