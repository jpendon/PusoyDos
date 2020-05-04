package sample.model;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Serialization {
    private Vector<Player> m_players;
    private Vector<Card> m_tableCards;
    private int m_startingPlayer;
    private int m_currentPlayer;
    private int m_roundCounter;
    private int m_playerCounter;

/**/
/*
Serialization::Serialization()

NAME

    Serialization::Serialization - Default constructor for Serialization

SYNOPSIS

    Serialization::Serialization();

DESCRIPTION

    The function serves as the default constructor for Serialization. It also initializes all member
    variables.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:09am 3/14/2020

*/
/**/
    public Serialization()
    {
        this.m_players = new Vector<Player>();
        this.m_tableCards = new Vector<Card>();
        this.m_startingPlayer = -1;
        this.m_currentPlayer = -1;
        this.m_roundCounter = -1;
        this.m_playerCounter = -1;
    }
/*Serialization::Serialization();*/

/**/
/*
Serialization::SaveGame(File a_file, Round a_round)

NAME

    Serialization::SaveGame - Saves the game to file

SYNOPSIS

    void SaveGame(File a_file, Round a_round);
        a_file          -> File object for information to be saved to
        a_round         -> Round object to be saved

DESCRIPTION

    The function calls GetRoundSaveString in order to create the string of text to be saved. Then a FileOutputStream
    object is created and written to.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:11am 3/14/2020

*/
/**/
    public void SaveGame(File a_file, Round a_round) {
        String roundSaveString = GetRoundSaveString(a_round);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(a_file);
            fileOutputStream.write(roundSaveString.getBytes());
            fileOutputStream.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
/*void Serialization::SaveGame(File a_file, Round a_round);*/

/**/
/*
Serialization::GetRoundSaveString(Round a_round)

NAME

    Serialization::GetRoundSaveString - Creates the String to save the game

SYNOPSIS

    String Serialization::GetRoundSaveString(Round a_round);
        a_round         -> the round to be saved

DESCRIPTION

    The function calls FindRoundInfo in order to retrieve the necessary info. Then SaveRoundinfo
    is called to create the string for round info. Lastly, SavePlayerInfo is called to save the player information.

RETURNS

    Returns a String representing the game's information.

AUTHOR

    Jay Pendon

DATE

    6:51am 3/14/2020

*/
/**/
    private String GetRoundSaveString(Round a_round) {

        String roundSaveString = "";

        FindRoundInfo(a_round);
        roundSaveString += SaveRoundInfo(roundSaveString);

        roundSaveString += SavePlayerInfo();

        return roundSaveString;
    }
/*String Serialization::GetRoundSaveString(Round a_round);*/

/**/
/*
Serialization::FindRoundInfo(Round a_round)

NAME

    Serialization::FindRoundInfo - Finds information about the round

SYNOPSIS

    void Serialization::FindRoundInfo(Round a_round);
        a_round         -> Round to be saved

DESCRIPTION

    The function finds information about the current round such as currentPlayer, players, etc.

RETURNS

AUTHOR

    Jay Pendon

DATE

    6:53am 3/14/2020

*/
/**/
    private void FindRoundInfo(Round a_round)
    {
        this.m_currentPlayer = a_round.GetCurrentPlayer();
        this.m_players = a_round.GetPlayers();
        this.m_tableCards = a_round.GetTableCards();
        this.m_startingPlayer = a_round.GetStartingPlayer();
        this.m_playerCounter = a_round.GetPlayerCounter();
        this.m_roundCounter = a_round.GetRoundCounter();
    }
/*void Serialization::FindRoundInfo(Round a_round);*/

/**/
/*
Serialization::SaveRoundInfo(String a_roundSaveString)

NAME

    Serialization::SaveRoundInfo - Adds information to string

SYNOPSIS

    String Serialization::SaveRoundInfo(String a_roundSaveString);
        a_roundSaveString       -> String containing round information

DESCRIPTION

    The function appends information such as current round number, current player, table cards, etc. to
    a_roundSaveString.

RETURNS

    Returns a string containing information about the round.

AUTHOR

    Jay Pendon

DATE

    7:04am 3/14/2020

*/
/**/
    private String SaveRoundInfo(String a_roundSaveString)
    {
        a_roundSaveString += "Current Round: ";
        a_roundSaveString += Integer.toString(this.m_roundCounter);

        a_roundSaveString += "\nCurrent Player: ";
        a_roundSaveString += Integer.toString(this.m_currentPlayer);

        a_roundSaveString += "\nPlayer Left In Round: ";
        a_roundSaveString += Integer.toString(this.m_playerCounter);

        a_roundSaveString += "\nStarting Player: ";
        a_roundSaveString += Integer.toString(this.m_startingPlayer);

        a_roundSaveString += "\nTable: ";
        a_roundSaveString += VectorCardsToString(this.m_tableCards);

        return a_roundSaveString;
    }
/*String Serialization::SaveRoundInfo(String a_roundSaveString);*/

/**/
/*
Serialization::SavePlayerInfo()

NAME

    Serialization::SavePlayerInfo - Saves Player information

SYNOPSIS

    String Serialization::SavePlayerInfo();

DESCRIPTION

    The function iterates through m_players in order to find each player's information. It will check if the Player
    object is an instance of an AI, retrieve the player's id, hand, and if it has passed its turn.

RETURNS

    The function returns a String containing all of the player's information.

AUTHOR

    Jay Pendon

DATE

    7:12am 3/14/2020

*/
/**/
    private String SavePlayerInfo()
    {
        String playerInfo = "";

        for (int i = 0; i < this.m_players.size(); i++)
        {
            Player player = this.m_players.elementAt(i);

            if (player instanceof AI)
                playerInfo += "\nAI ";
            else
                playerInfo += "\nHuman";

            playerInfo += "\nID: ";
            playerInfo += Integer.toString(player.GetPlayerId());

            // Save player's Hand
            playerInfo += "\nHand: ";
            for (Card card: player.GetHand())
            {
                playerInfo += card.GetCardName();
                playerInfo += " ";
            }

            playerInfo += "\nHas Passed: ";

            if (player.GetHasPassed())
                playerInfo += "True";
            else
                playerInfo += "False";

        }

        return playerInfo;
    }
/*String Serialization::SavePlayerInfo();*/


/**/
/*
Serialization::VectorCardsToString(Vector<Card> a_cards)

NAME

    Serialization::VectorCardsToString - Turns a Vector into String

SYNOPSIS

    String Serialization::VectorCardsToString(Vector<Card> a_cards);
        a_cards         -> Vector of Cards

DESCRIPTION

    The function iterates through a_cards and gets its card name. It will return a string
    containing all of the vector's card names.

RETURNS

    Returns a String containing all of a vector's card names.

AUTHOR

    Jay Pendon

DATE

    7:17am 3/14/2020

*/
/**/
    private String VectorCardsToString(Vector<Card> a_cards)
    {
        String cardsString = "";

        // Iterate through the player's hand
        for (Card card : a_cards)
        {
            cardsString += card.GetCardName();
            cardsString += " ";
        }

        return cardsString;
    }
/*String Serialization::VectorCardsToString(Vector<Card> a_cards);*/


/**/
/*
Serialization::ResumeGame(String a_filePath)

NAME

    Serialization::ResumeGame - Resumes the game

SYNOPSIS

    Round Serialization::ResumeGame(String a_filePath);
        a_filePath          -> String containing the file's path

DESCRIPTION

    The function creates a new file using a_filePath. Then it will read each line of the file and call
    ParseToken.  After reading each line, the function will call CheckifContainsC3 for each player. Lastly,
    the function will create a new round object using information from the file and return it.

RETURNS

    Returns a Round object using information from the file.

AUTHOR

    Jay Pendon

DATE

    7:21am 3/15/2020

*/
/**/
    public Round ResumeGame(String a_filePath) throws IOException
    {
        Round round = new Round();
        String line = "";

        File file = new File(a_filePath);
        Scanner in = new Scanner(file,"UTF-8");

        while((in.hasNext()))
        {
            line = in.nextLine();

            line = line.trim();
            String[] splitLine = line.split(":");

            String token = splitLine[0];

            ParseToken(token, splitLine);
        }

        for (Player player: this.m_players)
            CheckIfContainsC3(player);


        round.SetPlayers(this.m_players);
        round.SetCurrentPlayer(this.m_currentPlayer);
        round.SetPlayerCounter(this.m_playerCounter);
        round.SetRoundCounter(this.m_roundCounter);
        round.SetTableCards(this.m_tableCards);
        round.SetStartingPlayer(this.m_startingPlayer);

        in.close();

        return round;
    }
/*Round Serialization::ResumeGame(String a_filePath);*/

/**/
/*
Serialization::ParseToken(String a_token, String[] a_info)

NAME

    Serialization::ParseToken - Parses the token

SYNOPSIS

    void Serialization::ParseToken(String a_token, String[] a_info);
        a_token         -> Token to be parsed
        a_info          -> Information to be used

DESCRIPTION

    The function checks a_token and compares it to different conditions. If the token were to be equal to
    "Current Round", then the function will use a_info and parse it to m_roundCounter. Every possible token is being
    checked for in this function and its information recorded.

RETURNS

AUTHOR

    Jay Pendon

DATE

    7:43am 3/15/2020

*/
/**/
    private void ParseToken(String a_token, String[] a_info)
    {
        if (a_token.equals("Current Round"))
            this.m_roundCounter = Integer.parseInt(a_info[1].trim());

        if (a_token.equals("Current Player"))
            this.m_currentPlayer = Integer.parseInt(a_info[1].trim());

        if (a_token.equals("Player Left In Round"))
            this.m_playerCounter = Integer.parseInt(a_info[1].trim());

        if (a_token.equals("Starting Player"))
            this.m_startingPlayer = Integer.parseInt(a_info[1].trim());

        if (a_token.equals("Table") && a_info.length == 2)
            this.m_tableCards = CreateCardVec(GetCardNames(a_info[1].trim()));

        if (a_token.equals("AI"))
            this.m_players.add(new AI());

        if (a_token.equals("Human"))
            this.m_players.add(new Human());

        if (a_token.equals("ID"))
            this.m_players.lastElement().SetPlayerId(Integer.parseInt(a_info[1].trim()));

        if (a_token.equals("Hand") && !a_info[1].equals(""))
        {
            Vector<String> cardNames = GetCardNames(a_info[1]);
            Vector<Card> cards = CreateCardVec(cardNames);
            this.m_players.lastElement().SetPlayerHand(cards);
        }

        if (a_token.equals("Has Passed") &&a_info[1].trim().equals("True"))
            this.m_players.lastElement().SetHasPassed(true);


    }
/*void Serialization::ParseToken(String a_token, String[] a_info);*/

/**/
/*
Serialization::CheckIfContainsC3(Player a_player)

NAME

    Serialization::CheckIfContainsC3 - Checks to see if a player holds C3

SYNOPSIS

    void Serialization::CheckIfContainsC3(Player a_player);
        a_player            -> Player object

DESCRIPTION

RETURNS

AUTHOR

    Jay Pendon

DATE

    3:21pm 4/19/2020

*/
/**/
    private void CheckIfContainsC3(Player a_player)
    {
        for (Card card: a_player.GetHand())
        {
            if (card.GetCardName().equals("C3"))
                a_player.SetContainsS3(true);
        }
    }
/*void Serialization::CheckIfContainsC3(Player a_player);*/

/**/
/*
Serialization::GetCardNames(String a_line)

NAME

    Serialization::GetCardNames - Get Card names from a string

SYNOPSIS

    Vector<String> Serialization::GetCardNames(String a_line);
        a_line      -> String containing Card's names

DESCRIPTION

    The function uses a_line and matches a pattern to the string. Each match is equivalent to a Card's name.
    The function then adds the card's name to a vector and returns the vector.

RETURNS

    Returns a Vector of Strings representing Card names.

AUTHOR

    Jay Pendon

DATE

    5:22am 3/15/2020

*/
/**/
    private Vector<String> GetCardNames(String a_line)
    {
        // allCards is a vector that holds all cards
        Vector<String> allCards = new Vector<String>();

        Pattern pattern = Pattern.compile("([^\\[ ][^ ])");
        Matcher matcher = pattern.matcher(a_line);

        while(matcher.find()){
            allCards.add(matcher.group(0));
        }
        return allCards;
    }
/*Vector<String> Serialization::GetCardNames(String a_line);*/

/**/
/*
Serialization::CreateCardVec(Vector<String> a_allCards)

NAME

    Serialization::CreateCardVec - Creates a Card Vector

SYNOPSIS

    Vector<Card> Serialization::CreateCardVec(Vector<String> a_allCards);
        a_allCards          -> Vector containing strings

DESCRIPTION

    The function iterates through a_allCards and creates a Card based on its name. The function returns all
    of the cards from a_allCards as a Vector of Cards.

RETURNS

    Returns a Vector of Cards.

AUTHOR

    Jay Pendon

DATE

    5:43am 3/15/2020

*/
/**/
    private Vector<Card> CreateCardVec(Vector<String> a_allCards)
    {
        Vector<Card> cardVec = new Vector<Card>();

        for (String cardName: a_allCards) {
            Card card = new Card(cardName);
            cardVec.add(card);
        }

        // Return the vector containing cards
        return cardVec;
    }
/*Vector<Card> Serialization::CreateCardVec(Vector<String> a_allCards);*/
}
