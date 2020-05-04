package sample.view;

import javafx.application.Platform;
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

/**/
/*
GameEndScene::ContinueButtonOnClick()

NAME

    GameEndScene::ContinueButtonOnClick - Function for continue button

SYNOPSIS

    void GameEndScene::ContinueButtonOnClick();

DESCRIPTION

    The function prepares and displays loadGameScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:32am 4/5/2020
*/
/**/
    public void ContinueButtonOnClick() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loadGameScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        LoadGameScene controller = fxmlLoader.getController();
        controller.SetGameFile(this.m_gameFile);
        controller.SetStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void GameEndScene::ContinueButtonOnClick(); */

/**/
/*
GameEndScene::SetGameFile(GameFile a_gameFile)

NAME

    GameEndScene::SetGameFile - Sets gameFile
SYNOPSIS

    void GameEndScene::SetGameFile(GameFile a_gameFile);
        a_gameFile          -> GameFile object

DESCRIPTION

    The function sets m_gameFile as a_gameFile.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:31pm 4/11/2020
*/
/**/
    public void SetGameFile(GameFile a_gameFile) { this.m_gameFile = a_gameFile; }

/**/
/*
GameEndScene::SetStage(Stage a_stage)

NAME

    GameEndScene::SetStage - Sets the stage

SYNOPSIS

    void GameEndScene::SetStage(Stage a_stage);
        a_stage         -> Stage object

DESCRIPTION

    The function sets m_stage as a_stage.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:32pm 4/11/2020

*/
/**/
    public void SetStage(Stage a_stage) { this.m_stage = a_stage;}

}
