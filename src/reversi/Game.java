package reversi;

import java.awt.Frame;

import javax.swing.JOptionPane;

import reversi.Tile.State;

public class Game {
	public enum Intelligence {
		HUMAN, AI;
	}

	public Board board;
	private boolean playerOneTurn = true;
	private Tile.State playerOne, playerTwo;
	private Intelligence playerOneIntelligence, playerTwoIntelligence;
	private ScoreBoard scoreBoard;
	//ccr code [l,r,u,d,ul,ur,dl,dr]
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game(){
		board = new Board(this);
		blackOrWhite();
		humanOrAI();
		this.scoreBoard = new ScoreBoard(this);

		scoreBoard.setPlayerOneScore(getPlayerOneScore());
		scoreBoard.setPlayerTwoScore(getPlayerTwoScore());
		
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
	
	public void humanOrAI() {
		//Custom button text
		Object[] options = {"Human",
		                    "AI"};
		int n = JOptionPane.showOptionDialog(new Frame(),
		    "Player 1: Are you human or AI?",
		    "Black or White",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[1]);
		if (n == 1) {
			playerOneIntelligence = Intelligence.AI;
		}
		else {
			playerOneIntelligence = Intelligence.HUMAN;
		}
		Object[] options2 = {"Human",
        "AI"};
		int m = JOptionPane.showOptionDialog(new Frame(),
				"Player 2: Are you human or AI?",
				"Black or White",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options2,
				options2[1]);
		if (m == 1) {
			playerTwoIntelligence = Intelligence.AI;
		}
		else {
			playerTwoIntelligence = Intelligence.HUMAN;
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
	public void giveAIAdvice(){
		System.out.println(AI.getBestMove(board, getCurrentPlayer()));
	}
	public void makeMoveAI(){
		Move best = AI.getBestMove(board, getCurrentPlayer());
		System.out.println("AI makes move: "+best);
		tileClick(best.getX(), best.getY());
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
		
		//If the player is AI, make an AI move
		if ((playerOneTurn && playerOneIntelligence == Intelligence.AI) ||
				(!playerOneTurn && playerTwoIntelligence == Intelligence.AI)) {
			makeMoveAI();
		}
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
	public static boolean isGameOver(VirtualBoard board) {
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
		if (!board.isValidMove(x,y,player)) {
			return false;
		}
		
		//Remove previous highlights
		board.unHighLightTiles();
		
		//Place the new tile
		board.placeTile(x, y, player);
		board.tiles[x][y].highlight();
		
		//Flips the tiles in every direction
		board.flipTiles(x,y,player);
		
		//Show move on scoreboard
		scoreBoard.showMove(x,y);
		scoreBoard.setPlayerOneScore(getPlayerOneScore());
		scoreBoard.setPlayerTwoScore(getPlayerTwoScore());

		return true;
	}
	
}
