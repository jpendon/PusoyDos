package sample.model;

import java.util.Collections;
import java.util.Vector;

public class Game {
    private Round m_round;
    private Vector<Player> m_players;
    private Boolean m_isResumed;

/**/
/*
Game::Game()

NAME

    Game::Game - Constructor for Game object

SYNOPSIS

    Game::Game();

DESCRIPTION

    Default constructor for Game object.

RETURNS

AUTHOR

    Jay Pendon

DATE

    9:12am 2/25/2020

*/
/**/
    Game(){}

/**/
/*
Game::Game(int a_numPlayers)

NAME

    Game::Game - Constructor for Game object

SYNOPSIS

    Game::Game(int a_numPlayers);
        a_numPlayers    -> Number of Players

DESCRIPTION

    Constructor for a Game object that uses a_numPlayers to set the number of Players. Then
    a Deck object is created and shuffled. The function deals each Card object from Deck to the players.
    Lastly, the function searches for the Card C3 and sets the player as the starting player for the game.

RETURNS

AUTHOR

    Jay Pendon

DATE

    9:12am 2/25/2020

*/
/**/
    public Game(int a_numPlayers)
    {
        this.m_isResumed = false;

        int playerId = 0;
        this.m_players = new Vector<Player>();

        // Create Human Player
        this.m_players.add(new Human(playerId));

        for (playerId = 1; playerId < a_numPlayers; playerId++)
            this.m_players.add(new AI(playerId));


        Collections.shuffle(this.m_players);

        Deck deck = new Deck();
        deck.ShuffleDeck();

        int counter = 0;

        while(counter < 52)
        {
            Card card = deck.DrawCard();

            switch(counter % this.m_players.size())
            {
                case 0:
                    this.m_players.elementAt(0).AddCardToHand(card);
                    break;
                case 1:
                    this.m_players.elementAt(1).AddCardToHand(card);
                    break;
                case 2:
                    this.m_players.elementAt(2).AddCardToHand(card);
                    break;
                case 3:
                    this.m_players.elementAt(3).AddCardToHand(card);
                    break;
            }

            counter++;
        }

        for(int i = 0; i < this.m_players.size(); i++)
            this.m_players.elementAt(i).SearchForC3();


    }
/*Game::Game(int a_numPlayers);*/

/**/
/*
Game::Game(Vector<Player> a_players)

NAME

    Game::Game - Constructor for a Game object

SYNOPSIS

    Game::Game(Vector<Player> a_players);
        a_players   -> Vector containing Player objects

DESCRIPTION

    A Constructor that creates a game object using a_players.

RETURNS

AUTHOR

    Jay Pendon

DATE

    9:33am 2/25/2020

*/
/**/
    public Game(Vector<Player> a_players)
    {
        this.m_isResumed = false;
        this.m_players = a_players;
    }
/*Game::Game(Vector<Player> a_players);*/

/**/
/*
Game::FindHuman()

NAME

    Game::FindHuman - Finds the Human object

SYNOPSIS

    Game::findHuman();

DESCRIPTION

    The function iterates through m_players and returns the Human Object.

RETURNS

    Returns a Player object that is an instance of a Human object. Returns a new Player object
    if a Human object was not found.

AUTHOR

    Jay Pendon

DATE

    9:47am 2/25/2020

*/
/**/
    public Player FindHuman()
    {
        for (Player player: this.m_players)
        {
            if (player instanceof AI)
                continue;
            return player;
        }

        return new Player();
    }
/*Game::FindHuman();*/

/**/
/*
Game::CheckIfPlayerWon(Player a_player)

NAME

    Game::CheckIfPlayerWon - Checks if the player has won

SYNOPSIS

    Boolean Game::CheckIfPlayerWon(Player a_player);
        a_player    -> A Player object

DESCRIPTION

    The function checks if the player's hand has zero Cards. If the player has zero Cards, then
    the Player object won the game.

RETURNS

    Returns true if a_player has won. Otherwise, the function will return false.

AUTHOR

    Jay Pendon

DATE

    9:42am 2/25/2020

*/
/**/
    private Boolean CheckIfPlayerWon(Player a_player)
    {
        if (a_player.GetNumCards() == 0)
            return true;
        return false;
    }
/*Boolean Game::CheckIfPlayerWon(Player a_player);*/

/**/
/*
Game::GetWinner()

NAME

    Game::GetWinner - Retrieves the winning Player object

SYNOPSIS

    Player Game::GetWinner();

DESCRIPTION

    The function iterates through m_players in order to find
    the winning Player object.

RETURNS

    Returns the winning Player object. If there is not a winning player, then a new Player object
    is returned.

AUTHOR

    Jay Pendon

DATE

    10:42am 2/25/2020

*/
/**/
    public Player GetWinner()
    {
        for (Player player: this.m_players)
        {
            if (CheckIfPlayerWon(player))
                return player;
        }

        return new Player();
    }
/*Player Game::GetWinner();*/

/**/
/*
Game::SetRound(Round a_round)

NAME

    Game::SetRound - Sets the round object

SYNOPSIS

    void Game::SetRound(Round a_round);
        a_round -> Round object

DESCRIPTION

    The function sets m_round as a_round.

RETURNS

AUTHOR

    Jay Pendon

DATE

    9:45am 2/25/2020

*/
/**/
    public void SetRound(Round a_round) { this.m_round = a_round; }

/**/
/*
Game::GetRound()

NAME

    Game::GetRound - Gets the Round object

SYNOPSIS

    Round Game::GetRound();

DESCRIPTION

    The function returns m_round.

RETURNS

    Returns a round object.
AUTHOR

    Jay Pendon

DATE

    9:45am 2/25/2020

*/
/**/
    public Round GetRound() { return this.m_round; }

/**/
/*
Game::GetPlayers()

NAME

    Game::GetPlayers - Gets the Vector of Players

SYNOPSIS

    Vector<Player> Game::GetPlayers();

DESCRIPTION

    The function returns m_players which is a vector of Player objects.

RETURNS

    Returns a Vector of Players.

AUTHOR

    Jay Pendon

DATE

    9:46am 2/25/2020

*/
/**/
    public Vector<Player> GetPlayers() { return this.m_players; }

/**/
/*
Game::SetPlayers(Vector<Player> a_players)

NAME

    Game::SetPlayers - Sets the round object

SYNOPSIS

    void Game::SetPlayers(Vector<Player> a_players);
        a_players -> Vector of Players

DESCRIPTION

    The function sets m_players as a_players.

RETURNS

AUTHOR

    Jay Pendon

DATE

    9:45am 2/25/2020

*/
/**/
    public void SetPlayers(Vector<Player> a_players) { this.m_players = a_players; }

/**/
/*
Game::GetIsResumed()

NAME

    Game::GetIsResumed - Gets a Boolean object

SYNOPSIS

    Boolean Game::GetIsResumed();

DESCRIPTION

    The function returns m_isResumed.

RETURNS

    Returns a Boolean value based on m_isResumed. True if the game is resumed and false if it is not.

AUTHOR

    Jay Pendon

DATE

    9:45am 2/25/2020

*/
/**/
    public Boolean GetIsResumed(){ return this.m_isResumed; }

/**/
/*
Game::SetIsResumed(Boolean a_isResumed)

NAME

    Game::SetIsResumed

SYNOPSIS

    void Game::SetIsResumed(Boolean a_isResumed);
        a_isResumed -> Boolean value

DESCRIPTION

    The function sets m_isResumed as a_isResumed.

RETURNS

AUTHOR

    Jay Pendon

DATE

    9:48am 2/25/2020

*/
/**/
    public void SetIsResumed(Boolean a_isResumed) { this.m_isResumed = a_isResumed; }
}
