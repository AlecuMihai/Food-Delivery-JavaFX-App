package org.javaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FinalController implements Initializable {
    @FXML
    private ImageView imageViewFinal;

    @FXML
    private Button closeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image imgFinal = new Image(new File("img/icon11_final.png").toURI().toString());
        imageViewFinal.setImage(imgFinal);
    }

    public void createFinalControllerForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalController.fxml"));
        Parent parent = loader.load();

        Stage finalStage = new Stage();
        finalStage.initStyle(StageStyle.UNDECORATED);
        finalStage.setTitle("");

        finalStage.setScene(new Scene(parent, 600, 400));

        finalStage.show();
    }

    public void closeButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
