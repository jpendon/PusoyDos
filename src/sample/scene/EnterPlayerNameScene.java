package sample.scene;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.model.GameFile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EnterPlayerNameScene implements Initializable {
    private Stage stage;
    private GameFile gameFile;

    @FXML
    private Label infoLabel;

    @FXML
    private TextArea enterPlayerNameTextArea;

    @FXML
    private Button continueButton;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            infoLabel.setText("Enter your name:");

        });

    }

    public void continueButtonOnClick(ActionEvent actionEvent) throws IOException {
        String userInput = enterPlayerNameTextArea.getText();
        userInput = userInput.trim();

        this.gameFile.SetPlayerName(userInput);
        this.gameFile.SetIsDefaultFile(false);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loadGameScene.fxml"));
        Parent parent = fxmlLoader.load();

        LoadGameScene controller = fxmlLoader.getController();
        controller.setGameFile(this.gameFile);
        controller.setStage(this.stage);

        this.stage.setScene(new Scene(parent, 1280, 720));
        this.stage.show();
    }

    public void backButtonOnClick(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loadGameScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        LoadGameScene controller = fxmlLoader.getController();
        controller.setStage(this.stage);

        this.stage.setScene(new Scene(parent, 1280, 720));
        this.stage.show();
    }

    public void setStage(Stage stage) { this.stage = stage; }

    public void setGameFile(GameFile gameFile) { this.gameFile = gameFile; }


}
