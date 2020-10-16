
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;


public class Data {
    private static final Map<Integer, Composition> compositionMap = new HashMap<Integer, Composition>();

    public Map<Integer, Composition> get_data(List<String> jsonFiles) {
        JSONParser jsonParser = new JSONParser();
        try {
            int id = 0;

            for (var jsonFile : jsonFiles) {
                JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(jsonFile));
                for (Object element : jsonArray) {
                    var jsonObject = (JSONObject) element;

                    String title = (String) jsonObject.get("shortTitle");
                    String coordinates = jsonObject.get("coordinates").toString();
                    var photos = jsonObject.get("photos");
                    var photosList = getPhotos(photos);
                    String address = (String) jsonObject.get("fullAddress");
                    String summary = (String) jsonObject.get("summary");

                    var summaryNormal = validate(summary, "summary");

                    var coordinatesNormal = coordinates.substring(1, coordinates.length() - 1).split(",");

                    compositionMap.put(id, new Composition(title, coordinatesNormal, address, summaryNormal, photosList));
                    id++;

                }
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return compositionMap;

    }

    public String validate(String property, String nameOfProperty) {
        switch (nameOfProperty) {
            case "summary":
                if (property == null) property = "Описание отсутствует";
                break;

        }
        return property;
    }

    public List<String> getPhotos(Object photosOb) {
        List<String> photosList = new ArrayList<String>();

        if (photosOb != null) {
            var photosObj = (JSONObject) photosOb;
            var previewPhoto = photosObj.get("urlTemplate").toString().replace("%s", "XXXL");
            photosList.add(previewPhoto);
            var otherPhotos = (JSONArray) photosObj.get("items");
            for (var photo : otherPhotos) {
                var photoObject = (JSONObject) photo;
                photosList.add(photoObject.get("urlTemplate").toString().replace("%s", "XXXL"));

            }

        }
        return photosList;

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



