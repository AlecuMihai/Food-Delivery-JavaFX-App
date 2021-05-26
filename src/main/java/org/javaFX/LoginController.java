package org.javaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.CustomerRepository;
import utilClasses.Customer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label loginMessage;

    @FXML
    private ImageView lockImageView;

    @FXML
    private ImageView cargoImageView;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    private Stage prevStage;

    public void setPrevStage(Stage prevStage) {
        this.prevStage = prevStage;
    }

    public void createLoginControllerForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginController.fxml"));
        Parent root = loader.load();
        LoginController loginController = (LoginController) loader.getController();

        Stage loginStage = new Stage();

        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.setTitle("Food Delivery Application");
        loginStage.setScene(new Scene(root, 600, 400));

        loginController.setPrevStage(loginStage);
        loginStage.show();
    }

    public void loginButtonAction(ActionEvent e) throws IOException {
        loginMessage.setText("Please enter your username and password");

        if(!usernameTextField.getText().isBlank() && !usernameTextField.getText().isBlank()) {
            if(validateLogin()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainController.fxml"));
                Parent parentRoot = loader.load();
                MainController controller = loader.getController();
                Customer customer = CustomerRepository.getCustomerByUsername(usernameTextField.getText());

                controller.setCustomer(Objects.requireNonNull(customer).getId());
                controller.setPrevStage(prevStage);

                controller.createMainControllerForm(parentRoot);
            }
        } else {
            loginMessage.setText("Please enter your username and password");
        }
    }

    public void registerButtonAction(ActionEvent e) {
        registerAccountForm();
    }

    public void registerAccountForm() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerController.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RegisterController controller = loader.getController();
        controller.setPrevStage(prevStage);

        controller.createRegisterControllerForm();
    }

    public void cancelButtonAction(ActionEvent e) {
        Stage rootStage = (Stage) cancelButton.getScene().getWindow();
        rootStage.close();
    }

    public boolean validateLogin() {
        String customerUsername = usernameTextField.getText();
        String customerPassword = passwordTextField.getText();
        if(CustomerRepository.validateCustomerCredentials(customerUsername, customerPassword)) {
            loginMessage.setText("Welcome: " + customerUsername);
            return true;
        }
        else {
            loginMessage.setText("Incorrect username or password");
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image cargoImage = new Image(new File("img/icon1.png").toURI().toString());
        cargoImageView.setImage(cargoImage);

        Image lockImage = new Image(new File("img/icon2_lock.png").toURI().toString());
        lockImageView.setImage(lockImage);
    }
}
