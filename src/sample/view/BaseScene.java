package sample.view;

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
                    DisplayGameEndScene();
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
                    DisplayNewRound();
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
                    DisplayBaseScene();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Check if an AI is next
            if (round.GetPlayers().elementAt(round.GetCurrentPlayer()) instanceof AI)
            {
                try {
                    PlayComputerTurn();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Human is Next

            try {
                DisplayPlayerScene();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

/**/
/*
BaseScene::PlayComputerTurn()

NAME

    BaseScene::PlayComputerTurn - Computer makes a turn

SYNOPSIS

    void BaseScene::PlayComputerTurn();

DESCRIPTION

    The function gets the AI object from the round and calls the function DecideAction.
    If the vector of cards returned from the function has a size of zero, then the computer
    has passed its turn and the necessary actions are taken. If not then the cards
    on the table are replaced.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:42am 4/2/2020

*/
/**/
    private void PlayComputerTurn() throws IOException {
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

            DisplayComputerAction(computerActionString, playCards);

            return;
        }

        computer.RemoveMultipleCardsFromHand(playCards);
        round.ReplaceTableCards(playCards);

        // Display Computer's Action
        String computerActionString =  "Computer " + computer.GetPlayerId() + " played: ";

        // Display Computer's Action to View
        DisplayComputerAction(computerActionString, playCards);

    }
/*void BaseScene::PlayComputerTurn();*/

/**/
/*
BaseScene::DisplayGameEndScene()

NAME

    BaseScene::DisplayGameEndScene - Displays the scene for ending the game

SYNOPSIS

    void BaseScene::DisplayGameEndScene();

DESCRIPTION

    The function sets up and display the scene for ending the game.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:21am 4/2/2020
*/
/**/
    private void DisplayGameEndScene() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("gameEndScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        GameEndScene controller = loader.getController();
        controller.SetGameFile(this.m_gameFile);
        controller.SetStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void BaseScene::DisplayGameEndScene();*/

/**/
/*
BaseScene::DisplayNewRound()

NAME

    BaseScene::DisplayNewRound - Displays the new round scene

SYNOPSIS

    void BaseScene::DisplayNewRound()

DESCRIPTION

    The function prepares and display the scene for a new Round.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:54am 4/2/2020

*/
/**/
    private void DisplayNewRound() throws IOException
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
/*void BaseScene::DisplayNewRound();*/

/**/
/*
BaseScene::DisplayPlayerScene()

NAME

    BaseScene::DisplayPlayerScene - Displays the PlayerScene

SYNOPSIS

    void BaseScene::DisplayPlayerScene();

DESCRIPTION

    The function prepares and displays the PlayerScene when it is the player's
    turn.

RETURNS

AUTHOR

    Jay Pendon

DATE

    8:33am 4/2/2020

*/
/**/
    private void DisplayPlayerScene() throws IOException
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
/*void BaseScene::DisplayPlayerScene();*/

/**/
/*
BaseScene::DisplayComputerAction(String a_computerAction, Vector<Card> a_cardsUsed)

NAME

    BaseScene::DisplayComputerAction - Displays the Computer's action

SYNOPSIS

    void BaseScene::DisplayComputerAction(String a_computerAction, Vector<Card> a_cardsUsed)
        a_computerAction        -> String containing the computer's action
        a_cardsUsed             -> Vector of Card objects

DESCRIPTION

    The function prepares the next scene and passes a_computerAction and a_cardsUsed to the
    next controller.

RETURNS

AUTHOR

    Jay Pendon

DATE

    10:05am 4/2/2020

*/
/**/
    private void DisplayComputerAction(String a_computerAction, Vector<Card> a_cardsUsed) throws IOException
    {
        FXMLLoader computerActionScene = new FXMLLoader(getClass().getResource("computerActionScene.fxml"));
        Parent parent = computerActionScene.load();
        parent.setStyle("-fx-background-color: #009900;");

        ComputerActionScene controller = computerActionScene.getController();

        controller.SetCardsUsed(a_cardsUsed);
        controller.SetComputerAction(a_computerAction);
        controller.SetGameFile(this.m_gameFile);
        controller.SetStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void BaseScene::DisplayComputerAction(String a_computerAction, Vector<Card> a_cardsUsed);*/

/**/
/*
BaseScene::DisplayBaseScene()

NAME

    BaseScene::DisplayBaseScene - Displays the BaseScene

SYNOPSIS

    void BaseScene::DisplayBaseScene();

DESCRIPTION

    The function prepares and displays the BaseScene

RETURNS

AUTHOR

    Jay Pendon

DATE

        6:06am 4/12/2020

*/
/**/
    private void DisplayBaseScene() throws IOException
    {
        // Load Game Scene to Continue the game
        FXMLLoader loader= new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = loader.getController();
        controller.SetGameFile(this.m_gameFile);
        controller.SetStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void BaseScene::DisplayBaseScene()*/

/**/
/*
BaseScene::SetGameFile(GameFile a_gameFile)

NAME

    BaseScene::SetGameFile - Sets the GameFile for the controller

SYNOPSIS

    void BaseScene::SetGameFile(GameFile a_gameFile);
        a_gameFile          -> GameFile object

DESCRIPTION

    The function sets m_gameFile as a_gameFile.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:21pm 4/11/2020
*/
/**/
    public void SetGameFile(GameFile a_gameFile) { this.m_gameFile = a_gameFile; }

/**/
/*
BaseScene::SetStage(Stage a_stage)

NAME

    BaseScene::SetStage - Sets the stage

SYNOPSIS

    void BaseScene::SetStage(Stage a_stage);
        a_stage         -> Stage object

DESCRIPTION

    The function sets m_stage as a_stage.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:21pm 4/11/2020

*/
/**/
    public void SetStage(Stage a_stage) { this.m_stage = a_stage; }

}
