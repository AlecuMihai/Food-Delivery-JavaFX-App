package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class InitializeTable implements Loggable{

    public static void setUpDb() {
        List<String> SQLTables = new ArrayList<>();
        SQLTables.add("CREATE DATABASE IF NOT EXISTS `food_delivery_schema`;");

        // =================
        // `city` - TABLE  ||
        // =================
        SQLTables.add("CREATE TABLE IF NOT EXISTS `city` (" +
                      "`city_id` INT NOT NULL," +
                      "`city_name` VARCHAR(45) NOT NULL," +
                      "CONSTRAINT PRIMARY KEY (`city_id`)," +
                      "CONSTRAINT `unique_city_name` UNIQUE (`city_name`)" +
                      ");");

        // =====================
        // `customer` - TABLE  ||
        // =====================
        SQLTables.add(" CREATE TABLE IF NOT EXISTS `customer` (" +
                     "`customer_id` INT NOT NULL AUTO_INCREMENT," +
                     "`customer_username` VARCHAR(45) NOT NULL," +
                     "`city_id` INT NOT NULL," +
                     "`address` VARCHAR(45) NOT NULL," +
                     "`contact_phone` VARCHAR(45) NOT NULL," +
                     "`email` VARCHAR(45) NOT NULL," +
                     "`password` VARCHAR(45) NOT NULL," +
                     "CONSTRAINT PRIMARY KEY (`customer_id`)," +
                     "CONSTRAINT `FK_city_customer` FOREIGN KEY (`city_id`)" +
                     "       REFERENCES `city` (`city_id`)" +
                     "       ON UPDATE CASCADE ON DELETE RESTRICT," +
                     "CONSTRAINT `unique_customer_username` UNIQUE(`customer_username`)," +
                     "CONSTRAINT `unique_contact_phone` UNIQUE (`contact_phone`)," +
                     "CONSTRAINT `unique_email` UNIQUE (`email`));");

        // ===================
        // `driver` - TABLE  ||
        // ===================
        SQLTables.add(" CREATE TABLE IF NOT EXISTS `driver` (" +
                      "`driver_id` INT NOT NULL AUTO_INCREMENT," +
                      "`driver_name` VARCHAR(45) NOT NULL," +
                      "`vehicle` VARCHAR(45) NOT NULL," +
                      "CONSTRAINT PRIMARY KEY (`driver_id`));");

        // =======================
        // `restaurant` - TABLE  ||
        // =======================
        SQLTables.add("CREATE TABLE IF NOT EXISTS `restaurant` (" +
                      "`restaurant_id` INT NOT NULL AUTO_INCREMENT," +
                      "`restaurant_name` VARCHAR(45) NOT NULL," +
                      "`city_id` INT NOT NULL," +
                      "CONSTRAINT PRIMARY KEY (`restaurant_id`)," +
                      "CONSTRAINT `unique_restaurant_name` UNIQUE (`restaurant_name`)," +
                      "CONSTRAINT `FK_city_restaurant` FOREIGN KEY (`city_id`)" +
                      "REFERENCES `city` (`city_id`)" +
                      "ON UPDATE CASCADE ON DELETE RESTRICT);");

        // =====================
        // `menu_item` - TABLE  ||
        // =====================
        SQLTables.add("CREATE TABLE IF NOT EXISTS `menu_item` (" +
                      "`menu_item_id` INT NOT NULL AUTO_INCREMENT," +
                      "`restaurant_id` INT NOT NULL," +
                      "`item_name` VARCHAR(45) NOT NULL," +
                      "`price` DOUBLE NOT NULL," +
                      "CONSTRAINT PRIMARY KEY (`menu_item_id`)," +
                      "CONSTRAINT `FK_restaurant_id` FOREIGN KEY (`restaurant_id`)" +
                      "    REFERENCES `restaurant` (`restaurant_id`)" +
                      "    ON UPDATE CASCADE ON DELETE CASCADE," +
                      "CONSTRAINT `vf_price` CHECK (price > 0));");

        Connection dataBaseConnection = SqlConfig.getDataBaseConnection();
        if(dataBaseConnection == null) {
            System.exit(-1);
        }

        for(String createTable : SQLTables) {
            try(Statement statement = dataBaseConnection.createStatement()) {
                statement.execute(createTable);

                System.out.println("Successfully created table : " + createTable.substring(25, 40) + "[...]");
                //LOGGER.info("Successfully created table : " + createTable.substring(15, 35) + "[...]");
            } catch (SQLException exception) {
                System.out.println("Could not create table!\n" + exception.getMessage());
                //LOGGER.error("Could not create table!\n" + exception.getMessage());
            }
        }
    }
    private InitializeTable() {}
}
