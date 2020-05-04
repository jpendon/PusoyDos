package sample.model;

import java.util.Iterator;
import java.util.Vector;
import java.util.Collections;

public class Player {
    private int m_playerId;
    protected Vector<Card> m_hand;
    protected Vector<Character> m_suitRanking;
    protected Vector<Character> m_valueRanking;
    protected Vector<String> m_fiveCardHandRanking;
    private Boolean m_startRound;
    private Boolean m_containsC3;
    private Boolean m_hasPassed;

/**/
/*
Player::Player()

NAME

    Player::Player - Default constructor

SYNOPSIS

    Player::Player()

DESCRIPTION

    This is the default constructor for a Player object.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:10pm 2/19/2020

*/
/**/
    public Player()
    {
        this.m_playerId = -1;
        this.m_hand = new Vector<Card>();
        this.m_hasPassed = false;
        this.m_startRound = false;
        this.m_containsC3 = false;

        SetupSuitRankings();
        SetupValueRankings();
        SetupFiveCardHandRankings();
    }
/*Player::Player()*/

/**/
/*
Player::Player(int a_id)

NAME

    Player::Player - Constructor for a Player object

SYNOPSIS

    Player::Player(int a_id);
        a_id    -> Integer representing the object's id

DESCRIPTION

    This is a constructor for a Player object. It also assigns a_id to m_id.\

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:12pm 2/19/2020

*/
/**/
    public Player(int a_id)
    {
        this.m_playerId = a_id;
        this.m_hand = new Vector<Card>();
        this.m_hasPassed = false;
        this.m_startRound = false;
        this.m_containsC3 = false;

        SetupSuitRankings();
        SetupValueRankings();
        SetupFiveCardHandRankings();
    }
/*Player::Player(int a_id);*/

/**/
/*
Player::SetupSuitRankings()

NAME

    Player::SetupSuitRankings - Sets up Suit rankings

SYNOPSIS

    void Player::SetupSuitRankings();

DESCRIPTION

    The function creates suit rankings for the Player object.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:12pm 2/19/2020

*/
/**/
    private void SetupSuitRankings()
    {
        this.m_suitRanking = new Vector<Character>();

        this.m_suitRanking.add('C');
        this.m_suitRanking.add('S');
        this.m_suitRanking.add('H');
        this.m_suitRanking.add('D');
    }
/*void Player::SetupSuitRankings();*/

/**/
/*
Player::SetupValueRankings()

NAME

    Player::SetupValueRankings - Creates value rankings.

SYNOPSIS

    void Player::SetupValueRankings()

DESCRIPTION

    The function creates and sets the rankings for values.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:13pm 2/19/2020

*/
/**/
    private void SetupValueRankings()
    {
        this.m_valueRanking = new Vector<Character>();

        this.m_valueRanking.add('N');

        for (int i = 3; i < 10; i++)
        {
            this.m_valueRanking.add((char)(i + '0'));
        }

        this.m_valueRanking.add('X');
        this.m_valueRanking.add('J');
        this.m_valueRanking.add('Q');
        this.m_valueRanking.add('K');
        this.m_valueRanking.add('A');
        this.m_valueRanking.add((char)(2 + '0'));
    }
/*void Player::SetupValueRankings();*/

/**/
/*
Player::SetupFiveCardHandRankings()

NAME

    Player::SetupfiveCardRankings - Sets up Five card rankings

SYNOPSIS

    void Player::SetupFiveCardHandRankings();

DESCRIPTION

    The function creates and sets up five card rankings.
RETURNS

AUTHOR

    Jay Pendon

DATE

    4:15pm 2/19/2020

*/
/**/
    private void SetupFiveCardHandRankings()
    {
        this.m_fiveCardHandRanking = new Vector<String>();

        this.m_fiveCardHandRanking.add("None");
        this.m_fiveCardHandRanking.add("Straight");
        this.m_fiveCardHandRanking.add("Full House");
        this.m_fiveCardHandRanking.add("Straight Flush");
        this.m_fiveCardHandRanking.add("Quad and One Card");
        this.m_fiveCardHandRanking.add("Royal Flush");
    }
/*void Player::SetupFiveCardHandRankings();*/

/**/
/*
Player::AddCardToHand(Card a_card)

NAME

    Player::AddCardToHand - Adds card to hand

SYNOPSIS

    void Player::AddCardToHand(Card a_card);

DESCRIPTION

    The function adds a_card to m_hand.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:16pm 2/19/2020

*/
/**/
    public void AddCardToHand(Card a_card)
    {
        this.m_hand.add(a_card);
    }
/*void Player::AddCardToHand(Card a_card);*/

/**/
/*
Player::RemoveCardFromHand(Card a_card)

NAME

    Player::RemoveCardFromHand - Removes a Card from hand

SYNOPSIS

    void Player::RemoveCardFromHand(Card a_card);
        a_card      -> A Card object chosen

DESCRIPTION

    The function iterates through m_hand until a card's name is equal to a_card. Then the function
    removes the card from the player's hand.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:16pm 2/19/2020

*/
/**/
    public void RemoveCardFromHand(Card a_card)
    {
        for (Iterator<Card> iter = this.m_hand.iterator(); iter.hasNext();){
            Card card = iter.next();
            if (a_card.GetCardName().equals(card.GetCardName())){
                iter.remove();
                return;
            }
        }
    }
/*void Player::RemoveCardFromHand(Card a_card);*/

/**/
/*
Player::SearchForC3()

NAME

    Player::SearchForC3 - Searches for the Card C3

SYNOPSIS

    void Player::SearchForC3()

DESCRIPTION

    The function iterates through the player's hand and searches for C3. If C3 was found, then m_startRound
    and m_containsC3 is equal to true.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:26pm 2/19/2020

*/
/**/
    public void SearchForC3()
    {
        Iterator it = this.m_hand.iterator();

        while(it.hasNext())
        {
            Card card = (Card)it.next();

            if (card.GetSuit() != 'C')
                continue;
            if (card.GetValue() != '3')
                continue;

            this.m_startRound = true;
            this.m_containsC3 = true;
            break;
        }
    }
/*void Player::SearchForC3();*/

/**/
/*
Player::CheckCardConditions(Vector<Card> a_cardsPlayed, Vector<Card> a_tableCards)

NAME

    Player::CheckCardConditions - Checks Card conditions

SYNOPSIS

    Boolean Player::CheckCardConditions(Vector<Card> a_cardsPlayed, Vector<Card> a_tableCards);
        a_cardsPlayed       -> Cards chosen by the Player
        a_tableCards        -> Cards on the table

DESCRIPTION

    The function first checks if the player's hand contains C3. If so, then the function calls CheckIfCardsHaveC3
    because C3 is needed to be played first. If a_cardsPlayed is empty, then the function returns true because
    the player passes. If a_tableCards is empty, then anything the Player has selected is valid. If both
    a_cardsPlayed and a_tableCards have different sizes then the function returns false because it is invalid.
    Lastly, single, multiple and five card hands' conditions are checked by calling their respective
    checks.

RETURNS

    Returns true if the cards selected were valid and false if it is not.

AUTHOR

    Jay Pendon

DATE

    4:29pm 2/19/2020

*/
/**/
    public Boolean CheckCardConditions(Vector<Card> a_cardsPlayed, Vector<Card> a_tableCards)
    {
        if (this.m_containsC3)
            return CheckIfCardsHaveC3(a_cardsPlayed);

        // Check if player has passed
        if (a_cardsPlayed.size() == 0)
            return true;

        // Check if the player starts the round
        if (a_tableCards.size() == 0)
            return true;

        // Check if Cards played and Cards on table are equal
        if (a_cardsPlayed.size() != a_tableCards.size())
            return false;

        // Check if Cards played is a single card
        if (a_cardsPlayed.size() == 1)
            return CheckSingleCardConditions(a_cardsPlayed.elementAt(0), a_tableCards.elementAt(0));

        // Check if Cards played is a double
        if (a_cardsPlayed.size() > 1 && a_cardsPlayed.size() < 5)
            return CheckMultipleCardConditions(a_cardsPlayed, a_tableCards);

        if (a_cardsPlayed.size() == 5)
            return IsBetterSet(a_cardsPlayed, a_tableCards);

        return false;
    }
/*Boolean Player::CheckCardConditions(Vector<Card> a_cardsPlayed, Vector<Card> a_tableCards);*/

/**/
/*
Player::CheckSingleCardConditions(Card a_cardPlayer, Card a_cardTable)

NAME

    Player::CheckSingleCardConditions - Checks Conditions when round is for singles

SYNOPSIS

    Boolean Player::CheckSingleCardConditions(Card a_cardPlayer, Card a_cardTable);
        a_cardPlayer        -> Card chosen by player
        a_cardTable         -> Card on the table

DESCRIPTION

    The function calls CompareCardValues to see if a_cardPlayer's value is greater than a_cardTable.
    If not, then the cards' suits are compared.
RETURNS

    Returns true if the card chosen by the player is greater than the card on the table. If not,
    the function returns false.
AUTHOR

    Jay Pendon

DATE

    4:59pm 2/19/2020

*/
/**/
    private Boolean CheckSingleCardConditions(Card a_cardPlayer, Card a_cardTable)
    {
        if (CompareCardValues(a_cardPlayer, a_cardTable))
            return true;

        if (a_cardPlayer.GetValue() == a_cardTable.GetValue())
            return CompareSuitValue(a_cardPlayer, a_cardTable);

        return false;
    }
/*Boolean Player::CheckSingleCardConditions(Card a_cardPlayer, Card a_cardTable);*/

/**/
/*
Player::CheckIfCardsHaveC3(Vector<Card> a_cards)

NAME

    Player::CjeclOfCardsHaveC3 - Checks for C3

SYNOPSIS

    Boolean Player::CheckIfCardsHaveC3(Vector<Card> a_cards);
        a_cards     -> Cards chosen by the player

DESCRIPTION

    The function checks for C3 in a_cards.

RETURNS

    If C3 was found, then the function returns true. Otherwise the function returns false.

AUTHOR

    Jay Pendon

DATE

    7:59pm 2/19/2020

*/
/**/
    private Boolean CheckIfCardsHaveC3(Vector<Card> a_cards) {
        Boolean isValid = false;
        for (int i = 0; i < a_cards.size(); i++)
        {
            if (a_cards.elementAt(i).GetCardName().equals("C3"))
                isValid = true;
        }

        if (!isValid)
            return false;

        // Check if Cards played is a single card
        if (a_cards.size() == 1)
            return true;

        // Check if Cards played is a multiple
        if (a_cards.size() > 1 && a_cards.size() < 5)
            return AreCardsEqual(a_cards);



         if (DetermineFiveCardType(a_cards).equals("NULL"))
            return false;

         return true;
    }
/*Boolean Player::CheckIfCardsHaveC3(Vector<Card> a_cards);*/

/**/
/*
Player::CheckMultipleCardConditions(Vector<Card> a_playedCards, Vector<Card> a_tableCards)

NAME

    Player::CheckMultipleCardConditions - Checks conditions for multiples

SYNOPSIS

    Boolean Player::CheckMultipleCardConditions(Vector<Card> a_playedCards, Vector<Card> a_tableCards);
        a_playedCards       -> Cards chosen by the Player
        a_tableCards        -> Cards on the table

DESCRIPTION

    The functions checks if all the cards chosen by the player are equal in value by calling AreCardsEqual.
    Then the function compares a card from a_tableCards and a_playedCards. If the Card chosen by the player is better,
    then the function returns true. If not then the function returns false. However, if the multiple is a double,
    then the suits of the cards needs to be compared.

RETURNS

    Returns true if the selected cards are greater than the table's cards. If not then the function returns false.
AUTHOR

    Jay Pendon

DATE

    7:59am 2/21/2020

*/
/**/
    private Boolean CheckMultipleCardConditions(Vector<Card> a_playedCards, Vector<Card> a_tableCards)
    {
        // Check if the played cards all have the same value
        if(!AreCardsEqual(a_playedCards))
            return false;

        // Check if played Cards have a value greater than cards on the table
        if (CompareCardValues(a_playedCards.elementAt(0), a_tableCards.elementAt(0)))
            return true;

        if (a_playedCards.size() == 3)
            return false;

        // Check if Suit Ranking of played cards are Greater than table
        char highSuitPlayerCardSuit = FindHighestSuitedCard(a_playedCards).GetSuit();
        char highSuitTableCardSuit = FindHighestSuitedCard(a_tableCards).GetSuit();

        if(this.m_suitRanking.indexOf(highSuitPlayerCardSuit) > this.m_suitRanking.indexOf(highSuitTableCardSuit))
            return true;

        return false;
    }
/*Boolean Player::CheckMultipleCardConditions(Vector<Card> a_playedCards, Vector<Card> a_tableCards);*/

/**/
/*
Player::AreCardsEqual(Vector<Card> a_playedCards)

NAME

    Player::AreCardsEqual - Checks if cards are equal

SYNOPSIS

    Boolean Player::AreCardsEqual(Vector<Card> a_playedCards);
        a_playedCards       -> Cards chosen by the Player

DESCRIPTION

    The function calls CheckCardValue in order to compare values. Then the function iterates and checks if all
    of the cards have the same value as the first card.

RETURNS

    Returns true if all of the cards are equal and false if it is not.

AUTHOR

    Jay Pendon

DATE

    6:54am 2/21/2020

*/
/**/
    private Boolean AreCardsEqual(Vector<Card> a_playedCards)
    {
        int setVal = CheckCardValue(a_playedCards.elementAt(0));

        Iterator it = a_playedCards.iterator();

        while(it.hasNext())
        {
            Card card = (Card)it.next();

            int cardVal = CheckCardValue(card);

            if (setVal != cardVal)
                return false;
        }

        return true;
    }
/*Boolean Player::AreCardsEqual(Vector<Card> a_playedCards)*/

/**/
/*
Player::FindHighestSuitedCard(Vector<Card> a_playedCard)

NAME

    Player::FindHighestSuitedCard - Finds the highest suited card

SYNOPSIS

    Card Player::FindHighestSuitedCard(Vector<Card> a_playedCard);
        a_playedCard        -> Cards chosen by the player.

DESCRIPTION

    The function iterates through the player's chosen cards and compares suits.

RETURNS

    Returns the card with the highest suit value.

AUTHOR

    Jay Pendon

DATE

    6:58am 2/21/2020

*/
/**/
    private Card FindHighestSuitedCard(Vector<Card> a_playedCard)
    {
        Iterator it = a_playedCard.iterator();
        Card card = (Card)it.next();

        while(it.hasNext())
        {
            Card nextCard = (Card)it.next();
            int cardSuitVal = this.m_suitRanking.indexOf(card.GetSuit());
            int nextCardSuitVal = this.m_suitRanking.indexOf(nextCard.GetSuit());

            if (cardSuitVal < nextCardSuitVal)
                card = nextCard;
        }

        return card;
    }
/*Card Player::FindHighestSuitedCard(Vector<Card> a_playedCard);*/

/**/
/*
Player::CompareCardValues(Card a_playedCard, Card a_tableCard)

NAME

    Player::CompareCardValues - Compares cards

SYNOPSIS

    Boolean Player::CompareCardValues(Card a_playedCard, Card a_tableCard);
        a_playedCard        -> Card chosen by the Player
        a_tableCard         -> Card on the table

DESCRIPTION

    The function compares a_playedCard and a_tableCard by retrieving their values. Then they are compared.

RETURNS

    Returns true if the card chosen by the player is greater than the card on the table.

AUTHOR

    Jay Pendon

DATE

    7:01am 2/21/2020

*/
/**/
    private Boolean CompareCardValues(Card a_playedCard, Card a_tableCard)
    {
        int playerVal = CheckCardValue(a_playedCard);
        int tableVal = CheckCardValue(a_tableCard);

        if (playerVal > tableVal) return true;

        return false;
    }
/*Boolean Player::CompareCardValues(Card a_playedCard, Card a_tableCard);*/

/**/
/*
Player::CompareSuitValue(Card a_cardPlayer, Card a_cardTable)

NAME

    Player::CompareSuitValue - Compares suits

SYNOPSIS

    Boolean Player::CompareSuitValue(Card a_cardPlayer, Card a_cardTable);
        a_cardPlayer        -> Card chosen by the player
        a_cardTable         -> Card on the table

DESCRIPTION

    The function compares a_playedCard and a_tableCard by retrieving their suit values. Then they are compared.

RETURNS

    Returns true if the card chosen by the player is greater than the card on the table.

AUTHOR

    Jay Pendon

DATE

    7:02am 2/21/2020

*/
/**/
    public Boolean CompareSuitValue(Card a_cardPlayer, Card a_cardTable)
    {
        int playerVal = this.m_suitRanking.indexOf(a_cardPlayer.GetSuit());
        int tableVal = this.m_suitRanking.indexOf(a_cardTable.GetSuit());

        if (playerVal > tableVal) return true;

        return false;
    }
/*Boolean Player::CompareSuitValue(Card a_cardPlayer, Card a_cardTable)*/

/**/
/*
Player::DetermineFiveCardType(Vector<Card> a_cardsPlayed)

NAME

    Player::DetermineFiveCardType   - Categorizes the five card hand

SYNOPSIS

    String Player::DetermineFiveCardType(Vector<Card> a_cardsPlayed);
        a_cardsPlayed       -> Cards chosen by the player.

DESCRIPTION

    The function calls functions for each type of five card set condition.

RETURNS

    Returns a string containing the set's category.

AUTHOR

    Jay Pendon

DATE

    7:42am 2/27/2020

*/
/**/
    public String DetermineFiveCardType(Vector<Card> a_cardsPlayed)
    {
        String type = "NULL";

        if (a_cardsPlayed.size() == 0)
            return "None";

        if (IsQuadPlusOne(a_cardsPlayed))
            return "Quad and One Card";

        if (IsFullHouse(a_cardsPlayed))
            return "Full House";

        if (IsRoyals(a_cardsPlayed))
            return "Royal Flush";

        if (IsHandFlush(a_cardsPlayed))
            return "Straight Flush";

        if (IsHandStraight(a_cardsPlayed))
            return "Straight";


        return type;
    }
/*String Player::DetermineFiveCardType(Vector<Card> a_cardsPlayed)*/

/**/
/*
Player::IsQuadPlusOne(Vector<Card> a_cardsPlayed)

NAME

    Player::IsQuadPlusOne - Checks if cards are a quad plus one extra

SYNOPSIS

    Boolean Player::IsQuadPlusOne(Vector<Card> a_cardsPlayed);
        a_cardsPlayed       -> Cards chosen by the player

DESCRIPTION

    Function iterates through the player's chosen cards and calls IsCardPartOfSet. If it is part of a set,
    then the function returns true.

RETURNS

    True if the cards are a Quad plus one extra and false if it is not.

AUTHOR

    Jay Pendon

DATE

    8:22am 2/27/2020

*/
/**/
    private Boolean IsQuadPlusOne(Vector<Card> a_cardsPlayed)
    {

        for (int i = 0; i < 2; i++)
        {
            if (IsCardPartOfSet(a_cardsPlayed, a_cardsPlayed.elementAt(i), 4))
                return true;
        }

        return false;
    }
/*Boolean Player::IsQuadPlusOne(Vector<Card> a_cardsPlayed);*/

/**/
/*
Player::IsCardPartOfSet(Vector<Card> a_cardsPlayed, Card a_card, int a_numCards)

NAME

    Player::IsCardPartOfSet - Checks to see if a card is part of a set

SYNOPSIS

    Boolean Player::IsCardPartOfSet(Vector<Card> a_cardsPlayed, Card a_card, int a_numCards)
        a_cardsPlayed       -> Cards chosen by the player
        a_card              -> Card to be compared
        a_numCards          -> Number of cards to be part of a set

DESCRIPTION

    The function retrieves a_card's value and compares it to all of the cards in a_cardsPlayed. The function increases
    a counter for each card that has an equivalent value to a_card. If the counter is equal to a_numCards, then
    the function returns true. If not then False.
RETURNS

    Returns if the counter is equal to a_numCards, then the function returns true. If not then False.
AUTHOR

    Jay Pendon

DATE

    6:02am 2/27/2020

*/
/**/
    public Boolean IsCardPartOfSet(Vector<Card> a_cardsPlayed, Card a_card, int a_numCards)
    {
        int counter = 0;
        char cardVal = a_card.GetValue();

        for (int i = 0; i < a_cardsPlayed.size(); i++)
        {
            if (cardVal == a_cardsPlayed.elementAt(i).GetValue())
                counter++;
        }

        if (a_numCards != counter)
            return false;

        return true;
    }
/*Boolean Player::IsCardPartOfSet(Vector<Card> a_cardsPlayed, Card a_card, int a_numCards);*/

/**/
/*
Player::IsFullHouse(Vector<Card> a_cardsPlayed)

NAME

    Player::IsFullHouse - Checks if the cards are a full house

SYNOPSIS

    Boolean Player::IsFullHouse(Vector<Card> a_cardsPlayed);
        a_cardsPlayed       -> Cards chosen by the player

DESCRIPTION

    The function iterates through a_cardsPlayed to gather all of the card values. Then the function compares and
    increases the counter based on the number of equal card values. Returns true or false based on if the counter
    is or isn't equal to three or two.

RETURNS

    Returns true or false based on if the counter is or isn't equal to three or two.

AUTHOR

    Jay Pendon

DATE

    2:14pm 2/27/2020

*/
/**/
    private Boolean IsFullHouse(Vector<Card> a_cardsPlayed)
    {
        Vector<Integer> cardValues = new Vector<Integer>();
        Iterator it = a_cardsPlayed.iterator();

        // Add all card values into the vector
        while(it.hasNext())
        {
            Card card = (Card)it.next();

            cardValues.add(CheckCardValue(card));
        }

        // Sort values by ascending order
        Collections.sort(cardValues);

        int firstSetVal = cardValues.firstElement();
        int counter = 1;

        for (int i = 1; i < cardValues.size() - 1; i++)
        {
            if (firstSetVal == cardValues.elementAt(i))
                counter++;
        }

        if (counter > 3 || counter  < 2)
            return false;

        return true;
    }
/*Boolean Player::IsFullHouse(Vector<Card> a_cardsPlayed)*/

/**/
/*
Player::IsHandStraight(Vector<Card> a_cardsPlayed)

NAME

    Player::IsHandStraight - Checks if the cards are a straight

SYNOPSIS

    Boolean Player::IsHandStraight(Vector<Card> a_cardsPlayed);
        a_cardsPlayed       -> Cards chosen by the player

DESCRIPTION

    The function gathers all of the card values from a_cardsPlayed into a vector. Then the function sorts and
    checks to see if all of the values are in order and consecutive. The function returns a boolean value based on
    if it is successful.

RETURNS

    Returns true if the cards are a straight and false if it is not.

AUTHOR

    Jay Pendon

DATE

    6:47am 2/24/2020

*/
/**/
    private Boolean IsHandStraight(Vector<Card> a_cardsPlayed)
    {
        Vector<Integer> cardValues = new Vector<Integer>();
        Iterator it = a_cardsPlayed.iterator();

        // Add all card values into the vector
        while(it.hasNext())
        {
            Card card = (Card)it.next();

            cardValues.add(CheckCardValue(card));
        }

        // Sort values by ascending order
        Collections.sort(cardValues);
        int counter = cardValues.elementAt(0);

        // Check if Values are in order
        for (int i = 1; i < cardValues.size(); i++)
        {
            if (counter + 1 != cardValues.elementAt(i))
                // Next value is missing from vector
                return false;
            counter++;
        }

        return true;
    }
/*Boolean Player::IsHandStraight(Vector<Card> a_cardsPlayed);*/

/**/
/*
Player::IsHandFlush(Vector<Card> a_cardsPlayed)

NAME

    Player::IsHandFlush - Checks if cards are a flush\

SYNOPSIS

    Boolean Player::IsHandFlush(Vector<Card> a_cardsPlayed);
        a_cardsPlayed       -> Vector of cards selected by the player

DESCRIPTION

    The function checks to see if the cards are all of the same suit value. Then it will call and return the
    value from the function IsHandStraight.

RETURNS

    Returns true if the cards selected is a flush and false if it is not.

AUTHOR

    Jay Pendon

DATE

    6:59am 2/24/2020

*/
/**/
    private Boolean IsHandFlush(Vector<Card> a_cardsPlayed)
    {
        Card firstCard = a_cardsPlayed.firstElement();

        for (int i = 1; i < a_cardsPlayed.size(); i++)
        {
            if (firstCard.GetSuit() != a_cardsPlayed.elementAt(i).GetSuit())
                return false;
        }

        return IsHandStraight(a_cardsPlayed);
    }
/*Boolean Player::IsHandFlush(Vector<Card> a_cardsPlayed);*/

/**/
/*
Player::IsRoyals(Vector<Card> a_cardsPlayed)

NAME

    Player::IsRoyals - Checks to see if the cards played are royals

SYNOPSIS

    Boolean Player::IsRoyals(Vector<Card> a_cardsPlayed);
        a_cardsPlayed       -> Vector of Cards chosen by the player

DESCRIPTION

    The function checks to see if all of the cards have the same suit. Then it will check if
    the cards are all Royals.

RETURNS

    Returns true if the cards are Royals and a straight. Otherwise it will return false.

AUTHOR

    Jay Pendon

DATE

    11:51am 2/24/2020

*/
/**/
    private Boolean IsRoyals(Vector<Card> a_cardsPlayed)
    {
        Vector<Integer> cardValues = new Vector<Integer>();
        Iterator it = a_cardsPlayed.iterator();

        char suitVal = a_cardsPlayed.elementAt(0).GetSuit();
        // Add all card values into the vector
        while(it.hasNext())
        {
            Card card = (Card)it.next();
            cardValues.add(CheckCardValue(card));

            if (suitVal != card.GetSuit())
                return false;
        }

        // Sort values by ascending order
        Collections.sort(cardValues);
        int counter = cardValues.elementAt(0);

        if (counter != 7)
            return false;

        // Check if Values are in order
        for (int i = 1; i < cardValues.size(); i++)
        {
            if (counter + 1 != cardValues.elementAt(i))
                // Next value is missing from vector
                return false;
            counter++;
        }

        return true;
    }
/*Boolean Player::IsRoyals(Vector<Card> a_cardsPlayed)*/

/**/
/*
Player::IsBetterSet(Vector<Card> a_playedCards, Vector<Card> a_tableCards)

NAME

    Player::IsBetterSet - Checks to see if the cards are better than the table

SYNOPSIS

    Boolean Player::IsBetterSet(Vector<Card> a_playedCards, Vector<Card> a_tableCards);
        a_cardsPlayed       -> Cards chosen by the player
        a_tableCards        -> Cards on the table

DESCRIPTION

    The function first determines the type of hand both the player and the table has. Then it will compare
    the rankings. If the player's chosen cards are lower than the table's then it will return false. Otherwise, it
    will return true. If the rankings are equal, then the function will call IsSameSetTypeBetter and return its
    result.

RETURNS

    Returns true if the Cards selected by the player are greater than the cards on the table. Otherwise, it
    will return false.

AUTHOR

    Jay Pendon

DATE

    10:21am 2/23/2020

*/
/**/
    private Boolean IsBetterSet(Vector<Card> a_playedCards, Vector<Card> a_tableCards)
    {
        String typeOfPlayerHand = DetermineFiveCardType(a_playedCards);
        String currentCardHand = DetermineFiveCardType(a_tableCards);

        if (this.m_fiveCardHandRanking.indexOf(typeOfPlayerHand) < this.m_fiveCardHandRanking.indexOf(currentCardHand))
            return false;

        if (typeOfPlayerHand.equals(currentCardHand))
            return IsSameSetTypeBetter(a_playedCards, a_tableCards);

        return true;
    }
/*Boolean Player::IsBetterSet(Vector<Card> a_playedCards, Vector<Card> a_tableCards)*/

/**/
/*
Player::IsSameSetTypeBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards)

NAME

    Player::IsSameSetTypeBetter - Checks if the same type of set is better

SYNOPSIS

    Boolean Player::IsSameSetTypeBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards);
        a_cardsPlayed       -> Cards chosen by the player
        a_tableCards        -> Cards on the table

DESCRIPTION

    The function determines the type of five card hand is being played. Then the function calls different functions
    for each type of set and returns true or false.

RETURNS

    Returns true if the cards played by the player is better than the cards on the table.

AUTHOR

    Jay Pendon

DATE

    6:42am 3/01/2020
*/
/**/
    private Boolean IsSameSetTypeBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards)
    {
        String typeOfHand = DetermineFiveCardType(a_playedCards);

        if (typeOfHand.equals("Straight") || typeOfHand.equals("Straight Flush"))
            return IsThisStraightBetter(a_playedCards, a_tableCards);

        if (typeOfHand.equals("Full House"))
            return IsSetOfCardsBetter(a_playedCards, a_tableCards, 3);

        if (typeOfHand.equals("Quad and One Card"))
            return IsSetOfCardsBetter(a_playedCards, a_tableCards, 4);

        if (typeOfHand.equals("Royal Flush"))
            return CompareSuitValue(a_playedCards.firstElement(), a_tableCards.firstElement());

        return false;
    }
