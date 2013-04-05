package reversi;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class ScoreBoard extends JFrame {

	private JLabel playerOneScore = new JLabel();
	private JLabel playerTwoScore = new JLabel();
	private JLabel lastMove = new JLabel();
	
	public ScoreBoard() {
		setSize(200, 100);
		setResizable(false);
		setLayout(new GridLayout(3,1));
		getContentPane().setBackground(Color.white);

		setPlayerOneScore(0);
		setPlayerTwoScore(0);
		add(playerOneScore);
		add(playerTwoScore);
		
		lastMove.setText("Last Move: None");
		add(lastMove);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void setPlayerOneScore(int score) {
		playerOneScore.setText("Player 1: "+score);
	}
	
	public void setPlayerTwoScore(int score) {
		playerTwoScore.setText("Player 2: "+score);
	}
	
	public void showMove(int x, int y) {
		lastMove.setText("Last Move: "+(y+1)+","+(x+1));
	}
}
