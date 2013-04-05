package reversi;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import reversi.Tile.State;

public class Game {
	//directions for refrencing the CCR
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int UPLEFT = 4;
	public static final int UPRIGHT = 5;
	public static final int DOWNLEFT = 6;
	public static final int DOWNRIGHT = 7;
	public Board board;
	private boolean[] CCR = new boolean[8];
	private boolean playerOneTurn = true;
	private Tile.State playerOne;
	private ScoreBoard scoreBoard;
	//ccr code [l,r,u,d,ul,ur,dl,dr]
	public int playerOneScore;
	public int playerTwoScore;
	private List<Spot> affectedSpots = new ArrayList<Spot>();
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game(){
		playerOne = blackOrWhite();
		this.scoreBoard = new ScoreBoard();
		board = new Board(this);
		playerOneScore = playerTwoScore = 2;
		scoreBoard.setPlayerOneScore(playerOneScore);
		scoreBoard.setPlayerTwoScore(playerTwoScore);
		clearCCR();
	}
	
	public void highlightAffectedSpots(){
		for(Spot spot : affectedSpots){
			board.getTile(spot.x, spot.y).highlight();
		}
	}

	public void lowlightAffectedSpots(){
		for(Spot spot : affectedSpots){
			board.getTile(spot.x, spot.y).lowlight();
		}
	}
	
	private void clearCCR(){
		for(int i = 0; i < Board.SIZE; i++){
			CCR[i] = false;
		}
	}
	
	public Tile.State blackOrWhite() {
		//Custom button text
		Object[] options = {"White",
		                    "Black"};
		int n = JOptionPane.showOptionDialog(new Frame(),
		    "Player 1: Would you like to be black or white?",
		    "Black or White",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[1]);
		if (n == 1) {
			return State.BLACK;
		}
		else {
			return State.WHITE;
		}
	}
	
	private void changeScore(int change, State player){
		if(player == playerOne) {
			playerOneScore += change;
			scoreBoard.setPlayerOneScore(playerOneScore);
		}
		else {
			playerTwoScore += change;
			scoreBoard.setPlayerTwoScore(playerTwoScore);
		}
	}
	
	public void setScores(){
		int blk = 0;
		int wht = 0;
		for (Tile[] y: board.tiles) {
		    for (Tile x: y) {
		    	if(x.getState().equals(State.BLACK))
		    		blk++;
		    	if(x.getState().equals(State.WHITE))
		    		wht++;
		    }
		}
		
		if (playerOne == State.BLACK) {
			playerOneScore = blk;
			playerTwoScore = wht;
		}
		else {
			playerOneScore = wht;
			playerTwoScore = blk;
		}
		
		//Update scoreboard
		scoreBoard.setPlayerOneScore(playerOneScore);
		scoreBoard.setPlayerTwoScore(playerTwoScore);
	}
	
	public void makeMove(int x, int y){
		//change highlighted spots back to normal
		lowlightAffectedSpots();
		affectedSpots.clear();
		Tile.State player;
		if (playerOneTurn) {
			player = playerOne;
			if (isValidMove(x,y, player)) {
				playerOneTurn = false;
			}
			else {
				return;
			}
		}
		else {
			if (playerOne == State.BLACK) {
				player = State.WHITE;
			}
			else {
				player = State.BLACK;
			}
			if (isValidMove(x,y, player)) {
				playerOneTurn = true;
			}
			else {
				return;
			}
		}
		
		board.setTile(x, y, player);
		//Add point
		changeScore(1, player);
		
		//Show move on scoreboard
		scoreBoard.showMove(x,y);
		
		if(CCR[LEFT]){
			flipLeft(x, y, player);
		}
		if(CCR[RIGHT]){
			flipRight(x, y, player);
		}
		if(CCR[UP]){
			flipUp(x, y, player);
		}
		if(CCR[DOWN]){
			flipDown(x, y, player);
		}
		if(CCR[DOWNLEFT]){
			flipDownLeft(x, y, player);
		}
		if(CCR[DOWNRIGHT]){
			flipDownRight(x, y, player);
		}
		if(CCR[UPLEFT]){
			flipUpLeft(x, y, player);
		}
		if(CCR[UPRIGHT]){
			flipUpRight(x, y, player);
		}
		//highlight changed spaces
		highlightAffectedSpots();
	}
	
