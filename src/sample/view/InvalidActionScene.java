package sample.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class InvalidActionScene implements Initializable
{
    private GameFile m_gameFile;
    private Vector<Card> cardsSelected;
    private Stage stage;

    @FXML
    private Label invalidTextPane;

    @FXML
    private Button continueButton;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            Game game = this.m_gameFile.GetGame();
            Round round = game.GetRound();
            Human player = round.GetHumanPlayer();

            invalidTextPane.setText(player.GetInvalidActionString(this.cardsSelected, round.GetTableCards()));

        });
    }

    public void continueButtonOnAction(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("PlayerScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        PlayerScene controller = loader.getController();
        controller.setGameFile(this.m_gameFile);
        controller.setStage(this.stage);

        this.stage.setScene(new Scene(parent, 1280, 720));
        this.stage.show();
    }

    public void setGameFile(GameFile gameFile) { this.m_gameFile = gameFile; }

    public void setCardsSelected(Vector<Card> cardsSelected) { this.cardsSelected = cardsSelected; }

    public void setStage(Stage stage) {this.stage = stage; }

}
