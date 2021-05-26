package repository;

import config.SqlConfig;
import utilClasses.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class MenuItemRepository {

    public static void insertMenuItem(MenuItem menuItem) {
        String sql = "INSERT INTO `menu_item` (`item_name`, `price`, `restaurant_id`) VALUES (?,?,?)";

        Connection connection = SqlConfig.getDataBaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, menuItem.getItemName());
            preparedStatement.setDouble(2, menuItem.getPrice());
            preparedStatement.setInt(3, menuItem.getRestaurantId());

            preparedStatement.executeUpdate();
            System.out.println("Successfully inserted menuItem into database!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static List<MenuItem> getAllMenuItemsByRestaurantID(int restaurantID) {
        String sql = "SELECT * FROM `menu_item` WHERE `restaurant_id`=?";
        Connection connection = SqlConfig.getDataBaseConnection();

        List<MenuItem> menuItems = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, restaurantID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MenuItem menuItem = new MenuItem(
                        resultSet.getInt("menu_item_id"),
                        resultSet.getInt("restaurant_id"),
                        resultSet.getString("item_name"),
                        resultSet.getDouble("price"));
                menuItems.add(menuItem);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return menuItems;
    }

    // PURGE AND REPLACE!!!

    public static void populateDB() {
        String sql = "DELETE FROM `menu_item`";
        Connection connection = SqlConfig.getDataBaseConnection();
        try(Statement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate(sql);
            int restaurantID1 = RestaurantRepository.getRestaurantByName("SuzannaRibs&Wings").getId();
            int restaurantID2 = RestaurantRepository.getRestaurantByName("VivoRestaurant").getId();

            insertMenuItem(new MenuItem(restaurantID1, "Baby Ribs", 34.99));
            insertMenuItem(new MenuItem(restaurantID1, "Baby Wings", 37.75));
            insertMenuItem(new MenuItem(restaurantID1, "Spicy Ribs", 45.00));
            insertMenuItem(new MenuItem(restaurantID1, "Pastrami chicken wings", 82.00));
            insertMenuItem(new MenuItem(restaurantID1, "Texan meal", 38.00));

            insertMenuItem(new MenuItem(restaurantID2, "Royal Cheeseburger", 49.99));
            insertMenuItem(new MenuItem(restaurantID2, "Royal Hamburger", 54.99));
            insertMenuItem(new MenuItem(restaurantID2, "Spaghetti Carbanana", 420.69));
            insertMenuItem(new MenuItem(restaurantID2, "Vivo Burger menu", 38.00));
            insertMenuItem(new MenuItem(restaurantID2, "Halloumi Burger", 37.99));

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
