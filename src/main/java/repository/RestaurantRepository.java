package repository;

import config.SqlConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utilClasses.Restaurant;

public final class RestaurantRepository {

    public static void insertRestaurant(Restaurant restaurant) {
        String sql = "INSERT INTO `restaurant` (`restaurant_name`, `city_id`)" +
                "VALUES(?,?)";

        Connection connection = SqlConfig.getDataBaseConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, restaurant.getRestaurantNameName());
            preparedStatement.setInt(2, restaurant.getCityId());

            preparedStatement.executeUpdate();
            System.out.println("Successfully inserted restaurant into database!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static Restaurant getRestaurantById(int id) {
        String sql = "SELECT * FROM `restaurant` WHERE `restaurant_id`=?";
        Connection connection = SqlConfig.getDataBaseConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Restaurant restaurant = new Restaurant(
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getInt("city_id"));
                restaurant.setId(restaurant.getId());
                return restaurant;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static Restaurant getRestaurantByName(String restaurantName) {
        String sql = "SELECT * FROM `restaurant` WHERE `restaurant_name`=?";
        Connection connection = SqlConfig.getDataBaseConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, restaurantName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Restaurant restaurant = new Restaurant(
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getInt("city_id"));
                restaurant.setId(restaurant.getId());
                return restaurant;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static List<Restaurant> getAllRestaurantsByCityID(int cityID) {
        String sql = "SELECT * FROM `restaurant` WHERE `city_id`=?";
        Connection connection = SqlConfig.getDataBaseConnection();

        List<Restaurant> restaurants = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cityID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Restaurant restaurant = new Restaurant(
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("restaurant_name"),
                        resultSet.getInt("city_id"));
                restaurants.add(restaurant);
                System.out.println("Successfully loaded restaurant with id: " + restaurant.getId() + " into list!");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return restaurants;
    }

    public static List<Restaurant> getAllRestaurants() {
        return null;
    }

    public static void populateDB() {

        // PURGE AND REPLACE!!!

        String sql = "DELETE FROM `restaurant`";
        Connection connection = SqlConfig.getDataBaseConnection();
        try(Statement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate(sql);
            insertRestaurant(new Restaurant("SuzannaRibs&Wings", 10));

            insertRestaurant(new Restaurant("VivoRestaurant", 10));

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    private RestaurantRepository() {}
}