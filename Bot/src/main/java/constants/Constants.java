package constants;


public class Constants {
    public static final String HELP = "/help";
    public static final String START = "/start";
    public static final String AUTHORS = "/authors";
    public static final String WORKS = "/works";
    public static final String NEXTART = "/n";
    public static final String GET_ROUTE = "/route";
    public static final String GET_LAST_LOC = "Отправьте геопозицию, где бы вы хотели закончить маршрут";

    public static final String PathYandexMapLoc = "https://yandex.ru/maps/54/yekaterinburg/?ll=60.6125%2C56.8575&mode=routes&rtext=";
    public static final String TITLE = "TITLE";
    public static final String IDS = "IDS";
    public static final String PHOTOS = "PHOTOS";
    public static final String COORDINATES = "COORDINATES";
    public static final String NONE_MSG = "Такой команды не существует";

    public static final Integer BUFFER = 5;
    public static final Integer COLWORKS = 114;
    public static final String ENDEDWORKSMSG = "Работ больше нет :(";

    public static final String SYSTOKEN = "TOKEN";
    public static final String SYSNAMEBOT = "NAMEBOT";

    public static final String NOARTINLOC = "А в таком радиусе работ нет :( \nПопробуйте выбрать другой!";
    public static final String SENDLOC = "Отправьте текущую геопозицию";

    public static final String SENDRADMSG = "Введите радиус (км), в пределах которого хотите увидеть работы";

    public static final Integer EARTH_DIAMETER = 2*6371;

    public static final String YA_MAP_PATH_PART = "&rtt=mt&ruri=~&z=12";
    public static final String YA_MAP_PATH_2C = "%2C";
}
