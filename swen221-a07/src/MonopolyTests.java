import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import swen221.monopoly.*;
import swen221.monopoly.GameOfMonopoly.InvalidMove;

public class MonopolyTests {
	// this is where you must write your tests; do not alter the package, or the
    // name of this file.  An example test is provided for you.

	@Test
	public void testValidBuyProperty_1() {
		try {
			tryAndBuy(1500, "Park Lane");
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInValidBuyProperty_1() {
		try {
			tryAndBuy(15000, "Community Chest");
			fail("Shouldn't be able to buy this property");
		} catch (InvalidMove e) {
			// all good
		}
	}
	
	/**
	 * This is a helper function.  Feel free to modify this as you wish.
	 */
	private void tryAndBuy(int cash, String locationName) throws GameOfMonopoly.InvalidMove {
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location location = board.findLocation(locationName);
		Player player = new Player("Dave","Dog",1500,location);
		game.buyProperty(player);
	}

	private static void movePlayer(Player player, int nsteps, GameOfMonopoly game) {
		int oldBalance = player.getBalance();
		game.movePlayer(player, nsteps);
		System.out.print(player.getName() + "'s " + player.getToken() + " lands on " + player.getLocation().getName());
		int newBalance = player.getBalance();
		if (oldBalance != newBalance) {
			System.out.print("Rent deducted: $" + (oldBalance - newBalance));
		}
		System.out.println("");
	}

	private static void buyProperty(Player player, GameOfMonopoly game) throws GameOfMonopoly.InvalidMove {
		Location location = player.getLocation();
		game.buyProperty(player);
		System.out.println(player.getName() + " purchased " + location.getName() + " from bank for $" + ((Property) location).getPrice());
		System.out.println(player.getName() + " now has $" + player.getBalance() + " remaining.");
	}

	private static void sellProperty(Player player, GameOfMonopoly game) throws GameOfMonopoly.InvalidMove {
		String name = inputString("Which property?");
		Location loc = game.getBoard().findLocation(name);
		if (loc == null) {
			System.out.println("No such property!");
			return;
		}
		game.sellProperty(player, loc);
		Property prop = (Property) loc; // safe
		System.out.println(prop.getName() + " sold to bank for $" + prop.getPrice());
		System.out.println(player.getName() + " now has $" + player.getBalance() + " remaining.");
	}

	private static void mortgageProperty(Player player, GameOfMonopoly game) throws GameOfMonopoly.InvalidMove {
		String name = inputString("Which property?");
		Location loc = game.getBoard().findLocation(name);
		if (loc == null) {
			System.out.println("No such property!");
			return;
		}
		int oldBalance = player.getBalance();
		game.mortgageProperty(player, loc);
		int newBalance = player.getBalance();
		Property prop = (Property) loc; // safe
		System.out.println(prop.getName() + " mortgaged for $" + (newBalance - oldBalance));
		System.out.println(player.getName() + " now has $" + player.getBalance() + " remaining.");
	}

	private static void unmortgageProperty(Player player, GameOfMonopoly game) throws GameOfMonopoly.InvalidMove {
		String name = inputString("Which property?");
		Location loc = game.getBoard().findLocation(name);
		if (loc == null) {
			System.out.println("No such property!");
			return;
		}
		int oldBalance = player.getBalance();
		game.unmortgageProperty(player, loc);
		int newBalance = player.getBalance();
		Property prop = (Property) loc; // safe
		System.out.println(prop.getName() + " unmortgaged for $" + (oldBalance - newBalance));
		System.out.println(player.getName() + " now has $" + player.getBalance() + " remaining.");
	}

	/**
	 * Print out details of properties owned by player
	 */
	private static void listProperties(Player player, GameOfMonopoly game) {
		System.out.println("Properties owned by " + player.getName());
		int c = 0;
		for (Property p : player) {
			System.out.print("* " + p.getName());
			if (p.isMortgaged()) {
				System.out.print(" (mortgaged)");
			}
			System.out.println("");
			c++;
		}
		if (c == 0) {
			System.out.println("* None.");
		}
	}

	/**
	 * Provide information on a specific location.
	 */
	private static void detailLocation(Player player, GameOfMonopoly game) {
		String name = inputString("Which location?");
		// check owned by player!
		Location loc = game.getBoard().findLocation(name);

		// check property OK to buy
		if (loc == null) {
			System.out.println("No such property!");
			return;
		}
		System.out.println("Information on " + loc.getName() + ":");
		if (loc instanceof Property) {
			Property prop = (Property) loc;
			System.out.println("* Price $" + prop.getPrice() + ".");
			if (prop.hasOwner()) {
				System.out.println("* Owned by " + prop.getOwner().getName() + ".");
			} else {
				System.out.println("* Not Owned.");
			}
			if (prop.isMortgaged()) {
				System.out.println("* Mortgaged.");
			} else {
				System.out.println("* Unmortgaged.");
			}
			if (prop instanceof Street) {
				Street street = (Street) prop;
				System.out.println("* " + street.getHouses() + " houses " + street.getHotels() + " and hotels.");
				System.out.println("* Colour " + street.getColourGroup().getColour() + ".");
			}
		}
	}
	
	
}
