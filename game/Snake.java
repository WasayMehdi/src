package game;


import java.awt.Point;
import java.util.LinkedList;

public class Snake {
	public static boolean skipping = false;
	public static boolean lastSkip;
	public static volatile int direction;
	private static int points;
	private static boolean removeAnother = false;
	public static void setPoints(int points) {
		Snake.points = points;
	}
	public static int getPoints() {
		return points;
	}
	static LinkedList<Point> snakePoints;
	/**
	 * Changes the points inside {@link #snakePoints}
	 * Then changes tiles using {@link #changeTile(int, int, TileColor)}
	 * Removes last point in snake, checks for collision 
	 */
	public void updateSnake() throws IllegalArgumentException {
		Point referenceTile = snakePoints.getFirst();
		Point newTile = null;
		switch(direction) {
		case Util.NORTH:
			newTile = new Point(referenceTile.x, --referenceTile.y);
			break;
		case Util.SOUTH:
			newTile = new Point(referenceTile.x, ++referenceTile.y);
			break;
		case Util.EAST:
			newTile = new Point(++referenceTile.x, referenceTile.y);
			break;
		case Util.WEST:
			newTile = new Point(--referenceTile.x, referenceTile.y);
			break;
		}
		java.util.List<Point> stuff = snakePoints.subList(2, snakePoints.size());
		if((newTile.x < 0 || newTile.y > 25 || newTile.x > 25 || newTile.y < 0) || stuff.contains(newTile)) {
			if(stuff.contains(newTile))
				Util.deathMessageId = 1;
			else if(newTile.x < 0)
				Util.deathMessageId = 2;
			Util.gameOver = true;
			return;
		}
		Point last = snakePoints.getLast();
		Util.TILES.changeTile(last.x, last.y, TileColor.SPACE);
		TileColor color = Util.TILES.getTileColor(newTile.x, newTile.y);
		if(color == TileColor.ORANGE || color == TileColor.KIWI || color == TileColor.BANANA) {
			Util.TILES.resetFruit();
			if((Util.rightChoice == 0 && color == TileColor.ORANGE)
					|| (Util.rightChoice == 1 && color == TileColor.KIWI)
					|| (Util.rightChoice == 2 && color == TileColor.BANANA)) {
				Snake.setPoints(1 + Snake.getPoints());
				Util.BOARD.repaint();
				switch(direction) {
				case Util.NORTH:
					snakePoints.add(new Point(last.x, --last.y));
				break;
				case Util.SOUTH:
					snakePoints.add(new Point(last.x, ++last.y));
				break;
				case Util.EAST:
					snakePoints.add(new Point(++last.x, last.y));
				break;
				case Util.WEST:
					snakePoints.add(new Point(--last.x, last.y));
				break;
				}
			} else {
				removeAnother = true;
			}
			Util.BOARD.resetQuestions();
			Util.BOARD.addQuestions();
			Util.TILES.spawnFruit();
			
		}
		if(color == TileColor.SLOWMOTION) {
			skipping = true;
			Util.skipStart = System.currentTimeMillis();
		}
		last = snakePoints.removeLast();
		if(removeAnother) {
			Point d = snakePoints.getLast();
			Util.TILES.changeTile(d.x, d.y, TileColor.SPACE);
			d = snakePoints.removeLast();
			removeAnother = false;
		}
		snakePoints.push(newTile);
		System.out.println("Amount: "+snakePoints.size());
		for(int j = 0; j < snakePoints.size(); j++) {
			if(!snakePoints.get(j).toString().equalsIgnoreCase(last.toString()));
				Util.TILES.changeTile(snakePoints.get(j).x, snakePoints.get(j).y, TileColor.SNAKE);
				System.out.println("Index: " + j + " Point: ("+snakePoints.get(j).x+", "+snakePoints.get(j).y+")");
		}
		
	}
	/**
	 * Creates snake head, adds it to {@link #snakePoints} 
	 * Draws it in the center of the screen to start the game
	 */
	public void resetSnake() {
		Util.deathMessageId = 0;
		snakePoints.clear();
		Point startPoint = new Point((Util.MAP_SIZE)/2,(Util.MAP_SIZE)/2);
		Point heads[] = {new Point(startPoint.x, 3+startPoint.y),new Point(startPoint.x, 2+startPoint.y), new Point(startPoint.x, ++startPoint.y), startPoint};
		for(Point p : heads) {
			snakePoints.push(p);
		}
	}
}