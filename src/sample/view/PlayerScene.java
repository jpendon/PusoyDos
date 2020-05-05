package sample.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

public class PlayerScene implements Initializable {
    private GameFile m_gameFile;

    private Vector<Pair<ToggleButton, String>> m_cardPairs;

    private Stage m_stage;

    @FXML
    public Label gameInfoLabel;

    @FXML
    private FlowPane playerHandPane;

    @FXML
    private FlowPane tablePane;

    @FXML
    public Button playCardButton;


/**/
/*
PlayerScene::initialize(URL a_location, ResourceBundle a_resources)

NAME

    PlayerScene::initialize - initialize function for controller

SYNOPSIS

    void PlayerScene::initialize(URL a_location, ResourceBundle a_resources);
        a_location          -> URL Object
        a_resources         -> ResourceBundle Object

DESCRIPTION

    The function serves as the initialize function for this class. The function
    will update the view for the player to see.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:07pm 4/4/2020

*/
/**/
    @Override
    public void initialize(URL a_location, ResourceBundle a_resources)
    {
        Platform.runLater(()-> {
            Game game = this.m_gameFile.GetGame();
            Round round = game.GetRound();
            this.m_cardPairs = new Vector<Pair<ToggleButton, String>>();

            // Update View
            UpdatePlayerHand();
            UpdateTable(round);
            UpdateGameInfo();

        });
    }
/*void PlayerScene::initialize(URL a_location, ResourceBundle a_resources);*/

/**/
/*
PlayerScene::PlayCardButtonOnClick()

NAME

    PlayerScene::PlayCardButtonOnClick -  Function associated with play card button

SYNOPSIS

    void PlayerScene::PlayCardButtonOnClick();

DESCRIPTION

    The function gets the player from the round and iterates through the playerHandPane's children
    Each node is casted as a ToggleButton and calls isSelected to see if it has been chosen
    by the player. If it is then the function calls FindPair to get the card's name and is added to
    a vector for selected cards.

    When the cards are found, the player calls the function CheckCardConditions and checks to see if it
    is a valid play. If it isn't then DisplayInvalidAction is called. Otherwise, cards on the table are replaced and
    removed from the player's hand. Lastly, ReturnToBaseScene is called.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:11pm 4/4/2020
*/
/**/
    public void PlayCardButtonOnClick() throws IOException
    {
        Game game = this.m_gameFile.GetGame();
        Round round = game.GetRound();
        Player player = round.GetPlayers().elementAt(round.GetCurrentPlayer());

        Vector<Card> selectedCards = new Vector<>();

        for (Node node: playerHandPane.getChildren())
        {
            ToggleButton radioButton = (ToggleButton) node;

            if (radioButton.isSelected())
            {
                String cardName = FindPair(radioButton);
                selectedCards.add(player.GetCard(cardName));
            }
        }

        // Check if Valid Action
        if (!player.CheckCardConditions(selectedCards, round.GetTableCards()) || selectedCards.size() == 0)
        {
            DisplayInvalidAction(selectedCards);
            return;
        }
        // Replace cards on table
        round.ReplaceTableCards(selectedCards);

        // Remove Cards from hand
        player.RemoveMultipleCardsFromHand(selectedCards);

        ReturnToBaseScene();
    }
/*void PlayerScene::PlayCardButtonOnClick();*/

/**/
/*
PlayerScene::FindPair(ToggleButton a_toggleButton)

NAME

     PlayerScene::FindPair - Finds the Pair

SYNOPSIS

    String PlayerScene::FindPair(ToggleButton a_toggleButton);
        a_toggleButton          -> ToggleButton object

DESCRIPTION

    The function iterates through all of the pairs in m_cardPairs and checks to see if the key
    matches with a_toggleButton. If it does then the value is returned.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:14pm 4/4/2020

*/
/**/
    private String FindPair(ToggleButton a_toggleButton)
    {
        for (Pair<ToggleButton, String> pair : m_cardPairs)
        {
            if (pair.getKey() != a_toggleButton)
                continue;
            return pair.getValue();
        }

        return null;
    }
/*String PlayerScene::FindPair(ToggleButton a_toggleButton);*/

/**/
/*
PlayerScene::PassButtonOnClick()

NAME

    PlayerScene::PassButtonOnClick - Function associated with pass button

SYNOPSIS

    void PlayerScene::PassButtonOnClick();

DESCRIPTION

    The function gets the round from the table. If the table is empty then the function returns. If not
    then the function sets the current player's pass value to true and reduces the player counter by one.
    Then ReturnToBaseScene is called.

RETURNS

AUTHOR

    Jay Pendon

DATE

    12:33pm 4/4/2020

*/
/**/
    public void PassButtonOnClick() throws IOException
    {
        Round round = m_gameFile.GetGame().GetRound();

        if (round.GetTableCards().size() == 0)
            return;

        round.GetPlayers().elementAt(round.GetCurrentPlayer()).SetHasPassed(true);
        round.SetPlayerCounter(round.GetPlayerCounter() - 1);

        ReturnToBaseScene();
    }
/*void PlayerScene::PassButtonOnClick();*/

/**/
/*
PlayerScene::UpdatePlayerHand()

NAME

    PlayerScene::UpdatePlayerHand - Updates the player's hand for pane
SYNOPSIS

    void PlayerScene::UpdatePlayerHand();

DESCRIPTION

    The function finds the human player and iterates through the player's hand. Then the imageview for
    each card is created along with a pane. For each card a togglebutton is created and added to a button list.
    Then a pair is created with the key being the button and the value being a card's name. It is then added
    to a vector. When all buttons are created they are added to the pane.

RETURNS

AUTHOR

    Jay Pendon

DATE

    2:01pm 4/4/2020

*/
/**/
    private void UpdatePlayerHand()
    {
        Player player = this.m_gameFile.GetGame().FindHuman();
        ArrayList<ToggleButton> buttonList = new ArrayList<>();

        for (Card card: player.GetHand())
        {
            ImageView iv = CreateImageView(card);
            StackPane pane = new StackPane(iv);
            ToggleButton toggleButton = new ToggleButton("", pane);

            // Change the toggle Highlight
            toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue)
                    toggleButton.setStyle("-fx-background-color: #fff891;");
                else
                    toggleButton.setStyle(null);
            });


            buttonList.add(toggleButton);
            Pair<ToggleButton, String> pair = new Pair<ToggleButton, String>(toggleButton, card.GetCardName());
            m_cardPairs.add(pair);

        }

        playerHandPane.getChildren().clear();
        playerHandPane.setVgap(10);
        playerHandPane.setHgap(10);
        playerHandPane.getChildren().addAll(buttonList);

    }
