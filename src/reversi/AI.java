/**
 * 
 */
package reversi;

import java.util.ArrayList;
import java.util.List;

import reversi.Tile.State;

/**
 * @author kAG0
 *
 */
public class AI {
	private final static double FORFEIT_WEIGHT = 1;
	private static final double MOBILITY_WEIGHT = 1;
	private static final double FRONTIER_WEIGHT = 1;
	private static final double STABILITY_WEIGHT = 1;
	private static final double SCORE_WEIGHT = 1;
	private static final int MAX_DEPTH = 5;
	private static final int VERY_LOW = Integer.MIN_VALUE-200;
	private static final int VERY_HIGH = Integer.MAX_VALUE-200;
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
		
		//Search for next best move for player
		
		//Coordinate move = new Coordinate<Integer,Integer>(1,2);
		//return move;
		
		return null;
	}public Coordinate<Integer,Integer> getMove(Tile.State player) {
		
		//Search for next best move for player
		
		//Coordinate move = new Coordinate<Integer,Integer>(1,2);
		//return move;
		
		return null;
	}
}
