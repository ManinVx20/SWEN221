package cards.util;

import java.util.Iterator;
import java.util.Set;
import cards.core.Card;
import cards.core.Player;
import cards.core.Trick;

/**
 * Implements a simple computer player who plays the highest card available when the trick can still be won, otherwise discards the lowest card available. In the special case that the player must win
 * the trick (i.e. this is the last card in the trick), then the player conservatively plays the least card needed to win.
 * 
 * @author David J. Pearce
 * 
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer {

	public SimpleComputerPlayer(Player player) {
		super(player);
	}

	@Override
	public Card getNextCard(Trick trick) {
		Card.Suit trumps = trick.getTrumps();
		Card nextMax = null;
		Card nextMin = null;
		Card winCard = null;
		Set<Card> leadingCards = null;
		if (trick.getLeadSuit() != null) {
			leadingCards = player.getHand().matches(trick.getLeadSuit());
		}
		Iterator<Card> cardsItr = player.getHand().iterator();

		if (trick.getCardsPlayed() != null) {
			for (Card card : trick.getCardsPlayed()) {
				if (card.suit() == trumps || card.suit() == trick.getLeadSuit()) {
					if (winCard == null) {
						winCard = card;
						// compare with trumps to check if would win
					} else if (card.compareWithTrumps(winCard, trumps) > 0) {
						winCard = card;
					}
				}
			}
		}
		
		// AI player can potentially win the trick, then it plays the highest eligible card, otherwise discards the lowest eligible card.
		if (leadingCards != null && leadingCards.size() > 0) {
			if (trick.getCardsPlayed().size() == 3) {
				nextMax = getHighestWinning(leadingCards, winCard, trumps);
			} else {
				nextMax = getHighest(leadingCards, trumps);
			}
			nextMin = getLowest(leadingCards, trumps);
		} else if (player.getHand().matches(trumps).size() > 0) {
			// have no leading cards, play highest trump
			if (trick.getCardsPlayed().size() == 3) {
				nextMax = getHighestWinning(player.getHand().matches(trumps), winCard, trumps);
			} else {
				nextMax = getHighest(player.getHand().matches(trumps), trumps);
			}
			nextMin = getLowest(player.getHand().matches(trumps), trumps);
		} else {
			// otherwise just use highest and lowest cards
			while (cardsItr.hasNext()) {
				Card nextCard = cardsItr.next();
				if (nextMax == null) {
					nextMax = nextCard;
				} else if (nextCard.compareByRank(nextMax) > 0) {
					nextMax = nextCard;
				} else if (nextCard.compareByRank(nextMax) == 0 && nextCard.compareWithTrumps(nextMax, trumps) > 0) {
					nextMax = nextCard;
				}
				if (nextMin == null) {
					nextMin = nextCard;
				} else if (nextCard.compareByRank(nextMin) < 0) {
					nextMin = nextCard;
				} else if (nextCard.compareByRank(nextMin) == 0 && nextCard.compareWithTrumps(nextMin, trumps) < 0) {
					nextMin = nextCard;
				}
			}
		}
		if (winCard == null || nextMax.compareWithTrumps(winCard, trumps) > 0) {
			if (nextMax.suit() == trumps || nextMax.suit() == trick.getLeadSuit() || trick.getLeadSuit() == null) {
				return nextMax;
			}
		}
		return nextMin;
	}

	private Card getLowest(Set<Card> cards, Card.Suit trumps) {
		Card lowest = null;
		for (Card card : cards) {
			if (lowest == null) {
				lowest = card;
			} else if (card.compareWithTrumps(lowest, trumps) < 0) {
				lowest = card;
			}
		}
		return lowest;
	}

	private Card getHighest(Set<Card> cards, Card.Suit trumps) {
		Card highest = null;
		for (Card card : cards) {
			if (highest == null) {
				highest = card;
			} else if (card.compareWithTrumps(highest, trumps) > 0) {
				highest = card;
			}
		}
		return highest;
	}

	private Card getHighestWinning(Set<Card> cards, Card winning, Card.Suit trumps) {
		Card highest = null;
		for (Card card : cards) {
			if (highest == null) {
				highest = card;
			} else if (card.compareWithTrumps(highest, trumps) > 0) {
				if (winning == null || highest.compareWithTrumps(winning, trumps) <= 0) {
					highest = card;
				}
			} else {
				if (winning != null && card.compareWithTrumps(winning, trumps) > 0) {
					highest = card;
				}
			}
		}
		return highest;
	}
}