/*Boolean Player::IsSameSetTypeBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards);*/

/**/
/*
Player::IsThisStraightBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards)

NAME

    Player::IsThisStraightBetter - Checks to see if the straight is better

SYNOPSIS

    Boolean Player::IsThisStraightBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards);
        a_cardsPlayed       -> Cards chosen by the player
        a_tableCards        -> Cards on the table

DESCRIPTION
    The function finds the best Card from a_playedCards and a_tableCards in order to compare. It returns the value
    after calling CheckSingleCardConditions.

RETURNS

    Returns true if the straight is better and false if it is not.

AUTHOR

    Jay Pendon

DATE

    7:13am 3/01/2020

*/
/**/
    private Boolean IsThisStraightBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards)
    {
        Card bestPlayerCard = FindBestCardInSet(a_playedCards);
        Card bestTableCard = FindBestCardInSet(a_tableCards);

        return CheckSingleCardConditions(bestPlayerCard, bestTableCard);
    }
/*Boolean Player::IsThisStraightBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards)*/

/**/
/*
Player::IsSetOfCardsBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards, int a_numCards)

NAME

    Player::IsSetOfCardsBetter - Checks to see if the set of cards are better

SYNOPSIS

    Boolean Player::IsSetOfCardsBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards, int a_numCards);
        a_cardsPlayed       -> Cards chosen by the player
        a_tableCards        -> Cards on the table
        a_numCards          -> Number of cards in the set

DESCRIPTION

    The function finds the set from both a_playedCards and a_tableCards. Then it returns true or false after
    calling CheckSingleCardConditions.

RETURNS

    Returns true or false based on the result of CheckSingleCardConditions.

AUTHOR

    Jay Pendon

DATE

    3:33pm 3/01/2020

*/
/**/
    private Boolean IsSetOfCardsBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards, int a_numCards)
    {
        Card playerCard = FindSet(a_playedCards, a_numCards);
        Card tableCard = FindSet(a_tableCards, a_numCards);

        return CheckSingleCardConditions(playerCard, tableCard);
    }
