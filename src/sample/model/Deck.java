package sample.model;

import java.util.Vector;
import java.util.Collections;

public class Deck {
    private Vector<Card> m_deck = new Vector<Card>();

/**/
/*
Deck::Deck()

NAME

    Deck::Deck - Default constructor for a Deck object
SYNOPSIS

    Deck::Deck();

DESCRIPTION

    This is the default constructor for a Deck object.
RETURNS

AUTHOR
    Jay Pendon

DATE

    8:11am 2/17/2020

*/
/**/
    public Deck()
    {
        CreateNewDeck();
    }

/**/
/*
Deck::CreateNewDeck()

NAME
    Deck::CreateNewDeck() - Creates a new deck to be used.
SYNOPSIS

    void Deck::CreateNewDeck();

DESCRIPTION

    This function will create a new deck of 52 cards.

RETURNS

AUTHOR
    Jay Pendon

DATE

    8:13am 2/17/2020

*/
    /**/
    public void CreateNewDeck()
    {
        for(int i = 1; i < 14; i++)
        {
            this.m_deck.add(new Card('S', i));
            this.m_deck.add(new Card('D', i));
            this.m_deck.add(new Card('H', i));
            this.m_deck.add(new Card('C', i));
        }
    }
/* void Deck::CreateNewDeck();*/

/**/
/*
Deck::ShuffleDeck()

NAME
    Deck::ShuffleDeck
SYNOPSIS

    void Deck::ShuffleDeck();

DESCRIPTION

    This function will shuffle the vector m_deck.

RETURNS

AUTHOR

    Jay Pendon

DATE

    8:12am 2/17/2020

*/
/**/
    public void ShuffleDeck() { Collections.shuffle(this.m_deck); }

    /**/
/*
Deck::DrawCard()

NAME

    Deck::DrawCard  - Draws a card from the deck

SYNOPSIS

    Card Deck::DrawCard();

DESCRIPTION

    Draws and removes a card from the Vector m_deck.

RETURNS

    Returns a Card object from the deck.

AUTHOR

    Jay Pendon

DATE

    8:14am 2/17/2020

*/
/**/
    public Card DrawCard()
    {
        Card drawnCard = this.m_deck.firstElement();
        // Remove the card from the deck
        this.m_deck.remove(0);

        return drawnCard;
    }
    /*Card Deck::DrawCard();*/
}
