package sample.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.AI;
import sample.model.Game;
import sample.model.GameFile;
import sample.model.Player;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class GameEndScene implements Initializable {
    private GameFile m_gameFile;
    private Stage m_stage;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->{
            Game game = m_gameFile.GetGame();

            Path path = Paths.get("");
            String filePath =  path.toAbsolutePath().toString()+ "\\saves\\" + this.m_gameFile.GetPlayerName() + "_Game.txt";

            // Delete File
            File file = new File(filePath);
            file.delete();

            // Update Win/Loss Rate
            Player winner = game.GetWinner();

            this.m_gameFile.SetNumGamesPlayed(this.m_gameFile.GetNumGamesPlayed() + 1);

            if (winner instanceof AI)
            {
                this.m_gameFile.SetNumLosses(this.m_gameFile.GetNumLosses() + 1);
                return;
            }

            this.m_gameFile.SetNumWins(this.m_gameFile.GetNumWins() + 1);
        });


    }


    public void continueButtonOnClick(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loadGameScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        LoadGameScene controller = fxmlLoader.getController();
        controller.setGameFile(this.m_gameFile);
        controller.setStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }

    public void setGameFile(GameFile gameFile) { this.m_gameFile = gameFile; }

    public void setStage(Stage stage) { this.m_stage = stage;}

}
