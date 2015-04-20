package robotwar;

import java.util.Random;
import java.util.Scanner;

import robotwar.core.Battle;
import robotwar.core.RandomBot;
import robotwar.core.Robot;
import robotwar.core.GuardBot;
import robotwar.ui.BattleFrame;

public class Main {
	private static Random random = new Random();
	
	/**
	 * Generate a random integer between 0 and n
	 */
	public static int randomInteger(int n) {
		return random.nextInt(n);
	}
		
	public static void main(String[] args) {	
		Battle battle = new Battle(16,16);		
		new BattleFrame(battle);
	}
}
