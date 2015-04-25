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

		int status = game.getStatus();

		switch (status) {
			case Game.GAME_CONTINUES :
				throw new SyntaxError("invalid move " + this
						+ " --- game not finished");
			case Game.PLAYER_WON :
				if (!isWinner) {
					throw new SyntaxError("invalid move " + this
							+ " --- player won not bomb exposed");
				}
				break;
			case Game.PLAYER_LOST :
				if (isWinner) {
					throw new SyntaxError("invalid move " + this
							+ " --- bomb exposed not player won");
				}
				break;
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