	/**
	 * @pre the x,y must have a tile in it
	 * @param x 
	 * @param y
	 * @param player the player who's turn it is
	 */
	private void flipLeft(int x, int y, State player){
		
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			affectedSpots.add(new Spot(x,y));
			board.flipTile(x, y);
			changeScore(1, player);
			changeScore(-1,Tile.getOppositeState(player));
		}
		if(x-1 >= 0){
			if(board.getTile(x-1, y).getState().equals(Tile.getOppositeState(player)))
				flipLeft(x-1, y, player);
		}
	}
	private void flipRight(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			affectedSpots.add(new Spot(x,y));
			board.flipTile(x, y);
			changeScore(1, player);
			changeScore(-1,Tile.getOppositeState(player));
		}
		if(x+1 <= 7){
			if(board.getTile(x+1, y).getState().equals(Tile.getOppositeState(player)))
				flipRight(x+1, y, player);
		}
	}
	private void flipUp(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			affectedSpots.add(new Spot(x,y));
			board.flipTile(x, y);
			changeScore(1, player);
			changeScore(-1,Tile.getOppositeState(player));
		}
		if(y-1 >= 0){
			if(board.getTile(x, y-1).getState().equals(Tile.getOppositeState(player)))
				flipUp(x, y-1, player);
		}
	}
	private void flipUpRight(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			affectedSpots.add(new Spot(x,y));
			board.flipTile(x, y);
			changeScore(1, player);
			changeScore(-1,Tile.getOppositeState(player));
		}
		if(y-1 >= 0 && x+1 <= 7){
			if(board.getTile(x+1, y-1).getState().equals(Tile.getOppositeState(player)))
				flipUpRight(x+1, y-1, player);
		}
	}
	private void flipUpLeft(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			affectedSpots.add(new Spot(x,y));
			board.flipTile(x, y);
			changeScore(1, player);
			changeScore(-1,Tile.getOppositeState(player));
		}
		if(y-1 >= 0 && x-1 >= 0){
			if(board.getTile(x-1, y-1).getState().equals(Tile.getOppositeState(player)))
				flipUpLeft(x-1, y-1, player);
		}
	}
	private void flipDown(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			affectedSpots.add(new Spot(x,y));
			board.flipTile(x, y);
			changeScore(1, player);
			changeScore(-1,Tile.getOppositeState(player));
		}
		if(y+1 <= 7){
			if(board.getTile(x, y+1).getState().equals(Tile.getOppositeState(player)))
				flipDown(x, y+1, player);
		}
	}
	private void flipDownLeft(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			affectedSpots.add(new Spot(x,y));
			board.flipTile(x, y);
			changeScore(1, player);
			changeScore(-1,Tile.getOppositeState(player));
		}
		if(y+1 <= 7 && x-1 >= 0){
			if(board.getTile(x-1, y+1).getState().equals(Tile.getOppositeState(player)))
				flipDownLeft(x-1, y+1, player);
		}
	}
	private void flipDownRight(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			affectedSpots.add(new Spot(x,y));
			board.flipTile(x, y);
			changeScore(1, player);
			changeScore(-1,Tile.getOppositeState(player));
		}
		if(y+1 <= 7 && x+1 <= 7){
			if(board.getTile(x+1, y+1).getState().equals(Tile.getOppositeState(player)))
				flipDownRight(x+1, y+1, player);
		}
	}
	
	
	public boolean moveFromLeft(int x, int y, State col){
		if(x-1 >= 0){
			if(board.getTile(x-1, y).isBlank())
				return false;
			if(board.getTile(x-1, y).getState().equals(col)){
				if(board.getTile(x, y).isBlank())
					return false;
				if(board.getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromLeft(x-1, y, col);
			}
		}
		return false;
	}
	public boolean moveFromRight(int x, int y, State col){
		if(x+1 <= 7){
			if(board.getTile(x+1, y).isBlank())
				return false;
			if(board.getTile(x+1, y).getState().equals(col)){
				if(board.getTile(x, y).isBlank())
					return false;
				if(board.getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromRight(x+1, y, col);
			}
		}
		return false;
	}
	public boolean moveFromUp(int x, int y, State col){
		if(y-1 >= 0){
			if(board.getTile(x, y-1).isBlank())
				return false;
			if(board.getTile(x, y-1).getState().equals(col)){
				if(board.getTile(x, y).isBlank())
					return false;
				if(board.getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromUp(x, y-1, col);
			}
		}
		return false;
	}
	public boolean moveFromUpRight(int x, int y, State col){
		if(y-1 >= 0 && x+1 <=7){
			if(board.getTile(x+1, y-1).isBlank())
				return false;
			if(board.getTile(x+1, y-1).getState().equals(col)){
				if(board.getTile(x, y).isBlank())
					return false;
				if(board.getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromUpRight(x+1, y-1, col);
			}
		}
		return false;
	}
	public boolean moveFromUpLeft(int x, int y, State col){
		if(y-1 >= 0 && x-1 >=0){
			if(board.getTile(x-1, y-1).isBlank())
				return false;
			if(board.getTile(x-1, y-1).getState().equals(col)){
				if(board.getTile(x, y).isBlank())
					return false;
				if(board.getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromUpLeft(x-1, y-1, col);
			}
		}
		return false;
	}
	public boolean moveFromDown(int x, int y, State col){
		if(y+1 <= 7){
			if(board.getTile(x, y+1).isBlank())
				return false;
			if(board.getTile(x, y+1).getState().equals(col)){
				if(board.getTile(x, y).isBlank())
					return false;
				if(board.getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromDown(x, y+1, col);
			}
		}
		return false;
	}
	public boolean moveFromDownRight(int x, int y, State col){
		if(y+1 <= 7 && x+1 <= 7){
			if(board.getTile(x+1, y+1).isBlank())
				return false;
			if(board.getTile(x+1, y+1).getState().equals(col)){
				if(board.getTile(x, y).isBlank())
					return false;
				if(board.getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromDownRight(x+1, y+1, col);
			}
		}
		return false;
	}
	public boolean moveFromDownLeft(int x, int y, State col){
		if(y+1 <= 7 && x-1 >= 0){
			if(board.getTile(x-1, y+1).isBlank())
				return false;
			if(board.getTile(x-1, y+1).getState().equals(col)){
				if(board.getTile(x, y).isBlank())
					return false;
				if(board.getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromDownLeft(x-1, y+1, col);
			}
		}
		return false;
	}
	//need to change null checks to checks for blank state
	public boolean isValidMove(int x, int y, State player){
		clearCCR();
		boolean u = false;
		boolean	d = false;
		if(!board.getTile(x, y).isBlank())
			return false;
		if(x != 0){
			if(moveFromLeft(x,y,player)) //check left
				CCR[LEFT] = true;
			if(y != 0){
				if(moveFromUp(x,y,player))//check up
					CCR[UP] = true;
				u = true;
				//check up-left
				if(moveFromUpLeft(x,y,player))
					CCR[UPLEFT] = true;
			}
			if(y != 7){
				if(moveFromDown(x,y,player))//check down
					CCR[DOWN] = true;
				d = true;
				//check down-left
				if(moveFromDownLeft(x,y,player))
					CCR[DOWNLEFT] = true;
			}
		}
		if(x != 7){
			//check right
			if(moveFromRight(x,y,player))
				CCR[RIGHT] = true;
			if(y != 0){
				//check up-right
				if(moveFromUpRight(x,y,player))
					CCR[UPRIGHT] = true;
				if(!u){
					//check up
					if(moveFromUp(x,y,player))
						CCR[UP] = true;
				}
				
			}
			if(y != 7){
				if(!d){
					//check down
					if(moveFromDown(x,y,player))
						CCR[DOWN] = true;
				}
				
				//check down-right
				if(moveFromDownRight(x,y,player))
					CCR[DOWNRIGHT] = true;
			}
		}
		for(boolean dir : CCR){
			if(dir) return true;
		}
		return false;
		
	}
}
