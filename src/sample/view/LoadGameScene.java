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
    private Vector<GameFile> m_gameFiles;
    private GameFile m_gameFile;
    private Stage m_stage;
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
                LoadGameFiles();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (m_gameFile != null)
            {
                UpdateGameFile();
                SaveGameFiles();
            }

            deletePane.setVisible(false);
            UpdateButtons();
        });
    }

/**/
/*
LoadGameScene::UpdateGameFile()

NAME

    LoadGameScene::UpdateGameFile - Updates GameFiles

SYNOPSIS

    void LoadGameScene::UpdateGameFile();

DESCRIPTION

    The function updates a GameFile.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:12am 4/6/2020

*/
/**/
    private void UpdateGameFile()
    {
        GameFile prevGameFile = this.m_gameFiles.elementAt(m_gameFile.GetIndex());

        prevGameFile.SetPlayerName(m_gameFile.GetPlayerName());
        prevGameFile.SetIsDefaultFile(false);
        prevGameFile.SetNumGamesPlayed(m_gameFile.GetNumGamesPlayed());
        prevGameFile.SetNumWins(m_gameFile.GetNumWins());
        prevGameFile.SetNumLosses(m_gameFile.GetNumLosses());
    }
/*void LoadGameScene::UpdateGameFile();*/

/**/
/*
LoadGameScene::UpdateButtons()

NAME

    LoadGameScene::UpdateButtons

SYNOPSIS

    void LoadGameScene::UpdateButtons();

DESCRIPTION

    The function updates the buttons for the users to view.

RETURNS

AUTHOR

    Jay Pendon

DATE

        6:17am 4/6/2020

*/
/**/
    private void UpdateButtons()
    {
        UpdateButtonInfo(this.loadGameOneButton, 0);
        UpdateButtonInfo(this.loadGameTwoButton, 1);
        UpdateButtonInfo(this.loadGameThreeButton, 2);
    }
/*void LoadGameScene::UpdateButtons();*/

/**/
/*
LoadGameScene::UpdateButtonInfo(Button a_button, int a_index)

NAME

    LoadGameScene::UpdateButtonInfo - Updates Information on Button

SYNOPSIS

    void UpdateButtonInfo(Button a_button, int a_index);
        a_button            -> Button Object
        a_index             -> Integer of index

DESCRIPTION

    The function updates the information on the buttons. It gets the gamefile from gameFiles vector
    at the index a_index. Then using the getters from the class the string is constructed and displayed
    to the user.

RETURNS

AUTHOR

    Jay Pendon

DATE

        6:18am 4/6/2020

*/
/**/
    private void UpdateButtonInfo(Button a_button, int a_index)
    {
        GameFile gameFile = this.m_gameFiles.elementAt(a_index);

        String buttonText = "";
        buttonText += gameFile.GetPlayerName();

        if (!gameFile.GetIsDefaultFile())
        {
            buttonText += "\nTotal Games Played: " + gameFile.GetNumGamesPlayed();
            buttonText += "\nWins: " + gameFile.GetNumWins();
            buttonText += "\nLosses: " + gameFile.GetNumLosses();
        }
        a_button.setText(buttonText);
    }
/*void LoadGameScene::UpdateButtonInfo(Button a_button, int a_index);*/