/*Boolean Player::IsSetOfCardsBetter(Vector<Card> a_playedCards, Vector<Card> a_tableCards, int a_numCards);*/

/**/
/*
Player::FindSet(Vector<Card> a_playedCards, int a_numCards)

NAME

    Player::FindSet - Finds the set

SYNOPSIS

    Card Player::FindSet(Vector<Card> a_playedCards, int a_numCards)
        a_playedCards       -> Vector of Cards chosen by the player
        a_numCards          -> Number of cards to be found

DESCRIPTION

    The function uses the first card from a_playedCards to be compared to all cards in a_playedCards.
    A counter is used to keep track the number of cards that is equal to the first card. If it is equal,
    then the first Card is returned.  If not then the card with the different value is returned.

RETURNS

    Returns a Card whose value is found a_numCards times.

AUTHOR

    Jay Pendon

DATE

    6:32pm 3/05/2020

*/
/**/
    private Card FindSet(Vector<Card> a_playedCards, int a_numCards)
    {
        Card card = a_playedCards.firstElement();

        int counter = 1;

        for (int i = 1; i < a_playedCards.size(); i++)
        {
            if (card.GetValue() == a_playedCards.elementAt(i).GetValue())
                counter++;
        }

        // Card that is part of the set has been found
        if (counter == a_numCards)
            return card;

        for (int i = 1; i < a_playedCards.size(); i++)
        {
            if (card.GetValue() != a_playedCards.elementAt(i).GetValue())
                return a_playedCards.elementAt(i);
        }

        return card;

    }
