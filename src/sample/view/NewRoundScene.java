package sample.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
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
    public Label gameInfoLabel;

    @FXML
    public Label newRoundInfoPane;

/**/
/*
NewRoundScene::initialize(URL a_location, ResourceBundle a_resources)

NAME

    NewRoundScene::initialize - initialize function for controller

SYNOPSIS

    void NewRoundScene::initialize(URL a_location, ResourceBundle a_resources);
        a_location          -> URL Object
        a_resources         -> ResourceBundle Object

DESCRIPTION

    The function serves as the initialize function for this class. The function will get the game from the
    gamefile. It will then end the round, set the players and set the boolean value for isNewRound as true.
    Then it will call DisplayRoundInfo.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:38am 4/8/2020

*/
/**/
    @Override
    public void initialize(URL a_location, ResourceBundle a_resources)
    {
        Platform.runLater(()->
        {
            Game game = this.m_gameFile.GetGame();
            game.GetRound().EndRound();
            game.SetPlayers(game.GetRound().GetPlayers());
            game.GetRound().SetIsNewRound(true);

            // Display Round info
            DisplayRoundInfo();
        });
    }
/*void NewRoundScene::initialize(URL a_location, ResourceBundle a_resources);*/


/**/
/*
NewRoundScene::DisplayRoundInfo()

NAME

    NewRoundScene::DisplayRoundInfo - Displays information about round

SYNOPSIS

    void NewRoundScene::DisplayRoundInfo()

DESCRIPTION

    The function retrieves the round from m_gameFile and finds the last player. Then information about
    the round is added to a string and displays it to the user.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:45am 4/8/2020
*/
/**/
    private void DisplayRoundInfo()
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
        this.newRoundInfoPane.setFont(new Font(24));
        this.newRoundInfoPane.setStyle("-fx-font-weight: bold;-fx-alignment:center;");

        UpdateGameInfo();
    }
/*void NewRoundScene::DisplayRoundInfo();*/

/**/
/*
NewRoundScene::UpdateGameInfo()

NAME

    NewRoundScene::UpdateGameInfo - Updates Game information

SYNOPSIS

    void NewRoundScene::UpdateGameInfo();

DESCRIPTION

    The function retrieves information about the game such as who is going first and
    displays it to the user.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:59am 4/8/2020

*/
/**/
    private void UpdateGameInfo()
    {
        String gameString =  "";

        // Iterate through m_gameFile
        for (Player player: this.m_gameFile.GetGame().GetRound().GetPlayers())
        {
            if (player instanceof AI)
                gameString += "Computer " + player.GetPlayerId() + ": ";
            else
                gameString += "Player: ";

            gameString += player.GetNumCards();

            if (player.GetNumCards() == 1)
                gameString += " Card Left";
            else
                gameString += " Cards Left";

            gameString += "\t\t\t";
        }

        gameInfoLabel.setText(gameString);
        gameInfoLabel.setFont(new Font(20));
        gameInfoLabel.setStyle("-fx-font-weight: bold;");
    }
/*void NewRoundScene::UpdateGameInfo();*/

/**/
/*
NewRoundScene::ContinueButtonOnClick()

NAME

    NewRoundScene::ContinueButtonOnClick - Function for Continue Button

SYNOPSIS

    void NewRoundScene::ContinueButtonOnClick();

DESCRIPTION

    The function prepares and displays the BaseScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:11am 4/12/2020
*/
/**/
    public void ContinueButtonOnClick() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = loader.getController();
        controller.SetGameFile(this.m_gameFile);
        controller.SetStage(this.stage);

        this.stage.setScene(new Scene(parent, 1280, 720));
        this.stage.show();
    }
/*void NewRoundScene::ContinueButtonOnClick();*/

/**/
/*
NewRoundScene::SetGameFile(GameFile a_gameFile)

NAME

    NewRoundScene::SetGameFile - Sets the GameFile for the controller

SYNOPSIS

    void NewRoundScene::SetGameFile(GameFile a_gameFile);
        a_gameFile          -> GameFile object

DESCRIPTION

    The function sets m_gameFile as a_gameFile.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:22pm 4/11/2020
*/
/**/
    public void SetGameFile(GameFile a_gameFile) { this.m_gameFile = a_gameFile; }

/**/
/*
NewRoundScene::SetStage(Stage a_stage)

NAME

    NewRoundScene::SetStage - Sets the stage

SYNOPSIS

    void NewRoundScene::SetStage(Stage a_stage);
        a_stage         -> Stage object

DESCRIPTION

    The function sets m_stage as a_stage.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:21pm 4/13/2020

*/
/**/
    public void SetStage(Stage a_stage) { this.stage = a_stage; }
}
