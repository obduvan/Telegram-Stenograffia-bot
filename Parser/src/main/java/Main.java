import org.json.simple.parser.ParseException;

import javax.swing.plaf.synth.SynthLookAndFeel;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws ParseException {
        String jsonPath = "src\\main\\resources\\jsonFiles";

        var pathJson = new File(jsonPath);
        var jsonFiles = listFilesForFolder(pathJson);
        Data parser = new Data();
        var compositionMap = parser.get_data(jsonFiles);

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