/*Card Player::FindSet(Vector<Card> a_playedCards, int a_numCards)*/

/**/
/*
Player::FindBestCardInSet(Vector<Card> a_cards)

NAME

    Player::FindBestCardInSet - Finds the best card in the set

SYNOPSIS

    Card Player::FindBestCardInSet(Vector<Card> a_cards);
        a_cards     -> Cards chosen by the player.

DESCRIPTION

    The function iterates through a_cards and compares values. The best card is reassigned when
    a card that is better is found.

RETURNS

    Returns a Card object that is the best out of a_cards.

AUTHOR

    Jay Pendon

DATE

    6:43pm 2/19/2020

*/
/**/
    private Card FindBestCardInSet(Vector<Card> a_cards)
    {
        Card bestCard = a_cards.firstElement();

        for (int i = 1; i < a_cards.size(); i++)
        {
            int nextCardRank = CheckCardValue(a_cards.elementAt(i));

            if (CheckCardValue(bestCard) <  nextCardRank)
                bestCard = a_cards.elementAt(i);
        }

        return bestCard;
    }
/*Card Player::FindBestCardInSet(Vector<Card> a_cards);*/

/**/
/*
Player::SortCards(Vector<Card> a_cards)

NAME

    Player::SortCards - Sorts a given vector of cards.

SYNOPSIS

    void Player::SortCards(Vector<Card> a_cards);
        a_cards     -> Cards to be sorted

DESCRIPTION

    The function iterates through a_cards until it is sorted in ascending order.

RETURNS

AUTHOR

    Jay Pendon

DATE

    8:19am 3/11/2020

*/
/**/
    public void SortCards(Vector<Card> a_cards)
    {
        Boolean isSorted = false;
        Card temp = new Card();

        while(isSorted == false)
        {
            isSorted = true;
            for (int i = 0; i < a_cards.size() - 1; i++)
            {
                int n = CheckCardValue(a_cards.elementAt(i));
                int o = CheckCardValue(a_cards.elementAt(i + 1));
                if (CheckCardValue(a_cards.elementAt(i)) <=  CheckCardValue(a_cards.elementAt(i + 1)))
                    continue;

                temp = a_cards.elementAt(i);
                a_cards.set(i, a_cards.elementAt(i + 1));
                a_cards.set(i + 1, temp);
                isSorted = false;
            }
        }
    }
