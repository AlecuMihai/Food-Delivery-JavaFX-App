package org.javaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.CityRepository;
import repository.CustomerRepository;
import repository.RestaurantRepository;
import utilClasses.Customer;
import utilClasses.Restaurant;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView hamburgerImageView;

    @FXML
    private ImageView paellaImageView;

    @FXML
    private Button addButton;

    @FXML
    private Button loadButton;

    @FXML
    private VBox content;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label phLabel;

    private Stage prevStage;

    private Customer customer;

    public void setCustomer(int customerID) {
        this.customer = CustomerRepository.getCustomerById(customerID);
        System.out.println(this.customer + " @@@ customer successfully set");
    }

    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image hamburgerImage = new Image(new File("img/icon8_hamburger.png").toURI().toString());
        try {
            hamburgerImageView.setImage(hamburgerImage);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Image paellaImage = new Image(new File("img/icon6_paella.png").toURI().toString());
        try {
            paellaImageView.setImage(paellaImage);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        final Random rng = new Random();
        addButton.setOnAction(e -> {
            AnchorPane anchorPane = new AnchorPane();
            String style = String.format("-fx-background: rgb(%d, %d, %d);"+
                            "-fx-background-color: -fx-background;",
                    rng.nextInt(256),
                    rng.nextInt(256),
                    rng.nextInt(256));
            anchorPane.setStyle(style);
            Label label = new Label("Pane "+(content.getChildren().size()+1));
            AnchorPane.setLeftAnchor(label, 5.0);
            AnchorPane.setTopAnchor(label, 5.0);
            Button button = new Button("Remove");
            button.setOnAction(evt -> content.getChildren().remove(anchorPane));
            AnchorPane.setRightAnchor(button, 5.0);
            AnchorPane.setTopAnchor(button, 5.0);
            AnchorPane.setBottomAnchor(button, 5.0);
            anchorPane.getChildren().addAll(label, button);
            content.getChildren().add(anchorPane);
        });
    }

    public void createMainControllerForm(Parent parentRoot) {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainController.fxml"));
        Stage mainStage = new Stage();
        mainStage.initStyle(StageStyle.UNDECORATED);
        mainStage.setTitle("Main Stage");

        mainStage.setScene(new Scene(parentRoot, 400, 600));
        prevStage.close();
        mainStage.show();
    }

    public void loadButtonAction(ActionEvent e) {
        Stage rootStage = (Stage) loadButton.getScene().getWindow();
        try {
            usernameLabel.setText(customer.getUserName());
            phLabel.setText(customer.getContactPhone());
            locationLabel.setText(CityRepository.getCityByID(customer.getCityID()).getName());

            List<Restaurant> availableRestaurants = RestaurantRepository.getAllRestaurantsByCityID(customer.getCityID());

            availableRestaurants.forEach(restaurant -> {
                AnchorPane anchorPane = new AnchorPane();
                String style = "-fx-background: rgb(221, 221, 212); -fx-background-color: -fx-background;";
                anchorPane.setStyle(style);
                anchorPane.setPrefHeight(50);

                ImageView imgView = new ImageView(new File("img/icon7_nuggets.png").toURI().toString());
                imgView.setFitHeight(40);
                imgView.setFitWidth(40);

                AnchorPane.setLeftAnchor(imgView, 5.0);
                AnchorPane.setTopAnchor(imgView, 5.0);

                Label restaurantLabel = new Label(restaurant.getRestaurantNameName());
                AnchorPane.setLeftAnchor(restaurantLabel, 60.0);
                AnchorPane.setTopAnchor(restaurantLabel, 5.0);

                Button button = new Button("go To");
                button.setStyle("-fx-background: rgb(255, 102, 0); -fx-background-color: -fx-background;");
                button.setPrefHeight(20);

                button.setOnAction(event -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/menuController.fxml"));
                    Parent rootParent = null;
                    try {
                        rootParent = loader.load();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    MenuController menuController = loader.getController();

                    menuController.setPrevStage((Stage) button.getScene().getWindow());
                    menuController.createMenuControllerForm(rootParent, customer.getId(), restaurant.getId());
                });

                AnchorPane.setRightAnchor(button, 5.0);
                AnchorPane.setTopAnchor(button, 5.0);

                anchorPane.getChildren().addAll(restaurantLabel, button, imgView);

                AnchorPane padding = new AnchorPane();
                padding.setPrefHeight(10);
                content.getChildren().addAll(padding, anchorPane);
            });
            loadButton.setDisable(true);
            addButton.setDisable(true);

        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    public void cancelButtonAction(ActionEvent e) throws IOException {
        Stage rootStage = (Stage) cancelButton.getScene().getWindow();
        rootStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginController.fxml"));
        Parent root = loader.load();
        LoginController loginController = (LoginController) loader.getController();
        loginController.createLoginControllerForm();
    }
}
