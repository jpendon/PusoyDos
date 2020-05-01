package sample.model;

public class Card
{
    protected String m_cardName;
    protected char m_value;
    private char m_suit;

/**/
/*
Card::Card()

NAME

    Card::Card - Default Constructor for a Card object

SYNOPSIS

    Card::Card();

DESCRIPTION

    This function will create a Card object with default values.

RETURNS

AUTHOR
    Jay Pendon

DATE
    8:05am 2/17/2020

*/
/**/
    public Card()
    {
        this.m_cardName = "NULL";
        this.m_value = 'N';
        this.m_suit = 'N';
    }
    /*Card::Card();*/

/**/
/*
Card::Card(String a_cardName)

NAME

    Card::Card - A constructor for a card object

SYNOPSIS

    Card::Card(String a_cardName); - Creates a card object
        a_cardName      -> the name of the card to be created

DESCRIPTION

    This function will create a Card object with the specified name of the card.

RETURNS

AUTHOR
    Jay Pendon

DATE
    8:06am 2/17/2020

*/
    /**/
    public Card(String a_cardName)
    {
        this.m_cardName = a_cardName;
        this.m_suit = a_cardName.charAt(0);
        this.m_value = a_cardName.charAt(1);
    }
    /*Card::Card(String a_cardName);*/

/**/
/*
Card::Card(char a_suit, int a_numVal)

NAME
    Card::Card
SYNOPSIS

    Card::Card(char a_suit, int a_numVal);
        a_suit  -> specifies the card's suit
        a_numVal -> determines the value of the card

DESCRIPTION

    This function will create a card with the specified suit and value.

RETURNS

AUTHOR
    Jay Pendon

DATE
    8:07am 2/17/2020

*/
/**/
    public Card(char a_suit, int a_numVal)
    {
        switch(a_numVal)
        {
            case 1:
                this.m_value = 'A';
                break;
            case 10:
                this.m_value = 'X';
                break;
            case 11:
                this.m_value = 'J';
                break;
            case 12:
                this.m_value = 'Q';
                break;
            case 13:
                this.m_value = 'K';
                break;
            default:
                this.m_value = (char)(a_numVal + '0');
                break;
        }

        this.m_suit = a_suit;
        this.m_cardName = "" + this.m_suit + this.m_value;
    }
/*public Card(char a_suit, int a_numVal);*/

/**/
/*
Card::GetCardName()

NAME
    Card::GetCardName - returns the card's name
SYNOPSIS

    String Card::GetCardName();

DESCRIPTION

    Returns the card's name

RETURNS

    Returns the card's name as a string.

AUTHOR
    Jay Pendon

DATE
    8:08am 2/17/2020

*/
/**/

    public String GetCardName() { return this.m_cardName; }

/**/
/*
Card::GetValue()

NAME

    Card::GetValue - returns the card's value
SYNOPSIS

    char Card::GetValue();

DESCRIPTION

    The function will return the card's value as a character.
RETURNS

    Returns a char value indicating the card's value.

AUTHOR

    Jay Pendon

DATE

    8:08am 2/17/2020

*/
/**/
    public char GetValue() { return this.m_value; }

/**/
/*
Card::GetSuit()

NAME

    Card::GetSuit - returns the card's suit
SYNOPSIS

    char Card::GetSuit();

DESCRIPTION

    The function will return the card's suit as a character.
RETURNS

    Returns a char value indicating the card's suit.
AUTHOR

    Jay Pendon

DATE

    8:08am 2/17/2020

*/
/**/
    public char GetSuit() { return this.m_suit; }
}
