package geocoder;

import city.City;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Geocoder {
    private String URL1 = "https://nominatim.openstreetmap.org/search?q=";
    private String URL2 = "&format=json";
    private String name;
    private String gmina;
    private double lat = 0.0;
    private double lon = 0.0;
    private AtomicInteger index = new AtomicInteger(0);

    public City getCity(String name, String gmina) {
        this.name = name;
        this.gmina = gmina;
        if (getLonLat()) {
            int i = index.getAndIncrement();
            System.out.println("new City(" + i + ", " + lat + ", " + lon + ", \"" + name + "\", \"" + gmina + "\", true),");
            return new City(i,lat, lon, name, gmina, true);
        } else {
            return new City(name, false);
        }
    }

    public void decrementIndex() {
        index.decrementAndGet();
    }

    public void setZeroIndex() { index.set(0); }

    private boolean getLonLat() {
        String html = fetchHTML();

        String rLon = "(?<=lon\":\")[0-9.]+";
        String rLat = "(?<=lat\":\")[0-9.]+";

        if(html.length() > 5) {
            Pattern pLon = Pattern.compile(rLon);
            Pattern pLat = Pattern.compile(rLat);

            Matcher mLon = pLon.matcher(html);
            Matcher mLat = pLat.matcher(html);

            if (!mLat.find() & !mLon.find()) {
                return false;
            }

            lat = Double.valueOf(mLat.group());
            lon = Double.valueOf(mLon.group());

            return true;
        }

        return false;
    }

    private String fetchHTML() {
        String content = null;
        URLConnection connection;
        try {
            connection =  new URL(getURL()).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }

        return content;
    }

    private String getURL() {
        return URL1 + name.replace(" ", "+") + "+" + gmina.replace(" ", "+") + URL2;
    }
}