/*void Player::SortCards(Vector<Card> a_cards);*/

/**/
/*
Player::RemoveMultipleCardsFromHand(Vector<Card> a_cards)

NAME

    Player::RemoveMultipleCardsFromHand - Removes cards from player hand

SYNOPSIS

    void Player::RemoveMultipleCardsFromHand(Vector<Card> a_cards);
        a_cards         -> Cards chosen by the player

DESCRIPTION

    The function iterates through a_cards and removes cards from m_hand.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:37pm 2/19/2020

*/
/**/
    public void RemoveMultipleCardsFromHand(Vector<Card> a_cards)
    {
        for (int i = 0; i < a_cards.size(); i++)
        {
            if (a_cards.elementAt(i).GetCardName().equals("C3"))
                this.m_containsC3 = false;

            RemoveCardFromHand(a_cards.elementAt(i));
        }
    }
/*void Player::RemoveMultipleCardsFromHand(Vector<Card> a_cards)*/

/**/
/*
Player::GetCard(String a_cardName)

NAME

    Player::GetCard - Gets the Card from m_hand

SYNOPSIS

    Card GetCard(String a_cardName);
        a_cardName      -> String that contains a card's name

DESCRIPTION

    The function iterates through m_hand until a_cardName has been found.

RETURNS

    Returns a Card object with the intended card name.

AUTHOR

    Jay Pendon

DATE

    4:29pm 2/19/2020

*/
/**/
    public Card GetCard(String a_cardName)
    {
        for (Card card: this.m_hand)
        {
            if (card.GetCardName().equals(a_cardName))
                return card;
        }

        return new Card();
    }