/*void PlayerScene::UpdatePlayerHand();*/

/**/
/*
PlayerScene::ReturnToBaseScene()

NAME

    PlayerScene::ReturnToBaseScene - Returns to BaseScene

SYNOPSIS

    void PlayerScene::ReturnToBaseScene();

DESCRIPTION

    The function prepares and displays the BaseScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    10:44am 4/14/2020

*/
/**/
    private void ReturnToBaseScene() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = loader.getController();
        controller.SetGameFile(this.m_gameFile);
        controller.SetStage(this.m_stage);


        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void PlayerScene::ReturnToBaseScene();*/

/**/
/*
PlayerScene::UpdateTable(Round a_round)

NAME

    PlayerScene::UpdateTable - Updates the table pane

SYNOPSIS

    void PlayerScene::UpdateTable(Round a_round);
        a_round         -> Round object

DESCRIPTION

    The function iterates through the cards on the table and creates an imageview and pane. Then it
    is added to the table pane as its children.

RETURNS

AUTHOR

    Jay Pendon

DATE

    2:24pm 4/4/2020

*/
/**/
    private void UpdateTable(Round a_round)
    {
        ArrayList<StackPane> paneList = new ArrayList<>();

        for (Card card: a_round.GetTableCards())
        {
            ImageView iv = CreateImageView(card);
            StackPane pane = new StackPane(iv);
            pane.setStyle("-fx-background-color: white;");

            paneList.add(pane);
        }

        tablePane.getChildren().clear();
        tablePane.setHgap(10);
        tablePane.getChildren().addAll(paneList);
    }
/*void PlayerScene::UpdateTable(Round a_round);*/

/**/
/*
PlayerScene::CreateImageView(Card a_card)

NAME

    PlayerScene::CreateImageView - Creates an ImageView

SYNOPSIS

    ImageView PlayerScene::CreateImageView(Card a_card);
        a_card          -> Card object

DESCRIPTION

    The function finds the path to the card's image and creates an imageview using an image object. Then it
    is returned.

RETURNS

    Returns an ImageView object of the card.

AUTHOR

    Jay Pendon

DATE

    2:33pm 4/4/2020

*/
/**/
    private ImageView CreateImageView(Card a_card)
    {
        String url = "resources/" + a_card.GetCardName().toLowerCase() + ".png";

        Image image = new Image(new File(url).toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(120);
        iv.setFitWidth(90);

        return iv;
    }
/*ImageView PlayerScene::CreateImageView(Card card);*/

/**/
/*
PlayerScene::UpdateGameInfo()

NAME

    PlayerScene::UpdateGameInfo()

SYNOPSIS

    void PlayerScene::UpdateGameInfo();

DESCRIPTION

    The function creates a string using information about the game and displays it
    to the user.

RETURNS

AUTHOR

    Jay Pendon

DATE

    2:39pm 4/4/2020

*/
/**/
    private void UpdateGameInfo()
    {
        String gameString =  "";

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
/*void PlayerScene::UpdateGameInfo();*/

/**/
/*
PlayerScene::DisplayInvalidAction(Vector<Card> a_cards)

NAME

    PlayerScene::DisplayInvalidAction - Displays InvalidActionScene

SYNOPSIS

    void PlayerScene::DisplayInvalidAction(Vector<Card> a_cards);
        a_cards         -> Cards selected by the user

DESCRIPTION

    The function prepares and display's the InvalidActionScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:59pm 4/4/2020

*/
/**/
    private void DisplayInvalidAction(Vector<Card> a_cards) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("InvalidActionScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        InvalidActionScene controller = loader.getController();
        controller.SetGameFile(this.m_gameFile);
        controller.SetCardsSelected(a_cards);
        controller.SetStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void PlayerScene::DisplayInvalidAction(Vector<Card> a_cards);*/

/**/
/*
PlayerScene::SetGameFile(GameFile a_gameFile)

NAME

    PlayerScene::SetGameFile - Sets the GameFile for the controller

SYNOPSIS

    void PlayerScene::SetGameFile(GameFile a_gameFile);
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
PlayerScene::SetStage(Stage a_stage)

NAME

    PlayerScene::SetStage - Sets the stage

SYNOPSIS

    void PlayerScene::SetStage(Stage a_stage);
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
    public void SetStage(Stage a_stage) { this.m_stage = a_stage;}
}
