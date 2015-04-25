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
			throw new SyntaxError("invalid move " + this + " --- square not hidden");
		}
			if (square.isFlagged()) {
			throw new SyntaxError("invalid move " + this + " --- square flagged");
		}
		
		if(square instanceof BlankSquare) {
			expose(game,position);
		} else {
			square.setHidden(false); // for bombs
		}
		
		int status = game.getStatus();

		switch (status) {
			case Game.PLAYER_WON :
				if (!(this instanceof EndMove)) {
					throw new SyntaxError(
							"invalid move " + this + " --- player won but not end move");
				}
				break;
			case Game.PLAYER_LOST :
				if (!(this instanceof EndMove)) {
					throw new SyntaxError(
							"invalid move " + this + " --- bomb exposed but not end move");
				}
				break;		
		}
	}

	private void expose(Game game, Position p) {		
		// invariant: p is exposed 
		Square sq = game.squareAt(p);
		if (!sq.isFlagged() && sq.isHidden() && sq instanceof BlankSquare) {			
			BlankSquare bs = (BlankSquare) sq;
			sq.setHidden(false);			
			if(bs.getNumberOfBombsAround() == 0) {				
				expose(game,p.getX()-1,p.getY()-1);
				expose(game,p.getX(),p.getY()-1);
				expose(game,p.getX()+1,p.getY()-1);
				expose(game,p.getX()-1,p.getY());
				expose(game,p.getX()+1,p.getY());
				expose(game,p.getX()-1,p.getY()+1);
				expose(game,p.getX(),p.getY()+1);
				expose(game,p.getX()+1,p.getY()+1);
			}
		}
	}
	
	private void expose(Game game, int x, int y) {
		if (x >= 0 && x < game.getWidth() && y >= 0 && y < game.getHeight()) {
			Position p = new Position(x, y);
			expose(game, p);
		}
	}
	
	public String toString() {		
		return "E" + position;		
	}
}