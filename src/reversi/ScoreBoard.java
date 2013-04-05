package reversi;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import reversi.Tile.State;


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
	
	public void actionPerformed(ActionEvent e) {
    	game.skipTurn();
    }

	public void setPlayerOneScore(int score) {
		playerOneScore.setText("Player 1: "+score);
	}
	
	public void setPlayerTwoScore(int score) {
		playerTwoScore.setText("Player 2: "+score);
	}
	
	public void showMove(int x, int y) {
		String column = getCharForNumber(y+1);
		lastMove.setText("Last Move: "+(x+1)+","+column);
	}
	
	//http://stackoverflow.com/questions/10813154/converting-number-to-letter
	private String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}
}
