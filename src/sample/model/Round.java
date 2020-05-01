package sample.model;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Vector;

public class Round {
    private Vector<Player> m_players;
    private Vector<Card> m_tableCards;
    private int m_startingPlayer;
    private int m_roundCounter;
    private int m_currentPlayer;
    private int m_playerCounter;
    private Boolean m_isNewRound;

/**/
/*
Round::Round()

NAME

    Round::Round - Default Constructor

SYNOPSIS

    Round::Round();

DESCRIPTION

    This is the default constructor for a Round object.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:11am 3/04/2020
*/
/**/
    public Round() {
        this.m_players = new Vector<Player>();
        this.m_tableCards = new Vector<Card>();
        this.m_startingPlayer = -1;
        this.m_roundCounter = -1;
        this.m_currentPlayer = -1;
        this.m_playerCounter = -1;
        this.m_isNewRound = true;
    }
/*Round::Round();*/

/**/
/*
Round::Round(Vector<Player> a_players)

NAME

    Round::Round - Constructor for Round object.

SYNOPSIS

    Round::Round(Vector<Player> a_players);
        a_players       -> Vector of players

DESCRIPTION

    This is a constructor for Round objects.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:11am 3/04/2020

*/
/**/
    public Round(Vector<Player> a_players)
    {
        this.m_players = a_players;
        this.m_tableCards = new Vector<Card>();
        this.m_roundCounter = 0;
        this.m_currentPlayer = -1;
        this.m_isNewRound = true;
    }
/*Round::Round(Vector<Player> a_players);*/

/**/
/*
Round::Round(Vector<Player> a_players, Vector<Card> a_tableCards, int a_startingPlayer, int a_roundCounter,
                 int a_currentPlayer, int a_playerCounter)

NAME

    Round::Round - Constructor for Round

SYNOPSIS

    Round::Round(Vector<Player> a_players, Vector<Card> a_tableCards, int a_startingPlayer, int a_roundCounter,
                 int a_currentPlayer, int a_playerCounter);
                 a_players          -> Vector of Players
                 a_tableCards       -> Vector of Cards for a table
                 a_startingPlayer   -> Index of starting player
                 a_roundCounter     -> Round counter
                 a_currentPlayer    -> Index of Current player
                 a_playerCounter    -> Player Counter

DESCRIPTION

    The constructor assigns member variables with their corresponding paramter

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:13am 3/04/2020

*/
/**/
    public Round(Vector<Player> a_players, Vector<Card> a_tableCards, int a_startingPlayer, int a_roundCounter,
                 int a_currentPlayer, int a_playerCounter) {
        this.m_players = a_players;
        this.m_tableCards = a_tableCards;
        this.m_startingPlayer = a_startingPlayer;
        this.m_currentPlayer = a_currentPlayer;
        this.m_roundCounter = a_roundCounter;
        this.m_playerCounter = a_playerCounter;
        this.m_isNewRound = false;
    }
/*Round::Round(Vector<Player> a_players, Vector<Card> a_tableCards, int a_startingPlayer, int a_roundCounter,
                 int a_currentPlayer, int a_playerCounter)*/

/**/
/*
Round::GetStartingPlayer()

NAME

    Round::GetStartingPlayer - Gets the starting player

SYNOPSIS

    int Round::GetStartingPlayer();

DESCRIPTION

    The function returns m_startingPlayer.

RETURNS

    Returns an integer representing the starting player.

AUTHOR

    Jay Pendon

DATE

    7:15am 3/04/2020

*/
/**/
    public int GetStartingPlayer() { return this.m_startingPlayer; }

/**/
/*
Round::SetStartingPlayer(int a_position)

NAME

    Round::SetStartingPlayer - Sets the starting player.

SYNOPSIS

    void Round::SetStartingPlayer(int a_position);
        a_position      -> Integer representing the position of the starting player

DESCRIPTION

    The function sets m_startingPlayer as a_position.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:15am 3/04/2020

*/
/**/
    public void SetStartingPlayer(int a_position) { this.m_startingPlayer = a_position; }

/**/
/*
Round::GetPlayers()

NAME

    Round::GetPlayers - Gets the Vector of Players

SYNOPSIS

    Vector<Player> Round::GetPlayers();

DESCRIPTION

    The function returns m_players.

RETURNS

    Returns a Vector of Players representing the players in the round.

AUTHOR

    Jay Pendon

DATE

    7:15am 3/04/2020

*/
/**/
    public Vector<Player> GetPlayers() { return this.m_players; }

/**/
/*
Round::ReplaceTableCards(Vector<Card> a_playedCards)

NAME

    Round::ReplaceTableCards - Replaces cards on table

SYNOPSIS

    void Round::ReplaceTableCards(Vector<Card> a_playedCards);
        a_playedCards       -> Vector of Cards selected by the player

DESCRIPTION

    The function replaces m_tableCards with a_playedCards.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:17am 3/04/2020

*/
/**/
    public void ReplaceTableCards(Vector<Card> a_playedCards) {
        Iterator it = a_playedCards.iterator();

        this.m_tableCards.clear();

        while (it.hasNext()) {
            this.m_tableCards.add((Card) it.next());
        }
    }
/*void Round::ReplaceTableCards(Vector<Card> a_playedCards);*/

/**/
/*
Round::FindStartingPlayer()

NAME

    Round::FindStartingPlayer - Returns the starting player's position

SYNOPSIS

    void Round::FindStartingPlayer();

DESCRIPTION

    The function searches for the player that holds C3 and checks if the player specifically
    starts the round.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:43am 3/04/2020

*/
/**/
    private void FindStartingPlayer()
    {
        for (int i = 0; i < m_players.size(); i++)
            m_players.elementAt(i).SearchForC3();


        for (int startingPlayer = 0; startingPlayer < m_players.size(); startingPlayer++) {
            if (m_players.elementAt(startingPlayer).DoesStartRound())
                this.m_startingPlayer = startingPlayer;
        }
    }
/*void Round::FindStartingPlayer();*/

/**/
/*
Round::void SetUpRound()

NAME

    Round::SetUpRound - Sets up the round

SYNOPSIS

    void Round::SetUpRound();

DESCRIPTION

    The function sets m_playerCounter to m_player's size, finds the starting player, sets m_currentPlayer to
    m_startingPlayer and assigns m_isNewRound as false.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:45am 3/04/2020

*/
/**/
    public void SetUpRound()
    {
        this.m_playerCounter = this.m_players.size();

        FindStartingPlayer();

        this.m_currentPlayer = this.m_startingPlayer;

        this.m_isNewRound = false;
    }
/*void Round::SetUpRound();*/

/**/
/*
Round::EndRound()

NAME

    Round::EndRound - Ends the round ands sets up for the next.

SYNOPSIS

    void Round::EndRound()

DESCRIPTION

    The function iterates through m_players and checks who did not pass their turn. If the player did
    pass their turn, it sets up the player for the next round. If it didn't then the player starts the next round.
    It also increases the round counter and clears the table.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:55am 3/04/2020

*/
/**/
    public void EndRound()
    {
        for (int i = 0; i < this.m_players.size(); i++) {
            if (!this.m_players.elementAt(i).GetHasPassed())
            {
                this.m_players.elementAt(i).SetStartRound(true);
                this.m_startingPlayer = i;
            }
            else
                this.m_players.elementAt(i).SetStartRound(false);

            this.m_players.elementAt(i).SetHasPassed(false);
        }

        this.m_roundCounter++;
        this.m_tableCards.clear();
        this.m_playerCounter = this.m_players.size();
    }
/*void Round::EndRound();*/

/**/
/*
Round::CheckIfGameEnded()

NAME

    Round::CheckIfGameEnded - Checks if the game has ended

SYNOPSIS

    Boolean Round::CheckIfGameEnded();

DESCRIPTION

    The function iterates through m_players and checks to see if any player has zero cards.

RETURNS

    Returns true if a player has zero cards and false if there isn't a player.

AUTHOR

    Jay Pendon

DATE

    8:12am 3/04/2020

*/
/**/
    public Boolean CheckIfGameEnded()
    {
        for (int i = 0; i < this.m_players.size(); i++) {
            if (this.m_players.elementAt(i).GetNumCards() == 0)
                return true;
        }

        return false;
    }
/*Boolean Round::CheckIfGameEnded();*/

/**/
/*
Round::NextTurnSetup(String a_playerName)

NAME

    Round::NextTurnSetup - Sets up the next turn

SYNOPSIS

    void Round::NextTurnSetup(String a_playerName);
        a_playerName        -> String containing the player's name

DESCRIPTION

    The function increases m_currentPlayer. Next, it checks to see if m_currentPlayer is greater than the
    size of m_players. If so, then the next player is at the beginning of m_players. Lastly, the game is saved.

RETURNS

AUTHOR

    Jay Pendon

DATE

    10:46am 3/04/2020

*/
/**/
    public void NextTurnSetup(String a_playerName)
    {
        // Increase currentPlayer counter to indicate next player
        this.m_currentPlayer++;
        // Check if the next player is the first element of the vector
        if (this.m_currentPlayer >= this.m_players.size())
            this.m_currentPlayer = 0;

        // Save the Game
        Serialization serialization = new Serialization();

        Path path = Paths.get("");
        String filePath =  path.toAbsolutePath().toString()+ "\\saves\\" + a_playerName + "_Game.txt";

        File file = new File(filePath);
        serialization.SaveGame(file, this);
    }
/*void Round::NextTurnSetup(String a_playerName)*/

/**/
/*
Human::GetHumanPlayer()

NAME

    Human::GetHumanPlayer - Gets the Human player

SYNOPSIS

    Human Human::GetHumanPlayer();

DESCRIPTION

    The function iterates through m_players for a Player object that is an instance of Human. Once found, the function
    will return the player.

RETURNS

    Returns a Human object.

AUTHOR

    Jay Pendon

DATE

    8:19am 4/19/2020

*/
/**/
    public Human GetHumanPlayer()
    {
        for (Player player: this.m_players)
        {
            if (!(player instanceof Human))
                continue;

            return (Human)player;
        }

        return new Human();
    }
/*Human Round::GetHumanPlayer()*/

/**/
/*
Round::SetPlayers(Vector<Player> a_players)

NAME

    Round::SetPlayers - Sets the player vector

SYNOPSIS

    void Round::SetPlayers(Vector<Player> a_players);
        a_players       -> Vector containing Players

DESCRIPTION

    The function sets m_players as a_players.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:21am 3/04/2020

*/
/**/
    public void SetPlayers(Vector<Player> a_players) { this.m_players = a_players; }

/**/
/*
Round::SetTableCards(Vector<Card> a_cards)

NAME

    Round::SetTableCards - Sets the cards on the table

SYNOPSIS

    void Round::SetTableCards(Vector<Card> a_cards);
        a_cards         -> Cards chosen by the player

DESCRIPTION

    The function sets m_tableCards as a_cards.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:21am 3/04/2020

*/
/**/
    public void SetTableCards(Vector<Card> a_cards) { this.m_tableCards = a_cards; }

/**/
/*
Round::GetCurrentPlayer()

NAME

    Round::GetCurrentPlayer - Returns the current player

SYNOPSIS

    int Round::GetCurrentPlayer();

DESCRIPTION

    The function returns m_currentPlayer.

RETURNS

    Returns an integer representing the current Player's position.

AUTHOR

    Jay Pendon

DATE

    7:21am 3/04/2020

*/
/**/
    public int GetCurrentPlayer() { return this.m_currentPlayer; }

/**/
/*
Round::SetCurrentPlayer(int a_currentPlayer)

NAME

    Round::SetCurrentPlayer - Sets the current player

SYNOPSIS

    void Round::SetCurrentPlayer(int a_currentPlayer);
        a_currentPlayer     -> Position of the current player

DESCRIPTION

    The function sets m_currentPlayer to a_currentPlayer.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:22am 3/04/2020

*/
/**/
    public void SetCurrentPlayer(int a_currentPlayer) { this.m_currentPlayer = a_currentPlayer; }

/**/
/*
Round::GetPlayerCounter()

NAME

    Round::GetPlayerCounter - Gets the player counter

SYNOPSIS

    int Round::GetPlayerCounter();

DESCRIPTION

    The function returns m_playerCounter.

RETURNS

    Returns an integer that represents the current m_player position.

AUTHOR

    Jay Pendon

DATE

    7:22am 3/04/2020

*/
/**/
    public int GetPlayerCounter() { return this.m_playerCounter; }

/**/
/*
Round::SetPlayerCounter(int a_playerCounter)

NAME

    Round::SetPlayerCounter - Sets the player counter

SYNOPSIS

    void Round::SetPlayerCounter(int a_playerCounter);

DESCRIPTION

    The function sets m_playerCounter as a_playerCounter.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:23am 3/04/2020

*/
/**/
    public void SetPlayerCounter(int a_playerCounter) { this.m_playerCounter = a_playerCounter; }

/**/
/*
Round::GetRoundCounter()

NAME

    Round::GetRoundCounter - Gets the round counter

SYNOPSIS

    int Round::GetRoundCounter();

DESCRIPTION

    The function returns m_roundCounter.

RETURNS

    An integer representing the number of rounds played.

AUTHOR

    Jay Pendon

DATE

    7:23am 3/04/2020

*/
/**/
    public int GetRoundCounter() { return this.m_roundCounter; }

/**/
/*
Round::SetRoundCounter(int a_roundCounter)

NAME

    Round::SetRoundCounter - Sets the round counter

SYNOPSIS

    void Round::SetRoundCounter(int a_roundCounter);
        a_roundCounter          -> Integer representing the current round number

DESCRIPTION

    The function sets the m_roundCounter as a_roundCounter.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:23am 3/04/2020

*/
/**/
    public void SetRoundCounter(int a_roundCounter) { this.m_roundCounter = a_roundCounter; }

/**/
/*
Round::GetIsNewRound()

NAME

    Round::GetIsNewRound - Get a boolean value representing if it is a new round

SYNOPSIS

    Boolean Round::GetIsNewRound();

DESCRIPTION

    The function returns m_isNewRound.

RETURNS

    Returns a Boolean representing if the round is new.
AUTHOR

    Jay Pendon

DATE

    7:23am 3/04/2020

*/
/**/
    public Boolean GetIsNewRound() { return this.m_isNewRound; }

/**/
/*
Round::SetIsNewRound(Boolean a_isNewRound)

NAME

    Round::SetIsNewRound - Sets the value if the round is new

SYNOPSIS

    void Round::SetIsNewRound(Boolean a_isNewRound)
        a_isNewRound        -> Boolean value representing if the round is new

DESCRIPTION

    The function sets m_isNewRound as a_isNewRound.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:23am 3/04/2020

*/
/**/
    public void SetIsNewRound(Boolean a_isNewRound) { this.m_isNewRound = a_isNewRound; }

/**/
/*
Round::GetTableCards()

NAME

    Round::GetTableCards - Get a vector of cards

SYNOPSIS

    Vector<Card> Round::GetTableCards();

DESCRIPTION

    The function returns m_tableCards.

RETURNS

    Returns a Vector of Cards representing the table.
AUTHOR

    Jay Pendon

DATE


*/
/**/
    public Vector<Card> GetTableCards() { return this.m_tableCards; }
}
