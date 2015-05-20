package cards.variations;

import java.util.ArrayList;
import java.util.List;
import cards.core.Card;
import cards.core.CardGame;
import cards.core.Player;
import cards.util.AbstractCardGame;

public class KnockOutWhist extends AbstractCardGame {
	private int hand = 13;
	
	public KnockOutWhist() {

	}

	public String getName() {
		return "Knock-Out Whist";
	}
	
	public boolean isGameFinished() {
		return hand == 0;		
	}
		
	public void deal(List<Card> deck) {	

		try {
			currentTrick = null;
			for (Player.Direction d : Player.Direction.values()) {
				players.get(d).getHand().clear();
			}
			Player.Direction d = Player.Direction.NORTH;
			for (int i = 0; i < hand * 4; ++i) {
				Card card = deck.get(i);
				players.get(d).getHand().add(card);
				d = d.next();
			}			
		} catch(IndexOutOfBoundsException e) {
			return; // OK

		}
	}	
	
	public void endHand() {
		try {
		super.endHand();
		hand = hand - 1;
		} catch (NullPointerException e) {
			return;
		}
	}
	
	@Override
	public CardGame clone() {
		
		KnockOutWhist clone = new KnockOutWhist();
		clone.players.putAll(this.players);
		clone.scores.putAll(this.scores);
		clone.tricks.putAll(this.tricks);
		clone.trumps = this.trumps;
		if(currentTrick != null) {
			clone.currentTrick = currentTrick.clone();
		}
		
		return clone;
	}
	
}