/*Card Player::GetCard(String a_cardName)*/

/**/
/*
Player::CheckCardValue(Card a_card)

NAME

    Player::CheckCardValue - Checks a cards value

SYNOPSIS

    int Player::CheckCardValue(Card a_card);
        a_card      -> Card object

DESCRIPTION

    Checks a_card ranking in m_valueRanking.

RETURNS

    Returns an integer value based on its position in m_valueRanking.

AUTHOR

    Jay Pendon

DATE

    4:15pm 2/19/2020

*/
/**/
    protected int CheckCardValue(Card a_card) { return this.m_valueRanking.indexOf(a_card.GetValue()); }

/**/
/*
Player::SetPlayerHand(Vector<Card> a_playerHand)

NAME

    Player::SetPlayerHand - Sets the player's hand

SYNOPSIS

    void Player::SetPlayerHand(Vector<Card> a_playerHand);
        a_playerHand        -> Vector of Cards representing the hand

DESCRIPTION

    The function sets m_hand as a_playerHand.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:15pm 2/19/2020

*/
/**/
    public void SetPlayerHand(Vector<Card> a_playerHand) { this.m_hand = a_playerHand; }

/**/
/*
Player::GetPlayerId()

NAME

    Player::GetPlayerId - Get Player's Id

SYNOPSIS

    int Player::GetPlayerId();

DESCRIPTION

    The function returns m_playerId.

RETURNS

    Returns an integer representing the player's id.

AUTHOR

    Jay Pendon

DATE

    4:15pm 2/19/2020

*/
/**/
    public int GetPlayerId() { return this.m_playerId; }

