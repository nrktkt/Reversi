package reversi;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScoreBoard extends JFrame implements ActionListener {

	private Game game;
	private JLabel playerOneScore = new JLabel();
	private JLabel playerTwoScore = new JLabel();
	private JLabel lastMove = new JLabel();
	
	public ScoreBoard(Game game) {
		this.game = game;
		setSize(200, 100);
		setResizable(false);
		setLayout(new GridLayout(4,1));
		getContentPane().setBackground(Color.white);

		//Set default scores to 0
		setPlayerOneScore(0);
		setPlayerTwoScore(0);
		add(playerOneScore);
		add(playerTwoScore);
		
		lastMove.setText("Last Move: None");
		add(lastMove);
		
		JButton skip = new JButton("Skip Turn :(");
		skip.addActionListener(this);
		add(skip);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//Action listener for the skip turn button
	public void actionPerformed(ActionEvent e) {
    	game.skipTurn();
    }

	//Sets player 1's score
	public void setPlayerOneScore(int score) {
		playerOneScore.setText("Player 1: "+score);
	}
	
	//Sets player 2's score
	public void setPlayerTwoScore(int score) {
		playerTwoScore.setText("Player 2: "+score);
	}
	
	//Display a move on the scoreboard
	public void showMove(int x, int y) {
		String column = getCharForNumber(y+1);
		lastMove.setText("Last Move: "+(x+1)+","+column);
	}
	
	//Convert a integer into its corresponding char value
	private String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}
}
