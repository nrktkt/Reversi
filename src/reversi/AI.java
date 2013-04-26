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
	private static int getBestMove(Board node, int depth, int a, int b, State maximizingPlayer){
		// TODO Auto-generated method stub
		if(depth == 0 || Game.isGameOver(node)){
		        return the heuristic value of node
		    // white will always try to get the highest board score
		    if (maximizingPlayer == State.WHITE){
		        for (each child of node){
		            a = max(a, alphabeta(child, depth - 1, a, b, not(maximizingPlayer)))
		            if (b.compareTo(a) <= 0)
		                break;       //(* Beta cut-off *)
		        }
		        return a;
		    }
		    // black will always try to get the lowest board score
		    else{
		        for (each child of node){
		            b = min(b, alphabeta(child, depth - 1, a, b, not(maximizingPlayer)))
		            if (b.compareTo(a) <= 0)
		                break;                             //(* Alpha cut-off *)
		        }
		    }
		        return b;
		}
		return new Move(0,0, Tile.State.BLACK);
		
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
