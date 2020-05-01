package sample.scene;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class BaseScene implements Initializable {
    private GameFile m_gameFile;

    private Stage m_stage;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            Game game = this.m_gameFile.GetGame();
            Round round = game.GetRound();

            // Check if the Round has ended
            if (round.CheckIfGameEnded())
            {
                try {
                    displayGameEndScene();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            // Check if there is only one player left in the round
            if (round.GetPlayerCounter() == 1)
            {
                try {
                    displayNewRound();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (game.GetIsResumed())
            {
                round.SetCurrentPlayer(round.GetCurrentPlayer() - 1);
                game.SetIsResumed(false);
            }

            // Check if the Round is new or resumed
            if (round.GetIsNewRound())
                round.SetUpRound();
            else
                round.NextTurnSetup(this.m_gameFile.GetPlayerName());

            // Check if Player has Passed
            if (round.GetPlayers().elementAt(round.GetCurrentPlayer()).GetHasPassed())
            {
                try {
                    displayBaseScene();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Check if an AI is next
            if (round.GetPlayers().elementAt(round.GetCurrentPlayer()) instanceof AI)
            {
                try {
                    playComputerTurn();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Human is Next

            try {
                displayPlayerScene();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void playComputerTurn() throws IOException {
        Game game = this.m_gameFile.GetGame();
        Round round = game.GetRound();

        AI computer = (AI)round.GetPlayers().elementAt(round.GetCurrentPlayer());

        Vector<Card> playCards = computer.DecideAction(round.GetTableCards());

        if (playCards.size() == 0)
        {
            // Computer has passed
            round.GetPlayers().elementAt(round.GetCurrentPlayer()).SetHasPassed(true);
            round.SetPlayerCounter(round.GetPlayerCounter() - 1);

            String computerActionString =  "Computer " + computer.GetPlayerId() + " passed.";

            displayComputerAction(computerActionString, playCards);

            return;
        }

        round.ReplaceTableCards(playCards);

        // Display Computer's Action
        String computerActionString =  "Computer " + computer.GetPlayerId() + " played: ";


        // Display Computer's Action to View
        displayComputerAction(computerActionString, playCards);

    }

    public void displayGameEndScene() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("gameEndScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        GameEndScene controller = loader.getController();
        controller.setGameFile(this.m_gameFile);
        controller.setStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }

    public void displayNewRound() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("NewRoundScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        NewRoundScene controller = loader.getController();
        controller.setGameFile(this.m_gameFile);
        controller.setStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }

    public void displayPlayerScene() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("playerScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        PlayerScene controller = loader.getController();
        controller.setGameFile(this.m_gameFile);
        controller.setStage(this.m_stage);


        Stage window = (Stage)this.m_stage.getScene().getWindow();
        Scene scene = new Scene(parent, 1280, 720);
        window.setScene(scene);
        window.show();
    }

    public void displayComputerAction(String a_computerAction, Vector<Card> a_cardsUsed) throws IOException
    {
        FXMLLoader computerActionScene = new FXMLLoader(getClass().getResource("computerActionScene.fxml"));
        Parent parent = computerActionScene.load();
        parent.setStyle("-fx-background-color: #009900;");

        ComputerActionScene controller = computerActionScene.getController();

        controller.setCardsUsed(a_cardsUsed);
        controller.setComputerAction(a_computerAction);
        controller.setGameFile(this.m_gameFile);
        controller.setStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }

    public void displayBaseScene() throws IOException
    {
        // Load Game Scene to Continue the game
        FXMLLoader loader= new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = loader.getController();
        controller.setGameFile(this.m_gameFile);
        controller.setStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }

    public void setGameFile(GameFile a_gameFile) { this.m_gameFile = a_gameFile; }

    public void setStage(Stage a_stage) { this.m_stage = a_stage; }

}
