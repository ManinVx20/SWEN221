package cathedral.moves;

import cathedral.Game;
import cathedral.Game.Player;
import cathedral.io.GameError;
import cathedral.pieces.*;
import cathedral.util.Position;

/**
 * Place a given building at a given origin on the board facing in a given direction.
 * 
 * @author David J. Pearce
 * 
 */
public class PlaceBuilding extends Move {

	private Position origin;

	private Game.Direction orientation;

	private BuildingTemplate template;

	public PlaceBuilding(Player player, Position origin, Game.Direction orientation, BuildingTemplate template) {

		super(player);
		this.origin = origin;
		this.orientation = orientation;
		this.template = template;
	}

	@Override
	public void apply(Game game) throws GameError {

		Building building = template.create(origin, orientation, player);
		// check position is valid
		for (int i = 0; i < building.getSquares().length; i++) {
			if (building.getSquares()[i].getX() < 0 || building.getSquares()[i].getY() < 0 || building.getSquares()[i].getX() >= game.getWidth() || building.getSquares()[i].getY() >= game.getHeight()) {
				throw new GameError("Building located outside board");
			}
		}
		
		// check if position is already taken
		for (int i = 0; i < building.getSquares().length; i++) {
			if (game.getSquare(building.getSquares()[i].getX(), building.getSquares()[i].getY()) != null) {
					throw new GameError("Square already taken");
				}
			}
				
		game.place(building);
	}

	public BuildingTemplate getTemplate() {

		return template;
	}
}
