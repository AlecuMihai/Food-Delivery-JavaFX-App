package org.javaFX;

import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.CustomerRepository;
import repository.MenuItemRepository;
import repository.RestaurantRepository;
import utilClasses.Customer;
import utilClasses.MenuItem;
import utilClasses.Restaurant;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MenuController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button doneButton;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private VBox content;

    @FXML
    private VBox commandContent;

    private Stage prevStage;

    private Customer customer;

    private Restaurant restaurant;

    private void setCustomer(int customerID) {
        this.customer = CustomerRepository.getCustomerById(customerID);
    }

    private void setRestaurant(int restaurantID) {
        this.restaurant = RestaurantRepository.getRestaurantById(restaurantID);
    }

    public void setPrevStage (Stage stage)  {
        this.prevStage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void createMenuControllerForm(Parent parentRoot, int customerID, int restaurantID) {
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(parentRoot, 600, 400));
        mainStage.initStyle(StageStyle.UNDECORATED);
        mainStage.setTitle("Menu scene");

        prevStage.close();

        setCustomer(customerID);
        setRestaurant(restaurantID);

        System.out.println(customer + "\n" + restaurant);

        List<MenuItem> menuItemList = null;
        try {
            menuItemList = MenuItemRepository.getAllMenuItemsByRestaurantID(restaurant.getId());
            menuItemList.forEach(item -> {
                AnchorPane anchorPane = new AnchorPane();
                String style = "-fx-background: rgb(221, 221, 212); -fx-background-color: -fx-background;";
                anchorPane.setStyle(style);
                anchorPane.setPrefHeight(50);

                Label itemName = new Label(item.getItemName());
                Label priceLabel = new Label(String.valueOf(item.getPrice()));

                AnchorPane.setTopAnchor(itemName, 5.0);
                AnchorPane.setLeftAnchor(itemName, 5.0);

                AnchorPane.setTopAnchor(priceLabel, 5.0);
                AnchorPane.setRightAnchor(priceLabel, 60.0);
                itemName.setText(item.getItemName());

                Button button = new Button("+");
                button.setStyle("-fx-background: rgb(255, 102, 0); -fx-background-color: -fx-background;");

                Map<MenuItem, Integer> commandContentMap = new HashMap<>();
                Map<MenuItem, Label> noOfPiecesMap = new HashMap<>();

                button.setOnAction(actionEvent -> {
                    if(!commandContentMap.containsKey(item)) {
                        commandContentMap.put(item, 1);

                        AnchorPane command = new AnchorPane();

                        Label commandItemNameLabel = new Label(item.getItemName());
                        Label noOfPieces = new Label("x 1");
                        noOfPiecesMap.put(item, noOfPieces);

                        AnchorPane.setLeftAnchor(commandItemNameLabel, 5.0);
                        AnchorPane.setTopAnchor(commandItemNameLabel, 5.0);

                        AnchorPane.setRightAnchor(noOfPieces, 5.0);
                        AnchorPane.setTopAnchor(noOfPieces, 5.0);
                        command.getChildren().addAll(commandItemNameLabel, noOfPieces);

                        commandContent.getChildren().add(command);

                        Double priceSoFar = Double.parseDouble(totalPriceLabel.getText());
                        totalPriceLabel.setText(String.format("%.3f", priceSoFar + item.getPrice()));
                    } else {
                        int noOfItems = commandContentMap.get(item);
                        commandContentMap.put(item, noOfItems + 1);

                        Label noOfItemsLabel = noOfPiecesMap.get(item);
                        noOfItemsLabel.setText(String.format("x %d", noOfItems + 1));

                        Double priceSoFar = Double.parseDouble(totalPriceLabel.getText());
                        totalPriceLabel.setText(String.format("%.3f", priceSoFar + item.getPrice()));
                    }
                });

                AnchorPane.setTopAnchor(button, 5.0);
                AnchorPane.setRightAnchor(button, 5.0);
                anchorPane.getChildren().addAll(button, itemName, priceLabel);

                AnchorPane padding = new AnchorPane();
                padding.setPrefHeight(10);
                content.getChildren().addAll(padding, anchorPane);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        mainStage.show();
    }

    public void cancelButtonAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void doneButtonAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalController.fxml"));
        Parent parentRoot = loader.load();
        FinalController controller = loader.getController();

        controller.createFinalControllerForm();
    }
}
