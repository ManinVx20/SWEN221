package cathedral.pieces;

import cathedral.Game;
import cathedral.util.Position;

/**
 * Represents a building which has been placed on to the board. Every building
 * occupies a number of squares on the board, and is owned by a given player.
 * 
 * @author David J. Pearce
 * 
 */
public final class Building {
	/**
	 * The player who owns this building
	 */
	private Game.Player player;
	
	/**
	 * The squares on the board which make up this building.
	 */
	private Position[] squares;
	
	/**
	 * The building template from which this building was constructed
	 */
	private BuildingTemplate template;
	
	public Building(Game.Player player, BuildingTemplate template, Position... squares) {
		this.squares = squares;
		this.player = player;
		this.template = template;
	}
		
	/**
	 * Get the squares on the board which make up this building.
	 * 
	 * @return
	 */
	public Position[] getSquares() {
		return squares;
	}
	
	/**
	 * Get the player who owns this building.
	 * 
	 * @return
	 */
	public Game.Player getPlayer() {
		return player;
	}
	
	/**
	 * Get the building template from which this building was constructed.
	 * 
	 * @return
	 */
	public BuildingTemplate getTemplate() {
		return template;
	}
	
}
