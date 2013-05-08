package reversi;

import java.awt.Frame;

import javax.swing.JOptionPane;

import reversi.Tile.State;

public class Game {
	//Two types of intelligence
	public enum Intelligence {
		HUMAN, AI;
	}

	public Board board;
	private boolean playerOneTurn = true;
	private Tile.State playerOne, playerTwo;
	private Intelligence playerOneIntelligence, playerTwoIntelligence;
	private ScoreBoard scoreBoard;
	
	//Start of the program creates a new game
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
	
	//Gets player one's score
	public int getPlayerOneScore() {
		if (playerOne == State.BLACK) {
			return board.getBlackTiles();
		}
		else {
			return board.getWhiteTiles();
		}
	}
	
	//Gets player two's score
	public int getPlayerTwoScore() {
		if (playerOne != State.BLACK) {
			return board.getBlackTiles();
		}
		else {
			return board.getWhiteTiles();
		}
	}
	
	//Asks the player if he would like to be black or white
	public void blackOrWhite() {
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
	
	//Asks the player if he would like to be human or AI
	public void humanOrAI() {
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
	
	//Gets the state/color of a given player
	public State getCurrentPlayer() {
		if (playerOneTurn) {
			return playerOne;
		}
		else {
			return playerTwo;
		}
	}

	//A pretty string of player1 or player2
	public String currentPlayerString() {
		if (playerOneTurn) {
			return "Player 1 ("+playerOne+")";
		}
		else {
			return "Player 2 ("+playerTwo+")";
		}
	}
	
	//Prints AI advice to the console
	public void giveAIAdvice(){
		System.out.println(AI.getBestMove(board, getCurrentPlayer()));
	}
	
	//Gets the optimal move from AI and then makes that move
	public void makeMoveAI(){
		Move best = AI.getBestMove(board, getCurrentPlayer());
		System.out.println("AI makes move: "+best);
		tileClick(best.getX(), best.getY());
	}
	
	//Confirms that the player is ready
	public void playerReady() {
		Object[] options = {"OK"};
		JOptionPane.showOptionDialog(new Frame(),
			currentPlayerString()+": ARE YOU READY!?",
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
	
	//Asks if the player is certain about his move
	public boolean playerConfirmation() {
		Object[] options = {"Not really.",
		                    "Yes!"};
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
	
	//Presents a popup dialog when the game is over
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
	
	//Gets a players confirmation before making a valid move
	public void tileClick(int x, int y) {
		if (playerConfirmation()) {
			boolean validMove = makeMove(x, y);
			
			//Check if game is over
			if(isGameOver()){
				gameOver();
			}
			
			//Make sure this is a valid move
			if (validMove) {
				switchTurns();
			}
		}
	}

	//Skips a player's turn
	public void skipTurn() {
		if (playerConfirmation()) {
			board.unHighLightTiles();
			switchTurns();
		}
	}
	
	//Determines if the game is finished.
	public boolean isGameOver() {
		if (!board.hasBlankTiles()) {
			return true;
		}
		if (board.getBlackTiles() == 0 || board.getWhiteTiles() == 0) {
			return true;
		}
		return false;
	}
	
	//Determines if a given board is full.
	public static boolean isGameOver(Board board) {
		if (!board.hasBlankTiles()) {
			return true;
		}
		if (board.getBlackTiles() == 0 || board.getWhiteTiles() == 0) {
			return true;
		}
		return false;
	}
	
	//Determines if a given virtual board is full.
	public static boolean isGameOver(VirtualBoard board) {
		if (!board.hasBlankTiles()) {
			return true;
		}
		if (board.getBlackTiles() == 0 || board.getWhiteTiles() == 0) {
			return true;
		}
		return false;
	}
	
	//Alternatives between players
	public void switchTurns() {
		playerOneTurn = !playerOneTurn;
		playerReady();
	}

	//Makes a move, highlighting tiles along the way.
	//Tiles are flipped and the score is set.
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
