package robotwar.core;

import java.util.*;

/**
 * An abstract class representing a Robot in the battle. Subclasses of this can implement different strategies for moving and fighting within the arena.
 * 
 * @author David J. Pearce
 * 
 */
public abstract class Robot {

	public final String name;
	private int xPosition;
	private int yPosition;
	private int strength;
	private boolean isDead;
	private Battle battle;

	/**
	 * Construct a robot at a given x and y position with a given strength.
	 * 
	 * @param xPosition
	 *            starting x position of robot
	 * @param yPosition
	 *            starting y position of robot
	 * @param strength
	 *            strength of robot
	 */
	public Robot(String name, int xPosition, int yPosition, int strength, Battle battle) {
		this.name = name;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.strength = strength;
		this.isDead = false;
		this.battle = battle;
	}

	/**
	 * This method is called to allow a robot to do something.
	 * 
	 * @param battle
	 */
	public abstract void takeTurn(Battle battle);

	/**
	 * This method is called when a robot is shot by another robot.
	 */
	public void isShot(int strength) {
		this.strength = this.strength - 1;
		// should check isDead here?
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		if (xPosition >= 0 && xPosition < this.battle.arenaWidth) {
			this.xPosition = xPosition;
		}
	}
	
	public boolean isXPositionValid(int xPosition) {
		return (xPosition >= 0 && xPosition < this.battle.arenaWidth);
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		if (yPosition >= 0 && yPosition < this.battle.arenaHeight) {
			this.yPosition = yPosition;
		}
	}
	
	public boolean isYPositionValid(int yPosition) {
		return (yPosition >= 0 && yPosition < this.battle.arenaHeight);
	}

	public int getStrength() {
		return strength;
	}

	public void decreseStrength() {
		this.strength = strength--;
		if (this.strength < 1) {
			isDead = true;
		}
	}

	public boolean isDead() {
		return isDead;
	}

	/**
	 * This helper method determines what robots are in sight of this robot. Robots which are in sight can be attacked.
	 * 
	 * @param distance
	 *            the distance that this robot can see.
	 * @return list of robots in sight
	 */
	protected List<Robot> findRobotsInSight(Battle battle, int distance) {
		
		List<Robot> robots = battle.getRobots();
		List<Robot> visibleRobots = new LinkedList<Robot>();
		for (Robot r : robots) {
			if (r != this && !r.isDead) {
				int dx = xPosition - r.xPosition;
				int dy = yPosition - r.yPosition;
				// Calculate distance from me to robot r using pythagorus
				// theorem.
				double distanceToR = Math.sqrt(dx * dx + dy * dy);
				if (((double) distance) > distanceToR) {
					// this robot is in range of sight
					visibleRobots.add(r);
				}
			}
		}
		return visibleRobots;
	}
}
