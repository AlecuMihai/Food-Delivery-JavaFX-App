package repository;

import utilClasses.Driver;
import config.SqlConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DriverRepository {

    public static void insertDriver(Driver driver) {
        String sql = "INSERT INTO `driver` (`driver_name`,`vehicle`) VALUES(?,?)";
        Connection connection = SqlConfig.getDataBaseConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, driver.getDriverName());
            preparedStatement.setString(2, driver.getVehicle().toString());

            preparedStatement.executeUpdate();
            System.out.println("Successfully inserted driver with id: " + driver.getId() + " into database!");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static Driver getDriverByID(int id) {
        String sql = "SELECT * FROM `driver` WHERE `driver_id`=?";
        Connection connection = SqlConfig.getDataBaseConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Driver driver = new Driver(resultSet.getString("driver_name"),
                        resultSet.getString("vehicle"));
                driver.setId(resultSet.getInt("driver_id"));
                return driver;
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private DriverRepository() {}
}
