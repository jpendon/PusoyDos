package sample.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
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

    private Vector<Pair<ToggleButton, String>> cardButtons;

    private Stage stage;

    @FXML
    public Label gameInfoPane;

    @FXML
    private FlowPane playerHandPane;

    @FXML
    private FlowPane tablePane;

    @FXML
    public Button playCardButton;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()-> {
            Game game = this.m_gameFile.GetGame();
            Round round = game.GetRound();
            this.cardButtons = new Vector<Pair<ToggleButton, String>>();

            // Update View
            updatePlayerHand();
            updateTable(round);
            updateGameInfo();

        });
    }


    public void playCardButtonOnClick(ActionEvent event) throws IOException {
        Game game = this.m_gameFile.GetGame();
        Round round = game.GetRound();
        Player player = round.GetPlayers().elementAt(round.GetCurrentPlayer());

        Vector<Card> selectedCards = new Vector<>();

        for (Node node: playerHandPane.getChildren())
        {
            ToggleButton radioButton = (ToggleButton) node;

            if (radioButton.isSelected())
            {
                String cardName = findPair(radioButton);
                selectedCards.add(player.GetCard(cardName));
            }
        }

        // Check if Valid Action
        if (!player.CheckCardConditions(selectedCards, round.GetTableCards()))
        {
            displayInvalidAction(selectedCards);

            return;
        }
        // Replace cards on table
        round.ReplaceTableCards(selectedCards);

        // Remove Cards from hand
        player.RemoveMultipleCardsFromHand(selectedCards);

        returnToBaseScene();
    }

    private String findPair(ToggleButton toggleButton)
    {
        for (Pair<ToggleButton, String> pair : cardButtons)
        {
            if (pair.getKey() != toggleButton)
                continue;
            return pair.getValue();
        }

        return null;
    }

    public void passButtonOnClick() throws IOException {
        Game game = this.m_gameFile.GetGame();
        Round round = game.GetRound();

        round.GetPlayers().elementAt(round.GetCurrentPlayer()).SetHasPassed(true);
        round.SetPlayerCounter(round.GetPlayerCounter() - 1);

        returnToBaseScene();
    }

    private void updatePlayerHand()
    {
        Player player = this.m_gameFile.GetGame().FindHuman();
        ArrayList<ToggleButton> buttonList = new ArrayList<>();

        for (Card card: player.GetHand())
        {
            ImageView iv = createImageView(card);
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
            cardButtons.add(pair);

        }

        playerHandPane.getChildren().clear();
        playerHandPane.setVgap(10);
        playerHandPane.setHgap(10);
        playerHandPane.getChildren().addAll(buttonList);

    }

    private void returnToBaseScene() throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = loader.getController();
        controller.setGameFile(this.m_gameFile);
        controller.setStage(this.stage);


        this.stage.setScene(new Scene(parent, 1280, 720));
        this.stage.show();
    }

    private void updateTable(Round round)
    {
        ArrayList<StackPane> paneList = new ArrayList<>();

        for (Card card: round.GetTableCards())
        {
            ImageView iv = createImageView(card);
            StackPane pane = new StackPane(iv);
            pane.setStyle("-fx-background-color: white;");

            paneList.add(pane);
        }

        tablePane.getChildren().clear();
        tablePane.setHgap(10);
        tablePane.getChildren().addAll(paneList);
    }

    private ImageView createImageView(Card card)
    {
        String url = "resources/" + card.GetCardName().toLowerCase() + ".png";

        Image image = new Image(new File(url).toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(120);
        iv.setFitWidth(90);

        return iv;
    }

    private void updateGameInfo()
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

        gameInfoPane.setText(gameString);
        gameInfoPane.setFont(new Font(20));
    }


    private void displayInvalidAction(Vector<Card> cards) throws IOException
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("InvalidActionScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        InvalidActionScene controller = loader.getController();
        controller.setGameFile(this.m_gameFile);
        controller.setCardsSelected(cards);
        controller.setStage(this.stage);

        this.stage.setScene(new Scene(parent, 1280, 720));
        this.stage.show();
    }


    public void setGameFile(GameFile gameFile) { this.m_gameFile = gameFile; }

    public void setStage(Stage stage) { this.stage = stage;}
}
