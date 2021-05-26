package org.javaFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.CityRepository;
import repository.CustomerRepository;
import utilClasses.City;
import utilClasses.Customer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {
    @FXML
    private ImageView userRegistrationImageView;

    @FXML
    private Button registerButton;

    @FXML
    private Label registrationMessage;

    @FXML
    private Button cancelButton;

    @FXML
    private MenuButton selectCityMenuButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label emailLabel;

    @FXML
    private Label selectCityLabel;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField contactPhoneTextField;

    @FXML
    private Label contactPhoneLabel;

    @FXML
    private ImageView backImageView;

    private Stage prevStage;

    public void setPrevStage(Stage prevStage) {
        this.prevStage = prevStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image userImage = new Image(new File("img/icon5_registration.png").toURI().toString());
        userRegistrationImageView.setImage(userImage);

        Image backImage = new Image(new File("img/icon10_back.png").toURI().toString());
        backImageView.setImage(backImage);
        backImageView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage rootStage = (Stage) backImageView.getScene().getWindow();
                rootStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginController.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LoginController loginController = (LoginController) loader.getController();

                try {
                    loginController.createLoginControllerForm();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        List<City> allCities = CityRepository.getAllCities();
        allCities.forEach(city -> {
            MenuItem menuItem = new MenuItem(city.getName());
            menuItem.setOnAction(MouseEvent -> selectCityLabel.setText(city.getName()));
            selectCityMenuButton.getItems().add(menuItem);
        });
    }

    public void createRegisterControllerForm() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerController.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RegisterController controller = loader.getController();
        //controller.setPrevStage(prevStage);

        Stage registerStage = new Stage();
        registerStage.initStyle(StageStyle.UNDECORATED);
        registerStage.setTitle("Register Account Form");

        assert root != null;
        registerStage.setScene(new Scene(root, 600, 400));

        prevStage.close();
        registerStage.show();
    }

    public void registerButtonAction(ActionEvent e) {
        //Stage rootStage = (Stage) registerButton.getScene().getWindow(); ???
        Pattern pattern = Pattern.compile("[!@#$%^&*()+= \\[\\]{}\\\\|:;\"'<,>.?\\/]");
        Pattern contactPhonePattern = Pattern.compile("\\D");

        boolean valid = true;
        String customerUsername = usernameTextField.getText();
        if(pattern.matcher(customerUsername).matches()) {
            usernameLabel.setTextFill(Color.RED);
            usernameLabel.setText("No special characters");
            valid = false;
        }

        if(customerUsername.isBlank()) {
            usernameLabel.setTextFill(Color.RED);
            usernameLabel.setText("*Enter your username");
            valid = false;
        }

        String customerPassword = passwordTextField.getText();
        if(!passwordTextField.getText().equals(confirmPasswordTextField.getText())) {
            passwordLabel.setTextFill(Color.RED);
            passwordLabel.setText("Passwords do not match");
            valid = false;
        }

        String customerEmail = emailTextField.getText();
        if(emailTextField.getText().isBlank()) {
            emailLabel.setTextFill(Color.RED);
            emailLabel.setText("*Enter your email");
            valid = false;
        }

        String cityName = selectCityLabel.getText();
        City city = CityRepository.getCityByName(cityName);

        String customerAddress = null;
        if(addressTextField.getText().isBlank()) {
            addressTextField.setAlignment(Pos.CENTER);
            addressTextField.setText("-");

            customerAddress = "-";
        } else {
            customerAddress = addressTextField.getText();
        }

        String contactPhone = contactPhoneTextField.getText();
        if(contactPhonePattern.matcher(contactPhone).matches() || contactPhone.length() != 10) {
            contactPhoneLabel.setTextFill(Color.RED);
            contactPhoneLabel.setText("Invalid phone number");
            valid = false;
        } else if(contactPhone.isBlank()) {
            contactPhoneLabel.setTextFill(Color.RED);
            contactPhoneLabel.setText("*Enter your phone number");
            valid = false;
        } else if(CustomerRepository.validateCustomerPhoneNumber(contactPhone)) {
            contactPhoneLabel.setTextFill(Color.RED);
            contactPhoneLabel.setText("*This phone number is already in use");
        }

        if(valid) {
            usernameLabel.setText("");
            passwordLabel.setText("");
            emailLabel.setText("");
            contactPhoneLabel.setText("");

            assert city != null;
            CustomerRepository.insertCustomer(new Customer(city.getId(), customerUsername , customerAddress, contactPhone, customerEmail, customerPassword));

            registrationMessage.setTextFill(Color.web("#8ac4f8"));
            registrationMessage.setText("Account created successfully");
        } else {
            registrationMessage.setTextFill(Color.RED);
            registrationMessage.setText("Failed to create account!");
        }
    }

    public void backOnAction(ActionEvent e) throws IOException {
        Stage rootStage = (Stage) backImageView.getScene().getWindow();
        rootStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginController.fxml"));
        Parent root = loader.load();
        LoginController loginController = (LoginController) loader.getController();

        loginController.createLoginControllerForm();
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
