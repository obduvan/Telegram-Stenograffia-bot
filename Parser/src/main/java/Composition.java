import java.util.List;

public class Composition {
    private String title;
    private String address;
    private String summary;
    private String coordinates;
    private String photos;

    public Composition(String title, String coordinates, String address, String summary, String photos) {
        this.title = title;
        this.coordinates = coordinates;
        this.address = address;
        this.summary = summary;
        this.photos = photos;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAddress() {
        return address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getPhotos() {
        return photos;
    }

    @Override
    public String toString() {
        return "Composition{" +
                "title" + title +
                "coordinates=" + coordinates +
                ", address='" + address + '\'' +
                ", summary=" + summary +
                ", photos=" + photos +
                '}';
    }




}
