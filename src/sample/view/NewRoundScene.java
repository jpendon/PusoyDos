package sample.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewRoundScene implements Initializable {
    private GameFile m_gameFile;

    private Stage stage;

    @FXML
    public Label newRoundInfoPane;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            Game game = this.m_gameFile.GetGame();
            game.GetRound().EndRound();
            game.SetPlayers(game.GetRound().GetPlayers());
            game.GetRound().SetIsNewRound(true);
            saveRound(game.GetRound());

            // Display Round info
            displayRoundInfo();
        });
    }

    private void displayRoundInfo()
    {
        Game game = this.m_gameFile.GetGame();
        Round round = game.GetRound();
        Player remainingPlayer = round.GetPlayers().elementAt(round.GetStartingPlayer());

        String roundInfoString = "Round " + (round.GetRoundCounter() + 1) + ":\n\n";

        if (remainingPlayer instanceof AI)
            roundInfoString += "Computer " + remainingPlayer.GetPlayerId()  + " Goes First.";
        else
            roundInfoString += "You go first";

        this.newRoundInfoPane.setText(roundInfoString);
    }


    public void continueButtonOnClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = loader.getController();
        controller.setGameFile(this.m_gameFile);
        controller.setStage(this.stage);

        this.stage.setScene(new Scene(parent, 1280, 720));
        this.stage.show();
    }

    // Remove
    public static void saveRound(Round round)
    {
        Serialization serialization = new Serialization();
        File file = new File("new_round.txt");

        serialization.SaveGame(file, round);
    }

    public void setGameFile(GameFile gameFile) { this.m_gameFile = gameFile; }

    public void setStage(Stage stage) { this.stage = stage; }
}