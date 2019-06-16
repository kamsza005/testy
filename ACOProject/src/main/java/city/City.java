package city;

public class City {
    private int index;
    private double lat;
    private double lon;
    private String name;
    private String gmina;
    private boolean exists;

    public City(int index, double lat, double lon, String name, String gmina, boolean exists) {
        this.index = index;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.gmina = gmina;
        this.exists = exists;
    }

    public City(String name, boolean exists) {
        this.name = name;
        this.exists = exists;
    }

    public City(String name, String gmina){
        this.name = name;
        this.gmina = gmina;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getDetails() {
        return "Name: " + name +
                "\nGmina: " + gmina +
                "\nLon: " + lon +
                "\nLat: " + lat;
    }

    @Override
    public String toString() {
        return name;
    }
}
