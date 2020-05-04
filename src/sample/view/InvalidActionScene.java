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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            Game game = this.m_gameFile.GetGame();
            Round round = game.GetRound();
            Human player = round.GetHumanPlayer();

            invalidTextPane.setText(player.GetInvalidActionString(this.cardsSelected, round.GetTableCards()));
            invalidTextPane.setStyle("-fx-font-weight: bold;-fx-alignment: center;");
            invalidTextPane.setFont(new Font(20));

        });
    }


/**/
/*
InvalidActionScene::ContinueButtonOnAction()

NAME

    InvalidActionScene::ContinueButtonOnAction - Function for continue button

SYNOPSIS

    void InvalidActionScene:ContinueButtonOnAction();

DESCRIPTION

    The function sets up and displays the PlayerScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:54am 4/10/2020
*/
/**/
    public void ContinueButtonOnAction() throws IOException
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
/*void InvalidActionScene::ContinueButtonOnAction();*/

/**/
/*
InvalidActionScene::SetGameFile(GameFile a_gameFile)

NAME

    InvalidActionScene::SetGameFile - Sets the GameFile for the controller

SYNOPSIS

    void InvalidActionScene::SetGameFile(GameFile a_gameFile);
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
InvalidActionScene::SetCardsSelected(Vector<Card> a_cardsSelected)

NAME

    InvalidActionScene::SetCardsSelected - Sets the cards selected

SYNOPSIS

    void InvalidActionScene::SetCardsSelected(Vector<Card> a_cardsSelected);
        a_cardsSelected         -> Vector of Cards

DESCRIPTION

    The function sets m_cardsSelected with a_cardsSelected.

RETURNS

AUTHOR

    Jay Pendon

DATE


*/
/**/
    public void SetCardsSelected(Vector<Card> a_cardsSelected) { this.cardsSelected = a_cardsSelected; }

/**/
/*
InvalidActionScene::SetStage(Stage a_stage)

NAME

    InvalidActionScene::SetStage - Sets the stage

SYNOPSIS

    void InvalidActionScene::SetStage(Stage a_stage);
        a_stage         -> Stage object

DESCRIPTION

    The function sets m_stage as a_stage.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:41pm 4/11/2020

*/
/**/
    public void SetStage(Stage a_stage) {this.stage = a_stage; }

}