/**/
/*
Player::SetPlayerId(int a_id)

NAME

    Player::SetPlayerId - Sets the player's id

SYNOPSIS

    void Player::SetPlayerId(int a_id);

DESCRIPTION

    The function sets m_id as a_id.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:15pm 2/19/2020

*/
/**/
    public void SetPlayerId(int a_id) { this.m_playerId = a_id; }

/**/
/*
Player::GetNumCards()

NAME

    Player::GetNumCards - Get number of cards from hand

SYNOPSIS

    int Player::GetNumCards();

DESCRIPTION

    The function returns m_hand's size.

RETURNS

    Returns an integer representing m_hand's size.

AUTHOR

    Jay Pendon

DATE

    4:15pm 2/19/2020

*/
/**/
    public int GetNumCards() { return this.m_hand.size(); }

/**/
/*
Player::Boolean DoesStartRound()

NAME

    Player::DoesStartRound - Checks if it starts the round

SYNOPSIS

    Boolean Player::DoesStartRound();

DESCRIPTION

    The function returns m_startRound.

RETURNS

    Returns a Boolean value based on if it starts the round.

AUTHOR

    Jay Pendon

DATE

    4:15pm 2/19/2020

*/
/**/
    public Boolean DoesStartRound() { return this.m_startRound; }

