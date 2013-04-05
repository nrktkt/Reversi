package reversi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 
 * @author kAG0
 *
 */
public class Tile extends JButton implements ActionListener {
	public enum State {
		BLACK, WHITE, BLANK
	}
	
	public static final String BLACK_IMG = "../resources/black_tile.png";
	public static final String WHITE_IMG = "../resources/white_tile.png";
	public static final String BLANK_IMG = "../resources/blank_tile.png";

	//private String side;
	private State state;
	private Board board;
	private int x;
	private int y;
	
	public Tile(Board board, int x, int y){
		this(State.BLANK, board, x, y);
	}
	
	public Tile(State state, Board board, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
		setState(state);
        addActionListener(this);      
	}
	
	public int getXCoord() {
		return x;
	}
	
	public int getYCoord() {
		return y;
	}
	
	public void actionPerformed(ActionEvent e) {
    	//Only accepts clicks on blank tiles
    	if (state == State.BLANK) {
    		//Check if game is ready to continue
    		board.tileClick(this);
    	}
    }

	public void flip(){
		if (state == State.BLACK)
			setState(State.WHITE);
		else if (state == State.WHITE) {
			setState(State.BLACK);
		}
	}

	public boolean isBlank(){
		return state.equals(State.BLANK);
	}
	
	public State getReverseState(){
		if(state == State.BLACK)
			return State.WHITE;
		else {
			return State.BLACK;
		}
	}
	public static State getOppositeState(State state){
		if(state == State.BLANK)
			return State.BLANK;
		if(state == State.BLACK)
			return State.WHITE;
		else {
			return State.BLACK;
		}
	}

	public void setState(State state){
		this.state = state;
		
		//Change image
		Image img;
		switch (state) {
		case BLACK:
			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(BLACK_IMG));
			break;
		case WHITE:
			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(WHITE_IMG));
			break;
		default:
			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(BLANK_IMG));
			break;
		}
		Image newimg = img.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
		ImageIcon icon = new ImageIcon(newimg);
		setIcon(icon);
	}

	public State getState(){
		return state;
	}
	
	public String toString(){
		return state.toString();
	}
	/**
	 * needs work
	 */
	public void highlight(){
		switch(state){
		case BLACK:
			//set image to black highlighted img
			break;
		case WHITE:
			//set image to white highlight img
			break;		
		default:
			break;
		}
	}
	public void lowlight(){
		if(state.equals(State.BLANK))
			return;
		setState(state);
	}
	
}
