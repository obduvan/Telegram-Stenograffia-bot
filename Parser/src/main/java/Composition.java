import java.util.List;

public class Composition {
    public String title;
    public String[] coordinates;
    public String address;
    public String summary;
    public List<String> photos;

    public Composition(String title, String[] coordinates, String address, String summary, List<String> photos) {
        this.title = title;
        this.coordinates = coordinates;
        this.address = address;
        this.summary = summary;
        this.photos = photos;

    }


}
