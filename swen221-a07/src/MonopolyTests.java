import org.junit.*;
import static org.junit.Assert.*;
import swen221.monopoly.*;
import swen221.monopoly.GameOfMonopoly.InvalidMove;

public class MonopolyTests {

	/**
	 * Valid test for buying a property
	 * @author diego
	 */
	@Test
	public void test_01() {
		try {
			String locationName = "Park Lane";
			String playerName = "Diego";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player = new Player(playerName, "Dog", 1500, location);
			game.buyProperty(player);
			String output = String.format("%s should have an owner",location.getName());
			assertTrue(output, location.hasOwner());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Invalid test: trying to buy a special property
	 */
	@Test
	public void test_02() {
		try {
			String locationName = "Community Chest";
			String playerName = "Diego";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player = new Player(playerName, "Dog", 1500, location);
			game.buyProperty(player);
			fail("Shouldn't be able to buy this property");
		} catch (GameOfMonopoly.InvalidMove e) {
			// all good
		}
	}
		
	/**
	 * Invalid test: trying to buy a property that belongs to another player
	 */
	@Test
	public void test_03() {
		try {
			String locationName = "Park Lane";
			String player1Name = "Diego";
			String player2Name = "Dave";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player1 = new Player(player1Name, "Dog", 1500, location);
			Player player2 = new Player(player2Name, "Cat", 1500, location);
			game.buyProperty(player1);
			game.buyProperty(player2);
			fail("Shouldn't be able to buy this property");
		} catch (GameOfMonopoly.InvalidMove e) {
			// all good
		}
	}
	
	/**
	 * Invalid test: trying to buy a property withouth enough funds
	 */
	@Test
	public void test_04() {
		try {
			String locationName = "Park Lane";
			String playerName = "Dave";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player = new Player(playerName, "Cat", 0, location);
			game.buyProperty(player);
			fail("Shouldn't be able to buy this property");
		} catch (GameOfMonopoly.InvalidMove e) {
			// all good
		}
	}

	/**
	 * Valid test on selling a property
	 * 
	 * @throws InvalidMove
	 */
	@Test
	public void test_05() {
		try {
			String locationName = "Park Lane";
			String playerName = "Diego";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player = new Player(playerName, "Dog", 1500, location);
			game.buyProperty(player);
			game.sellProperty(player, location);
			String output = String.format("%s shouldn't have an owner",location.getName());
			assertTrue(output, !location.hasOwner());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Try to sell a special area
	 */
	@Test
	public void test_06() {
		try {
			trySellProperty("Community Chest", "Diego");
			fail("Cannot sell a public property");
		} catch (GameOfMonopoly.InvalidMove e) {
			// all good
		}
	}

	/**
	 * Try sell property that belongs to someone else
	 */
	@Test
	public void test_07() {
		try {
			String locationName = "Park Lane";
			String player1Name = "Diego";
			String player2Name = "Dave";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player1 = new Player(player1Name, "Dog", 1500, location);
			Player player2 = new Player(player2Name, "Cat", 1500, location);
			game.buyProperty(player1);
			game.sellProperty(player2, location);
			fail("Cannot buy a property which belongs to someone else");
		} catch (GameOfMonopoly.InvalidMove e) {
			// all good
		}
	}

	/**
	 * Try to sell a property with a mortgage on
	 * 
	 * @throws InvalidMove
	 */
	@Test
	public void test_08() throws InvalidMove {
		try {
			String locationName = "Park Lane";
			String ownerName = "Dave";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Property property = (Property) board.findLocation(locationName);
			Player player = new Player(ownerName, "Cat", 1500, property);
			game.buyProperty(player);
			property.mortgage(); // set the peroperty on mortgage
			game.sellProperty(player, property);
			fail("Cannot sell a property with a mortgage on");
		} catch (GameOfMonopoly.InvalidMove e) {
			// all good
		}
	}
	
	/**
	 * Valid test for moving a player on the board
	 */
	@Test
	public void test_09() {
		int nsteps = 2;
		String playerName = "Diego";
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location location = board.findLocation("Go");
		Player player = new Player(playerName, "Dog", 1500, location);
		game.movePlayer(player, nsteps);
		String message = player.getName() + "'s " + player.getToken() + " should lands on " + player.getLocation().getName();
		assertTrue(message, player.getLocation().equals(board.findLocation(location, nsteps)));
		if (!player.getLocation().equals(board.findLocation(board.getStartLocation(), nsteps))) {
			fail("Player's location should be: " + player.getLocation().getName());
		}
	}
	
	/**
	 * Valid test for collecting rent form a property
	 */
	@Test
	public void test_10() {
		try {
			int nsteps = 1;
			String player1Name = "Diego";
			String player2Name = "Dave";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation("Go");
			Player player1 = new Player(player1Name, "Dog", 1500, location);
			game.movePlayer(player1, nsteps); // moves over new property
			game.buyProperty(player1); // buy property
			Player player2 = new Player(player2Name, "cat", 1500, location);
			game.movePlayer(player2, nsteps);
		} catch (GameOfMonopoly.InvalidMove e) {
			fail("Wasn't able to collect rent");
		}
	}
	
	/**
	 * Valid test for mortgaging a property
	 */
	@Test
	public void test_11() {
		try {
			String locationName = "Park Lane";
			String playerName = "Mario";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player = new Player(playerName, "Dog", 1500, location);
			game.buyProperty(player);
			game.mortgageProperty(player, location);
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Invalid test for mortgaging a property. Test tries to mortgage an invalid property
	 */
	@Test
	public void test_12() {
		try {
			String locationName = "Community Chest";
			String playerName = "Mario";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player = new Player(playerName, "Dog", 1500, location);
			game.mortgageProperty(player, location);
			fail("Invalid property, cannot mortage it.");
		} catch (GameOfMonopoly.InvalidMove e) {
			// all good
		}
	}
	
	/**
	 * Invalid test for mortgaging a property. Test tries to mortgage a property which already has a mortgage on
	 * @throws InvalidMove 
	 */
	@Test
	public void test_13() throws InvalidMove {
		try {
			String locationName = "Park Lane";
			String playerName = "Mario";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player = new Player(playerName, "Dog", 1500, location);
			game.buyProperty(player);
			game.mortgageProperty(player, location);
			game.mortgageProperty(player, location);
			fail("Invalid property, cannot mortage a property that already has a mortgage");
		} catch (GameOfMonopoly.InvalidMove e) {
			 // all good
		}
	}
	
	/**
	 * Invalid test for mortgaging a property. Test tries to mortgage a property which belongs to another player
	 * @throws InvalidMove 
	 */
	@Test
	public void test_14() throws InvalidMove {
		try {
			String locationName = "Park Lane";
			String player1Name = "Mario";
			String player2Name = "Dave";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player1 = new Player(player1Name, "Dog", 1500, location);
			Player player2 = new Player(player2Name, "Cat", 1500, location);
			game.buyProperty(player1);
			game.mortgageProperty(player2, location);
			fail("Invalid property, cannot mortage a property that you don't own");
		} catch (GameOfMonopoly.InvalidMove e) {
			 // all good
		}
	}
	
	/**
	 * Valid test for un-mortgaging a property. Test tries to mortgage a property then un-mortgage
	 * @throws InvalidMove 
	 */
	@Test
	public void test_15() throws InvalidMove {
		try {
			String locationName = "Park Lane";
			String playerName = "Mario";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Property location = (Property) board.findLocation(locationName);
			Player player = new Player(playerName, "Dog", 1500, location);
			game.buyProperty(player);
			game.mortgageProperty(player, location);
			game.unmortgageProperty(player, location);
			assertTrue("Property should not be mortgaged", !location.isMortgaged());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail("Un-mortgaging failed");
		}
	}
	
	/**
	 * Invalid test for un-mortgaging a property. Test tries to un-mortgage an invalid property
	 */
	@Test
	public void test_16() {
		try {
			String locationName = "Community Chest";
			String playerName = "Mario";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player = new Player(playerName, "Dog", 1500, location);
			game.unmortgageProperty(player, location);
			fail("Invalid property, cannot mortage it.");
		} catch (GameOfMonopoly.InvalidMove e) {
			// all good
		}
	}
	
	/**
	 * Invalid test for unmortgaging a property. Test tries to unmortgage a property which belongs to another player
	 * @throws InvalidMove 
	 */
	@Test
	public void test_17() throws InvalidMove {
		try {
			String locationName = "Park Lane";
			String player1Name = "Mario";
			String player2Name = "Dave";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player1 = new Player(player1Name, "Dog", 1500, location);
			Player player2 = new Player(player2Name, "Cat", 1500, location);
			game.buyProperty(player1);
			game.mortgageProperty(player1, location);
			game.unmortgageProperty(player2, location);
			fail("Invalid property, cannot unmortage a property that you don't own");
		} catch (GameOfMonopoly.InvalidMove e) {
			 // all good
		}
	}
	
	/**
	 * Invalid test for un-mortgaging a property. Test tries to un-mortgage an property which doesn't have a mortgage on
	 * @throws InvalidMove 
	 */
	@Test
	public void test_18() throws InvalidMove {
		try {
			String locationName = "Park Lane";
			String playerName = "Mario";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player = new Player(playerName, "Dog", 1500, location);
			game.buyProperty(player);
			game.unmortgageProperty(player, location);
			fail("Property doesn't has a mortgage on, cannot unmortage it.");
		} catch (GameOfMonopoly.InvalidMove e) {
			 //all good
		}
	}
	
	/**
	 * Invalid test for un-mortgaging a property. Test tries to un-mortgage an property which doesn't have a mortgage on
	 * @throws InvalidMove 
	 */
	@Test
	public void test_19() throws InvalidMove {
		try {
			String locationName = "Park Lane";
			String playerName = "Mario";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player = new Player(playerName, "Dog", 350, location);
			game.buyProperty(player);
			game.mortgageProperty(player, location);
			game.unmortgageProperty(player, location);
			fail("Not enough funds to unmortage the property.");
		} catch (GameOfMonopoly.InvalidMove e) {
			// all good
		}
	}
	
	/**
	 * Invalid test to find a location which doesn't exists
	 * @throws InvalidMove 
	 */
	@Test
	public void test_20() throws InvalidMove {
			String locationName = "Torre di Pisa";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			assertTrue(board.findLocation(locationName) == null);
	}
	
	/**
	 * Valid test for collecting rent form the train station
	 * @throws InvalidMove 
	 */
	@Test
	public void test_21() {
		try {
			int nsteps = 5;
			String player1Name = "Diego";
			String player2Name = "Dave";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation("Go");
			Player player1 = new Player(player1Name, "Dog", 1500, location);
			game.movePlayer(player1, nsteps); // moves over train station
			game.buyProperty(player1); // buy property
			Player player2 = new Player(player2Name, "cat", 1500, location);
			game.movePlayer(player2, nsteps);
		} catch (GameOfMonopoly.InvalidMove e) {
			fail("Couldn't get rent from train station");
		}
	}
	
	/**
	 * Valid test for checking street methods
	 */
	@Test
	public void test_22() {
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation("Regent Street");
			boolean hasOwner = location.hasOwner();
			Street street = (Street) (Property) location;
			String streetColourName = street.getColourGroup().getColour().toString();
			String text = String.format("%s is %s and has the %d houses and %d hotels but shouldn't have an owner",street.getName(), streetColourName, street.getHouses(), street.getHotels());
			assertTrue(text, !hasOwner);
//			System.out.printf(text);
	}
	
	/**
	 * Invalid test to check the owner of a special area
	 * @throws InvalidMove 
	 */
	@Test
	public void test_23() {
		try {
			String locationName = "Community Chest";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			SpecialArea location = (SpecialArea) board.findLocation(locationName); // cast the community chest to special area as we know is a special area
			System.out.println(location.getOwner());
			fail("Shouldn't not be able to get the owner of a special area");
		} catch (RuntimeException e) {
			// all good
		}
	}

	/**
	 * Invalid test to check the rent of a special area
	 * @throws InvalidMove 
	 */
	@Test
	public void test_24() {
		try {
			String locationName = "Community Chest";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			SpecialArea location = (SpecialArea) board.findLocation(locationName); // cast the community chest to special area as we know is a special area
			System.out.println(location.getRent());
			fail("Shouldn't not be able to get the rent of a special area");
		} catch (RuntimeException e) {
			// all good
		}
	}
	
	/**
	 * Invalid test to check when player is trying to buy a property which already has an owner
	 * @throws InvalidMove 
	 */
	@Test
	public void test_25() {
		try {
			String locationName = "Park Lane";
			String player1Name = "Diego";
			String player2Name = "Dave";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player1 = new Player(player1Name, "Dog", 1500, location);
			Player player2 = new Player(player2Name, "Cat", 1500, location);
			game.buyProperty(player1);
			player2.buy((Property) location); //save to cast to property as we know Park Lane is a Property
			fail("Shouldn't be able to buy this property");
		} catch (IllegalArgumentException e) {
			// all good
		} catch (InvalidMove e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Invalid test to check when player is trying to sell a property which belongs to another owner
	 * @throws InvalidMove 
	 */
	@Test
	public void test_26() {
		try {
			String locationName = "Park Lane";
			String player1Name = "Diego";
			String player2Name = "Dave";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player1 = new Player(player1Name, "Dog", 1500, location);
			Player player2 = new Player(player2Name, "Cat", 1500, location);
			game.buyProperty(player1);
			player2.sell((Property) location); //save to cast to property as we know Park Lane is a Property
			fail("Shouldn't be able to sell this property");
		} catch (IllegalArgumentException e) {
			// all good
		} catch (InvalidMove e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Valid test to calculate the rent of a utility property
	 * @throws InvalidMove 
	 */
	@Test
	public void test_27() {
		try {
			String locationName = "Electric Company";
			String player1Name = "Diego";
			GameOfMonopoly game = new GameOfMonopoly();
			Board board = game.getBoard();
			Location location = board.findLocation(locationName);
			Player player1 = new Player(player1Name, "Dog", 1500, location);
			game.buyProperty(player1);
			Utility electicCompany = (Utility) location;
			assertTrue("The rent for Electric Company wasn't caluclated correctly", electicCompany.getRent() == 75);
		} catch (GameOfMonopoly.InvalidMove e) {
			fail("Wasn't able to calculate the rent for the Electric Company");
		}
	}
	
	// ////////////////// HELPER METHODS

	private void trySellProperty(String locationName, String playerName) throws GameOfMonopoly.InvalidMove {
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location location = board.findLocation(locationName);
		Player player = new Player(playerName, "Dog", 1500, location);
		game.sellProperty(player, location);
	}

}
