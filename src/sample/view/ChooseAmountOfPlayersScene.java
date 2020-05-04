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
import sample.model.Game;
import sample.model.GameFile;
import sample.model.Round;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseAmountOfPlayersScene implements Initializable {
    private GameFile m_gameFile;
    private Stage m_stage;

    @FXML
    private Label choosePlayerLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            choosePlayerLabel.setText("Choose the amount of players");

        });
    }

/**/
/*
ChooseAmountOfPlayers::ThreePlayerButtonOnClick(ActionEvent actionEvent)

NAME

    ChooseAmountOfPlayers::ThreePlayerButtonOnClick - Function for button
SYNOPSIS

     void ChooseAmountOfPlayers::ThreePlayerButtonOnClick(ActionEvent actionEvent);
        actionEvent         -> ActionEvent object

DESCRIPTION

    The function passes 3 to DisplayBaseScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:19pm 4/2/2020

*/
/**/
    public void ThreePlayerButtonOnClick(ActionEvent actionEvent) throws IOException { DisplayBaseScene(3); }

/**/
/*
ChooseAmountOfPlayers::FourPlayerButtonOnClick(ActionEvent actionEvent)

NAME

    ChooseAmountOfPlayers::FourPlayerButtonOnClick - Function for button
SYNOPSIS

     void ChooseAmountOfPlayers::FourPlayerButtonOnClick(ActionEvent actionEvent);
        actionEvent         -> ActionEvent object

DESCRIPTION

    The function passes 4 to DisplayBaseScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:19pm 4/2/2020

*/
/**/
    public void FourPlayerButtonOnClick(ActionEvent actionEvent) throws IOException { DisplayBaseScene(4); }

/**/
/*
ChooseAmountOfPlayersScene::DisplayBaseScene(int a_numPlayers)

NAME


SYNOPSIS

    void ChooseAmountOfPlayersScene::DisplayBaseScene(int a_numPlayers)
        a_numPlayers        -> Integer representing the number of players

DESCRIPTION

    The function uses a_numPlayers to create a new game and pass it to the next scene.
    Then the BaseScene is displayed.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:15pm 4/2/2020
*/
/**/
    private void DisplayBaseScene(int a_numPlayers) throws IOException
    {
        Game game = new Game(a_numPlayers);
        game.SetRound(new Round(game.GetPlayers()));

        this.m_gameFile.SetGame(game);

        //Begin game
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = fxmlLoader.getController();
        controller.SetGameFile(this.m_gameFile);
        controller.SetStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void ChooseAmountOfPlayersScene::DisplayBaseScene(int a_numPlayers);*/

/**/
/*
ChooseAmountOfPlayersScene::SetGameFile(GameFile a_gameFile)

NAME

    ChooseAmountOfPlayersScene::SetGameFile - Sets the GameFile for the controller

SYNOPSIS

    void ChooseAmountOfPlayersScene::SetGameFile(GameFile a_gameFile);
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
ChooseAmountOfPlayersScene::SetStage(Stage a_stage)

NAME

    ChooseAmountOfPlayersScene::SetStage - Sets the stage

SYNOPSIS

    void ChooseAmountOfPlayersScene::SetStage(Stage a_stage);
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
