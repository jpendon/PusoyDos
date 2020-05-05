package sample.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

public class ComputerActionScene implements Initializable {

    private GameFile m_gameFile;
    private String m_computerAction;
    private Vector<Card> m_cardsUsed;
    private Stage m_stage;

    @FXML
    public Label gameInfoLabel;

    @FXML
    private FlowPane cardsUsedPane;

    @FXML
    private Label computerActionPane;

    @FXML
    public Button continueButton;


/**/
/*
ComputerActionScene::initialize(URL a_location, ResourceBundle a_resources)

NAME

    ComputerActionScene::initialize - initialize function for controller

SYNOPSIS

    void ComputerActionScene::initialize(URL a_location, ResourceBundle a_resources);
        a_location          -> URL Object
        a_resources         -> ResourceBundle Object

DESCRIPTION

    The function serves as the initialize function for this class. The function will iterate
    through m_cardsUsed and display it to the user using imageviews and StackPanes. Then the
    computer's action will be displayed to the user.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:00am 4/3/2020

*/
/**/
    @Override
    public void initialize(URL a_location, ResourceBundle a_resources)
    {
        Platform.runLater(()->
        {
            // Update cardsUsedPane
            ArrayList<StackPane> paneList = new ArrayList<>();

            // Iterate through the cards chosen by the AI
            for (Card card: this.m_cardsUsed)
            {
                String url = "resources/" + card.GetCardName().toLowerCase() + ".png";
                Image image = new Image(new File(url).toURI().toString());

                ImageView iv = new ImageView(image);
                iv.setFitHeight(250);
                iv.setFitWidth(170);

                StackPane pane = new StackPane(iv);
                pane.setStyle("-fx-background-color: white; -fx-font-weight: bold;");


                paneList.add(pane);
            }

            this.cardsUsedPane.getChildren().clear();
            this.cardsUsedPane.setHgap(10);
            this.cardsUsedPane.getChildren().addAll(paneList);

            // Update computerActionPane
            this.computerActionPane.setText(m_computerAction);
            this.computerActionPane.setFont(new Font(20));
            this.computerActionPane.setStyle("-fx-font-weight: bold;fx-alignment: center;");

            UpdateGameInfo();
        });
    }
/*void ComputerActionScene::initialize(URL a_location, ResourceBundle a_resources)*/

/**/
/*
ComputerActionScene::::UpdateGameInfo()

NAME

    ComputerActionScene::::UpdateGameInfo()

SYNOPSIS

    void ComputerActionScene::::UpdateGameInfo();

DESCRIPTION

    The function creates a string using information about the game and displays it
    to the user.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:55am 5/3/2020

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
/*void ComputerActionScene::UpdateGameInfo();*/


/**/
/*
ComputerActionScene::ContinueButtonOnClick(ActionEvent event)

NAME

    ComputerActionScene::ContinueButtonOnClick - Function for continue button

SYNOPSIS

    void ComputerActionScene::ContinueButtonOnClick(ActionEvent event);
        event           -> ActionEvent object

DESCRIPTION

    The function sets up and displays the BaseScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:11am 4/3/2020

*/
/**/
    public void ContinueButtonOnClick(ActionEvent event) throws IOException
    {
        // Load Game Scene to Continue the game
        FXMLLoader loader= new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = loader.getController();
        controller.SetGameFile(this.m_gameFile);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent, 1280, 720));
        controller.SetStage(window);
        window.show();
    }
/*void ComputerActionScene::ContinueButtonOnClick(ActionEvent event);*/

/**/
/*
ComputerActionScene::SetGameFile(GameFile a_gameFile)

NAME

    ComputerActionScene::SetGameFile - Sets the GameFile for the controller

SYNOPSIS

    void ComputerActionScene::SetGameFile(GameFile a_gameFile);
        a_gameFile          -> GameFile object

DESCRIPTION

    The function sets m_gameFile as a_gameFile.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:22pm 4/11/2020
*/
/**/
    public void SetGameFile(GameFile gameFile) { this.m_gameFile = gameFile; }

/**/
/*
ComputerActionScene::SetComputerAction(String a_computerAction)

NAME

    ComputerActionScene::SetComputerAction - Sets m_computerAction

SYNOPSIS

    void SetComputerAction(String computerAction);
        a_computerAction        -> String containing the computer's action

DESCRIPTION

    The function sets m_computerAction as a_computerAction.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:23pm 4/11/2020

*/
/**/
    public void SetComputerAction(String a_computerAction) { this.m_computerAction = a_computerAction; }

/**/
/*
ComputerActionScene::SetCardsUsed(Vector<Card> a_cardsUsed)

NAME

    ComputerActionScene::SetCardsUsed - sets m_cardsUsed

SYNOPSIS

    void ComputerActionScene::SetCardsUsed(Vector<Card> a_cardsUsed);

DESCRIPTION

    The function sets m_CardsUsed as a_cardsUsed.
RETURNS

AUTHOR

    Jay Pendon

DATE


*/
/**/
    public void SetCardsUsed(Vector<Card> a_cardsUsed) { this.m_cardsUsed = a_cardsUsed; }

/**/
/*
ComputerActionScene::SetStage(Stage a_stage)

NAME

    ComputerActionScene::SetStage - Sets the stage

SYNOPSIS

    void ComputerActionScene::SetStage(Stage a_stage);
        a_stage         -> Stage object

DESCRIPTION

    The function sets m_stage as a_stage.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:22pm 4/11/2020

*/
/**/
    public void SetStage(Stage stage) { this.m_stage = stage;}


}
