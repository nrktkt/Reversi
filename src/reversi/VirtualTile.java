package reversi;

import reversi.Tile.State;

/**
 * Most methods and variables will be identical to that of 
 * the Tile class. Comments will be left to a minimum since the
 * two classes are nearly the same. The main difference is that this
 * VirtualTile does not inherit UI Element qualities, keeping 
 * the alpha beta pruning a little more efficient.
 *
 */
public class VirtualTile implements TileForm {

	//private String side;
	private State state = State.BLANK;
	private VirtualBoard board;
	private int x;
	private int y;
	
	public VirtualTile(VirtualBoard board, int x, int y){
		this(State.BLANK, board, x, y);
	}
	
	public VirtualTile(State state, VirtualBoard board, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
		setState(state);
		board.addTile(state);
	}
	
	public int getXCoord() {
		return x;
	}
	
	public int getYCoord() {
		return y;
	}
	
	public void flip(){
		if (state == State.BLACK) {
			setState(State.WHITE);
		}
		else if (state == State.WHITE) {
			setState(State.BLACK);
		}
	}

	public boolean isBlank(){
		return state.equals(State.BLANK);
	}
	
	public State getReverseState(){
		if(state == State.BLACK) {
			return State.WHITE;
		}
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
		if (this.state != state) {
			board.removeTile(getState());
			board.addTile(state);
			this.state = state;
		}
	}
	
	public State getState(){
		return state;
	}
	
	public String toString(){
		return state.toString();
	}
}
