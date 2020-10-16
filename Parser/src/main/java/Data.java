
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Data {
    private static final Map<Integer, Composition> compositionMap = new HashMap<Integer, Composition>();
    private static final String nameTitle = "shortTitle";
    private static final String nameCoordinates = "coordinates";
    private static final String namePhotos = "photos";
    private static final String nameAddress = "fullAddress";
    private static final String nameSummary = "summary";


    public Map<Integer, Composition> get_data(List<String> jsonFiles) {
        JSONParser jsonParser = new JSONParser();
        try {
            int id = 0;

            for (var jsonFile : jsonFiles) {
                JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(jsonFile));
                for (Object element : jsonArray) {
                    var jsonObject = (JSONObject) element;

                    String title = (String) jsonObject.get(nameTitle);
                    String coordinates = jsonObject.get(nameCoordinates).toString();
                    String address = (String) jsonObject.get(nameAddress);
                    String summary = (String) jsonObject.get(nameSummary);

                    var photos = jsonObject.get(namePhotos);
                    var photosList = getUrlPhotos(photos);
                    var summaryNormal = validate(summary, nameSummary);
                    var coordinatesNormal = coordinates.substring(1, coordinates.length() - 1).split(",");
                    coordinates = coordinatesNormal[1] +" "+ coordinatesNormal[0];

                    compositionMap.put(id, new Composition(title, coordinates, address, summaryNormal, photosList));
                    id++;

                }
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return compositionMap;

    }


    public List<String> getUrlPhotos(Object photosOb) {
        List<String> photosList = new ArrayList<String>();

        if (photosOb != null) {
            var photosObj = (JSONObject) photosOb;
            var previewPhoto = changeFormatImage(photosObj.get("urlTemplate"));
            photosList.add(previewPhoto);
            var otherPhotos = (JSONArray) photosObj.get("items");
            for (var photo : otherPhotos) {
                var photoObject = (JSONObject) photo;
                photosList.add(changeFormatImage(photoObject.get("urlTemplate")));

            }

        }
        return photosList;

    }

    public String validate(String property, String nameOfProperty) {
        switch (nameOfProperty) {
            case nameSummary:
                if (property == null) property = "Описание отсутствует";
                break;
        }
        return property;
    }

    public String changeFormatImage(Object imageFile) {
        return imageFile.toString().replace("%s", "XXXL");
    }


    public void printSmth(String text) {
        if (text != null) {
            String[] summaryArray = text.split(" ");
            int a = 0;
            StringBuilder sent = new StringBuilder();

            for (String word : summaryArray) {
                a++;
                sent.append(" ").append(word);
                if (a == 10) {
                    System.out.println(sent);
                    sent.setLength(0);
                    a = 0;
                }
            }
            System.out.println(sent);
        }
    }
}



