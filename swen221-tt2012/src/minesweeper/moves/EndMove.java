package minesweeper.moves;

import minesweeper.*;
import minesweeper.squares.*;

/**
 * Represents a move which ends the game. If all blank squares are exposed, then
 * the player has one. Or, if a bomb is exposed, then the player has lost.
 *
 * @author David J. Pearce
 *
 */
public class EndMove extends ExposeMove {
	private boolean isWinner;
//	private Position position;

	/**
	 * Construct a EndGame object which represents a successful or unsuccessul
	 * outcome.
	 *
	 * @param position
	 *            --- position to which this move applies.
	 * @param isWinner
	 *            --- true if the player has won the game; false if a bomb is
	 *            exposed.
	 */
	public EndMove(Position position, boolean isWinner) {
		super(position);
		this.isWinner = isWinner;
	}

	/**
	 * Apply this move to a given game and check that it is valid. An end move
	 * must either expose a bomb or expose the last remaining blank square.
	 *
	 * @param game
	 *            --- game to which this move is applied.
	 */
	public void apply(Game game) throws SyntaxError {
		super.apply(game);
//		7. A losing move L(x,y) requires the square at position (x,y) contains a bomb.
//		8. A winning move W(x,y) requires that the square at position (x,y) does not contain a bomb.
//		9. A winning move W(x,y) requires that there are no hidden blank squares after the square at
//		position (x,y) is exposed.

		int status = game.getStatus();

		if (status == 0) { // game continues
			throw new SyntaxError("This game is not finished");

		} else if (status ==1 && this.isWinner) { // player has lost
			throw new SyntaxError("Player lost");
		} else if (status ==2 && !this.isWinner) {
			throw new SyntaxError("Player lkjjj");
		}


	}

	public String toString() {
		if(isWinner) {
			return "W" + position;
		} else {
			return "L" + position;
		}
	}
}
