package sample.scene;

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


    public void threePlayerButtonOnClick(ActionEvent actionEvent) throws IOException { displayBaseScene(3); }

    public void fourPlayerButtonOnClick(ActionEvent actionEvent) throws IOException { displayBaseScene(4); }

    public  void displayBaseScene(int a_numPlayers) throws IOException
    {
        Game game = new Game(a_numPlayers);
        game.SetRound(new Round(game.GetPlayers()));

        this.m_gameFile.SetGame(game);

        //Begin game
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = fxmlLoader.getController();
        controller.setGameFile(this.m_gameFile);
        controller.setStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }

    public void setGameFile(GameFile a_gameFile) { this.m_gameFile = a_gameFile; }

    public void setStage(Stage a_stage) { this.m_stage = a_stage; }
}
