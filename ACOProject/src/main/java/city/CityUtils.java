package city;

import geocoder.Geocoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CityUtils {
    private int maxRandom;
    CityDAO cityDAO = new CityDAO();
    Random random = new Random();
    Geocoder geocoder = new Geocoder();

    public CityUtils() {
        maxRandom = cityDAO.getMax();
    }

    public double distance(City c1, City c2) {
        final double R = 6371.008;

        double lon1 = c1.getLon();
        double lon2 = c2.getLon();
        double lat1 = c1.getLat();
        double lat2 = c2.getLat();

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }

    public List<City> getListOfCities(String name, String gmina) {
        List<City> cityList = new ArrayList<>();
        cityList.addAll(cityDAO.getCitiesByName(name));
        cityList.addAll(cityDAO.getCitiesByGmina(gmina));
        return cityList;
    }

    public List<City> getListOfCities(String name) {
        List<City> cityList = new ArrayList<>();
        cityList.addAll(cityDAO.getCitiesByName(name));
        return cityList;
    }

    public City[] getRandomCities(int number) {
        City[] cities = new City[number];
        int[] numbers = new int[number];
        City tempCity;


        for (int i = 0; i < number; i++) {
            int r = getRandomNumber(numbers);
            numbers[i] = r;
            tempCity = cityDAO.getCityById(r);
            cities[i] = geocoder.getCity(tempCity.getName(), tempCity.getGmina());
        }
        return cities;
    }

    private int getRandomNumber(int[] numbers) {
        boolean numberFlag = false;
        int r = 0;
        int number = numbers.length;
        while (!numberFlag) {
            r = random.nextInt(maxRandom) + 1;
            for (int i = 0; i < number; i++) {
                if (numbers[i] == r) {
                    numberFlag = false;
                    break;
                } else {
                    numberFlag = true;
                }
            }
        }
        return r;
    }

}
