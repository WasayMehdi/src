package game;
import java.awt.*;
import javax.swing.*;
import java.util.*;
/**
 * Contains meat of the game code
 * Requires encapsulation
 */

public class Tiles extends JPanel {
	public final static int tileSize = Util.TILE_SIZE * Util.MAP_SIZE;
	public static TileColor[] tiles = new TileColor[tileSize];
	public static JLabel label;
	private static Tiles tile = new Tiles();
	public static final int TEST = 1241;
	public static int timeUntilRestart;
	/**
	 * Instantiates variables, calls required methods to start a new game
	 */
	public static final Tiles getInstance() {
		return tile;
	}
	public Tiles() {
		super();
		setPreferredSize(new Dimension(Util.MAP_SIZE*Util.TILE_SIZE + 8, Util.MAP_SIZE*Util.TILE_SIZE + 26));
		Util.snake = new Snake();
		Snake.snakePoints = new LinkedList<Point>();
		resetBoard();
		Util.snake.resetSnake();
		label = initializedLabel();
		add(label);
		spawnFruit();
	}
	public JLabel initializedLabel() {
		JLabel l = new JLabel("Game Starting...");
		l.setLocation(getWidth()/2, getHeight()/2);
		l.setPreferredSize(new Dimension(450, 30));
		l.setFont(new Font("Arial", Font.BOLD, 25));
		l.setForeground(Color.WHITE);
		return l;
	}
	/**
	 * Called in constructor {@link #Tiles()}
	 * Resets board
	 */
	public void resetBoard() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = TileColor.SPACE;
		}
	}
	public void drawTime(Integer time) {
		if(timeUntilRestart == time)
			return;
		timeUntilRestart = time;
		label.setText(HandleDeath.getDeathMessage(Util.deathMessageId)+"... Restart: "+(Util.GAME_BREAK - time)+"sec");
		if(time >= Util.GAME_BREAK) {
			Util.gameAwaitingStart = false;
			resetBoard();
			Util.snake.resetSnake();
			spawnFruit();
		}
	}
	/**
	 * Changes snake {@link #updateSnake()} changes value in {@link #snakePoints} based on direction
	 * Updates tiles {@link #updateTiles(Graphics)} passes in g
	 * Updates lines {@link #updateLines(Graphics)} passes in g, called after updating tiles to draw over them
	 */
	public void paintComponent(Graphics g) {
		if(Snake.skipping)
			Snake.lastSkip = !Snake.lastSkip;
		if(Snake.lastSkip)
			return;
		if(System.currentTimeMillis() - Util.skipStart > 10000)
			Snake.skipping = false;
			
		updateTiles(g);
		if(Util.gameAwaitingStart) {
			return;
		}
		if(Util.paused) {
			label.setText("Paused");
			return;
		}
		try {
			Util.snake.updateSnake();
		}catch(IllegalArgumentException e) {
			Util.gameOver = true;
		}
	}
	/**
	 * Updates tiles based on {@link TileColor} tiles  array
	 * 
	 * {@code i % Util.MAP_SIZE} Remainder of tile after division by map size, equals y value
	 * 			Map Size is the number of tiles on the x/y plane
	 * {@code i / Util.MAP_SIZE} Integer value of the number in tile array / MAP_SIZE equals the x value
	 * 
	 * Fills tiles from their place on the map times the TILE_SIZE meaning if x = 0 and y = 1
	 * 			(i = 26), then y * 25 = start at pixel (0, 25) and fill up to pixel (25, 50)
	 * @param g draws tiles
	 *
	 */
	public void updateTiles(Graphics g) {
		for (int i = 0; i < Util.FULL_SIZE; i++) {
			int y = i % Util.MAP_SIZE;
			int x = i / Util.MAP_SIZE;
			g.setColor(Color.BLACK);
			if (tiles[i] == TileColor.SNAKE) {
				g.setColor(Color.BLUE);
				if(Snake.skipping)
					g.setColor(Color.RED);
				g.fillRect(x * Util.MAP_SIZE, y * Util.MAP_SIZE,
						Util.TILE_SIZE, Util.TILE_SIZE);
			}
			else if (tiles[i] == TileColor.SPACE)
				g.fillRect(x * Util.MAP_SIZE, y * Util.MAP_SIZE,
						Util.TILE_SIZE, Util.TILE_SIZE);
			else if(tiles[i] == TileColor.ORANGE) {
				g.fillRect(x * Util.MAP_SIZE, y * Util.MAP_SIZE,
						Util.TILE_SIZE, Util.TILE_SIZE);
				g.setColor(Color.ORANGE);
				g.fillOval(x * Util.MAP_SIZE, y * Util.MAP_SIZE, Util.TILE_SIZE, Util.TILE_SIZE);
			} else if (tiles[i] == TileColor.KIWI) {
				g.fillRect(x * Util.MAP_SIZE, y * Util.MAP_SIZE,
						Util.TILE_SIZE, Util.TILE_SIZE);
				g.setColor(Color.GREEN);
				g.fillOval(x * Util.MAP_SIZE, y * Util.MAP_SIZE, Util.TILE_SIZE, Util.TILE_SIZE);
			} else if (tiles[i] == TileColor.BANANA) {
				g.fillRect(x * Util.MAP_SIZE, y * Util.MAP_SIZE,
						Util.TILE_SIZE, Util.TILE_SIZE);
				g.setColor(Color.MAGENTA);
				g.fillOval(x * Util.MAP_SIZE, y * Util.MAP_SIZE, Util.TILE_SIZE, Util.TILE_SIZE);
			} else if (tiles[i] == TileColor.SLOWMOTION) {
				g.fillRect(x * Util.MAP_SIZE, y * Util.MAP_SIZE,
						Util.TILE_SIZE, Util.TILE_SIZE);
				g.setColor(Color.BLUE);
				g.fillOval(x * Util.MAP_SIZE, y * Util.MAP_SIZE, Util.TILE_SIZE, Util.TILE_SIZE);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 15));
				g.drawString("S", (x * Util.MAP_SIZE) + 8, y * Util.MAP_SIZE + 18);
			}
			
		}
	}
	/**
	 * Set's a tile in the tile array based on their (x, y) starting from the left hand corner
	 * Formula: x * UTIL.MAP_SIZE means that it will skip one column for every x value
	 * 			+y to then move accross that column to make the y value
	 */
	public void changeTile(int x, int y, TileColor newTileColor) {
		try {
			tiles[x*Util.MAP_SIZE + y] = newTileColor;
		}catch(ArrayIndexOutOfBoundsException e) {
			return;
		}
	}
	/**
	 * {@link #changeTile(int, int, TileColor)}
	 */
	public TileColor getTileColor(int x, int y) {
		return tiles[x*Util.MAP_SIZE + y];
	}
	public void resetFruit() {
		for(int i = 0; i < tiles.length; i++) {
			if(tiles[i] == TileColor.BANANA || tiles[i] == TileColor.ORANGE || tiles[i] == TileColor.KIWI)
				tiles[i] = TileColor.SPACE;
		}
	}
	public void spawnFruit() {
		TileColor tile = null;
		while(tile == null) {
			int i = Util.getRandom(tiles.length -1);
			TileColor newFruit = tiles[i];
			if(newFruit != TileColor.SPACE)
				continue;
			tiles[i] = TileColor.ORANGE;
			break;
		}
		tile = null;
		while(tile == null) {
			int i = Util.getRandom(tiles.length - 1);
			TileColor newFruit = tiles[i];
			if(newFruit != TileColor.SPACE)
				continue;
			tiles[i] = tile = TileColor.KIWI;
		}
		tile = null;
		while(tile == null) {
			int i = Util.getRandom(tiles.length - 1);
			TileColor newFruit = tiles[i];
			if(newFruit != TileColor.SPACE)
				continue;
			tiles[i] = tile = TileColor.BANANA;
		}
		tile = null;
		if(Util.getRandom(10) == 0) {
			while(tile == null) {
				int i = Util.getRandom(tiles.length - 1);
				TileColor newFruit = tiles[i];
				if(newFruit != TileColor.SPACE)
					continue;
				tiles[i] = tile = TileColor.SLOWMOTION;
			}
		}
	}

	/**
	 * Called every 600ms in Main's timer
	 */
	public void redraw() {
		repaint();
	}
}
