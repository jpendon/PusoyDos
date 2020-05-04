package sample.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.Game;
import sample.model.GameFile;
import sample.model.Round;
import sample.model.Serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LoadGameScene implements Initializable {
    private Vector<GameFile> gameFiles;
    private GameFile gameFile;
    private Stage stage;
    private int m_fileNum;

    @FXML
    private Button loadGameOneButton;
    @FXML
    private Button loadGameTwoButton;
    @FXML
    private Button loadGameThreeButton;
    @FXML
    private AnchorPane deletePane;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            try {
                loadGameFiles();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (gameFile != null)
            {
                updateGameFile();
                saveGameFiles();
            }

            deletePane.setVisible(false);
            updateButtons();
        });
    }

    void updateGameFile()
    {
        GameFile prevGameFile = this.gameFiles.elementAt(gameFile.GetIndex());

        prevGameFile.SetPlayerName(gameFile.GetPlayerName());
        prevGameFile.SetIsDefaultFile(false);
        prevGameFile.SetNumGamesPlayed(gameFile.GetNumGamesPlayed());
        prevGameFile.SetNumWins(gameFile.GetNumWins());
        prevGameFile.SetNumLosses(gameFile.GetNumLosses());
    }

    void updateButtons()
    {
        updateButtonInfo(this.loadGameOneButton, 0);
        updateButtonInfo(this.loadGameTwoButton, 1);
        updateButtonInfo(this.loadGameThreeButton, 2);
    }

    private void updateButtonInfo(Button button, int index)
    {
        GameFile gameFile = this.gameFiles.elementAt(index);

        String buttonText = "";
        buttonText += gameFile.GetPlayerName();

        if (!gameFile.GetIsDefaultFile())
        {
            buttonText += "\nTotal Games Played: " + gameFile.GetNumGamesPlayed();
            buttonText += "\nWins: " + gameFile.GetNumWins();
            buttonText += "\nLosses: " + gameFile.GetNumLosses();
        }
        button.setText(buttonText);
    }

    private void loadGameFiles() throws FileNotFoundException
    {
        this.gameFiles = new Vector<GameFile>();

        Path path = Paths.get("");
        String filePath =  path.toAbsolutePath().toString()+ "\\saves\\GameFiles.txt";

        for (int i = 0; i < 3; i++)
            this.gameFiles.add(new GameFile("File " + (i + 1), i));


        // Check if Games Files Exists
        if (!new File(filePath).exists())
        {
            saveGameFiles();
            return;
        }

        // Load Game Files
        int gameFileCounter = 0;
        String line = "";
        Scanner in = new Scanner(new File(filePath), "UTF-8");
        while((in.hasNext()))
        {
            line = in.nextLine();
            line = line.trim();

            String []splitLine = line.split(":");
            String token = splitLine[0];

            GameFile currentGameFile = this.gameFiles.elementAt(gameFileCounter);

            if (token.equals("File Name"))
                currentGameFile.SetPlayerName(splitLine[1].trim());
            if (token.equals("Total Games Played"))
                currentGameFile.SetNumGamesPlayed(Integer.parseInt(splitLine[1].trim()));
            if (token.equals("Number of Wins"))
                currentGameFile.SetNumWins(Integer.parseInt(splitLine[1].trim()));
            if(token.equals("Number of Losses"))
                currentGameFile.SetNumLosses(Integer.parseInt(splitLine[1].trim()));

            if (token.equals("Default"))
            {
                currentGameFile.SetIsDefaultFile(Boolean.parseBoolean(splitLine[1].trim()));
                currentGameFile.SetIndex(gameFileCounter);
                gameFileCounter++;
            }

        }
    }

    public void saveGameFiles()
    {
        Path path = Paths.get("");
        String filePath =  path.toAbsolutePath().toString()+ "\\saves\\GameFiles.txt";

        File file = new File(filePath);
        String saveFilesString = "Game Files";

        // Get SaveFilesString
        for (GameFile gameFile: this.gameFiles)
        {
            saveFilesString += "\nFile Name: " + gameFile.GetPlayerName();
            saveFilesString += "\nTotal Games Played: " + gameFile.GetNumGamesPlayed();
            saveFilesString += "\nNumber of Wins: " + gameFile.GetNumWins();
            saveFilesString += "\nNumber of Losses: " + gameFile.GetNumLosses();
            saveFilesString += "\nDefault: " + gameFile.GetIsDefaultFile();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(saveFilesString.getBytes());
            fileOutputStream.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }


    public void LoadButtonOnClick(ActionEvent event) throws IOException{ GetButton(event, true); }

    public void DeleteButtonOnClick(ActionEvent event) throws IOException{ GetButton(event, false);}

    private void GetButton(ActionEvent a_actionEvent, Boolean a_isLoading) throws IOException
    {
        Object node = a_actionEvent.getSource();
        Button button = (Button)node;
        String buttonId = button.getId();

        if (a_isLoading)
            LoadGame(buttonId);

        DeleteButtonOnClick(buttonId);
    }

    private void LoadGame(String a_buttonId) throws IOException
    {
        if (a_buttonId.equals("loadGameOneButton"))
            PrepareLoad(this.gameFiles.elementAt(0));

        if (a_buttonId.equals("loadGameTwoButton"))
            PrepareLoad(this.gameFiles.elementAt(1));

        PrepareLoad(this.gameFiles.elementAt(2));
    }

    private void DeleteButtonOnClick(String a_buttonId)
    {
        if (a_buttonId.equals("deleteGameOneButton"))
            DeleteGameFile(0);

        if (a_buttonId.equals("deleteGameTwoButton"))
            DeleteGameFile(1);

        DeleteGameFile(2);
    }

    public void PrepareLoad(GameFile currentGameFile) throws IOException {
        Path path = Paths.get("");
        String filePath =  path.toAbsolutePath().toString()+ "\\saves\\" + currentGameFile.GetPlayerName() + "_Game.txt";

        // Check if File is a default
        if (currentGameFile.GetIsDefaultFile())
        {
            DisplayChoosePlayerName(currentGameFile);
            return;
        }

        // Check if the file has a game to be continued
        if (!new File(filePath).exists())
        {
            DisplayChoosePlayersScene(currentGameFile);
            return;
        }

        // Resume Game
        Game game = ResumeGame(filePath);
        currentGameFile.SetGame(game);
        DisplayBaseScene(currentGameFile);
    }

    private Game ResumeGame(String filePath) throws IOException {
        Serialization serialization = new Serialization();

        Round round = serialization.ResumeGame(filePath);
        round.SetIsNewRound(false);
        Game game = new Game(round.GetPlayers());
        game.SetRound(round);
        game.SetIsResumed(true);

        return game;
    }

    private void DisplayBaseScene(GameFile gameFile) throws IOException
    {
        //Begin game
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = fxmlLoader.getController();
        controller.SetGameFile(gameFile);
        controller.SetStage(this.stage);

        this.stage.setScene(new Scene(parent, 1280, 720));
        this.stage.show();
    }

    private void DisplayChoosePlayerName(GameFile gameFile) throws IOException
    {
        Vector<String> fileNames = new Vector<String>();

        for (GameFile file : this.gameFiles)
            fileNames.add(file.GetPlayerName());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EnterPlayerNameScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        EnterPlayerNameScene controller = fxmlLoader.getController();
        controller.SetFileNames(fileNames);
        controller.SetGameFile(gameFile);
        controller.SetStage(this.stage);

        this.stage.setScene(new Scene(parent, 1280, 720));
        this.stage.show();
    }

    public void DeleteGameFile(int a_fileNum)
    {
        m_fileNum = a_fileNum;
        deletePane.setVisible(true);
    }

    @FXML
    private void DeleteFile()
    {
        String fileName = this.gameFiles.elementAt(m_fileNum).GetPlayerName();
        this.gameFiles.remove(m_fileNum);
        this.gameFiles.insertElementAt(new GameFile("File " + (m_fileNum + 1), m_fileNum), m_fileNum);

        Path path = Paths.get("");
        String filePath =  path.toAbsolutePath().toString()+ "\\saves\\" + fileName + "_Game.txt";

        // Delete File
        File file = new File(filePath);

        if(file.delete())
            System.out.println("File deleted successfully");
        else
            System.out.println("Failed to delete the file");

        updateButtons();
        saveGameFiles();
        HidePane();
    }

    public void DisplayChoosePlayersScene(GameFile chosenGameFile) throws IOException
    {
        FXMLLoader loadGameScene = new FXMLLoader(getClass().getResource("ChooseAmountOfPlayersScene.fxml"));
        Parent parent = loadGameScene.load();
        parent.setStyle("-fx-background-color: #009900;");

        ChooseAmountOfPlayersScene controller = loadGameScene.getController();
        controller.SetStage(this.stage);
        controller.SetGameFile(chosenGameFile);

        this.stage.setScene(new Scene(parent, 1280, 720));
        this.stage.show();
    }

    @FXML
    public void HidePane()
    {
        deletePane.setVisible(false);
    }

    public void SetStage(Stage stage) { this.stage = stage; }

    public void SetGameFile(GameFile gameFile) { this.gameFile = gameFile; }



}
