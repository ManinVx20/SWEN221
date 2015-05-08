package cards.variations;

import java.util.List;
import java.util.Set;
import cards.core.Card;
import cards.core.CardGame;
import cards.core.IllegalMove;
import cards.core.Player;
import cards.core.Player.Direction;
import cards.core.Trick;
import cards.util.AbstractCardGame;

/**
 * An implementation of the "classical" rules of Whist.
 * 
 * @author David J. Pearce
 *
 */
public class SingleHandWhist extends AbstractCardGame {

	public SingleHandWhist() {

	}

	public String getName() {
		return "Classic Whist";
	}
	
	public boolean isGameFinished() {
		for (Player.Direction d : Player.Direction.values()) {
			if (scores.get(d) == 1) {
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
		
		SingleHandWhist clone = new SingleHandWhist();
		//clone.players.putAll(this.players);
		
		for(Player.Direction dir : players.keySet()) {
			clone.players.put(dir, (Player) players.get(dir).clone());
		}
		
		clone.scores.putAll(this.scores);
		clone.tricks.putAll(this.tricks);
		clone.trumps = this.trumps;
		if(currentTrick != null) {
			clone.currentTrick = currentTrick.clone();
		}
		
		return clone;
	}
	
}
