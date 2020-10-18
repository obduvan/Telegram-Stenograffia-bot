import org.json.simple.parser.ParseException;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ParseException {
        String jsonPath = "src\\main\\resources\\jsonFiles";

        var pathJson = new File(jsonPath);
        var jsonFiles = listFilesForFolder(pathJson);
        Data parser = new Data();
        var compositionMap = parser.get_data(jsonFiles);
        Base db = new Base();

        db.open();

//        for (int i = 0; i < compositionMap.size(); i++) {
//            db.insert(compositionMap.get(i));
//        }
        // This code has already used: it's inserting rows to database
        // Don't uncomment it! 

        db.close();

    }

    public static List<String> listFilesForFolder(File folder) {
        var jsonFiles = new ArrayList<String>();

        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                jsonFiles.add(fileEntry.getPath());

            }
        }

        return jsonFiles;
    }
}