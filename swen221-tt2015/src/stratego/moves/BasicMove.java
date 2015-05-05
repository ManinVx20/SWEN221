package stratego.moves;

import stratego.Game;
import stratego.Game.Direction;
import stratego.Game.Player;
import stratego.io.GameError;
import stratego.pieces.FlagPiece;
import stratego.pieces.ImpassableTerrain;
import stratego.pieces.Piece;
import stratego.pieces.RegularPiece;
import stratego.util.Position;

/**
 * Represents a basic move on the board, where one player's piece moves to
 * another position on the board but does not attempt to take another piece.
 *
 * @author David J. Pearce
 *
 */
public class BasicMove extends Move {

	/**
	 * The number of steps taken
	 */
	protected final int steps;

	/**
	 * Construct a basic move for a given player going in one direction for a
	 * number of steps.
	 *
	 * @param player
	 *            --- Player making the move
	 * @param position
	 *            --- Starting position of piece to be moved
	 * @param direction
	 *            --- Direction into which to move
	 * @param steps
	 *            --- Number of steps to move
	 */
	public BasicMove(Player player, Position position, Direction direction, int steps) {
		super(player, position, direction);
		this.steps = steps;
	}

	@Override
	public void apply(Game game) throws GameError {
		Position newPosition = new Position(position,direction,steps);

		// First, check that piece exists at given position, and is owned by
		// this player and that destination is empty.
		Piece piece = game.getPiece(position);

		// TODO: need to implement these checks
		if (piece == null){
			throw new GameError("Piece doesn't exists");
		}
		if (piece.getPlayer() != player){
			throw new GameError("Piece doesn't belong to this player");
		}
		if (game.getPiece(newPosition) != null){
			throw new GameError("New position is not empty");
		}
		// check if we move a flag
		if (piece instanceof FlagPiece){
			throw new GameError("Can't move a flag");

		}
		// check if we move a flag
		if (piece instanceof ImpassableTerrain){
			throw new GameError("Can't move impassable terrain");

		}
		// try to move pieces more than one step
		if (piece instanceof RegularPiece){
			RegularPiece thisPiece = (RegularPiece) piece;
			if (thisPiece.getRank() !=1 && (  (Math.abs(newPosition.getX()-position.getX())>1) || (Math.abs(newPosition.getY()-position.getY())>1) )) {// is a scout
				throw new GameError("Can't move that much");
			}
		}



		// Second, check that piece is permitted to move a given number of steps
		// in the given direction.
		if(!piece.isValidMove(position, direction, steps, game)) {
			throw new GameError("Invalid move for piece");
		}

		// Third, apply the move!
		game.setPiece(newPosition,piece);
		game.setPiece(position,null);
	}


	/**
	 * Returns the number of steps to move
	 *
	 * @return
	 */
	public int getSteps() {
		return steps;
	}
}
