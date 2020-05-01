package sample.model;

import java.util.Vector;

public class Human extends Player{

/**/
/*
Human::Human()

NAME

    Human::Human()

SYNOPSIS

    Human::Human() - Default constructor for Human

DESCRIPTION

    The function is a default constructor for a Human object.

RETURNS

AUTHOR

    Jay Pendon

DATE

    8:01am 4/19/2020

*/
/**/
    public Human() {}

/**/
/*
Human::Human(int a_playerId)

NAME

    Human::Human - Constructor for Human
SYNOPSIS

    Human::Human(int a_playerId);
        a_playerId      -> Integer representing the player's id.

DESCRIPTION

    This function is a constructor for Human that sets the player id.

RETURNS

AUTHOR

    Jay Pendon

DATE

    8:01am 4/19/2020

*/
/**/
    public Human(int a_playerId)
    {
        super(a_playerId);
    }

/**/
/*
Human::GetInvalidActionString(Vector<Card> a_cards, Vector<Card> a_tableCards)

NAME

    Human::GetInvalidActionString - Returns the String for Invalid Actions

SYNOPSIS

    String Human::GetInvalidActionString(Vector<Card> a_cards, Vector<Card> a_tableCards);
        a_cards         -> Vector of Cards selected by the player
        a_tableCards    -> Vector of Cards representing the table

DESCRIPTION

    The function determines the invalid action based on the selected cards (a_cards). Then functions
RETURNS

AUTHOR

    Jay Pendon

DATE

    10:11am 4/19/2020

*/
/**/
    public String GetInvalidActionString(Vector<Card> a_cards, Vector<Card> a_tableCards)
    {
        if (GetContainsS3())
            return "The Cards you played must Contain the 3 of Clubs";

        if (a_cards.size() == 5)
            return GetInvalidSet(a_cards);

        if (a_cards.size() > 2)
            return GetInvalidMultiple(a_cards);

        if (a_cards.size() == 1)
            return "The Value of the Card is Lower than the Card on the Table";

        if (a_cards.size() != a_tableCards.size())
            return "The Cards you played differ from the amount of Cards on the Table";

        return "You have Selected Zero Cards.\nDid you mean to Pass the Turn?";
    }
/*String Human::GetInvalidActionString(Vector<Card> a_cards, Vector<Card> a_tableCards);*/

/**/
/*
Human::GetInvalidSet(Vector<Card> a_cards)

NAME

    Human::GetInvalidSet - Returns a string for invalid sets.

SYNOPSIS

    String Human::GetInvalidSet(Vector<Card> a_cards);
        a_cards     -> Vector of Cards selected by the player

DESCRIPTION

    The function calls DetermineFiveCardType in order to determine the type of set the player
    selected. Then depending on the string returned by the function, the function returns the error
    based on cards selected.

RETURNS

    Returns a string that represents the error made by the player.

AUTHOR

    Jay Pendon

DATE

    10:17am 4/19/2020

*/
/**/
    public String GetInvalidSet(Vector<Card> a_cards)
    {
        String typeOfSet = DetermineFiveCardType(a_cards);

        if (typeOfSet.equals("NULL"))
            return "Invalid Set of Cards";

        return "Set of Cards Have a Value Lower than the Cards on the Table";
    }
/*String Human::GetInvalidSet(Vector<Card> a_cards);*/

/**/
/*
Human::GetInvalidMultiple(Vector<Card> a_cards)

NAME

    Human::GetInvalidMultiple - Returns a string for invalid multiple.

SYNOPSIS

    String Human::GetInvalidMultiple(Vector<Card> a_cards)
        a_cards     -> Vector of Cards selected by the player

DESCRIPTION

    The function retrieves the first part of a_cards and checks if it is part of a set. If so, then the error
    is because the value is lower than the cards on the table. If not, then the cards are not a multiple.

RETURNS

    Returns a string that represents the error made by the player.

AUTHOR

    Jay Pendon

DATE

    10:17am 4/19/2020

*/
/**/
    public String GetInvalidMultiple(Vector<Card> a_cards)
    {
        Card card = a_cards.firstElement();

        if (IsCardPartOfSet(a_cards, card, a_cards.size()))
            return "Set of Cards Have a Value Lower than the Cards on the Table";

        return "The Cards selected have different Values";
    }
/*String Human::GetInvalidMultiple(Vector<Card> a_cards);*/
}
