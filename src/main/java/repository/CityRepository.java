package repository;

import utilClasses.City;
import config.SqlConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class CityRepository{

    public static void insertCity(City city) {
        String sql = "INSERT INTO `city` (`city_id`,`city_name`) VALUES (?,?)";
        Connection connection = SqlConfig.getDataBaseConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, city.getId());
            statement.setString(2, city.getName());

            statement.executeUpdate();
            System.out.println("Successfully inserted city " + city.getId());
            //LOGGER.info("Successfully inserted city " + city.getId());
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            //LOGGER.info(exception.getMessage());
        }
    }

    public static City getCityByID(int id) {
        String sql = "SELECT * FROM `city` WHERE `city_id`=?";
        Connection connection = SqlConfig.getDataBaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new City(resultSet.getInt(1), resultSet.getString(2));
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            //LOGGER.error(exception.getMessage());
        }
        return null;
    }

    public static City getCityByName(String cityName) {
        String sql = "SELECT * FROM `city` WHERE `city_name`=?";
        Connection connection = SqlConfig.getDataBaseConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cityName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new City(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static List<City> getAllCities() {
        String sql = "SELECT * FROM `city`";
        Connection connection = SqlConfig.getDataBaseConnection();
        List<City> allCities = new ArrayList<>();

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                allCities.add(new City(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            //LOGGER.error(exception.getMessage());
        }
        return allCities;
    }

    private CityRepository() {}
}
