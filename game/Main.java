package game;

import javax.swing.*;
import java.util.*;
import java.awt.*;

public class Main extends JFrame{
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -1178974578870720723L;
	/**
	 * Creates frame
	 */
	public Main() {
		super("Snake Game w/ Trivia");
		this.setLayout(new BorderLayout());
		setSize(Util.MAP_SIZE*Util.TILE_SIZE + 8 + 300, Util.MAP_SIZE*Util.TILE_SIZE + 26);
		add(Util.tiles);
		setLocationRelativeTo(null);
		add(Util.board, BorderLayout.WEST);
	}
	/**
	 * Creates new instance of Main, starts the timer
	 */
	public static void main(String...strings) {
		Main m = new Main();
		m.setDefaultCloseOperation(EXIT_ON_CLOSE);
		m.addKeyListener(new SnakeKeys());
		m.setVisible(true);
		new java.util.Timer().schedule(new TheTask(Util.tiles), 1000, 70);
	}
}

class TheTask extends TimerTask {
	Tiles tiles;
	public TheTask(Tiles tile) {
		this.tiles = tile;
		System.out.println("???");
	}
	/**
	 * Process
	 */
	public void run() {
		if(Util.gameOver) {
			Util.gameAwaitingStart = true;
			Util.gameOver = false;
			Util.startDelay = System.currentTimeMillis();
		}
		if(!Util.gameAwaitingStart) {
			Tiles.label.setText("");
			tiles.redraw();
		} else {
			Util.tiles.drawTime((int)((System.currentTimeMillis() - Util.startDelay)/1000));
		}
	}
}
