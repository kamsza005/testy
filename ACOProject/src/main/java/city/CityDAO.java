package city;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {
    private String url = "jdbc:sqlite:" + getPath();

    public int getMax() {
        int max = 0;
        try (Connection connection = getConnection()){
            Statement statement = connection.prepareStatement("SELECT count(*) FROM miejscowosci");
            ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
            while (resultSet.next()) {
                max = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return max;
    }

    public City getCityById(int id) {
        City city = null;
        try (Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM miejscowosci WHERE id=" + id);
            ResultSet resultSet = ((PreparedStatement)statement).executeQuery();
            while (resultSet.next()) {
                city = new City(resultSet.getString("nazwa"), resultSet.getString("gmina"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }


    public List<City> getCitiesByName(String name) {
        List<City> cities = new ArrayList<>();
        try(Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM miejscowosci WHERE nazwa LIKE '" + name + "%'");
            ResultSet resultSet = ((PreparedStatement)statement).executeQuery();
            while (resultSet.next()) {
                cities.add(new City(resultSet.getString("nazwa"), resultSet.getString("gmina")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Collections.sort(cities, Comparator.comparing((city -> city.getName())));
        return cities;
    }

    public List<City> getCitiesByGmina(String gmina) {
        List<City> cities = new ArrayList<>();
        try(Connection connection = getConnection()) {
            Statement statement = connection.prepareStatement("SELECT * FROM miejscowosci WHERE gmina LIKE '" + gmina + "%'");
            ResultSet resultSet = ((PreparedStatement)statement).executeQuery();
            while (resultSet.next()) {
                cities.add(new City(resultSet.getString("nazwa"), resultSet.getString("gmina")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Collections.sort(cities, Comparator.comparing((city -> city.getName())));
        return cities;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return connection;
        }
    }

    private String getPath() {
        String absolutePath = "";
        try {
            URL res = getClass().getClassLoader().getResource("m.db");
            File file = Paths.get(res.toURI()).toFile();
            absolutePath = file.getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return absolutePath;
    }
}