/**/
/*
LoadGameScene::LoadGameFiles()

NAME

    LoadGameScene::LoadGameFiles - Loads the GameFiles

SYNOPSIS

    void LoadGameScene::LoadGameFiles();

DESCRIPTION

    The function gets the current location and finds the GameFiles.txt. If it exists then the text will
    be parsed and the GameFile object in m_gameFiles will be updated. If not then a new file will be created
    at that location.

RETURNS

AUTHOR

    Jay Pendon

DATE

        8:11am 4/6/2020

*/
/**/
    private void LoadGameFiles() throws FileNotFoundException
    {
        this.m_gameFiles = new Vector<GameFile>();

        Path path = Paths.get("");
        String filePath =  path.toAbsolutePath().toString()+ "\\saves\\GameFiles.txt";

        for (int i = 0; i < 3; i++)
            this.m_gameFiles.add(new GameFile("File " + (i + 1), i));


        // Check if Games Files Exists
        if (!new File(filePath).exists())
        {
            SaveGameFiles();
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

            GameFile currentGameFile = this.m_gameFiles.elementAt(gameFileCounter);

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
/*void LoadGameScene::LoadGameFiles();*/

/**/
/*
LoadGameScene::SaveGameFiles()

NAME

    LoadGameScene::SaveGameFiles - Saves the game files

SYNOPSIS

    void LoadGameScene::SaveGameFiles();

DESCRIPTION

    The function saves the GameFiles to using the current path and name it GameFiles.txt. Each GameFile from
    m_gameFiles will retrieve the important information and place them into the string. Then the string will
    be saved to a text file.

RETURNS

AUTHOR

    Jay Pendon

DATE

        8:34am 4/6/2020

*/
/**/
    private void SaveGameFiles()
    {
        Path path = Paths.get("");
        String filePath =  path.toAbsolutePath().toString() + "\\saves\\GameFiles.txt";

        File file = new File(filePath);
        String saveFilesString = "Game Files";

        // Get SaveFilesString
        for (GameFile gameFile: this.m_gameFiles)
        {
            saveFilesString += "\n\nFile Name: " + gameFile.GetPlayerName();
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
/*void LoadGameScene::SaveGameFiles()*/

/**/
/*
LoadGameScene::LoadButtonOnClick(ActionEvent a_event)

NAME

    LoadGameScene::LoadButtonOnClick - Function associated with loading buttons

SYNOPSIS

    void LoadGameScene::LoadButtonOnClick(ActionEvent a_event);
        a_event         -> ActionEvent object

DESCRIPTION

    The function calls GetButton and passes a_event and true to the function.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:12pm 5/2/2020

*/
/**/
    public void LoadButtonOnClick(ActionEvent a_event) throws IOException{ GetButton(a_event, true); }

/**/
/*
LoadGameScene::DeleteButtonOnClick(ActionEvent a_event)

NAME

    LoadGameScene::DeleteButtonOnClick - Function associated with loading buttons

SYNOPSIS

    void LoadGameScene::DeleteButtonOnClick(ActionEvent a_event);
        a_event         -> ActionEvent object

DESCRIPTION

    The function calls GetButton and passes a_event and false to the function.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:19pm 5/2/2020

*/
/**/
    public void DeleteButtonOnClick(ActionEvent a_event) throws IOException{ GetButton(a_event, false);}

/**/
/*
LoadGameScene::GetButton(ActionEvent a_actionEvent, Boolean a_isLoading)
NAME

    LoadGameScene::GetButton - Gets the button clicked

SYNOPSIS

    void LoadGameScene::GetButton(ActionEvent a_actionEvent, Boolean a_isLoading);
        a_actionEvent           -> ActionEvent object representing the button clicked
        a_isLoading             -> Boolean value

DESCRIPTION

    The function gets the button and its id. Then if a_isLoading is true, LoadGame is called. If not then
    DeleteButtonOnCLick is called.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:19pm 5/2/2020

*/
/**/
    private void GetButton(ActionEvent a_actionEvent, Boolean a_isLoading) throws IOException
    {
        Object node = a_actionEvent.getSource();
        Button button = (Button)node;
        String buttonId = button.getId();

        if (a_isLoading)
            LoadGame(buttonId);

        DeleteButtonOnClick(buttonId);
    }
/*void LoadGameScene::GetButton(ActionEvent a_actionEvent, Boolean a_isLoading);*/

/**/
/*
LoadGameScene::LoadGame(String a_buttonId)

NAME

    LoadGameScene::LoadGame - Loads the Game

SYNOPSIS

    void LoadGameScene::LoadGame(String a_buttonId);
        a_buttonId          -> String object

DESCRIPTION

    The function compares the a_buttonId and calls PrepareLoad using the associated gameFile.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:47pm 5/2/2020

*/
/**/
    private void LoadGame(String a_buttonId) throws IOException
    {
        if (a_buttonId.equals("loadGameOneButton"))
            PrepareLoad(this.m_gameFiles.elementAt(0));

        if (a_buttonId.equals("loadGameTwoButton"))
            PrepareLoad(this.m_gameFiles.elementAt(1));

        if (a_buttonId.equals("loadGameThreeButton"))
            PrepareLoad(this.m_gameFiles.elementAt(2));
    }
/*void LoadGameScene::LoadGame(String a_buttonId);*/

/**/
/*
LoadGameScene::DeleteButtonOnClick(String a_buttonId)

NAME

    LoadGameScene::DeleteButtonOnClick - Loads the Game

SYNOPSIS

    void LoadGameScene::DeleteButtonOnClick(String a_buttonId);
        a_buttonId          -> String object

DESCRIPTION

    The function compares the a_buttonId and calls DeleteGameFile using the associated gameFile's index.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:27pm 5/2/2020

*/
/**/
    private void DeleteButtonOnClick(String a_buttonId)
    {
        if (a_buttonId.equals("deleteGameOneButton"))
            DeleteGameFile(0);

        if (a_buttonId.equals("deleteGameTwoButton"))
            DeleteGameFile(1);

        if (a_buttonId.equals("deleteGameThreeButton"))
            DeleteGameFile(2);
    }

/**/
/*
LoadGameScene::PrepareLoad(GameFile a_currentGameFile)

NAME

    LoadGameScene::PrepareLoad - Prepares and Loads the game.

SYNOPSIS

     LoadGameScene::PrepareLoad(GameFile a_currentGameFile);
        a_currentGameFile       -> GameFile Object

DESCRIPTION

    The function finds the path to the save file in order to be read. It also checks if a_currentGameFile is a
    default file and if it is it calls DisplayChoosePlayerName. If it isn't it checks if the file exists
    using the filepath. If it doesn't exist then DisplayChoosePlayerScene is called. Otherwise the game is resumed and
    then passed to DisplayBaseScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    9:14am 4/6/2020

*/
/**/
    private void PrepareLoad(GameFile a_currentGameFile) throws IOException {
        Path path = Paths.get("");
        String filePath =  path.toAbsolutePath().toString()+ "\\saves\\" + a_currentGameFile.GetPlayerName() + "_Game.txt";

        // Check if File is a default
        if (a_currentGameFile.GetIsDefaultFile())
        {
            DisplayChoosePlayerName(a_currentGameFile);
            return;
        }

        // Check if the file has a game to be continued
        if (!new File(filePath).exists())
        {
            DisplayChoosePlayersScene(a_currentGameFile);
            return;
        }

        // Resume Game
        Game game = ResumeGame(filePath);
        a_currentGameFile.SetGame(game);
        DisplayBaseScene(a_currentGameFile);
    }
/*void LoadGameScene::PrepareLoad(GameFile a_currentGameFile);*/

/**/
/*
LoadGameScene::ResumeGame(String a_filePath)

NAME

     LoadGameScene::ResumeGame - Resumes the game

SYNOPSIS

    Game LoadGameScene::ResumeGame(String a_filePath);
        a_filePath          -> String representing the file path

DESCRIPTION

    The function resumes the game by passing the filepath to the serilization function and returning
    the game.

RETURNS

    Game object representing the game.

AUTHOR

    Jay Pendon

DATE

    9:56am 4/6/2020

*/
/**/
    private Game ResumeGame(String a_filePath) throws IOException {
        Serialization serialization = new Serialization();

        Round round = serialization.ResumeGame(a_filePath);
        round.SetIsNewRound(false);
        Game game = new Game(round.GetPlayers());
        game.SetRound(round);
        game.SetIsResumed(true);

        return game;
    }
/*Game LoadGameScene::ResumeGame(String a_filePath);*/

/**/
/*
LoadGameScene::DisplayBaseScene(GameFile a_gameFile)

NAME

     LoadGameScene::DisplayBaseScene - Displays and prepares the BaseScene

SYNOPSIS

    void LoadGameScene::DisplayBaseScene(GameFile a_gameFile);
        a_gameFile          -> GameFile object

DESCRIPTION

    The function prepares and displays the BaseScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    3:21pm 4/11/2020
*/
/**/
    private void DisplayBaseScene(GameFile a_gameFile) throws IOException
    {
        //Begin game
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = fxmlLoader.getController();
        controller.SetGameFile(a_gameFile);
        controller.SetStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void LoadGameScene::DisplayBaseScene(GameFile a_gameFile);*/

/**/
/*
LoadGameScene::DisplayChoosePlayerName(GameFile a_gameFile)

NAME

    LoadGameScene::DisplayChoosePlayerName - Displays EnterPlayerNameScene
SYNOPSIS

    void LoadGameScene::DisplayChoosePlayerName(GameFile a_gameFile);
        a_gameFile          -> GameFile object

DESCRIPTION

    The function iterates through m_gameFiles to get all of the file names. Then the function prepares
    and displays the EnterPlayerNameScene while passing the vector of file names.

RETURNS

AUTHOR

    Jay Pendon

DATE

    3:55pm 4/11/2020

*/
/**/
    private void DisplayChoosePlayerName(GameFile a_gameFile) throws IOException
    {
        Vector<String> fileNames = new Vector<String>();

        for (GameFile file : this.m_gameFiles)
            fileNames.add(file.GetPlayerName());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EnterPlayerNameScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        EnterPlayerNameScene controller = fxmlLoader.getController();
        controller.SetFileNames(fileNames);
        controller.SetGameFile(a_gameFile);
        controller.SetStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void LoadGameScene::DisplayChoosePlayerName(GameFile a_gameFile);*/

/**/
/*
LoadGameScene::DeleteGameFile(int a_fileNum)

NAME

    LoadGameScene::DeleteGameFile - Prepares for deletion

SYNOPSIS

    void LoadGameScene::DeleteGameFile(int a_fileNum);
        a_fileNum           -> Integer representing the file number

DESCRIPTION

    The function sets m_fileNum as a_fileNum and sets the deletePane as visible.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:10pm 5/2/2020
*/
/**/
    public void DeleteGameFile(int a_fileNum)
    {
        m_fileNum = a_fileNum;
        deletePane.setVisible(true);
    }
/*void LoadGameScene::DeleteGameFile(int a_fileNum);*/


/**/
/*
LoadGameScene::DeleteFile()

NAME

    LoadGameScene::DeleteFile - Deletes the file

SYNOPSIS

    void LoadGameScene::DeleteFile();

DESCRIPTION

    The function retrieves the file name using m_fileNum as the index for m_gameFiles. Then the it is removed
    from m_gameFiles and a new default file is created. The function deletes the file after from saves folder
    and updates the buttons, saves game files and hides the deletePane.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:11am 5/2/2020
*/
/**/
    @FXML
    private void DeleteFile()
    {
        String fileName = this.m_gameFiles.elementAt(m_fileNum).GetPlayerName();
        this.m_gameFiles.remove(m_fileNum);
        this.m_gameFiles.insertElementAt(new GameFile("File " + (m_fileNum + 1), m_fileNum), m_fileNum);

        Path path = Paths.get("");
        String filePath =  path.toAbsolutePath().toString()+ "\\saves\\" + fileName + "_Game.txt";

        // Delete File
        File file = new File(filePath);

        if(file.delete())
            System.out.println("File deleted successfully");
        else
            System.out.println("Failed to delete the file");

        UpdateButtons();
        SaveGameFiles();
        HidePane();
    }
/*void LoadGameScene::DeleteFile()*/

/**/
/*
LoadGameScene::HidePane()

NAME

    LoadGameScene::HidePane - Hides a pane

SYNOPSIS

    void LoadGameScene::HidePane()

DESCRIPTION

    The function setsVisible for deletePane as false.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:45am 5/3/2020
*/
/**/
    @FXML
    public void HidePane() { deletePane.setVisible(false); }

/**/
/*

NAME

SYNOPSIS

DESCRIPTION

RETURNS

AUTHOR

    Jay Pendon

DATE


*/
/**/
    private void DisplayChoosePlayersScene(GameFile a_chosenGameFile) throws IOException
    {
        FXMLLoader loadGameScene = new FXMLLoader(getClass().getResource("ChooseAmountOfPlayersScene.fxml"));
        Parent parent = loadGameScene.load();
        parent.setStyle("-fx-background-color: #009900;");

        ChooseAmountOfPlayersScene controller = loadGameScene.getController();
        controller.SetStage(this.m_stage);
        controller.SetGameFile(a_chosenGameFile);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }


    public void SetStage(Stage a_stage) { this.m_stage = a_stage; }

    public void SetGameFile(GameFile a_gameFile) { this.m_gameFile = a_gameFile; }
}
