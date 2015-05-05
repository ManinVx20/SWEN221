package stratego.pieces;

import stratego.Game;
import stratego.Game.Direction;
import stratego.util.Position;

/**
 * Represents a bomb piece on the game board.
 *
 * @author David J. Pearce
 *
 */
public class BombPiece {

	// FIXME: you need to implement this class appropriately

	public BombPiece(Game.Player player) {

	}
	
	public boolean isValidMove(Position position, Direction direction,
			int steps, Game game) {
		// bombs cannot move
		return false;
	}
	
}
