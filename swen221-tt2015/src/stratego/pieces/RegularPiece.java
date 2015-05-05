package stratego.pieces;

import stratego.Game;
import stratego.Game.Direction;
import stratego.Game.Player;
import stratego.util.Position;

/**
 * Represents a "regular" piece on the board. That is, a piece which has a rank,
 * but no other special abilities.
 *
 * @author David J. Pearce
 *
 */
public class RegularPiece implements Piece {
	// Some constants to help
	public static final int MARSHAL = 9;
	public static final int GENERAL = 8;
	public static final int COLONEL = 7;
	public static final int MAJOR = 6;
	public static final int CAPTAIN = 5;
	public static final int LIEUTENANT = 4;
	public static final int SERGENT = 3;
	public static final int MINER = 2;
	public static final int SCOUT = 1;
	public static final int SPY = 0;

	/**
	 * Represents which side this piece is on
	 */
	private final Game.Player player;

	/**
	 * Represents the rank of this piece.
	 */
	private final int rank;

	/**
	 * Construct a regular piece with a given rank.
	 *
	 * @param rank
	 */
	public RegularPiece(Game.Player player, int rank) {
		this.player = player;
		this.rank = rank;
	}

	/**
	 * Get the rank of this piece, which is an integer from 1 to 10 (inclusive).
	 *
	 * @return
	 */
	public int getRank() {
		return rank;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean isValidMove(Position startPosition, Direction direction,
			int steps, Game game) {		
		return true;
	}
}
