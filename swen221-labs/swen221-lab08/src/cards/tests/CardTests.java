package cards.tests;

import java.util.*;

import static org.junit.Assert.*;
import static cards.core.Card.Suit.*;
import static cards.core.Card.Rank.*;
import cards.core.*;
import cards.core.Card.Suit;
import cards.util.*;

import org.junit.Test;

public class CardTests {

	// ===========================================================================
	// Part 1 --- Card comparisons
	// ===========================================================================
	
	@Test public void testCardEquals() {
		List<Card> deck1 = AbstractCardGame.createDeck();
		List<Card> deck2 = AbstractCardGame.createDeck();
		for(int i=0;i!=52;++i) {
			Card c1 = deck1.get(i);
			Card c2 = deck2.get(i);				
			if(!c1.equals(c2)) {
				fail("CARD: " + c1 + " should equal " + c2);
			}			
		}
	}
	
	@Test public void testCardNotEquals() {
		List<Card> deck1 = AbstractCardGame.createDeck();		
		for(int i=0;i!=52;++i) {
			for(int j=0;j!=52;++j) {
				if(i != j) {
					Card c1 = deck1.get(i);
					Card c2 = deck1.get(j);				
					if(c1.equals(c2)) {
						fail("CARD: " + c1 + " should not equal " + c2);
					}			
				}
			}
		}
	}
	
	@Test public void testCardCompareTo() {
		List<Card> deck1 = AbstractCardGame.createDeck();
		List<Card> deck2 = AbstractCardGame.createDeck();
		for(int i=1;i!=52;++i) {
			Card c1 = deck1.get(i-1);
			Card c2 = deck1.get(i);
			Card c3 = deck2.get(i);
			if(c1.compareTo(c2) >= 0) {
				fail("Card " + c1 + " should be less than " + c2);
			}
			if(c2.compareTo(c1) <= 0) {
				fail("Card " + c2 + " should be greater than " + c1);
			}
			if(c3.compareTo(c2) != 0) {
				fail("Card " + c3 + " should equal " + c2);
			}
		}
	}	

