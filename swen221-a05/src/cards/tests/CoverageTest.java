package cards.tests;

import static cards.core.Card.Rank.ACE;
import static cards.core.Card.Rank.FIVE;
import static cards.core.Card.Rank.FOUR;
import static cards.core.Card.Rank.JACK;
import static cards.core.Card.Rank.KING;
import static cards.core.Card.Rank.NINE;
import static cards.core.Card.Rank.QUEEN;
import static cards.core.Card.Rank.SEVEN;
import static cards.core.Card.Rank.TEN;
import static cards.core.Card.Rank.THREE;
import static cards.core.Card.Rank.TWO;
import static cards.core.Card.Suit.CLUBS;
import static cards.core.Card.Suit.DIAMONDS;
import static cards.core.Card.Suit.HEARTS;
import static cards.core.Card.Suit.SPADES;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import cards.core.Card;
import cards.core.CardGame;
import cards.core.IllegalMove;
import cards.core.Player;
import cards.core.Trick;
import cards.core.Player.Direction;
import cards.util.AbstractCardGame;
import cards.util.AbstractComputerPlayer;
import cards.util.SimpleComputerPlayer;
import cards.variations.ClassicWhist;
import cards.variations.KnockOutWhist;
import cards.variations.SingleHandWhist;
import cards.viewer.TableFrame;


public class CoverageTest {
	
