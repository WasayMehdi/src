package game;

import java.awt.event.*;

public class SnakeKeys extends KeyAdapter{
	/**
	 * Changes direction based on direction key
	 * Checks if it's an invalid direction
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if(Snake.direction != Util.SOUTH)
				Snake.direction = 0;
			break;
		case KeyEvent.VK_LEFT:
			if(Snake.direction != Util.EAST)
				Snake.direction = 1;
			break;
		case KeyEvent.VK_DOWN:
			if(Snake.direction != Util.NORTH)
				Snake.direction = 2;
			break;
		case KeyEvent.VK_RIGHT:
			if(Snake.direction != Util.WEST)
				Snake.direction = 3;
			break;
		case KeyEvent.VK_P:
			Util.paused = !Util.paused;
			break;
		}
	}
}