	// ===========================================================================
	// Part 2 --- Valid Plays
	// ===========================================================================
	@Test public void testValidPlay_1() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { };
		Card hand[] = { new Card(HEARTS,TWO),new Card(HEARTS,QUEEN), new Card(SPADES,ACE) };
		// attempt to play card not in hand
		checkInvalidPlay(new Card(CLUBS,TWO),Player.Direction.NORTH,trumps,played,hand);
	}
	
	@Test public void testValidPlay_2() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { new Card(HEARTS,THREE) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(HEARTS,QUEEN), new Card(SPADES,ACE) };
		// attempt to play card out of turn
		checkInvalidPlay(hand[0],Player.Direction.SOUTH,trumps,played,hand);
	}
	
	@Test public void testValidPlay_3() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { new Card(SPADES,THREE) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(HEARTS,QUEEN), new Card(SPADES,ACE) };
		// attempt to play card (trumps) which doesn't follow suit
		checkInvalidPlay(hand[0],Player.Direction.EAST,trumps,played,hand);
	}
	
	@Test public void testValidPlay_4() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { new Card(SPADES,THREE) };
		Card hand[] = { new Card(CLUBS,TWO),new Card(DIAMONDS,QUEEN), new Card(SPADES,ACE) };
		// attempt to play card (not trumps) which doesn't follow suit
		checkInvalidPlay(hand[0],Player.Direction.EAST,trumps,played,hand);
	}
	
	@Test public void testValidPlay_5() throws IllegalMove {
		Card.Suit trumps = null;
		Card[] played = { new Card(SPADES,THREE) };
		Card hand[] = { new Card(CLUBS,TWO),new Card(DIAMONDS,QUEEN), new Card(SPADES,ACE) };
		// attempt to play card (no trumps) which doesn't follow suit
		checkInvalidPlay(hand[0],Player.Direction.EAST,trumps,played,hand);
	}
	
	private void checkInvalidPlay(Card cardToPlay, Player.Direction player, Card.Suit trumps, Card[] played, Card[] hand) throws IllegalMove {
		// First, calculate AI's direction and setup Trick. We're assuming that
		// NORTH always leads.
		Trick trick = new Trick(Player.Direction.NORTH,trumps);
		Player.Direction d = Player.Direction.NORTH;
		for(int i=0;i!=played.length;++i) {
			Player ghost = new Player(d);
			ghost.getHand().add(played[i]);
			trick.play(ghost, played[i]);
			d = d.next();
		}
		Player avatar = new Player(player);
		// Second, set up AI's hand
		for(Card c : hand) {
			avatar.getHand().add(c);
		}
		// Third, attempt to play card!
		try {
			trick.play(avatar, cardToPlay);
		} catch(IllegalMove e) {
			return; // OK
		}
		fail("Invalid card was played, but not detetected");		
	}
	// ===========================================================================
	// Part 3 --- Simple Computer AI
	// ===========================================================================
	@Test public void testSimpleAI_1() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { };
		Card hand[] = { new Card(HEARTS,TWO),new Card(HEARTS,QUEEN), new Card(SPADES,ACE) };
		// lead strongest (trumps)
		checkCardPlayed(hand[1],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_2() throws IllegalMove {
		Card.Suit trumps = null;
		Card[] played = { };
		Card hand[] = { new Card(DIAMONDS, ACE), new Card(HEARTS,TWO),new Card(HEARTS,QUEEN) };
		// lead strongest (no trumps)
		checkCardPlayed(hand[0],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_3() throws IllegalMove {
		Card.Suit trumps = CLUBS;
		Card[] played = { };
		Card hand[] = { new Card(DIAMONDS, ACE), new Card(HEARTS,TWO),new Card(HEARTS,QUEEN), new Card(SPADES, TEN) };
		// lead strongest (trumps not available)
		checkCardPlayed(hand[0],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_4() throws IllegalMove {
		Card.Suit trumps = CLUBS;
		Card[] played = { };
		Card hand[] = { new Card(DIAMONDS, ACE), new Card(HEARTS,TWO),new Card(HEARTS,QUEEN), new Card(SPADES, ACE) };
		// lead strongest (trumps not available)
		checkCardPlayed(hand[3],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_5() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { new Card(HEARTS,ACE),new Card(HEARTS,KING) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(HEARTS,QUEEN) };
		// follow suit (trumps), discard
		checkCardPlayed(hand[0],trumps,played,hand);
	}

	@Test public void testSimpleAI_6() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { new Card(CLUBS,ACE),new Card(CLUBS,KING) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(CLUBS,QUEEN),new Card(CLUBS,THREE),new Card(CLUBS,TEN) };
		// follow suit (not trumps), discard
		checkCardPlayed(hand[2],trumps,played,hand);
	}

	@Test public void testSimpleAI_7() throws IllegalMove {
		Card.Suit trumps = null;
		Card[] played = { new Card(CLUBS,ACE),new Card(CLUBS,KING) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(CLUBS,QUEEN),new Card(CLUBS,THREE),new Card(CLUBS,TEN) };
		// follow suit (no trumps), discard
		checkCardPlayed(hand[2],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_8() throws IllegalMove {
		Card.Suit trumps = CLUBS;
		Card[] played = { new Card(CLUBS,FIVE),new Card(CLUBS,SEVEN) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(CLUBS,QUEEN),new Card(CLUBS,TEN) };
		// follow suit (trumps), try and win with queen 
		checkCardPlayed(hand[1],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_9() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { new Card(CLUBS,FIVE),new Card(CLUBS,SEVEN) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(CLUBS,QUEEN),new Card(CLUBS,TEN) };
		// follow suit (not trumps), try and win with queen 
		checkCardPlayed(hand[1],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_10() throws IllegalMove {
		Card.Suit trumps = null;
		Card[] played = { new Card(CLUBS,FIVE),new Card(CLUBS,SEVEN) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(CLUBS,QUEEN),new Card(CLUBS,TEN) };
		// follow suit (no trumps), try and win with queen 
		checkCardPlayed(hand[1],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_11() throws IllegalMove {
		Card.Suit trumps = DIAMONDS;
		Card[] played = { new Card(DIAMONDS,ACE),new Card(CLUBS,KING) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(SPADES,QUEEN) };
		// can't follow suit (trumps), discard
		checkCardPlayed(hand[0],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_12() throws IllegalMove {
		Card.Suit trumps = DIAMONDS;
		Card[] played = { new Card(CLUBS,ACE),new Card(CLUBS,KING) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(SPADES,QUEEN) };
		// can't follow suit (not trumps), discard
		checkCardPlayed(hand[0],trumps,played,hand);
	}		
	
	@Test public void testSimpleAI_13() throws IllegalMove {
		Card.Suit trumps = null;
		Card[] played = { new Card(SPADES,QUEEN),new Card(CLUBS,KING) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(SPADES,ACE) };
		// can't follow suit (no trumps), win with ace
		checkCardPlayed(hand[1],trumps,played,hand);
	}		
	
	@Test public void testSimpleAI_14() throws IllegalMove {
		Card.Suit trumps = DIAMONDS;
		Card[] played = { new Card(DIAMONDS,ACE),new Card(CLUBS,KING) };
		Card hand[] = { new Card(HEARTS,TWO),new Card(SPADES,TWO) };
		// can't follow suit (trumps), discard 
		checkCardPlayed(hand[0],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_15() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { new Card(HEARTS,TEN),new Card(HEARTS,FIVE),new Card(HEARTS,NINE) };
		Card hand[] = { new Card(HEARTS,JACK),new Card(SPADES,ACE),new Card(HEARTS,KING) };
		// follow suit (trumps), must win with jack
		checkCardPlayed(hand[0],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_16() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { new Card(CLUBS,TWO),new Card(SPADES,FIVE),new Card(DIAMONDS,NINE) };
		Card hand[] = { new Card(CLUBS,FIVE),new Card(HEARTS,ACE),new Card(CLUBS,KING) };
		// follow suit (not trumps), must win with five
		checkCardPlayed(hand[0],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_17() throws IllegalMove {
		Card.Suit trumps = null;
		Card[] played = { new Card(CLUBS,TWO),new Card(SPADES,FIVE),new Card(SPADES,NINE) };
		Card hand[] = { new Card(CLUBS,FIVE),new Card(HEARTS,ACE),new Card(CLUBS,KING) };
		// follow suit (no trumps), must win with five
		checkCardPlayed(hand[0],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_18() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { new Card(CLUBS,TWO),new Card(SPADES,FIVE),new Card(SPADES,NINE) };
		Card hand[] = { new Card(HEARTS,TEN),new Card(HEARTS,ACE),new Card(SPADES,KING) };
		// can't follow suit (not trumps), must win with ten
		checkCardPlayed(hand[0],trumps,played,hand);
	}
	
	@Test public void testSimpleAI_19() throws IllegalMove {
		Card.Suit trumps = HEARTS;
		Card[] played = { new Card(CLUBS,TWO),new Card(HEARTS,JACK),new Card(SPADES,NINE) };
		Card hand[] = { new Card(HEARTS,TEN),new Card(HEARTS,ACE),new Card(SPADES,KING),new Card(HEARTS,QUEEN) };
		// can't follow suit (not trumps), must win with Queen
		checkCardPlayed(hand[3],trumps,played,hand);
	}
	
	private void checkCardPlayed(Card expected, Card.Suit trumps,
			Card[] played, Card[] hand) throws IllegalMove {
		
		// First, calculate AI's direction and setup Trick. We're assuming that
		// NORTH always leads.
		Trick trick = new Trick(Player.Direction.NORTH,trumps);
		Player.Direction d = Player.Direction.NORTH;
		for(int i=0;i!=played.length;++i) {
			Player ghost = new Player(d);
			ghost.getHand().add(played[i]);
			trick.play(ghost, played[i]);
			d = d.next();
		}
		Player computerPlayer = new Player(d);
		// Second, set up AI's hand
		for(Card c : hand) {
			computerPlayer.getHand().add(c);
		}
		// Third, check card that player will pick
		SimpleComputerPlayer ai = new SimpleComputerPlayer(computerPlayer);
		Card next = ai.getNextCard(trick);
		if (next.suit() != expected.suit() || next.rank() != expected.rank()) {
			fail("Invalid card drawn by AI (was " + next + ", expected "
					+ expected + ")");
		}
	}
}