	private final Card[] DECK = { new Card(HEARTS, TWO),     // NORTH
			new Card(HEARTS, QUEEN),   // EAST
			new Card(SPADES, ACE),     // SOUTH
			new Card(HEARTS, THREE),   // WEST
			new Card(CLUBS, QUEEN),    // NORTH
			new Card(CLUBS, TWO),      // EAST
			new Card(CLUBS, ACE),      // SOUTH
			new Card(CLUBS, THREE),    // WEST
			new Card(DIAMONDS, QUEEN), // NORTH
			new Card(DIAMONDS, TWO),   // EAST
			new Card(DIAMONDS, TEN),   // SOUTH
			new Card(DIAMONDS, THREE), // WEST
			new Card(SPADES, KING),     // NORTH
			new Card(SPADES, THREE),   // EAST
			new Card(SPADES, QUEEN),   // SOUTH
			new Card(SPADES, FOUR)     // WEST
	};
	
	
	
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
			ghost.getHand().toString();
			trick.play(ghost, played[i]);
			d = d.next();
		}
		Player avatar = new Player(player);
		avatar.toString();
		avatar.getDirection();
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

	@Test public void testTableFrame_01() throws IllegalMove {
//		TableFrame t = new TableFrame();
//		t.timerEvent();
//		t.requestTimerEvent(0);
//		t.clone();
		
	}
	
	
	private void checkCardPlayed(Card expected, Card.Suit trumps,
			Card[] played, Card[] hand) throws IllegalMove {
		
		// First, calculate AI's direction and setup Trick. We're assuming that
		// NORTH always leads.
		Trick trick = new Trick(Player.Direction.NORTH,trumps);
		trick.getLeadPlayer();
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
		AbstractComputerPlayer ai2 = ai;
		ai2.setPlayer(computerPlayer);
		ai2.equals((AbstractComputerPlayer) ai);
		Card next = ai.getNextCard(trick);
		if (next.suit() != expected.suit() || next.rank() != expected.rank()) {
			fail("Invalid card drawn by AI (was " + next + ", expected "
					+ expected + ")");
		}
	}
	
	// CLONE TESTS 
	

		// In all the tests below, the following deck is used. Each card is dealt to
		// the player indicated in the comment.
		

		@Test
		public void testValidClone_1() throws IllegalMove {
			Card before[] = { new Card(HEARTS, TWO),   // NORTH
					new Card(HEARTS, QUEEN), // EAST
					new Card(SPADES, ACE),   // SOUTH
					new Card(HEARTS, THREE)  // WEST
			// EAST WINSx
			};
			Card afterOriginal[] = {};
			Card afterClone[] = {};
			int[] scoresOriginal = { 0, 1, 0, 0 };
			int[] scoresClone = { 0, 1, 0, 0 };
			// East to win trick...
			testValidClone(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
		}

		@Test
		public void testValidClone_2() throws IllegalMove {
			Card[] before = {};
			Card[] afterOriginal = { new Card(HEARTS, TWO),   // NORTH
					new Card(HEARTS, QUEEN), // EAST
					new Card(SPADES, ACE),   // SOUTH
					new Card(HEARTS, THREE)  // WEST
			// EAST WINS
			};
			
			Card[] afterClone = { new Card(CLUBS, QUEEN),  // NORTH
					new Card(CLUBS, TWO),    // EAST
					new Card(CLUBS, ACE),    // SOUTH
					new Card(CLUBS, THREE)   // WEST
			// SOUTH WINS
			};
			int[] scoresOriginal = { 0, 1, 0, 0 };
			int[] scoresClone = { 0, 0, 1, 0 };
			// East to win trick in original, south to win in clone...
			testValidClone(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
		}

		@Test
		public void testValidClone_3() throws IllegalMove {
			Card[] before = { new Card(CLUBS, QUEEN),  // NORTH
					new Card(CLUBS, TWO),    // EAST
					new Card(CLUBS, ACE),    // SOUTH
					new Card(CLUBS, THREE),  // WEST
			// SOUTH WINS
			};
			Card[] afterOriginal = { new Card(SPADES, ACE),     // SOUTH
					new Card(SPADES, FOUR),    // WEST
					new Card(SPADES, KING),    // NORTH
					new Card(SPADES, THREE),   // EAST
			// SOUTH WINS
			};
			Card[] afterClone = { new Card(DIAMONDS, TEN),   // SOUTH
					new Card(DIAMONDS, THREE), // WEST
					new Card(DIAMONDS, QUEEN), // NORTH
					new Card(DIAMONDS, TWO)    // EAST
			// NORTH WINS
			};
			int[] scoresOriginal = { 0, 0, 2, 0 };
			int[] scoresClone = { 1, 0, 1, 0 };
			// East to win trick in original, south to win in clone...
			testValidClone(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
		}

		@Test
		public void testValidClone_4() throws IllegalMove {
			Card[] before = { new Card(HEARTS, TWO),   // NORTH
					new Card(HEARTS, QUEEN), // EAST
					new Card(SPADES, QUEEN), // SOUTH
					new Card(HEARTS, THREE)  // WEST
			// EAST WINS
			};
			Card[] afterOriginal = { new Card(SPADES, THREE),   // EAST
					new Card(SPADES, ACE),     // SOUTH
					new Card(SPADES, FOUR),    // WEST
					new Card(SPADES, KING),    // NORTH
			// SOUTH WINS
			};
			Card[] afterClone = { new Card(DIAMONDS, TWO),   // EAST
					new Card(DIAMONDS, TEN),   // SOUTH
					new Card(DIAMONDS, THREE), // WEST
					new Card(DIAMONDS, QUEEN), // NORTH
					// NORTH WINS
					new Card(CLUBS, QUEEN),  // NORTH
					new Card(CLUBS, TWO),    // EAST
					new Card(CLUBS, ACE),    // SOUTH
					new Card(CLUBS, THREE),  // WEST
			// SOUTH WINS
			};
			int[] scoresOriginal = { 0, 1, 1, 0 };
			int[] scoresClone = { 1, 1, 1, 0 };
			// East to win trick in original, south to win in clone...
			testValidClone(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
		}

		@Test
		public void testValidClone_5() throws IllegalMove {
			Card before[] = { new Card(HEARTS, TWO),   // NORTH
			};
			Card afterOriginal[] = { new Card(HEARTS, QUEEN), // EAST
					new Card(SPADES, ACE),   // SOUTH
					new Card(HEARTS, THREE)  // WEST
			// EAST WINS
			};
			Card afterClone[] = { new Card(HEARTS, QUEEN), // EAST
					new Card(SPADES, QUEEN), // SOUTH
					new Card(HEARTS, THREE),  // WEST
					// EAST WINS
					new Card(SPADES, THREE),   // EAST
					new Card(SPADES, ACE),     // SOUTH
					new Card(SPADES, FOUR),    // WEST
					new Card(SPADES, KING),    // NORTH
			// SOUTH WINS
			};
			int[] scoresOriginal = { 0, 1, 0, 0 };
			int[] scoresClone = { 0, 1, 1, 0 };
			// East to win trick...
			testValidClone(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
			testValidCloneKnock(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
			testValidCloneKnockII(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
			testValidCloneClassic(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
			testValidCloneClassicII(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
			testValidCloneClassicIII(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
		}

		/**
		 * Play a game upto a given point. Then, clone the game in two and play the remainder of each differently. Finally, check that the right scores for each have been calculated.
		 * 
		 * @param before
		 *            Sequence of cards to play up to clone point.
		 * @param afterOriginal
		 *            Sequence of cards to play for original game (after cloning).
		 * @param afterClone
		 *            Sequence of cards to play for cloned game.
		 * @param scoresOriginal
		 *            Expected scores at end of original game
		 * @param scoresCloned
		 *            Expected scores at end of cloned game
		 * @throws IllegalMove
		 */
		private void testValidClone(Card[] before, Card[] afterOriginal, Card[] afterClone, int[] scoresOriginal, int[] scoresClone) throws IllegalMove {
			// First, create a game and deal our deck
			SingleHandWhist game = new SingleHandWhist();
			KnockOutWhist game2 = new KnockOutWhist();
			ClassicWhist game3 = new ClassicWhist();
			game.deal(Arrays.asList(DECK));
			// Second, play all cards up to the clone point
			playCards(game, 0, before);
			// Third, clone the game
			AbstractCardGame clone = (AbstractCardGame) game.clone();
			
			// Fourth, play all cards after the clone point
			playCards(game, before.length, afterOriginal);
			playCards(clone, before.length, afterClone);
			// Fifth, sanity check the original game score
			Map<Direction, Integer> scores = game.getTricksWon();
			Direction d = Direction.NORTH;
			for (int i = 0; i != 4; ++i) {
				int score = scores.get(d);
				if (score != scoresOriginal[i]) {
					fail("Scores for original game are incorrect (player " + d + " has score " + score + ", should be " + scoresOriginal[i] + ")");
				}
				d = d.next();
			}
			// Sixth, sanity check the cloned game score
			scores = clone.getTricksWon();
			d = Direction.NORTH;
			for (int i = 0; i != 4; ++i) {
				int score = scores.get(d);
				if (score != scoresClone[i]) {
					fail("Scores for cloned game are incorrect (player " + d + " has score " + score + ", should be " + scoresClone[i] + ")");
				}
				d = d.next();
			}
			// Done
		}
				
		private void testValidCloneKnock(Card[] before, Card[] afterOriginal, Card[] afterClone, int[] scoresOriginal, int[] scoresClone) throws IllegalMove {
			// First, create a game and deal our deck
			KnockOutWhist game = new KnockOutWhist();
			game.deal(Arrays.asList(DECK));
			playCards(game, 0, before);
			
			playCards(game, before.length, afterOriginal);
			// Fifth, sanity check the original game score
			Map<Direction, Integer> scores = game.getTricksWon();
			Direction d = Direction.NORTH;
			for (int i = 0; i != 4; ++i) {
				int score = scores.get(d);
				if (score != scoresOriginal[i]) {
					fail("Scores for original game are incorrect (player " + d + " has score " + score + ", should be " + scoresOriginal[i] + ")");
				}
				d = d.next();
			}
			
			
			game.clone();
			game.getName();
			game.getClass();
			game.getOverallScores();
			game.getPlayer(Direction.NORTH);
			game.getTrick();
			game.getTricksWon();
			game.getWinnersOfGame();
			game.isGameFinished();
			game.isHandFinished();
			game.endHand();
			} 
		
		private void testValidCloneKnockII(Card[] before, Card[] afterOriginal, Card[] afterClone, int[] scoresOriginal, int[] scoresClone) throws IllegalMove {
			KnockOutWhist game = new KnockOutWhist();
				game.deal(new ArrayList<Card>());
				game.endHand();
		}
		
			
		
		private void testValidCloneClassic(Card[] before, Card[] afterOriginal, Card[] afterClone, int[] scoresOriginal, int[] scoresClone) throws IllegalMove {
			// First, create a game and deal our deck
			ClassicWhist game = new ClassicWhist();
			
			game.deal(Arrays.asList(DECK));
			playCards(game, 0, before);
			
			playCards(game, before.length, afterOriginal);
			// Fifth, sanity check the original game score
			Map<Direction, Integer> scores = game.getTricksWon();
			Direction d = Direction.NORTH;
			for (int i = 0; i != 4; ++i) {
				int score = scores.get(d);
				if (score != scoresOriginal[i]) {
					fail("Scores for original game are incorrect (player " + d + " has score " + score + ", should be " + scoresOriginal[i] + ")");
				}
				d = d.next();
			}
			
			game.clone();
			game.getName();
			game.getClass();
			game.getOverallScores();
			game.getPlayer(Direction.NORTH);
			game.getTrick();
			game.getTricksWon();
			game.getWinnersOfGame();
			game.isGameFinished();
			game.isHandFinished();
			game.deal(Arrays.asList(DECK));
			try {
				game.endHand();
			} catch (NullPointerException e){
				return;
			}
		}
		
		
		private void testValidCloneClassicII(Card[] before, Card[] afterOriginal, Card[] afterClone, int[] scoresOriginal, int[] scoresClone) throws IllegalMove {
			ClassicWhist game = new ClassicWhist();
			try {
				game.deal(new ArrayList<Card>());
			} catch(IndexOutOfBoundsException e) {
				return; // OK

			}
		}
		
		
		private void testValidCloneClassicIII(Card[] before, Card[] afterOriginal, Card[] afterClone, int[] scoresOriginal, int[] scoresClone) throws IllegalMove {
			ClassicWhist game = new ClassicWhist();
			try {
				game.endRound();
			} catch(NullPointerException e) {
				return; // OK

			}
		}

		

		
		
		
		@Test
		public void testValidClone_XX() throws IllegalMove {
			Card before[] = { new Card(HEARTS, TWO),   // NORTH
			};
			Card afterOriginal[] = { new Card(HEARTS, QUEEN), // EAST
					new Card(SPADES, ACE),   // SOUTH
					new Card(HEARTS, THREE)  // WEST
			// EAST WINS
			};
			Card afterClone[] = { new Card(HEARTS, QUEEN), // EAST
					new Card(SPADES, QUEEN), // SOUTH
					new Card(HEARTS, THREE),  // WEST
					// EAST WINS
					new Card(SPADES, THREE),   // EAST
					new Card(SPADES, ACE),     // SOUTH
					new Card(SPADES, FOUR),    // WEST
					new Card(SPADES, KING),    // NORTH
			// SOUTH WINS
			};
			int[] scoresOriginal = { 0, 1, 0, 0 };
			int[] scoresClone = { 0, 1, 1, 0 };
			// East to win trick...
			testValidClone2(before, afterOriginal, afterClone, scoresOriginal, scoresClone);
		}
		
		
		
		
		
		private void testValidClone2(Card[] before, Card[] afterOriginal, Card[] afterClone, int[] scoresOriginal, int[] scoresClone) throws IllegalMove {
			// First, create a game and deal our deck
			SingleHandWhist game = new SingleHandWhist();
			game.deal(Arrays.asList(DECK));
			// Second, play all cards up to the clone point
			playCards(game, 0, before);
			// Third, clone the game
			AbstractCardGame clone = (AbstractCardGame) game.clone();
			
			// Fourth, play all cards after the clone point
			playCards(game, before.length, afterOriginal);
			playCards(clone, before.length, afterClone);
			// Fifth, sanity check the original game score
			Map<Direction, Integer> scores = game.getTricksWon();
			Direction d = Direction.NORTH;
			for (int i = 0; i != 4; ++i) {
				int score = scores.get(d);
				if (score != scoresOriginal[i]) {
					fail("Scores for original game are incorrect (player " + d + " has score " + score + ", should be " + scoresOriginal[i] + ")");
				}
				d = d.next();
			}
			// Sixth, sanity check the cloned game score
			scores = clone.getTricksWon();
			d = Direction.NORTH;
			for (int i = 0; i != 4; ++i) {
				int score = scores.get(d);
				if (score != scoresClone[i]) {
					fail("Scores for cloned game are incorrect (player " + d + " has score " + score + ", should be " + scoresClone[i] + ")");
				}
				d = d.next();
			}
			game.equals(game.clone());
			game.getPlayer(Direction.NORTH);
			game.getOverallScores();
			game.getWinnersOfGame();
			game.hashCode();
			game.isGameFinished();
			game.isHandFinished();
			game.getName();
			game.resetOverallScores();
			game.endHand();
			game.endRound();
			// Done
		}
		
		

		/**
		 * Play a set of given cards in the order they occur. It is assumed that all players have the correct cards to allow this.
		 * 
		 * @param game
		 *            The game we are playing.
		 * @param index
		 *            Gives the index through the entire game that we are starting at.
		 * @param cards
		 *            The sequence of cards to be played, in the order they are to be played.
		 * @throws IllegalMove
		 */
		private void playCards(CardGame game, int index, Card[] cards) throws IllegalMove {
			((AbstractCardGame) game).clone();
			((AbstractCardGame) game).getName();
			((AbstractCardGame) game).getOverallScores();
			((AbstractCardGame) game).getOverallScores();
			for (int i = 0; i != cards.length; ++i) {
				if ((index + i) % 4 == 0) {
					game.startRound();
				}
				Player.Direction player = game.getTrick().getNextToPlay();
				game.play(player, cards[i]);
				if ((index + i) % 4 == 3) {
					game.endRound();
				}
			}

		}
	}
