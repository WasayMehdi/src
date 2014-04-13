package game;

import java.awt.Graphics;
import static java.lang.Math.random;

public class Util{
	/**
	 * Utilities class contains any global singleton variables
	 */
	private Util() {
		
	}
	public static int MAP_SIZE = 25;
	public static int TILE_SIZE = 25;
	public static int FULL_SIZE = MAP_SIZE * TILE_SIZE;
	public static Tiles tiles = new Tiles();
	public static Snake snake = new Snake();
	public static boolean gameAwaitingStart;
	public static boolean gameOver;
	public static final int NORTH = 0, WEST = 1, SOUTH = 2, EAST = 3;
	public static long startDelay = 0L;
	public static Graphics graphics;
	public static final int GAME_BREAK = 10;
	public static ScoreBoard board = new ScoreBoard();
	public static int rightChoice;
	public static long skipStart;
	public static boolean paused;
	public static int deathMessageId;
	
	public final static int getRandom(int i) {
		return (int)(random() * (i + 1));
	}
}
