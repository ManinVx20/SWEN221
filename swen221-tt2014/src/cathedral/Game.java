package cathedral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cathedral.io.GameError;
import cathedral.moves.Move;
import cathedral.pieces.*;
import cathedral.util.Position;

public class Game {

	/**
	 * Represents one of the two players in the game (black or white)
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public enum Player {
		BLACK,
		WHITE;
	}

	/**
	 * Represents one of the four position of the compass (North, East, South and
	 * West). This is used to give each piece an "orientation" when its placed on
	 * the board.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public enum Direction {
		NORTH,
		EAST,
		SOUTH,
		WEST;
	}
	
	/**
	 * Stores the width of the board.
	 */
	private int width;
	
	/**
	 * Stores the height of the board.
	 */
	private int height;
	
	/**
	 * A 2-dimenional array representing the board itself.
	 */
	private Building[][] board;
	
	/**
	 * The array of moves which make up this game.
	 */
	private Move[] moves;
	
	//Set of building left for BLACK 
	private List<Character> buildingLBlack = new ArrayList<Character>();
	
	//Set of building left for WHITE 
	private List<Character> buildingLWhite = new ArrayList<Character>();
	
	private boolean iscCtedralPlaced = false;
		
	/**
	 * Construct a game with a board of a given width and height, and a sequence
	 * of moves to validate. Initially, the board is empty.
	 * 
	 * @param width
	 * @param height
	 * @param moves
	 */
	public Game(int width, int height, Move[] moves) {
		this.width = width;
		this.height = height;
		this.board = new Building[height][width];
		this.moves = moves;
		this.buildingLBlack.add('S');
		this.buildingLBlack.add('S');
		this.buildingLBlack.add('T');
		this.buildingLBlack.add('T');
		this.buildingLBlack.add('L');
		this.buildingLBlack.add('L');
		this.buildingLWhite.add('S');
		this.buildingLWhite.add('S');
		this.buildingLWhite.add('T');
		this.buildingLWhite.add('T');
		this.buildingLWhite.add('L');
		this.buildingLWhite.add('L');
	}
	
	/**
	 * Get the width (in squares) of the board.
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height (in squares) of the board.
	 * @return
	 */
	public int getHeight() {
		return height;
	}
			
	/**
	 * Get the building occupying a given square, or null if none.
	 * 
	 * @param x
	 *            Horizontal position on board
	 * @param y
	 *            Vertical position on board
	 * @return
	 */
	public Building getSquare(int x, int y) {
		return board[y][x];
	}
	
	/**
	 * Get the holder of a particular square, or null if none. The holder of a
	 * square is the player who captured that square. If the square is not
	 * captured, then there is no holder.
	 * 
	 * @param x
	 *            Horizontal position on board
	 * @param y
	 *            Vertical position on board
	 * @return
	 */
	public Game.Player getHolderOfSquare(int x, int y) {
		
		// FIXME: implement this function for part 6 only
		
		return null;
	}
	
	/**
	 * Register that one player has captured an area of the board. Any pieces of
	 * the opposing player contained within the area are automatically removed.
	 * 
	 * @param player
	 * @param area
	 */
	public void capture(Game.Player player, Area area) {
		
		// FIXME: implement this function for part 6 only
		
	}
	
	/**
	 * Update the board by placing a given piece onto it. As part of this, the record of pieces currently held by each player needs to be updated as well.
	 * 
	 * @param building
	 */
	public void place(Building building) throws GameError {
		Game.Player holder = building.getPlayer();

		// First, update board
		if (building.getTemplate() instanceof Cathedral && iscCtedralPlaced == true) {
			throw new GameError("Catedral already been placed!");
		} else if (holder == null) {
			System.out.println("PLAYER NULL");
			iscCtedralPlaced = true;
		} else if (holder == Player.BLACK) {
			System.out.println("PLAYER BLACK");
			System.out.println(buildingLBlack.indexOf('L'));
			if (building.getTemplate() instanceof L_Building) {
				if (buildingLBlack.indexOf('L') >= 0) {
					buildingLBlack.remove(buildingLBlack.indexOf('L'));
				} else {
					throw new GameError("No building left");
				}
			} else if (building.getTemplate() instanceof S_Building) {
				if (buildingLBlack.indexOf('S') >= 0) {
					buildingLBlack.remove(buildingLBlack.indexOf('S'));
				} else {
					throw new GameError("No building left");
				}
			} else if (building.getTemplate() instanceof S_Building) {
				if (buildingLBlack.indexOf('T') >= 0) {
					buildingLBlack.remove(buildingLBlack.indexOf('T'));
				} else {
					throw new GameError("No building left");
				}
			}
		} else if (holder == Player.WHITE) {
			System.out.println("PLAYER WHITE");
			if (building.getTemplate() instanceof L_Building) {
				if (buildingLWhite.indexOf('L') >= 0) {
					buildingLWhite.remove(buildingLWhite.indexOf('L'));
				} else {
					throw new GameError("No building left");
				}
			} else if (building.getTemplate() instanceof S_Building) {
				if (buildingLWhite.indexOf('S') >= 0) {
					buildingLWhite.remove(buildingLWhite.indexOf('S'));
				} else {
					throw new GameError("No building left");
				}
			} else if (building.getTemplate() instanceof S_Building) {
				if (buildingLWhite.indexOf('T') >= 0) {
					buildingLWhite.remove(buildingLWhite.indexOf('T'));
				} else {
					throw new GameError("No building left");
				}
			}
		}
			
		for (Position square : building.getSquares()) {
			board[square.getY()][square.getX()] = building;
		}
			

	}			
		
	/**
	 * Run this game to produce the final board, whilst also checking each move
	 * against the rules of Cathedral. In the event of an invalid move being
	 * encountered, then a syntax error should be thrown.
	 */
	public void run() throws GameError {
		for(int i=0;i!=moves.length;++i) {
			Move move = moves[i];			
			move.apply(this);			
		}
	}
	
	/**
	 * Provide a human-readable view of the current game board. This is
	 * particularly useful to look at when debugging your code!
	 */
	public String toString() {
		String r = "";
		for(int i=height-1;i>=0;--i) {
			r += i;
			for(int j=0;j!=width;++j) {
				r += "|";
				Building b = board[i][j];
				if(b == null) {
					Game.Player holder = getHolderOfSquare(j,i);
					if(holder == Game.Player.WHITE) {
						r += "w";
					} else if(holder == Game.Player.BLACK) {
						r += "b";
					} else {
						r += "_";
					}
				} else if(b.getPlayer() == null) {
					r += "C"; // the cathedral is not owned by either player
				} else if(b.getPlayer() == Game.Player.WHITE){
					r += "W";
				} else {
					r += "B";
				}
			}
			r += "|\n";
		}
		r += " ";
		// Do the X-Axis
		for(int j=0;j!=width;++j) {
			r += " " + j;
		}
		return r;
	}
}
