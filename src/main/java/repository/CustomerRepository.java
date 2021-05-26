package repository;

import utilClasses.Customer;
import config.SqlConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class CustomerRepository {

    public static void insertCustomer(Customer customer) {
        String sql = "INSERT INTO `customer` (`customer_username`, `city_id`, `address`, `contact_phone`, `email`, `password`)" +
                "VALUES(?,?,?,?,?,?)";

        Connection connection = SqlConfig.getDataBaseConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getUserName());
            statement.setInt(2, customer.getCityID());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getContactPhone());
            statement.setString(5, customer.getEmail());
            statement.setString(6, customer.getPassword());

            statement.executeUpdate();
            System.out.println("Successfully inserted customer into database!");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static boolean validateCustomerCredentials(String customerUsername, String customerPassword) {
        String sql = "SELECT COUNT(1) FROM `customer` WHERE `customer_username` = ? AND `password` = ?";

        Connection connection = SqlConfig.getDataBaseConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customerUsername);
            preparedStatement.setString(2, customerPassword);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                if(resultSet.getInt(1) == 0)
                    return false;
                else
                    return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean validateCustomerPhoneNumber(String customerPhoneNumber) {
        String sql = "SELECT COUNT(1) FROM `customer` WHERE `contact_phone` = ?";

        Connection connection = SqlConfig.getDataBaseConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customerPhoneNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1) == 1;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }


    public static Customer getCustomerById(int id) {
        String sql = "SELECT * FROM `customer` WHERE `customer_id`=?";
        Connection connection = SqlConfig.getDataBaseConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt("city_id"),
                        resultSet.getString("customer_username"),
                        resultSet.getString("address"),
                        resultSet.getString("contact_phone"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
                customer.setId(resultSet.getInt(1));
                return customer;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static Customer getCustomerByUsername(String customerUsername) {
        String sql = "SELECT * FROM `customer` WHERE `customer_username`=?";
        Connection connection = SqlConfig.getDataBaseConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customerUsername);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt("city_id"),
                        resultSet.getString("customer_username"),
                        resultSet.getString("address"),
                        resultSet.getString("contact_phone"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
                customer.setId(resultSet.getInt(1));
                return customer;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM `customer`";
        Connection connection = SqlConfig.getDataBaseConnection();

        List<Customer> allCustomers = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt("city_id"),
                        resultSet.getString("customer_username"),
                        resultSet.getString("address"),
                        resultSet.getString("contact_phone"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
                customer.setId(resultSet.getInt("customer_id"));
                allCustomers.add(customer);
                System.out.println("Successfully loaded customer with id: " + customer.getId() + " into list!");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return allCustomers;
    }
    private CustomerRepository() {}
}
