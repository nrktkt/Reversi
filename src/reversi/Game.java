package reversi;

import java.awt.Frame;

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
	private Tile.State playerTwo;
	private ScoreBoard scoreBoard;
	//ccr code [l,r,u,d,ul,ur,dl,dr]
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game(){
		board = new Board(this);
		blackOrWhite();
		this.scoreBoard = new ScoreBoard(this);

		scoreBoard.setPlayerOneScore(getPlayerOneScore());
		scoreBoard.setPlayerTwoScore(getPlayerTwoScore());
		clearCCR();
		
		playerReady();
	}
	
	public int getPlayerOneScore() {
		if (playerOne == State.BLACK) {
			return board.getBlackTiles();
		}
		else {
			return board.getWhiteTiles();
		}
	}
	
	public int getPlayerTwoScore() {
		if (playerOne != State.BLACK) {
			return board.getBlackTiles();
		}
		else {
			return board.getWhiteTiles();
		}
	}
	
	private void clearCCR(){
		for(int i = 0; i < Board.SIZE; i++){
			CCR[i] = false;
		}
	}
	
	public void blackOrWhite() {
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
			playerOne = State.BLACK;
			playerTwo = State.WHITE;
		}
		else {
			playerOne = State.WHITE;
			playerTwo = State.BLACK;
		}
	}
	
	public State getCurrentPlayer() {
		if (playerOneTurn) {
			return playerOne;
		}
		else {
			return playerTwo;
		}
	}

	public String currentPlayerString() {
		if (playerOneTurn) {
			return "Player 1 ("+playerOne+")";
		}
		else {
			return "Player 2 ("+playerTwo+")";
		}
	}
	
	public void playerReady() {
		Object[] options = {"THANKS!!!"};
		JOptionPane.showOptionDialog(new Frame(),
			currentPlayerString()+": ARE YOU READY!?!??!!?!?",
		    "Your Turn",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[0]);
	}
	
	public boolean playerConfirmation() {
		Object[] options = {"Not really...",
		                    "YES!!!"};
		int n = JOptionPane.showOptionDialog(new Frame(),
			currentPlayerString()+": ARE YOU POSITIVE????",
		    "Ready?",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[1]);
		if (n == 1) {
			return true;
		}
		return false;
	}
	
	public void gameOver() {
		String winner;
		int blacks = board.getBlackTiles();
		int whites = board.getWhiteTiles();	
		if (playerOne == State.BLACK && blacks > whites) {
			winner = "Player 1";
		}
		else if (blacks == whites){
			winner = "No one";
		}
		else {
			winner = "Player 2";
		}
		
		Object[] options = {"AWESOME!"};
		JOptionPane.showOptionDialog(new Frame(),
			winner+" WINS!",
		    "Game Over",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[0]);
		System.exit(1);
	}
	
	public void tileClick(int x, int y) {
		if (playerConfirmation()) {
			boolean validMove = makeMove(x, y);
			
			//Check if game is over
			if(isGameOver()){
				gameOver();
			}
			
			if (validMove) {
				switchTurns();
			}
		}
	}

	public void skipTurn() {
		if (playerConfirmation()) {
			board.unHighLightTiles();
			switchTurns();
		}
	}
	
	public boolean isGameOver() {
		if (!board.hasBlankTiles()) {
			return true;
		}
		if (board.getBlackTiles() == 0 || board.getWhiteTiles() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isGameOver(Board board) {
		if (!board.hasBlankTiles()) {
			return true;
		}
		if (board.getBlackTiles() == 0 || board.getWhiteTiles() == 0) {
			return true;
		}
		return false;
	}
	
	public void switchTurns() {
		playerOneTurn = !playerOneTurn;
		playerReady();
	}

	public boolean makeMove(int x, int y){
		State player = getCurrentPlayer();
		if (!isValidMove(x,y,player)) {
			return false;
		}
		
		//Remove previous highlights
		board.unHighLightTiles();
		
		//Place the new tile
		board.placeTile(x, y, player);
		board.tiles[x][y].highlight();
		
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
		//Show move on scoreboard
		scoreBoard.showMove(x,y);
		scoreBoard.setPlayerOneScore(getPlayerOneScore());
		scoreBoard.setPlayerTwoScore(getPlayerTwoScore());

		return true;
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
			board.flipTile(x, y);
		}
		if(x-1 >= 0){
			if(board.getTile(x-1, y).getState().equals(Tile.getOppositeState(player)))
				flipLeft(x-1, y, player);
		}
	}
	private void flipRight(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			board.flipTile(x, y);
		}
		if(x+1 <= 7){
			if(board.getTile(x+1, y).getState().equals(Tile.getOppositeState(player)))
				flipRight(x+1, y, player);
		}
	}
	private void flipUp(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			board.flipTile(x, y);
		}
		if(y-1 >= 0){
			if(board.getTile(x, y-1).getState().equals(Tile.getOppositeState(player)))
				flipUp(x, y-1, player);
		}
	}
	private void flipUpRight(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			board.flipTile(x, y);
		}
		if(y-1 >= 0 && x+1 <= 7){
			if(board.getTile(x+1, y-1).getState().equals(Tile.getOppositeState(player)))
				flipUpRight(x+1, y-1, player);
		}
	}
	private void flipUpLeft(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			board.flipTile(x, y);
		}
		if(y-1 >= 0 && x-1 >= 0){
			if(board.getTile(x-1, y-1).getState().equals(Tile.getOppositeState(player)))
				flipUpLeft(x-1, y-1, player);
		}
	}
	private void flipDown(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			board.flipTile(x, y);
		}
		if(y+1 <= 7){
			if(board.getTile(x, y+1).getState().equals(Tile.getOppositeState(player)))
				flipDown(x, y+1, player);
		}
	}
	private void flipDownLeft(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			board.flipTile(x, y);
		}
		if(y+1 <= 7 && x-1 >= 0){
			if(board.getTile(x-1, y+1).getState().equals(Tile.getOppositeState(player)))
				flipDownLeft(x-1, y+1, player);
		}
	}
	private void flipDownRight(int x, int y, State player){
		if(board.getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			board.flipTile(x, y);
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
			if(dir) {
				return true;
			}
		}
		return false;
		
	}
	
}
