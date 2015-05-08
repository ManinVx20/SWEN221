package cards.variations;

import java.util.List;
import cards.core.Card;
import cards.core.CardGame;
import cards.core.Player;
import cards.core.Trick;
import cards.util.AbstractCardGame;

/**
 * A simple variation of Whist where only a single hand is played.
 * 
 * @author David J. Pearce
 * 
 */
public class ClassicWhist extends AbstractCardGame {

	public ClassicWhist() {

	}

	public String getName() {
		return "Single Hand Whist";
	}
	
	public boolean isGameFinished() {
		for (Player.Direction d : Player.Direction.values()) {
			if (scores.get(d) == 5) {
				return true;
			}
		}
		return false;
	}
	
	public void deal(List<Card> deck) {	
		currentTrick = null;
		for (Player.Direction d : Player.Direction.values()) {
			players.get(d).getHand().clear();
		}
		Player.Direction d = Player.Direction.NORTH;
		for (int i = 0; i < deck.size(); ++i) {
			Card card = deck.get(i);
			players.get(d).getHand().add(card);
			d = d.next();
		}		
	}	
	
	@Override
	public CardGame clone() {
		
		ClassicWhist clone = new ClassicWhist();
		clone.players.putAll(this.players);
		clone.scores.putAll(this.scores);
		clone.tricks.putAll(this.tricks);
		clone.trumps = this.trumps;
		if(currentTrick != null) {
			clone.currentTrick = (Trick) currentTrick.clone();
		}
		
		return clone;
	}
	
}
