import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectDataBase {
    Connection conn;
    Statement state;
    public ConnectDataBase(){

        try {
            Path dirPath = Paths.get(System.getProperty("user.dir") + "/..").toRealPath();
            Path dbPath = dirPath.resolve("stenograffia_bot");
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:"+dbPath,
                    "root",
                    "password");
            state  = conn.createStatement();

            System.out.println("Connected to stenograffia_bot Succeed!");

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void close(){
        try {
            state.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
