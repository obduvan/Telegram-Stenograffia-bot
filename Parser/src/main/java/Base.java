import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Base {
    Connection conn;
    Statement statement;

    void open() {
        try {
            Path dirPath = Paths.get(System.getProperty("user.dir") + "/..").toRealPath();
            Path dbPath = dirPath.resolve("stenograffia_bot");
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:"+dbPath,
                    "root",
                    "password");
            statement = conn.createStatement();
            System.out.println("Connected Succeed!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void delete() {

        try {
            String query = "DELETE FROM ARTS";
            statement.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    void insert(Composition art, Integer numCol) {


        String title = art.getTitle();
        String coordinates = art.getCoordinates();
        String address = art.getAddress();
        String summary = art.getSummary();
        String photos = art.getPhotos();

        try {
            String query = "INSERT INTO ARTS (TITLE, COORDINATES, ADDRESS, SUMMARY, PHOTOS) " +
                    "VALUES ('" + title + "','" + coordinates + "','" + address + "','" + summary + "','" + photos + "')";
            statement.executeUpdate(query);
            System.out.println("Rows added! " + numCol);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void close() {
        try {
            statement.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