/**/
/*
Player::SetStartRound(Boolean a_doesStart)

NAME

    Player::SetStartRound - Sets if it starts a round

SYNOPSIS

    void SetStartRound(Boolean a_doesStart);
        a_doesStartRound        -> Boolean value indicating if it starts a round.

DESCRIPTION

    The function assigns m_startRound with a_doesStart.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:16pm 2/19/2020

*/
/**/
    public void SetStartRound(Boolean a_doesStart) {this.m_startRound = a_doesStart; }

/**/
/*
Player::void SetHasPassed(Boolean a_hasPassed)

NAME

    Player::SetHasPassed - Sets the value if the player has passed

SYNOPSIS

    void Player::SetHasPassed(Boolean a_hasPassed);
        a_hasPassed     -> Boolean value representing if the player has passed.

DESCRIPTION

    The function assigns m_hasPassed with a_hasPassed.

RETURNS

AUTHOR

    Jay Pendon

DATE

    4:16pm 2/19/2020

*/
/**/
    public void SetHasPassed(Boolean a_hasPassed) { this.m_hasPassed = a_hasPassed;}

/**/
/*
Player::GetHasPassed()

NAME

    Player::GetHasPassed - Get a value based on if the player has passed

SYNOPSIS

    Boolean Player::GetHasPassed();

DESCRIPTION

    The function returns m_hasPassed.

RETURNS

    Returns a Boolean value representing if the player has passed their turn.

AUTHOR

    Jay Pendon

DATE

    4:16pm 2/19/2020

*/
/**/
    public Boolean GetHasPassed() { return this.m_hasPassed; }

/**/
/*
Player::GetHand()

NAME

    Player::GetHand - Gets the player's hand

SYNOPSIS

    Vector<Card> Player::GetHand();

DESCRIPTION

    The function returns m_hand.

RETURNS

    Returns a Vector of Cards representing the player's hand.

AUTHOR

    Jay Pendon

DATE

    4:16pm 2/19/2020

*/
/**/
    public Vector<Card> GetHand() { return this.m_hand;}

/**/
/*
Player::GetContainsS3()

NAME

    Player::GetContainsS3 - Gets a boolean value based on their hand

SYNOPSIS

    Boolean Player::GetContainsS3();

DESCRIPTION

    The function returns m_containsC3.

RETURNS

    Returns true if the player has the card C3.

AUTHOR

    Jay Pendon

DATE

    4:16pm 2/19/2020

*/
/**/
    public Boolean GetContainsS3() { return this.m_containsC3; }

/**/
/*
Player::SetContainsC3(Boolean a_containsS3)

NAME

    Player::SetContainsC3 - Sets the player's boolean value

SYNOPSIS

    void Player::SetContainsS3(Boolean a_containsS3);
        a_containsC3        -> Boolean value indicating if the player has C3.

DESCRIPTION

    The function assigns m_containsC3.

RETURNS

    Returns a Boolean value based on if the player has C3.
AUTHOR

    Jay Pendon

DATE


*/
/**/
    public void SetContainsS3(Boolean a_containsC3) {this.m_containsC3 = a_containsC3; }
}
