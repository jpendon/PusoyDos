package sample.model;

public class GameFile {
    private int m_index;
    private String m_playerName;
    private int m_numGamesPlayed;
    private int m_numWins;
    private int m_numLosses;
    private Boolean m_isDefaultFile;
    private Game m_game;

/**/
/*
GameFile::GameFile()

NAME

    GameFile::GameFile - Default constructor for GameFile

SYNOPSIS

    GameFile:: GameFile();

DESCRIPTION

    The function is a default constructor for GameFile.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:12am 4/10/2020;
*/
/**/
    public GameFile()
    {
        this.m_index = -1;
        this.m_playerName = "Default Name";
        this.m_numGamesPlayed = 0;
        this.m_numWins = 0;
        this.m_numLosses = 0;
        this.m_isDefaultFile = true;
        this.m_game = new Game(null);
    }
/*GameFile::GameFile();*/

/**/
/*
GameFile::GameFile(String a_playerName, int a_index)

NAME

    GameFile::GameFile - Constructor for Object

SYNOPSIS

    GameFile::GameFile(String a_playerName, int a_index);
        a_playerName    -> A String that contains a player's name
        a_index         -> Integer used for file slots

DESCRIPTION

    The function is a constructor for this object. Each paremeter is assigned to their
    corresponding private variable.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:13am 4/10/2020;

*/
/**/
    public GameFile(String a_playerName, int a_index)
    {
        this.m_index = a_index;
        this.m_playerName = a_playerName;
        this.m_numGamesPlayed = 0;
        this.m_numWins = 0;
        this.m_numLosses = 0;
        this.m_isDefaultFile = true;
        this.m_game = new Game(null);
    }
/*GameFile::GameFile(String a_playerName, int a_index);*/

/**/
/*
GameFile::SetIndex(int a_index)

NAME

    GameFile::SetIndex - Sets m_index

SYNOPSIS

    void GameFile::SetIndex(int a_index);
        a_index -> Integer containing the index.

DESCRIPTION

    The function sets m_index as a_index.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:13am 4/10/2020;

*/
/**/
    public void SetIndex(int a_index) { this.m_index = a_index; }

/**/
/*
GameFile::GetIndex()

NAME

    GameFile::GetIndex - Gets the Index

SYNOPSIS

    int GameFile::GetIndex();

DESCRIPTION

    The function returns m_index.

RETURNS

    Returns an integer representing its index.

AUTHOR

    Jay Pendon

DATE

    7:13am 4/10/2020;

*/
/**/
    public int GetIndex() { return this.m_index; }

/**/
/*
GameFile::SetPlayerName(String a_playerName)

NAME

    GameFile::SetPlayerName - Sets the player's name

SYNOPSIS

    void GameFile::SetPlayerName(String a_playerName);
        a_playerName    -> String containing the player's name

DESCRIPTION

    The function sets m_playerName to a_playerName.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:13am 4/10/2020;

*/
/**/
    public void SetPlayerName(String a_playerName) { this.m_playerName = a_playerName; }

/**/
/*

NAME

SYNOPSIS

DESCRIPTION

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:13am 4/10/2020;

*/
/**/
    public String GetPlayerName() { return this.m_playerName; }

/**/
/*
GameFile::SetNumGamesPlayed(int a_numGamesPlayed)

NAME

    GameFile::SetNumGamesPlayed - Set the number of games played

SYNOPSIS

    void GameFile::SetNumGamesPlayed(int a_numGamesPlayed);
        a_numGamesPlayed    -> The number of games played.

DESCRIPTION

    The function sets m_numGamesPlayed with a_numGamesPlayed.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:13am 4/10/2020;

*/
/**/
    public void SetNumGamesPlayed(int a_numGamesPlayed) { this.m_numGamesPlayed = a_numGamesPlayed;}

/**/
/*
GameFile::GetNumGamesPlayed()

NAME

    GameFile::GetNumGamesPlayed - Gets the number of games played

SYNOPSIS

    int GameFile::GetNumGamesPlayed();

DESCRIPTION

    The function returns the number of games played.

RETURNS

    Returns an integer representing the number of games played.

AUTHOR

    Jay Pendon

DATE

    7:13am 4/10/2020;

*/
/**/
    public int GetNumGamesPlayed() { return this.m_numGamesPlayed; }

/**/
/*
GameFile::GetNumWins(int a_numWins)

NAME

    GameFile::GetNumWins - Gets the number of wins

SYNOPSIS

    void GameFile::SetNumWins(int a_numWins);
        a_numWins   -> An integer that represents the number of wins

DESCRIPTION

    The function sets m_numWins with a_numWins.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:15am 4/10/2020;

*/
/**/
    public void SetNumWins(int a_numWins) { this.m_numWins = a_numWins; }

/**/
/*
GameFile::GetNumWins()

NAME

    GameFile::GetNumWins - Gets the number of wins

SYNOPSIS

    int GameFile::GetNumWins();

DESCRIPTION

    The function returns m_numWins.

RETURNS

    Returns an integer representing the number of wins.

AUTHOR

    Jay Pendon

DATE

    7:15am 4/10/2020;

*/
/**/
    public int GetNumWins() { return this.m_numWins;}

/**/
/*
GameFile::SetNumLosses(int a_numLosses)

NAME

    GameFile::SetNumLosses - Sets the number of losses

SYNOPSIS

    void GameFile::SetNumLosses(int a_numLosses);
        a_numLosses     -> Number of losses as an integer.

DESCRIPTION

    The function sets m_numLosses as a_numLosses.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:16am 4/10/2020;

*/
/**/
    public void SetNumLosses(int a_numLosses) { this.m_numLosses = a_numLosses; }

/**/
/*
GameFile::GetNumLosses()

NAME

    GameFile::GetNumLosses - Gets the number of losses

SYNOPSIS

    int GameFile::GetNumLosses();

DESCRIPTION

    The function returns the number of losses.

RETURNS

    Returns an integer representing the number of losses.

AUTHOR

    Jay Pendon

DATE

    7:18am 4/10/2020;

*/
/**/
    public int GetNumLosses() { return this.m_numLosses; }

/**/
/*
GameFile::SetIsDefaultFile(Boolean a_isDefaultFile)

NAME

    GameFile::SetIsDefaultFile - Sets the boolean value representing default files

SYNOPSIS

    void GameFile::SetIsDefaultFile(Boolean a_isDefaultFile);
        a_isDefaultfile     -> Boolean value representing if the object is a default file

DESCRIPTION

    The function sets m_isDefaultFile with a_isDefaultFile.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:18am 4/10/2020;

*/
/**/
    public void SetIsDefaultFile(Boolean a_isDefaultFile) { this.m_isDefaultFile = a_isDefaultFile; }

/**/
/*
GameFile::GetIsDefaultFile()

NAME

    GameFile::GetIsDefaultFile - Gets a Boolean value

SYNOPSIS

    Boolean GameFile::GetIsDefaultFile();

DESCRIPTION

    The function returns a value indicating if the file is a default.

RETURNS

    Returns true if the object is a default file and false if it is not.

AUTHOR

    Jay Pendon

DATE

    7:18am 4/10/2020;

*/
/**/
    public Boolean GetIsDefaultFile() { return this.m_isDefaultFile; }

/**/
/*
GameFile::SetGame(Game a_game)

NAME

    GameFile::SetGame - Sets the game variable

SYNOPSIS

    void GameFile::SetGame(Game a_game);
        a_game      -> Game object

DESCRIPTION

    The function sets m_game with a_game.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:18am 4/10/2020;

*/
/**/
    public void SetGame(Game a_game) { this.m_game = a_game; }

/**/
/*
GameFile::GetGame()

NAME

    GameFile::GetGame - Gets the game variable

SYNOPSIS

    Game GameFile::GetGame();

DESCRIPTION

    The function returns m_game.

RETURNS

    Returns a Game object which is m_game.

AUTHOR

    Jay Pendon

DATE

    7:19am 4/10/2020;

*/
/**/
    public Game GetGame() { return this.m_game; }

}
