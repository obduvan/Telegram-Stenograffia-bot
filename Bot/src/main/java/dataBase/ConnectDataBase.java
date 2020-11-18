package dataBase;

import constants.Constants;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectDataBase {
    private Connection connection;
    private Statement state;

    public ConnectDataBase(){
        try {
            Path dirPath = Paths.get(System.getProperty("user.dir") + "/..").toRealPath();
            Path dbPath = dirPath.resolve("stenograffia_bot");
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:"+dbPath,
                    "root",
                    "password");
            state  = connection.createStatement();

            System.out.println("Connected to stenograffia_bot Succeed!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String[] parsePhotos(String photos) {
        return photos.split(",");
    }

    public List<Map<String, String>> getDataList() throws SQLException {
        List<Map<String, String>> dataList = new ArrayList<>(){};
        ResultSet rs = state.executeQuery("select * from ARTS;");
        while(rs.next()){
            var map = new ConcurrentHashMap(){};
            map.put(Constants.TITLE, rs.getString(Constants.TITLE));
            map.put(Constants.PHOTOS, parsePhotos(rs.getString(Constants.IDS))[0]);
            map.put(Constants.IDS, rs.getString(Constants.IDS));
            map.put(Constants.COORDINATES, rs.getString(Constants.COORDINATES));
            dataList.add(map);
        }

       return dataList;
    }

    public void close(){
        try {
            state.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
