package sample.model;

import java.util.Vector;

public class AI extends Player{

/**/
/*
AI::AI()

NAME

    AI::AI - Default constructor for the object.

SYNOPSIS

    AI::AI()

DESCRIPTION

    This function is the default constructor for the AI object.

RETURNS

AUTHOR

    Jay Pendon

DATE

    9:13am 2/23/2020
*/
/**/
    public AI() { }

/**/
/*
AI::AI()

NAME

    AI::AI - A constructor for the object.

SYNOPSIS

    AI::AI(int a_playerId);
        a_playerId  -> the AI's player id.

DESCRIPTION

    This function is a constructor for the AI object. It will assign the id using
    a_playerId.

RETURNS

AUTHOR

    Jay Pendon

DATE

    9:14am 2/23/2020
*/
/**/
    public AI(int a_playerId)
    {
        super(a_playerId);
    }

/**/
/*
AI::DecideAction(Vector<Card> a_tableCards)

NAME

    AI::DecideAction - Decides the AI's course of action

SYNOPSIS

    Vector<Card> AI::DecideAction(Vector<Card> a_tableCards);
        a_tableCards    -> the cards currently on the table

DESCRIPTION

    The function will attempt to decide its course of action and return a Vector of Card objects.
    First the AI will check if it contains the Card C3. If it does have the card in its hand, then the AI
    will call FindBestC3Combination and attempt to find the best possible Vector of Cards to play. Otherwise, the
    AI will determine if a five card hand can be played. If so, then the AI will attempt to find and return
    a five card hand.The AI will continue to follow this set of logic for three, two and single card rounds.

RETURNS

    Returns a Vector of Cards to be played. If an empty vector is returned, then the AI has decided to pass its turn
    because it does not have any valid combination of cards to play.

AUTHOR

    Jay Pendon

DATE

    1:23pm 2/20/2020

*/
/**/
    public Vector<Card> DecideAction(Vector<Card> a_tableCards)
    {
        Vector<Card> playCards = new Vector<Card>();

        if (GetContainsS3())
        {
            playCards = FindBestC3Combination();
            SetContainsS3(false);
            RemoveCardsFromHand(playCards);
            return playCards;
        }

        if ((a_tableCards.size() == 5 || a_tableCards.size() == 0) && this.m_hand.size() > 4)
        {
            playCards =  ChooseSet(a_tableCards);
            RemoveCardsFromHand(playCards);
        }

        if (playCards.size() == 5)
            return playCards;

        if (a_tableCards.size() > 1 && a_tableCards.size() < 5 || a_tableCards.size() == 0)
            playCards = ChooseMultiple(a_tableCards);

        if (playCards.size() > 2 && playCards.size() < 5)
            return playCards;

        if (a_tableCards.size() < 2)
            return ChooseSingle(a_tableCards);


        return playCards;
    }
/*Vector<Card> AI::DecideAction(Vector<Card> a_tableCards);*/

/**/
/*
AI::FindBestC3Combination()

NAME

    AI::FindBestC3Combination - Finds the best Vector of Card objects to be played

SYNOPSIS

    Vector<Card> AI::FindBestC3Combination()

DESCRIPTION

    This function will attempt to find the best possible Card combinations to be played with C3.
    It will first attempt to find a five Card hand such as a straight, full house, and a quad plus one card hand.
    If a set of cards were not found, then the AI will attempt to find a multiple of cards to play. Otherwise,
    the AI will just play C3 by itself.

RETURNS

    Returns the best possible combination of cards that includes C3. The default return value is just a
    single card which is just C3.

AUTHOR

    Jay Pendon

DATE

    6:13am 4/20/2020

*/
/**/
    private Vector<Card> FindBestC3Combination()
    {
        Vector<Card> cards = new Vector<Card>();
        Card c3 = GetCard("C3");

        SortCards(this.m_hand);

        cards = CreateStraight(c3);
        if (cards.size() == 5)
            return cards;

        cards = FindFullHouseWithC3(c3);
        if (cards.size() == 5)
            return cards;

        cards = FindQuadWithC3(c3);
        if (cards.size() == 5)
            return cards;

        cards = FindMultiple(new Vector<Card>(), c3, 3);
        if (cards.size() == 3)
            return cards;

        cards = FindMultiple(new Vector<Card>(), c3, 2);
        if (cards.size() == 2)
            return cards;

        cards.add(c3);
        return cards;
    }
/* Vector<Card> AI::FindBestC3Combination();*/

/**/
/*
AI::Vector<Card> FindFullHouseWithC3(Card a_card)

NAME

    AI::FindFullHouseWithC3 - Attempts to find a Full house

SYNOPSIS

    Vector<Card> AI::FindFullHouseWithC3(Card a_card);
        a_card  -> A Card to be used to include in the full house.

DESCRIPTION

    This function attempts to find a possible full house with a_card. It will check to see if the player's hand
    contains two or three cards with the same value of a_card. If it not possible, then the function will
    return an empty Vector that indicates a full house is not possible.

    If the player's hand contains multiple cards with the same value as a_card, then CreateFullHouse is called twice
    for each part of the hand.If a Vector of Cards was found then the function will return the set of cards.
    If not, then an empty Vector will be returned.

RETURNS

    Returns a Vector of Cards. If the Vector contains five cards, then a full house had been found. If not, an empty
    Vector will be returned indicating a full house is not possible.
AUTHOR

    Jay Pendon

DATE

    6:31am 4/20/2020
*/
/**/
    private Vector<Card> FindFullHouseWithC3(Card a_card)
    {
        Vector<Card> playCards = new Vector<Card>();

        if (FindMultiple(new Vector<Card>(), a_card, 2).size() < 2)
            return new Vector<Card>();

        playCards = CreateFullHouse(a_card, 2);

        if (playCards.size() == 5)
            return playCards;

        playCards = CreateFullHouse(a_card, 3);

        if (playCards.size() == 5)
            return playCards;

        return new Vector<Card>();
    }
/*Vector<Card> AI::FindFullHouseWithC3(Card a_card);*/

/**/
/*
AI::CreateFullHouse(Card a_card, int a_numSameCardVal)

NAME

    AI::CreateFullHouse - Attempts to create a full house with a card

SYNOPSIS

    Vector<Card> AI::CreateFullHouse(Card a_card, int a_numSameCardVal);
        a_card  -> The Card to be used when finding a full house
        a_numSameCardVal -> The number of Cards with the same value as a_card to be found

DESCRIPTION

    The function will attempt to find the first half of the full house that does not include the value of a_card. The
    number of cards for the first part of the full house is 5 - a_numSameCardVal. For example if a_numSameCardVal is 3,
    then the function will attempt to find two cards with the same value. If it is not possible, then an empty
    vector will be returned. Otherwise the function will attempt to find the second half of the full house with the
    size of a_numSameCardVal.

RETURNS

    Returns a vector of Cards. If five cards were found, then a full house had been found. If not, then it is
    not possible for a full house to be played.

AUTHOR

    Jay Pendon

DATE

    9:10am 4/20/2020

*/
/**/
    private Vector<Card> CreateFullHouse(Card a_card, int a_numSameCardVal)
    {
        Vector<Card> firstPartOfSet = new Vector<Card>();
        int firstPartOfSetSize = (5 - a_numSameCardVal);

        // Find the first half of set without the card
        for (int i = 0; i < this.m_hand.size(); i++)
        {
            if (this.m_hand.elementAt(i).GetValue() != a_card.GetValue())
                firstPartOfSet = FindMultiple(new Vector<Card>(), this.m_hand.elementAt(i), firstPartOfSetSize);

            if (firstPartOfSet.size() == firstPartOfSetSize)
                break;
        }

        if (firstPartOfSet.size() != firstPartOfSetSize)
            return new Vector<Card>();

        // Find second half with the card
        Vector<Card> secondHalfOfSet = FindMultiple(new Vector<Card>(), a_card, a_numSameCardVal);

        if (secondHalfOfSet.size() != a_numSameCardVal)
            return new Vector<Card>();

        Vector<Card> playCards = firstPartOfSet;

        playCards.addAll(secondHalfOfSet);
        return playCards;
    }
/*Vector<Card> AI::CreateFullHouse(Card a_card, int a_numSameCardVal);*/

/**/
/*
AI::Vector<Card> FindQuadWithC3(Card a_card)

NAME

    AI::FindQuadWithC3 - function that attempts to find four cards with 3 as its value.

SYNOPSIS

    Vector<Card> AI::FindQuadWithC3(Card a_card);
        a_card  -> the card used to find three other cards with the same value.

DESCRIPTION

    The function will attempt to find three other cards with the same value. If a vector of 4 has been found,
    the computer will add an extra card to create a five card hand.
RETURNS

    Returns a five card hand indicating a quad plus one card has been found. If not then an empty vector is returned.
AUTHOR

    Jay Pendon

DATE

    4:32pm 4/21/2020

*/
/**/
    private Vector<Card> FindQuadWithC3(Card a_card)
    {
        Vector<Card> playCards = FindMultiple(new Vector<Card>(), a_card, 4);

        if (playCards.size() != 4)
            return new Vector<Card>();

        AddExtraCard(playCards);

        return playCards;
    }
/*Vector<Card> AI::FindQuadWithC3(Card a_card);*/

/**/
/*
AI::ChooseSet(Vector<Card> a_tableCards)

NAME

    AI::ChooseSet - Attempts to find a suitable five card hand to beat the table

SYNOPSIS

    Vector<Card> AI::ChooseSet(Vector<Card> a_tableCards);
        a_tableCards    -> Vector of Cards on the table.

DESCRIPTION

    The function will first determine the type of combination of five cards on the table. Then the function will
    attempt to create a five card hand that can beat the cards on the table.

RETURNS

    Returns a Vector of Cards that will beat and replace the cards on the table.

AUTHOR

    Jay Pendon

DATE

    7:45am 3/2/2020
*/
/**/
    private Vector<Card> ChooseSet(Vector<Card> a_tableCards)
    {
        Vector<Card> temp = new Vector<Card>();

        SortCards(a_tableCards);
        SortCards(this.m_hand);

        String tableRank = DetermineFiveCardType(a_tableCards);
        int rank = this.m_fiveCardHandRanking.indexOf(tableRank);


        temp = FindStraight(a_tableCards);
        if (rank < this.m_fiveCardHandRanking.indexOf("Full House") && temp.size() == 5)
            return temp;

        temp = FindFullHouse(a_tableCards);
        if (rank < this.m_fiveCardHandRanking.indexOf("Straight Flush") && temp.size() == 5)
            return temp;

        temp = FindStraightFlush(a_tableCards);
        if (rank < this.m_fiveCardHandRanking.indexOf("Quad and One Card") && temp.size() == 5)
            return temp;

        temp = FindQuadPlusOne(a_tableCards);
        if (rank < this.m_fiveCardHandRanking.indexOf("Royal Flush") && temp.size() == 5)
            return temp;

        temp = FindRoyalFlush();
        if (temp.size() == 5)
            return temp;

        return new Vector<Card>();
    }
/*Vector<Card> AI::ChooseSet(Vector<Card> a_tableCards);*/

/**/
/*
AI::FindRoyalFlush()

NAME

    AI::FindRoyalFlush - Attempts to find a royal flush combination

SYNOPSIS

    Vector<Card> AI::FindRoyalFlush()

DESCRIPTION

    This function attempts to find a royal flush card combination in the player's hand. First, the function will
    determine all of cards that are aces and adds them to a vector. Then the function will call FindRoyalSet that will
    attempt to create a royal flush with each ace. If an royal flush is possible, then the Vector of Cards
    containing a royal flush will be returned

RETURNS

    Returns a Vector of Cards that represents a royal flush. If the vector is empty when returned, then a royal flush is
    not possible.
AUTHOR

    Jay Pendon

DATE

    7:32am 3/5/2020

*/
/**/
    private Vector<Card> FindRoyalFlush()
    {
        Vector<Card> playCards = new Vector<Card>();
        Vector<Card> aceCards = new Vector<Card>();

        for (int i = 0; i < this.m_hand.size(); i++)
        {
            if (this.m_hand.elementAt(i).GetValue() == 'A')
                aceCards.add(this.m_hand.elementAt(i));
        }

        for (int i = 0; i < aceCards.size(); i++)
        {
            playCards = FindRoyalSet(aceCards.elementAt(i));

            if (playCards.size() == 5)
                break;
        }

        return playCards;
    }
/*Vector<Card> AI::FindRoyalFlush();*/

/**/
/*
AI::FindRoyalSet(Card a_ace)

NAME

    AI::FindRoyalSet - finds royal set for a card

SYNOPSIS

    Vector<Card> AI::FindRoyalSet(Card a_ace);
        a_ace   -> A Card that is an ace

DESCRIPTION

    The function will attempt to create a Royal Flush with the given card.

RETURNS

    Returns a Vector of Cards that represents a royal flush. If the vector is empty when returned, then a
    royal flush is not possible.

AUTHOR

    Jay Pendon

DATE

    7:53am 3/5/2020
*/
/**/
    private Vector<Card> FindRoyalSet(Card a_ace)
    {
        Vector<Card> cards = new Vector<Card>();

        char suit = a_ace.GetSuit();
        int cardVal = this.m_valueRanking.indexOf(a_ace.GetValue());

        for (; cardVal >= this.m_valueRanking.indexOf('X'); --cardVal)
        {
            String cardName = suit + Character.toString(this.m_valueRanking.elementAt(cardVal));

            Card card = GetCard(cardName);

            if (card.GetCardName().equals("NULL"))
                return new Vector<Card>();

            cards.add(card);
        }

        return cards;
    }
/*Vector<Card> AI::FindRoyalSet(Card a_ace);*/

/**/
/*
AI::FindQuadPlusOne(Vector<Card> a_tableCards)

NAME

     AI::FindQuadPlusOne

SYNOPSIS

     Vector<Card> AI::FindQuadPlusOne(Vector<Card> a_tableCards);
        a_tableCards    -> A Vector of Card objects that represents the table

DESCRIPTION

    The function will first call the function FindMultipleToBeat that will determine the Card value to beat.
    Then the function will go through each of the cards in the player's hand. If the Card's value is greater than
    the card to beat then it will attempt to find three other cards of the same value. If four cards were found, then
    the function will add an extra card and return it as a vector. If not, then an empty vector will be returned.

RETURNS

    Returns a Vector of Cards that represents a Quad plus one extra card. If an ampty vector was returned, then
    it was not found.

AUTHOR

    Jay Pendon

DATE

    4:12pm 2/23/2020

*/
/**/
    private Vector<Card> FindQuadPlusOne(Vector<Card> a_tableCards)
    {
        Vector<Card> playCards = new Vector<Card>();
        Card multipleToBeat = FindMultipleToBeat(a_tableCards, 4);


        for (int i = 0; i < this.m_hand.size(); i++)
        {
            if (CheckCardValue(multipleToBeat) > CheckCardValue(this.m_hand.elementAt(i)))
                continue;
            playCards = FindMultiple(playCards, this.m_hand.elementAt(i), 4);

            if (playCards.size() == 4)
                break;

            playCards.clear();
        }

        if (playCards.size() != 4)
        {
            playCards.clear();
            return playCards;
        }

        AddExtraCard(playCards);
        return playCards;
    }
/*Vector<Card> AI::FindQuadPlusOne(Vector<Card> a_tableCards);*/

/**/
/*
AI::AddExtraCard(Vector<Card> a_cards)

NAME

    AI::AddExtraCard - Adds an extra card to a_cards

SYNOPSIS

    void AI::AddExtraCard(Vector<Card> a_cards);
        a_cards -> Vector of Cards

DESCRIPTION

    This function adds an extra card from the player's hand to a_cards. The function will sort the hand and iterate
    through the m_hand. Each iteration, the function will check if the cards are already in a_cards. If it isn't, then
    the function will add the extra card to a_cards and return the vector after sorting.

RETURNS

    Returns a vector of five cards representing a Quad plus One Extra Card combination.

AUTHOR

    Jay Pendon

DATE

    4:13pm 2/23/2020
*/
/**/
    private void AddExtraCard(Vector<Card> a_cards)
    {
        //Find and add an extra card
        SortCards(this.m_hand);

        for (int i = 0; i < this.m_hand.size(); i++)
        {
            if (IsCardInUsed(a_cards, this.m_hand.elementAt(i)))
                continue;

            a_cards.add(this.m_hand.elementAt(i));
            break;
        }

        SortCards(a_cards);
    }
/*void AI::AddExtraCard(Vector<Card> a_cards);*/

/**/
/*
AI::FindStraightFlush(Vector<Card> a_tableCards)

NAME

    AI::FindStraightFlush - function attempts to find a Straight Flush

SYNOPSIS

    Vector<Card> AI::FindStraightFlush(Vector<Card> a_tableCards);
        a_tableCards    -> a Vector of Cards that represents the table

DESCRIPTION

    The function begins by finding the card to beat from a_tableCards. Then the function will
    iterate through the player's hand and checks to see if a card can beat the highest card from the table.
    If so, then the function CreateStraightFlush is called which will return a vector of either five or zero cards. If
    five cards were found, then it will return the vector containing a straight flush. If not, then the function
    will continue to iterate through the hand. If a Straight Flush was not found, then an empty Vector.
    is returned.

RETURNS

    Returns a vector containing a straight flush. If a Straight Flush was not found, then an empty Vector.

AUTHOR

    Jay Pendon

DATE

    1:15pm 4/19/2020

*/
/**/
    private Vector<Card> FindStraightFlush(Vector<Card> a_tableCards)
    {
        Vector<Card> playCards = new Vector<Card>();

        Card highestCard = new Card();

        if (!a_tableCards.isEmpty())
            highestCard = a_tableCards.lastElement();

        for (int i = 0; i < this.m_hand.size(); i++)
        {
            if (CheckCardValue(highestCard) < CheckCardValue(this.m_hand.elementAt(i)))
                playCards = CreateStraightFlush(this.m_hand.elementAt(i));

            if (playCards.size() == 5)
                return playCards;
        }

        return playCards;
    }
/*Vector<Card> AI::FindStraightFlush(Vector<Card> a_tableCards);*/

/**/
/*
AI::CreateStraightFlush(Card a_card)

NAME

    AI::CreateStraightFlush - function creates a straight flush

SYNOPSIS

    Vector<Card> AI::CreateStraightFlush(Card a_card);
        a_card  -> Card that is used to create a Straight Flush

DESCRIPTION

    The function will use a_card to determine the suit value of the flush. Then the function will
    iterate through the player's hand in an attempt to find the next card of the straight flush.
    If all five cards were found then the function will return the flush. If not then the function
    will return an empty vector.

RETURNS

    Returns a vector containing a straight flush. If a Straight Flush was not found, then an empty Vector.

AUTHOR

    Jay Pendon

DATE

    1:43pm 4/19/2020

*/
/**/
    private Vector<Card> CreateStraightFlush(Card a_card)
    {
        Vector<Card> playCards = new Vector<Card>();
        playCards.add(a_card);

        char suit = a_card.GetSuit();

        int counter = CheckCardValue(a_card);

        for (int i  = this.m_hand.indexOf(a_card) - 1; i >= 0; i--)
        {
            Card currentCard = this.m_hand.elementAt(i);
            int currentCardVal = CheckCardValue(currentCard);

            counter--;

            if (counter != currentCardVal || currentCard.GetSuit() != suit)
                break;

            playCards.add(this.m_hand.elementAt(i));

            // Straight has been found
            if (playCards.size() == 5)
                break;
        }

        if (playCards.size() == 5)
            return playCards;

        playCards.clear();
        return playCards;
    }
/*Vector<Card> AI::CreateStraightFlush(Card a_card);*/

/**/
/*
AI::FindFullHouse(Vector<Card> a_tableCards)

NAME

    AI::FindFullHouse - function attempts to find a full house

SYNOPSIS

    Vector<Card> AI::FindFullHouse(Vector<Card> a_tableCards);
        a_tableCards    -> Vector of cards representing the table

DESCRIPTION

    The function first checks to see if the table has a full house to beat. Then it will determine the multiple
    from the full house to beat. Then the function will iterate through the player's hand and find a card that
    is higher than the multiple to beat. Then it will call a function CreateFullHouse passing that card as a parameter
    that will create a full house. If the function returns a full house then it will return the cards as a vector.
    If not, then the function will continue. If a full house was not found, then the function will return an
    empty vector.

RETURNS

    Returns a vector containing a Full House. If a Full House was not found, then an empty Vector.

AUTHOR

    Jay Pendon

DATE

    10:33am 4/19/2020

*/
/**/
    private Vector<Card> FindFullHouse(Vector<Card> a_tableCards)
    {
        Card cardToBeat = new Card();

        if (a_tableCards.size() == 5)
            cardToBeat = FindMultipleToBeat(a_tableCards, 3);

        for (Card card: this.m_hand)
        {
            if (CheckCardValue(card) < CheckCardValue(cardToBeat))
                continue;

            Vector<Card> fullHouseVec = CreateFullHouse(card, 3);

            if (fullHouseVec.size() == 5)
                return fullHouseVec;
        }

        return new Vector<Card>() ;
    }
/*Vector<Card> AI::FindFullHouse(Vector<Card> a_tableCards);*/

/**/
/*
AI::FindMultipleToBeat(Vector<Card> a_tableCards, int a_numCards)

NAME

    AI::FindMultipleToBeat - function that attempts to find the multiple to beat

SYNOPSIS

    Card AI::FindMultipleToBeat(Vector<Card> a_tableCards, int a_numCards);
        a_tableCards    -> Vector of cards representing the table
        a_numCards      -> number of cards to be found from the player's hand
DESCRIPTION

    The function iterates through a_tableCards. Using each Card from a_tableCards, the function will call FindMultiple
    that uses a_tableCards and a_numCards as parameters. The return value is assigned to a local variable and determines
    if the size of the multiple is equal to a_numCards. If it is, then the multiple is found and a the first
    element of the multiple is returned.

RETURNS

    Returns a Card that represents the multiple to be beaten.

AUTHOR

    Jay Pendon

DATE

    10:23am 4/19/2020

*/
/**/
    private Card FindMultipleToBeat(Vector<Card> a_tableCards, int a_numCards)
    {
        Vector<Card> multiple = new Vector<Card>();

        for (int i = 0; i < a_tableCards.size(); i++)
        {
            multiple = FindMultiple(a_tableCards, a_tableCards.elementAt(i));

            if (multiple.size() == a_numCards)
                break;
        }

        if (multiple.size() == 0)
            return new Card();

        return multiple.firstElement();
    }
/*Card AI::FindMultipleToBeat(Vector<Card> a_tableCards, int a_numCards);*/

/**/
/*
AI::FindMultiple(Vector<Card> a_cards, Card a_card)

NAME

    AI::FindMultiple - Finds the multiple from a given vector

SYNOPSIS

    Vector<Card> AI::FindMultiple(Vector<Card> a_cards, Card a_card);
        a_cards -> Vector of Cards
        a_card  -> A Card to be used for its value

DESCRIPTION

    The function iterates through a_cards and attempts to find cards of the same value
    a_card. The function will then return a Vector of cards that have the same value as a_card.

RETURNS

    Returns a Vector of Cards that have the same value.

AUTHOR

    Jay Pendon

DATE

    10:11am 4/19/2020

*/
/**/
    private Vector<Card> FindMultiple(Vector<Card> a_cards, Card a_card)
    {
        Vector<Card> multiple = new Vector<Card>();

        for (Card handCard: a_cards)
        {
            if (a_card.GetValue() == handCard.GetValue())
                multiple.add(handCard);
        }

        return multiple;
    }
/*Vector<Card> AI::FindMultiple(Vector<Card> a_cards, Card a_card);*/

/**/
/*
AI::FindMultipleSameVal(Vector<Card> a_cardsUsed, Card a_card, int a_numCards)

NAME
    AI::FindMultiple - function that attempts to find a multiple
SYNOPSIS

    Vector<Card> AI::FindMultiple(Vector<Card> a_cards, Card a_card, int a_numCards);
        a_cards     -> Vector of Cards
        a_card      -> A Card to be used for its value
        a_numCards  -> Number of Cards to found

DESCRIPTION

    The function iterates through a_cards and attempts to find cards of the same value
    a_card. The function will then return a Vector of cards that have the same value as a_card and the vector
    has a size equal to a_numCards.

RETURNS

    Returns a Vector of Cards that have the same value.

AUTHOR

    Jay Pendon

DATE

    10:11am 4/19/2020

*/
/**/
    private Vector<Card> FindMultiple(Vector<Card> a_cardsUsed, Card a_card, int a_numCards)
    {
        if (IsCardInUsed(a_cardsUsed, a_card))
            return a_cardsUsed;

        int cardVal = CheckCardValue(a_card);
        int cardCounter = 1;

        Vector<Card> multipleCards = new Vector<Card>();

        multipleCards.add(a_card);

        for (int i = 0; i < this.m_hand.size(); i++)
        {
            Card currentCard = this.m_hand.elementAt(i);

            if (CheckCardValue(currentCard) != cardVal)
                continue;

            if (IsCardInUsed(a_cardsUsed, currentCard))
                continue;

            if (IsCardInUsed(multipleCards, currentCard))
                continue;

            multipleCards.add(currentCard);
            cardCounter++;

            if (a_numCards == cardCounter)
                return multipleCards;
        }

        multipleCards.clear();
        return multipleCards;
    }
/*Vector<Card> FindMultipleSameVal(Vector<Card> a_cardsUsed, Card a_card, int a_numCards);*/

/**/
/*
AI::IsCardInUsed(Vector<Card> a_cardsUsed, Card a_card)

NAME

    AI::IsCardInUsed - Function that determines if the card is used in a vector

SYNOPSIS

    Boolean AI::IsCardInUsed(Vector<Card> a_cardsUsed, Card a_card);

DESCRIPTION

    The function iterates through the vector and to see if a_card is in the vector.

RETURNS

    Returns true if a_card is in a_cardsUsed. Otherwise it will return false.

AUTHOR

    Jay Pendon

DATE

    6:45am 2/20/2020

*/
/**/
    private Boolean IsCardInUsed(Vector<Card> a_cardsUsed, Card a_card)
    {
        for (int i = 0; i < a_cardsUsed.size(); i++)
        {
            if (a_card.GetCardName().equals(a_cardsUsed.elementAt(i).GetCardName()))
                return true;
        }

        return false;
    }
/*Boolean AI::IsCardInUsed(Vector<Card> a_cardsUsed, Card a_card);*/

/**/
/*
AI::FindStraight(Vector<Card> a_tableCards)

NAME

    AI::FindStraight - Attempts to find a straight from player's hand

SYNOPSIS

    Vector<Card> FindStraight(Vector<Card> a_tableCards);
        a_tableCards    -> Vector of Cards representing the table

DESCRIPTION

    The function attempts to find a Straight from the player's hand. First, the function determines
    if their is a straight to beat from the table. If there is, then the function finds the highest Card
    from the table to be compared. Then the function iterates through the player's hand in order to create
    Straights. If a Straight has been found then, the highest card from the straight is compared to the highest
    card from the table. If it the card is better than the table, then the vector is returned. If not then, the
    function continues to iterate through the hand. If a straight has not been found, then an
    empty vector is returned.

RETURNS

    Returns a Vector of Cards if a Straight has been found. If not, then an empty vector is returned.

AUTHOR

    Jay Pendon

DATE

    7:14am 2/24/2020

*/
/**/
    private Vector<Card> FindStraight(Vector<Card> a_tableCards)
    {
        Vector<Card> playCards = new Vector<Card>();

        Card highestCard = new Card();

        if (!a_tableCards.isEmpty())
            highestCard = a_tableCards.lastElement();

        for (int i = 0; i < this.m_hand.size(); i++)
        {
            playCards = CreateStraight(this.m_hand.elementAt(i));

            if (playCards.size() != 5)
                continue;

            SortCards(playCards);

            if (CheckCardValue(highestCard) < CheckCardValue(playCards.lastElement()))
                break;
        }

        return playCards;
    }
/*Vector<Card> AI::FindStraight(Vector<Card> a_tableCards);*/

/**/
/*
AI::CreateStraight(Card a_card)

NAME

    AI::CreateStraight - function creates a straight with a given card

SYNOPSIS

    Vector<Card> AI::CreateStraight(Card a_card);
        a_card  -> Card object to be used to create a Straight.

DESCRIPTION

    The function adds a_card to a vector that represents a Straight. Then a_card's value is used
    as a counter to be used to find the next value of the Straight. The card is found by using the function
    FindCardVal. When five cards are found, the cards are returned as a vector. If a Straight has not been found,
    then an empty vector is returned.

RETURNS

    Returns a Vector of Cards if a Straight has been found. If not, then an empty vector is returned.

AUTHOR

    Jay Pendon

DATE

    7:14am 2/24/2020

*/
/**/
    private Vector<Card> CreateStraight(Card a_card)
    {
        Vector<Card> playCards = new Vector<Card>();
        playCards.add(a_card);

        int counter = CheckCardValue(a_card);
        counter++;

        int highestVal = counter + 4;

        for (; counter < highestVal;)
        {
           Card foundCard =  FindCardVal(counter);

           if (foundCard == null)
               break;

            playCards.add(foundCard);
            counter++;

           if (playCards.size() == 5)
               break;
        }

        if (playCards.size() == 5)
            return playCards;

        playCards.clear();
        return playCards;

    }
/*Vector<Card> AI::CreateStraight(Card a_card);*/

/**/
/*
AI::FindCardVal(int a_cardVal)

NAME

    AI::FindCardVal - Finds a Card with the specified value from hand

SYNOPSIS

    Card AI::FindCardVal(int a_cardVal);
        a_cardVal   -> Value of the card to be found

DESCRIPTION

    The function iterates through the player's hand until the specified value has been found.

RETURNS

    Returns the card with the given value from the player's hand. If not a null object.

AUTHOR

    Jay Pendon

DATE

    6:44am 2/24/2020

*/
/**/
    private Card FindCardVal(int a_cardVal)
    {
        for (Card a_card: this.m_hand)
        {
            if (this.m_valueRanking.indexOf(a_card.GetValue()) == a_cardVal)
                return a_card;
        }

        return null;
    }
/*Card AI::FindCardVal(int a_cardVal);*/

/**/
/*
AI::ChooseMultiple(Vector<Card> a_tableCards)

NAME

    AI::ChooseMultiple - Functions chooses a multiple to be played

SYNOPSIS

    Vector<Card> AI::ChooseMultiple(Vector<Card> a_tableCards);
        a_tableCards    -> Vector of Cards representing the table

DESCRIPTION

    The function determines the size of the tables on the cards. Then function iterates through
    the player's hand and calls FindMultiple that returns a vector of cards of a multiple. If a multiple has been
    found and is better than the table's cards. Then it is returned.

RETURNS

    Returns a Vector of Cards that represents a multiple that beats the table. If not, then an
    empty vector of cards is returned.
AUTHOR

    Jay Pendon

DATE

    5:42pm 2/21/2020
*/
/**/
    private Vector<Card> ChooseMultiple(Vector<Card> a_tableCards)
    {
        Vector<Card> playCards = new Vector<Card>();
        int numCards = a_tableCards.size();

        for (int i = 0; i < this.m_hand.size(); i++)
        {
            playCards = FindMultiple(this.m_hand.elementAt(i), numCards);

            if (playCards.size() != numCards)
                continue;

            if (playCards.isEmpty())
                continue;

            if (CheckCardValue(a_tableCards.firstElement()) > CheckCardValue(playCards.firstElement()))
                continue;

            RemoveCardsFromHand(playCards);
            return playCards;
        }

        return new Vector<Card>();
    }
/*Vector<Card> AI::ChooseMultiple(Vector<Card> a_tableCards);*/

/**/
/*
AI::FindMultiple(Card a_card, int a_numCards)

NAME

    AI::FindMultiple - Functions finds a multiple

SYNOPSIS

    Vector<Card> AI::ChooseMultiple(Card a_card, int a_numCards);
        a_tableCards    -> Vector of Cards representing the table
        a_numCards      -> Number of cards to be found
DESCRIPTION

    The function adds a_card to a vector. Then the function iterate through the player's hand looking for
    cards with the same value. If a Card has the same value, then it is added a vector. The loop continues until
    the vector containing the multiple has the same size of a_numCards. When required cards have been found,
    the function will return the vector. If not then an empty vector is returned.

RETURNS

    Returns a Vector of Cards that represents a multiple that contains the same value as a_card. If not, then an
    empty vector of cards is returned.

AUTHOR

    Jay Pendon

DATE

    5:42pm 2/21/2020
*/
/**/
    private Vector<Card> FindMultiple(Card a_card, int a_numCards)
    {
        Vector<Card> playCards = new Vector<Card>();
        playCards.add(a_card);

        for (int i = 0; i < this.m_hand.size(); i++)
        {
            if (a_card.GetCardName() == this.m_hand.elementAt(i).GetCardName())
                continue;

            if (a_card.GetValue() == this.m_hand.elementAt(i).GetValue())
                playCards.add(this.m_hand.elementAt(i));

            if (playCards.size() == a_numCards)
                return playCards;
        }

        return new Vector<Card>();
    }
/*Vector<Card> AI::FindMultiple(Card a_card, int a_numCards);*/

/**/
/*
AI::ChooseSingle(Vector<Card> a_tableCards)

NAME

    AI::ChooseSingle - function that chooses a single card from hand

SYNOPSIS

    Vector<Card> AI::ChooseSingle(Vector<Card> a_tableCards);
        a_tableCards    -> Vector of Cards representing the table's cards

DESCRIPTION

    The function iterates through the table in order to find a Card that is better than the table's card.

RETURNS

    Returns a Vector containing one card if the player's card can beat the table. If there are no cards from
    the player's hand that can beat the table, then an empty vector is returned.
AUTHOR

    Jay Pendon

DATE

    5:49am 2/19/2020

*/
/**/
    private Vector<Card> ChooseSingle(Vector<Card> a_tableCards)
    {
        Vector<Card> playCards = new Vector<Card>();
        Card card = new Card();

        if (a_tableCards.size() != 0)
            card = a_tableCards.firstElement();

        if (a_tableCards.size() == 0)
        {
            card = this.m_hand.firstElement();
            playCards.add(card);
            return playCards;
        }

        for (int i = 0; i < this.m_hand.size(); i++)
        {
            Card currentCard = this.m_hand.elementAt(i);

            if (CheckCardValue(currentCard) < CheckCardValue(card))
                continue;

            if (CheckCardValue(currentCard) == CheckCardValue(card) &&(!CompareSuitValue(currentCard, card)))
                continue;

            playCards.add(currentCard);
            break;
        }

        RemoveCardsFromHand(playCards);

        return playCards;
    }
/*Vector<Card> AI::ChooseSingle(Vector<Card> a_tableCards);*/

/**/
/*
AI::RemoveCardsFromHand(Vector<Card> a_playedCards)

NAME

    AI::RemoveCardsFromHand - Removes the cards from the player's hand

SYNOPSIS

    void AI::RemoveCardsFromHand(Vector<Card> a_playedCards);
        a_playedCards   -> Vector of Cards to be removed from the player's hand

DESCRIPTION

    The function iterates through a_playedCards and calls RemoveCardFromHand in order to remove a Card.

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:40am 2/19/2020

*/
/**/
    private void RemoveCardsFromHand(Vector<Card> a_playedCards)
    {
        for(int i = 0; i < a_playedCards.size(); i++)
            RemoveCardFromHand(a_playedCards.elementAt(i));
    }
/*void RemoveCardsFromHand(Vector<Card> a_playedCards);*/
}
