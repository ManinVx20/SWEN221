package minesweeper.moves;

import minesweeper.*;
import minesweeper.squares.*;

/**
 * Represents a move that exposes a square. The square may contain a bomb, or be blank. However, it should not be flagged.
 *
 * @author David J. Pearce
 *
 */
public class ExposeMove extends Move {

	/**
	 * Construct an ExposeMove at a given position on the board.
	 *
	 * @param position
	 */
	public ExposeMove(Position position) {

		super(position);
	}

	/**
	 * Apply this move to a given game and check it is valid. A square can only be exposed if it is not already exposed. And, if either a bomb is exposed or there are no remaining unexposed squares,
	 * then this should be an EndGame move.
	 *
	 * @param game
	 *            --- game to which this move is applied.
	 */
	public void apply(Game game) throws SyntaxError {

		Square square = game.squareAt(position);
		if (!square.isHidden()) {
			throw new SyntaxError("Error the square is already exposed");
		} 
		if (square.isFlagged()) {
			throw new SyntaxError("Error the square is flagged");
		}
		square.setHidden(false); // now exposed
	}

	public String toString() {

		return "E" + position;
	}
}
