package minesweeper.moves;

import minesweeper.*;
import minesweeper.squares.Square;

/**
 * Represents a move which either flags or unflags a square on the board.
 *
 * @author David J. Pearce
 *
 */
public class FlagMove extends Move {

	private boolean isFlagged;

	private Position sqPos;

	/**
	 * Construct a FlagSquare move which either flags or unflags a given position.
	 *
	 * @param position
	 *            --- position to be flagged/unflagged.
	 * @param isFlagged
	 *            --- true if position is being flagged; false if it is being unflagged.
	 */
	public FlagMove(Position position, boolean isFlagged) {

		super(position);
		this.sqPos = position;
		this.isFlagged = isFlagged;
	}

	/**
	 * Apply this move to a given game and check that it is valid. A square can only be flagged if it is currently unflagged and vice versa.
	 *
	 * @param game
	 *            --- game to which this move is applied.
	 */
	public void apply(Game game) throws SyntaxError {

		Square sq = game.squareAt(position);
		if (!sq.isHidden()) {
			throw new SyntaxError("invalid move " + this + " --- cannot flag/unflag square which is already exposed");
		}
		if (sq.isFlagged() && isFlagged) {
			throw new SyntaxError("invalid move " + this + " --- cannot flag square which is already flagged");
		}
		if (!sq.isFlagged() && !isFlagged) {
			throw new SyntaxError("invalid move " + this + " --- cannot unflag square which is already unflagged");
		}
		sq.setFlagged(isFlagged);
	}

	public String toString() {

		if (isFlagged) {
			return "F" + position;
		} else {
			return "U" + position;
		}
	}
}
