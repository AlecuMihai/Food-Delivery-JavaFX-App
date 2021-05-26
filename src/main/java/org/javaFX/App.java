package org.javaFX;

import config.InitializeTable;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.MenuItemRepository;
import repository.RestaurantRepository;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        InitializeTable.setUpDb();
        RestaurantRepository.populateDB();
        MenuItemRepository.populateDB();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginController.fxml"));
        Parent root = loader.load();
        LoginController loginController = (LoginController) loader.getController();

        loginController.createLoginControllerForm();
    }
}