package cards.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import cards.core.*;
import cards.core.Card.Suit;

/**
 * Implements a simple computer player who plays the highest card available when
 * the trick can still be won, otherwise discards the lowest card available.
 * 
 * @author David J. Pearce
 * 
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer {

	public SimpleComputerPlayer(Player player) {
		super(player);
	}

	public Card getNextCard(Trick trick) {
		Card leadCard = trick.getCardPlayed(trick.getLeadPlayer());

		if (leadCard != null) {
			// we're not the lead player.
			if(canWinTrick(trick)) {
				// attempt to win
				return getHighestCard(leadCard.suit(),trick.getTrumps());
			} else {
				// discard
				return getLowestCard(leadCard.suit(),trick.getTrumps());
			}			
		} else {
			// this player is leading, so always go for the strongest available
			// card.
			return getHighestCard(null,trick.getTrumps());
		}		
	}
	
	/**
	 * Determine whether or not this player has a chance to win the trick.
	 * 
	 * @param trick
	 * @return
	 */
	private boolean canWinTrick(Trick trick) {
		Card leadCard = trick.getCardPlayed(trick.getLeadPlayer());
		if(leadCard != null) {
			Card.Suit leadSuit = leadCard.suit();
			Card highest = getHighestCard(leadSuit,trick.getTrumps());
			return isCurrentWinningCard(highest,leadCard,trick);
		}
		return true;
	}
	
	/**
	 * Determine whether the given card is the current winner of the trick.
	 * 
	 * @param card
	 * @return
	 */
	private boolean isCurrentWinningCard(Card card, Card leadCard, Trick trick) {
		Card.Suit trumps = trick.getTrumps();
		if(card.suit() != trumps) {
			// this card is not a trump
			if(card.suit() != leadCard.suit()) {
				// couldn't follow suit, so can't win
				return false;
			}
		} 
		for(Card c : trick.getCardsPlayed()) {
			if(c.suit() == card.suit()) {
				if(c.rank().compareTo(card.rank()) == 1) {
					// card already played which is stronger than my best
					// effort.
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Get the highest card which the player can play.
	 * 
	 * @param lead
	 * @param trumps
	 * @return
	 */
	private Card getHighestCard(Card.Suit lead, Card.Suit trumps) {
		ArrayList<Card> eligibleCards = sortEligibleByValue(lead,trumps);
		return eligibleCards.get(eligibleCards.size()-1);
	}
	
	/**
	 * Get the loweest card which the player can play.
	 * 
	 * @param lead
	 * @param trumps
	 * @return
	 */
	private Card getLowestCard(Card.Suit lead, Card.Suit trumps) {
		ArrayList<Card> eligibleCards = sortEligibleByValue(lead,trumps);
		return eligibleCards.get(0);
	}
	
	/**
	 * Sort elgible cards by value
	 */
	private ArrayList<Card> sortEligibleByValue(Card.Suit lead, Card.Suit trumps) {
		Hand hand = player.getHand();
		ArrayList<Card> eligibleCards = new ArrayList<Card>();
		// First, check whether we have to follow suit.
		Set<Card> matches = Collections.EMPTY_SET;
		if(lead != null) {
			matches = hand.matches(lead);	
		}
		 
		if(matches.size() > 0) {
			// yes, we do
			eligibleCards.addAll(matches);
		} else {
			// no we don't --- all cards are eligible
			for(Card c : hand) {
				eligibleCards.add(c);
			}
		}
		
		Collections.sort(eligibleCards, new EligibleComparator(trumps));
		
		return eligibleCards;
	}
	
	private class EligibleComparator implements Comparator<Card> {
		private Card.Suit trumps;
		public EligibleComparator(Card.Suit trumps) {
			this.trumps = trumps;
		}
		public int compare(Card c1, Card c2) {			
			if(c1.suit() == trumps && c2.suit() != trumps) {
				return 1;
			} else if(c1.suit() != trumps && c2.suit() == trumps) {
				return -1;
			} else {
				if(c1.rank() == c2.rank()) {
					return c1.suit().compareTo(c2.suit());
				} else {
					return c1.rank().compareTo(c2.rank());
				}
			}
		}
	}
}
