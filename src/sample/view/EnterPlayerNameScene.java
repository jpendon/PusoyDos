package sample.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.model.GameFile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnterPlayerNameScene implements Initializable {
    private Stage m_stage;
    private GameFile m_gameFile;
    private Vector<String> m_fileNames;

    @FXML
    private Label infoLabel;

    @FXML
    private TextArea enterPlayerNameTextArea;

    @FXML
    private Label invalidInputLabel;

    @FXML
    private Button continueButton;

/**/
/*
EnterPlayerNameScene::initialize(URL a_location, ResourceBundle a_resources)

NAME

    EnterPlayerNameScene::initialize - initialize function for controller

SYNOPSIS

    void EnterPlayerNameScene::initialize(URL a_location, ResourceBundle a_resources);
        a_location          -> URL Object
        a_resources         -> ResourceBundle Object

DESCRIPTION

    The function serves as the initialize function for this class. The function sets
    infoLabel's text as "Enter your name".

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:40am 4/4/2020

*/
/**/
    @Override
    public void initialize(URL a_location, ResourceBundle a_resources)
    {
        Platform.runLater(()->
        {
            infoLabel.setText("Enter your name:");
        });

    }

/**/
/*
EnterPlayerNameScene::ContinueButtonOnClick()

NAME

    EnterPlayerNameScene::ContinueButtonOnClick - Function for a Continue Button

SYNOPSIS

    void EnterPlayerNameScene::ContinueButtonOnClick();

DESCRIPTION

    The function is for the continue button. It will get the text from enterPlayerNameTextArea
    and validate the input using ValidateUserInput by passing the string. If ValidateUserInput
    returns false then the function returns. If not then the function prepares and displays the
    BaseScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:41am 4/4/2020
*/
/**/
    public void ContinueButtonOnClick() throws IOException {
        String userInput = enterPlayerNameTextArea.getText();
        userInput = userInput.trim();

        if(!ValidateUserInput(userInput))
            return;

        this.m_gameFile.SetPlayerName(userInput);
        this.m_gameFile.SetIsDefaultFile(false);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loadGameScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        LoadGameScene controller = fxmlLoader.getController();
        controller.SetGameFile(this.m_gameFile);
        controller.SetStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void EnterPlayerNameScene::ContinueButtonOnClick();*/

/**/
/*
EnterPlayerNameScene::ValidateUserInput(String a_userInput)

NAME

    EnterPlayerNameScene::ValidateUserInput - Validates user input

SYNOPSIS

    Boolean ValidateUserInput(String a_userInput);
        a_userInput     -> String containing user's input

DESCRIPTION

    The function validates the user's input by checking its length, if it contains a
    special character, if it exists or if it is empty. IF the user's input is not valid
    then the reasoning is displayed by calling DisplayInvalidInput and returns false. If
    it is valid then the function will return true.

RETURNS

    Returns true if the input is valid and false if it isn't.

AUTHOR

    Jay Pendon

DATE

    10:01am 5/2/2020
    
*/
/**/
    private Boolean ValidateUserInput(String a_userInput)
    {
        int errNum = -1;

        if (a_userInput.length() > 12)
            errNum = 0;

        if (ContainsSpecialCharacter(a_userInput))
            errNum = 1;

        if (a_userInput.equals(""))
            errNum = 2;

        if (CheckIfNameExists(a_userInput))
            errNum = 3;

        if (errNum != -1)
        {
            DisplayInvalidInput(errNum);
            return false;
        }

        return true;
    }
/*Boolean EnterPlayerNameScene::ValidateUserInput(String a_userInput);*/

/**/
/*
EnterPlayerNameScene::ContainsSpecialCharacter(String a)userInput)

NAME

    EnterPlayerNameScene::ContainsSpecialCharacter - Checks if input contains a special character

SYNOPSIS
    
    Boolean EnterPlayerNameScene::ContainsSpecialCharacter(String a)userInput);
        a_userInput     -> String containing user's input
        
DESCRIPTION

    The function checks if a_userInput contains a special character using a
    regex expression.

RETURNS

    Returns true or false depending on if matcher.find() returns a match.

AUTHOR

    Jay Pendon

DATE

    10:01am 5/2/2020

*/
/**/
    private Boolean ContainsSpecialCharacter(String a_userInput)
    {
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher matcher = pattern.matcher(a_userInput);

        return matcher.find();
    }
/*Boolean EnterPlayerNameScene::ContainsSpecialCharacter(String a_userInput);*/

/**/
/*
EnterPlayerNameScene::CheckIfNameExists
NAME

    EnterPlayerNameScene::CheckIfNameExists - Checks if a file name already exists

SYNOPSIS

     Boolean EnterPlayerNameScene::CheckIfNameExists(String a_userInput);
        a_userInput     -> String containing user's input

DESCRIPTION

    The function iterates through m_fileNames and checks if the
    name exists.

RETURNS

    Returns true if it doesn't exist and false if it exists.

AUTHOR

    Jay Pendon

DATE

    10:11am 5/2/2020

*/
/**/
    private Boolean CheckIfNameExists(String a_userInput)
    {
        for (String fileName : this.m_fileNames)
        {
            if (fileName.equals(a_userInput))
                return true;
        }
        return false;
    }
/* Boolean EnterPlayerNameScene::CheckIfNameExists(String a_userInput);*/

/**/
/*
EnterPlayerNameScene::DisplayInvalidInput(int a_errNum)

NAME

    EnterPlayerNameScene::DisplayInvalidInput - Displays the invalid message to the user

SYNOPSIS

    void EnterPlayerNameScene::DisplayInvalidInput(int a_errNum);
        a_errNum        -> Integer representing the error number

DESCRIPTION

    The function will display text to the user based on a_errNum.

RETURNS

AUTHOR

    Jay Pendon

DATE

    11:11am 4/14/2020

*/
/**/
    private void DisplayInvalidInput(int a_errNum)
    {
        String errString = "";

        switch(a_errNum)
        {
            case 0:
                errString += "Maximum length of File name is 12";
                break;
            case 1:
                errString += "Input contains special characters";
                break;
            case 2:
                errString += "Input cannot be empty";
                break;
            case 3:
                errString += "File Name Already Exists";
                break;
            default:
                errString += "NULL";
        }


        invalidInputLabel.setText(errString);
        invalidInputLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        invalidInputLabel.setFont(new Font(18));
    }
/*void EnterPlayerNameScene::DisplayInvalidInput(int a_errNum)*/

/**/
/*
EnterPlayerNameScene::BackButtonOnClick()

NAME

    EnterPlayerNameScene::BackButtonOnClick - Function for back button

SYNOPSIS

    void EnterPlayerNameScene::BackButtonOnClick();

DESCRIPTION

    The function will prepare and display the LoadGameScene.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:13am 4/22/2020
*/
/**/
    public void BackButtonOnClick() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loadGameScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        LoadGameScene controller = fxmlLoader.getController();
        controller.SetStage(this.m_stage);

        this.m_stage.setScene(new Scene(parent, 1280, 720));
        this.m_stage.show();
    }
/*void EnterPlayerNameScene::BackButtonOnClick();*/

/**/
/*
EnterPlayerNameScene::SetStage(Stage a_stage)

NAME

    EnterPlayerNameScene::SetStage - Sets the stage variable

SYNOPSIS

    void EnterPlayerNameScene::SetStage(Stage a_stage);
        a_stage         -> Stage object

DESCRIPTION

    The function sets m_stage as a_stage.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:23pm 4/11/2020

*/
/**/
    public void SetStage(Stage a_stage) { this.m_stage = a_stage; }

/**/
/*
EnterPlayerNameScene::SetGameFile(GameFile a_gameFile)

NAME

    EnterPlayerNameScene::SetGameFile - Sets the GameFile

SYNOPSIS

    void EnterPlayerNameScene::SetGameFile(GameFile a_gameFile);
        a_gameFile          -> GameFile object

DESCRIPTION

    The function sets m_gameFile as a_gameFile.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:23pm 4/11/2020

*/
/**/
    public void SetGameFile(GameFile a_gameFile) { this.m_gameFile = a_gameFile; }

/**/
/*
EnterPlayerNameScene::SetFileNames(Vector<String> a_fileNames)

NAME

    EnterPlayerNameScene::SetFileNames - Sets fileNames

SYNOPSIS

    void EnterPlayerNameScene::SetFileNames(Vector<String> a_fileNames);
        a_fileNames         -> Vector of Strings

DESCRIPTION

    The function sets m_filesNames as a_fileNames.

RETURNS

AUTHOR

    Jay Pendon

DATE

    1:23pm 4/11/2020

*/
/**/
    public void SetFileNames(Vector<String> a_fileNames) { this.m_fileNames = a_fileNames; }


}
