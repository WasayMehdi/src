package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScoreBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int SCORETEST =2143;
	/**
	 * Create only ScoreBoard instance
	 */
	//private static ScoreBoard scoreBoard = new ScoreBoard();

	/**
	 * Labels, names are self explanitory
	 */
	private JLabel label;
	private JLabel question;
	private JLabel choices[];

	public ScoreBoard() {
		setLayout(new BoxLayout(this, 3));
		this.setPreferredSize(new Dimension(300, Util.FULL_SIZE));
		this.setVisible(true);
		label = new JLabel("Score: 0");
		question = new JLabel("?");
		choices = new JLabel[]{new JLabel("Orange: "), new JLabel("Green: "), new JLabel("Magenta: ")};
		addVariables();
	}
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		label.setText("Score: "+Snake.getPoints());
	}
	private void addVariables() {
		label.setLocation(0, 0);
		label.setPreferredSize(new Dimension(100, 25));
		label.setFont(new Font("Arial", Font.BOLD, 25));
		label.setForeground(Color.WHITE);
		question.setForeground(Color.WHITE);
		add(label);
		add(question);
		for(int i = 0; i < choices.length; i++) {
			choices[i].setLocation(0, 100*(i+1));
			add(choices[i]);
		}
		choices[0].setForeground(Color.ORANGE);
		choices[1].setForeground(Color.GREEN);
		choices[2].setForeground(Color.MAGENTA);
		addQuestions();
	}
	protected void resetQuestions() {
		choices[0].setText("Orange: ");
		choices[1].setText("Green: ");
		choices[2].setText("Magenta: ");
		choices[0].setLocation(0, 200);
	}
	public void addQuestions() {
		String[] newQuestion = Questions.getQuestions()[Util.getRandom(Questions.getQuestions().length - 1)];
		String question = newQuestion[0];
		String rightAnswer = newQuestion[1];
		String[] wrongAnswers = new String[newQuestion.length - 2];
		for(int i = 0; i < wrongAnswers.length; i++) {
			wrongAnswers[i] = newQuestion[2+i]; 
		}
		this.question.setText(question);
		hasBeenUsed = new java.util.LinkedList<Integer>();
		Util.rightChoice = Util.getRandom(choices.length - 1);
		choices[Util.rightChoice].setText(choices[Util.rightChoice].getText()+rightAnswer.substring(2));
		int[] beenUsed = new int[2];
		beenUsed[0] = Util.getRandom(wrongAnswers.length - 1);
		hasBeenUsed.add(nextChoice());
		choices[hasBeenUsed.get(0)].setText(choices[hasBeenUsed.get(0)].getText()+wrongAnswers[beenUsed[0]].substring(2));
		beenUsed[1] = nextUsed(beenUsed[0], wrongAnswers);
		hasBeenUsed.add(nextChoice());
		choices[hasBeenUsed.get(1)].setText(choices[hasBeenUsed.get(1)].getText()+wrongAnswers[beenUsed[1]].substring(2));
	}
	private static java.util.List<Integer> hasBeenUsed = new java.util.LinkedList<Integer>();
	private int nextUsed(int lastUsed, String[] wrongAnswers) {
		int returnValue = Util.getRandom(wrongAnswers.length - 1);
		return returnValue == lastUsed ? nextUsed(lastUsed, wrongAnswers) : returnValue;
	}
	private int nextChoice() {
		int stuff = Util.getRandom(choices.length - 1);
		if(stuff == Util.rightChoice || hasBeenUsed.contains(stuff))
			return nextChoice();
		else
			return stuff;
	}
	
}
