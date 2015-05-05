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
 * Represents a move where one pieces strikes a square containing an opponent.
 * At this point, the piece attempts to "take" the other. However, depending on
 * the strength of the two pieces, it may or may not succeed in doing this.
 *
 * @author David J. Pearce
 *
 */
public class StrikeMove extends BasicMove {
	/**
	 * The three possible outcomes when one piece strikes anothyer
	 *
	 */
	public enum Outcome {
		WIN, LOSE, DRAW
	}

	/**
	 * Indicates the outcome of the encounter, from the perspective of the
	 * moving piece.
	 */
	private Outcome outcome;

	/**
	 * Construct a take move for a given player going in one direction for a
	 * number of steps. Also, identifies whether or not the moving player is the
	 * winner.
	 *
	 * @param player
	 *            --- Player making the move
	 * @param position
	 *            --- Starting position of piece to be moved
	 * @param direction
	 *            --- Direction into which to move
	 * @param steps
	 *            --- Number of steps to move
	 * @param outcome
	 *            --- Indicates the outcome of the encounter, from the
	 *            perspective of the moving piece.
	 */
	public StrikeMove(Player player, Position origin, Direction direction,
			int steps, Outcome outcome) {
		super(player, origin, direction, steps);
		this.outcome = outcome;
	}

	@Override
	public void apply(Game game) throws GameError {
		Position newPosition = new Position(position, direction, steps);
		Piece opponent = game.getPiece(newPosition);
		// First, check that piece exists at given position, and is owned by
		// this player and that destination contains an opponent.
		Piece piece = game.getPiece(position);
		Piece pieceNewPos = game.getPiece(newPosition);

		// TODO: need to implement these checks
		if (piece == null){
			throw new GameError("Piece doesn't exists");
		}
		if (piece.getPlayer() != player){
			throw new GameError("Piece doesn't belong to this player");
		}
		if (opponent == null){
			throw new GameError("Piece doesn't exists");
		}

		// Second, check that piece is permitted to move a given number of steps
		// in the given direction.
		if (!piece.isValidMove(position, direction, steps, game)) {
			throw new GameError("Invalid move for piece");
		}

		// try to move pieces more than one step
		if (piece instanceof RegularPiece){
			RegularPiece thisPiece = (RegularPiece) piece;
			if (thisPiece.getRank() !=1 && (  (Math.abs(newPosition.getX()-position.getX())>1) || (Math.abs(newPosition.getY()-position.getY())>1) )) {// is a scout
				throw new GameError("Can't move that much");
			}
		}

		if (piece instanceof RegularPiece && opponent instanceof RegularPiece){
			RegularPiece thisPiece = (RegularPiece) piece;
			int thisRank = thisPiece.getRank();
			RegularPiece opponentPiece = (RegularPiece) opponent;
			int opponentRank = opponentPiece.getRank();
			if (thisRank < opponentRank && game.getPiece(position).equals(pieceNewPos) ) {
				throw new GameError("defeted not valid");
			}
			if (thisRank == opponentRank && game.getPiece(position).equals(pieceNewPos) ) {
				throw new GameError("defeted not valid");
			}
			if (thisRank > opponentRank && game.getPiece(newPosition).equals(piece) ) {
				throw new GameError("defeted not valid");
			}
		}

		

		if (newPosition == null || game.getPiece(newPosition) instanceof ImpassableTerrain){
			throw new GameError("not movable");
		}

		// Finally, apply the move!

		// new position is the opponent spot and old position is null
		if (piece instanceof RegularPiece && opponent instanceof RegularPiece) {
			RegularPiece thisPiece = (RegularPiece) piece;
			int thisRank = thisPiece.getRank();
			RegularPiece opponentPiece = (RegularPiece) opponent;
			int opponentRank = opponentPiece.getRank();
			if (thisRank < opponentRank) {
				game.setPiece(newPosition, opponent);
				game.setPiece(position, null);
			} else if (thisRank > opponentRank){
				game.setPiece(newPosition, piece);
				game.setPiece(position, null);
			} else {
				game.setPiece(newPosition, null);
				game.setPiece(position, null);
			}
		}










	}
}
