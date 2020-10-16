import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Base {
    Connection conn;

    void open() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:stenograffia_bot",
                    "root",
                    "password");
            System.out.println("Succeed!");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    void insert(Composition art) {

        String title = art.getTitle();
        String coordinates = art.getCoordinates();
        String address = art.getAddress();
        String summary = art.getSummary();
        String photos = art.getPhotos();

        try {
            String query = "INSERT INTO ARTS (TITLE, COORDINATES, ADDRESS, SUMMARY, PHOTOS) " +
                    "VALUES ('" + title + "','" + coordinates + "','" + address + "','" + summary + "','" + photos + "')";
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Rows added!");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    void close() {
        try {
            conn.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
