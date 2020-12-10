import org.json.simple.parser.ParseException;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class Main {
    static Map<Integer, Composition> compositionMap;


    public static void main(String[] args) throws ParseException {
        String jsonPath = "src\\main\\resources\\jsonFiles";

        var pathJson = new File(jsonPath);
        var jsonFiles = listFilesForFolder(pathJson);
        Data parser = new Data();
        compositionMap = parser.get_data(jsonFiles);

        Base db = new Base();
        db.open();

        Scanner input = new Scanner(System.in);
        System.out.println("Overwrite data: enter 'O' \nAdd data: enter 'A' ");


        var question = input.next();
        switch (question) {
            case "O":
                overwriteDataBase(compositionMap, db);
                break;
            case "A":
                addDataBse(compositionMap, db);

        }
        db.close();

    }


    public static void addDataBse(Map<Integer, Composition> compositionMap, Base db) {
        for (int i = 0; i < compositionMap.size(); i++) {
            db.insert(compositionMap.get(i), i);
        }
    }


    public static void overwriteDataBase(Map<Integer, Composition> compositionMap, Base db) {
        db.delete();
        addDataBse(compositionMap, db);

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