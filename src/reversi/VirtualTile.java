package reversi;

import reversi.Tile.State;

public class VirtualTile implements TileForm{

	//private String side;
	private State state = State.BLANK;
	private VirtualBoard board;
	private boolean highLighted = false;
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
		//setImage(state);
		board.addTile(state);
        //addActionListener(this);      
	}
	
	public int getXCoord() {
		return x;
	}
	
	public int getYCoord() {
		return y;
	}
	
//	public void actionPerformed(ActionEvent e) {
//    	//Only accepts clicks on blank tiles
//    	if (state == State.BLANK) {
//    		//Check if game is ready to continue
//    		board.tileClick(x,y);
//    	}
//    }

	public void flip(){
		if (state == State.BLACK) {
			setState(State.WHITE);
		}
		else if (state == State.WHITE) {
			setState(State.BLACK);
		}
		//highlight();
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
		if (this.state != state) {
			board.removeTile(getState());
			board.addTile(state);
			//setImage(state);
			this.state = state;
		}
	}
	
//	private void setImage(State state) {
//		//Change image
//		Image img;
//		switch (state) {
//		case BLACK:
//			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(BLACK_IMG));
//			break;
//		case WHITE:
//			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(WHITE_IMG));
//			break;
//		default:
//			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(BLANK_IMG));
//			break;
//		}
//		Image newimg = img.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
//		ImageIcon icon = new ImageIcon(newimg);
//		setIcon(icon);
//	}

//	public void highlight(){
//		Image img;
//		switch (state) {
//		case BLACK:
//			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(BLACK_HI_IMG));
//			break;
//		case WHITE:
//			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(WHITE_HI_IMG));
//			break;
//		default:
//			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource(BLANK_IMG));
//			break;
//		}
//		Image newimg = img.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH ) ;  
//		ImageIcon icon = new ImageIcon(newimg);
//		setIcon(icon);
//		highLighted = true;
//	}
	
//	public void lowlight(){
//		setImage(state);
//		highLighted = false;
//	}

	public State getState(){
		return state;
	}
	
	public String toString(){
		return state.toString();
	}
}
