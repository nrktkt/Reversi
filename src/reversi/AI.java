/**
 * 
 */
package reversi;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashSet;
import java.util.List;
import java.util.Set;
=======
import java.util.List;
>>>>>>> 6c7db4b5db64d6dc8f84118479f2eeb8ad6a5c39

import reversi.Tile.State;

/**
 * @author kAG0
 *
 */
public class AI {
<<<<<<< HEAD
	private static final double FORFEIT_WEIGHT = 1;
=======
	private final static double FORFEIT_WEIGHT = 1;
>>>>>>> 6c7db4b5db64d6dc8f84118479f2eeb8ad6a5c39
	private static final double MOBILITY_WEIGHT = 1;
	private static final double FRONTIER_WEIGHT = 1;
	private static final double STABILITY_WEIGHT = 1;
	private static final double SCORE_WEIGHT = 1;
	private static final int MAX_DEPTH = 5;
	private static final int VERY_LOW = Integer.MIN_VALUE-200;
	private static final int VERY_HIGH = Integer.MAX_VALUE-200;
<<<<<<< HEAD
	
	private Board gameBoard;
	
	public AI(Board gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	/**
	 * 
	 * @param player
	 * @return the best move the given player can make on this board
	 */
	public Move getBestMove(Tile.State player){
		//Get the tiles from the current state of the board.
		Tile[][] tiles = gameBoard.getTiles();

		Move bestMove;
		if(player == State.WHITE) {
			bestMove = new Move(-1, -1, player, VERY_LOW);
		}
		else {
			bestMove = new Move(-1, -1, player, VERY_HIGH);
		}
=======
	/**
	 * 
	 * @param board
	 * @param player
	 * @return the best move the given player can make on this board
	 */
	public static Move getBestMove(Board board, Tile.State player){
		// TODO Auto-generated method stub
		Move bestMove;
		if(player == State.WHITE) bestMove = new Move(0, 0, player, VERY_LOW);
		else bestMove = new Move(0, 0, player, VERY_HIGH);
		List<Move> possibleMoves = new ArrayList<Move>();
		Board temp;

		for (Move currentMove : possibleMoves) {
			if (player == State.WHITE) {
				// temp = the board after current move is preformed
				currentMove.setScore(getBestMove(temp, MAX_DEPTH, VERY_LOW,
						VERY_HIGH, player));// or is it opposite player?
				if (currentMove.getScore() >= bestMove.getScore())
					bestMove = currentMove;
			} else {
				// temp = the board after current move is preformed
				currentMove.setScore(getBestMove(temp, MAX_DEPTH, VERY_LOW,
						VERY_HIGH, player));// or is it opposite player?
				if (currentMove.getScore() <= bestMove.getScore())
					bestMove = currentMove;
			}

		}
		return bestMove;
	}
	/**
	 * 
	 * @param board
	 * @param player
	 * @return the best move the given player can make on this board
	 */
	private static int getBestMove(Board node, int depth, int a, int b,
			State maximizingPlayer) {
		// TODO Auto-generated method stub
		if (depth == 0 || Game.isGameOver(node))
			return rateBoard(node);
		List<Move> possibleMoves = new ArrayList<Move>();
		// possibleMoves = the moves maximizingPlayer can make
		Board temp;
		// white will always try to get the highest board score aka white is max
		if (maximizingPlayer == State.WHITE) {
			for (Move move : possibleMoves) {
				// temp = board after move is made
				a = max(a, getBestMove(temp, depth - 1, a, b,
								Tile.getOppositeState(maximizingPlayer)));
				if (b <= a)
					break; // (* Beta cut-off *)
			}
			return a;
		}
		// black will always try to get the lowest board score aka black is min
		else {
			for (Move move : possibleMoves) {
				// temp = board after move is made
				b = min(b, getBestMove(temp, depth - 1, a, b,
								Tile.getOppositeState(maximizingPlayer)));
				if (b <= a)
					break; // (* Alpha cut-off *)
			}

			return b;
		}
	}
	
	private static int max(int h, int k){
		if(h>k)
			return k;
		else return h;
	}
	private static int min(int h, int k){
		if(h<k)
			return k;
		else return h;
	}
	
	/**
	 *
	 * @param board
	 * @return a very positive number means the board is favorable for white
	 * 		while a very negative number means the board is favorable for black
	 */
	private static int rateBoard(Board board){
		// TODO Auto-generated method stub
		return 0;
	}
	public Coordinate<Integer,Integer> getMove(Tile.State player) {
>>>>>>> 6c7db4b5db64d6dc8f84118479f2eeb8ad6a5c39
		
		List<Move> possibleMoves = new ArrayList<Move>();
		//MinMaxPriorityQueue
		for (Move currentMove : possibleMoves) {
			if (player == State.WHITE) {
				// temp = the board after current move is preformed
				currentMove.setScore(getBestMove(MAX_DEPTH,VERY_LOW, VERY_HIGH, player));// or is it opposite player?
				if (currentMove.getScore() >= bestMove.getScore()) {
					bestMove = currentMove;
				}
			} 
			else {
				// temp = the board after current move is preformed
				currentMove.setScore(getBestMove(MAX_DEPTH,VERY_LOW, VERY_HIGH, player));// or is it opposite player?
				if (currentMove.getScore() <= bestMove.getScore()) {
					bestMove = currentMove;
				}
			}

		}
		return bestMove;
	}
	
	private Set<Move> getPossibleMoves(Tile[][] tiles) {
		Set<Move> possibleMoves = new HashSet<Move>();
		Set<Tile> playerTiles = gameBoard.getTileSet();
		
//		//Check each blank tile around the black and white tiles.
//		for (Tile tile : playerTiles) {
//			//Union of the tile's adjacent blanks with ALL adjacent blanks
//			possibleMoves.addAll(tile.getAdjacentBlanks());
//		}
		
		return possibleMoves;
	}
	
	/**
	 * 
	 * @param player
	 * @return the best move the given player can make on this board
	 */
	private int getBestMove(int depth, int a, int b, State maximizingPlayer){
		// TODO Auto-generated method stub
//		if(depth == 0 || Game.isGameOver(node)) {
//		        return the heuristic value of node
//		    // white will always try to get the highest board score
//		    if (maximizingPlayer == State.WHITE){
//		        for (each child of node){
//		            a = max(a, alphabeta(child, depth - 1, a, b, not(maximizingPlayer)))
//		            if (b.compareTo(a) <= 0)
//		                break;       //(* Beta cut-off *)
//		        }
//		        return a;
//		    }
//		    // black will always try to get the lowest board score
//		    else{
//		        for (each child of node){
//		            b = min(b, alphabeta(child, depth - 1, a, b, not(maximizingPlayer)))
//		            if (b.compareTo(a) <= 0)
//		                break;                             //(* Alpha cut-off *)
//		        }
//		    }
//		        return b;
//		}
//		return new Move(0,0, Tile.State.BLACK);
		return 0;
	}
	
	/**
	 *
	 * @param board
	 * @return a very positive number means the board is favorable for white
	 * 		while a very negative number means the board is favorable for black
	 */
	private int rateBoard(Board board){
		// TODO Auto-generated method stub
		return 0;
	}
}