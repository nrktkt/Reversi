package reversi;

import java.util.HashSet;
import java.util.Set;

import reversi.Tile.State;

/**
 * The AI class is responsible for finding the next optimal 
 * move given a current board state and a player. This class uses
 * alpha beta pruning to recursively call getBestMove() where certain 
 * subtrees are pruned from the search space. This class has a search depth of 5.
 * Scores are determined by the current score of player white and player black.
 * Scores are also influenced by the number of possible next move options
 * and the number of moves that black has versus the number of moves that white
 * has. Lastly, if a given move causes the other player to forfeit, the score
 * is incremented again.
 *
 */
public class AI {
	private static final double FORFEIT_WEIGHT = 8;
	private static final double MOBILITY_WEIGHT = 2;
	private static final double FRONTIER_WEIGHT = 2;
	private static final double SCORE_WEIGHT = 5;
	
	private static final int MAX_DEPTH = 5;
	private static final int VERY_LOW = Integer.MIN_VALUE+200;
	private static final int VERY_HIGH = Integer.MAX_VALUE-200;

	/**
	 * 
	 * @param board
	 * @param player
	 * @return the best move the given player can make on this board
	 */
	public static Move getBestMove(Board board, Tile.State player){
		//Initialize the best move to zero
		Move bestMove;

		if(player == State.WHITE) {
			bestMove = new Move(0, 0, player, VERY_LOW);
		}
		else {
			bestMove = new Move(0, 0, player, VERY_HIGH);
		}
		
		//The set of all possible moves for a given player
		Set<Move> possibleMoves = getPossibleMoves(board, player);
		//The temporary board used for visualizing possible move sequences
		VirtualBoard temp;
		//The alpha beta tree
		Tree<Move> moveTree = new Tree<Move>(new Move(-1,-1,State.BLANK));

		for (Move currentMove : possibleMoves) {
			//Add each possible move to the tree
			moveTree.addLeaf(currentMove);
			Tree<Move> subTree = moveTree.getTree(currentMove);
			// apply the current move
			temp = new VirtualBoard(board);
			if(temp.isValidMove(currentMove.getX(), currentMove.getY(), player)) {
				temp.flipTiles(currentMove.getX(), currentMove.getY(), player);
			}
			
			//finds the best move for player white or player black
			if (player == State.WHITE) {
				//Recursive call for alpha beta pruning 
				currentMove.setScore(getBestMove(temp, MAX_DEPTH, VERY_LOW,
						VERY_HIGH, Tile.getOppositeState(player),subTree));// or is it opposite player?
				if (currentMove.getScore() >= bestMove.getScore()) {
					bestMove = currentMove;
				}
			} 
			else {
				//Recursive call for alpha beta pruning 
				currentMove.setScore(getBestMove(temp, MAX_DEPTH, VERY_LOW,
						VERY_HIGH, Tile.getOppositeState(player),subTree));// or is it opposite player?
				if (currentMove.getScore() <= bestMove.getScore()) {
					bestMove = currentMove;
				}
			}

		}
		
		//Print out the tree of moves
		System.out.println("Alpha Beta Tree:");
		System.out.println(moveTree);
		
		return bestMove;
	}
	/**
	 * 
	 * @param board
	 * @param player
	 * @return the best move the given player can make on this board
	 */
	private static int getBestMove(VirtualBoard node, int depth, int a, int b, State maximizingPlayer, Tree<Move> moveTree) {
		int an = a, bn = b;
		if (depth == 0 || Game.isGameOver(node)) {
			return rateBoard(node);
		}
		
		Set<Move> possibleMoves = getPossibleMoves(node, maximizingPlayer);
		VirtualBoard temp;
		// white will always try to get the highest board score aka white is max
		if (maximizingPlayer == State.WHITE) {
			for (Move move : possibleMoves) {
				moveTree.addLeaf(move);
				Tree<Move> subTree = moveTree.getTree(move);
				// apply the current move
				temp = new VirtualBoard(node);
				if(temp.isValidMove(move.getX(), move.getY(), maximizingPlayer)) {
					temp.flipTiles(move.getX(), move.getY(), maximizingPlayer);
				}
				an = Math.max(a, getBestMove(temp, depth - 1, an, bn, Tile.getOppositeState(maximizingPlayer),subTree));
				move.setScore(an);
				if (bn <= an) {
					break; // (* Beta cut-off *)
				}
			}
			return an;
		}
		// black will always try to get the lowest board score aka black is min
		else {
			for (Move move : possibleMoves) {
				moveTree.addLeaf(move);
				Tree<Move> subTree = moveTree.getTree(move);
				// apply the current move
				temp = new VirtualBoard(node);
				if(temp.isValidMove(move.getX(), move.getY(), maximizingPlayer)) {
					temp.flipTiles(move.getX(), move.getY(), maximizingPlayer);
				}
				bn = Math.min(b, getBestMove(temp, depth - 1, an, bn, Tile.getOppositeState(maximizingPlayer),subTree));
				move.setScore(bn);
				if (bn <= an) {
					break; // (* Alpha cut-off *)
				}
			}
			return bn;
		}
	}
		
	/**
	 *
	 * @param board
	 * @return a very positive number means the board is favorable for white
	 * 		while a very negative number means the board is favorable for black
	 */
	private static int rateBoard(VirtualBoard board){
		Set<Move> blackMoves = getPossibleMoves(board, State.BLACK);
		Set<Move> whiteMoves = getPossibleMoves(board, State.WHITE);
		
		int rating = 0;
		rating += getForfeit(blackMoves, whiteMoves) * FORFEIT_WEIGHT;
		rating += getMobility(blackMoves, whiteMoves) * MOBILITY_WEIGHT;
		rating += getFrontier(board) * FRONTIER_WEIGHT;
		rating += getScore(board) * SCORE_WEIGHT;
		return rating;
	}
	
	/**
	 * -1 if white would forfeit a turn on this board, 1 if black would forfeit, else 0
	 * @param board
	 * @return -1 if white would forfeit a turn on this board, 1 if black would forfeit, else 0
	 */
	private static int getForfeit(Set<Move> blackMoves, Set<Move> whiteMoves){
		int rating = 0;
		//case where players have no moves
		if(blackMoves.isEmpty()) {
			rating --;
		}
		if(whiteMoves.isEmpty()) {
			rating ++;
		}

		return rating;
	}
	
	/**
	 * rating based on how many moves each player can make
	 * @param board
	 * @return number of moves white can make - num moves black can make
	 */
	private static int getMobility(Set<Move> blackMoves, Set<Move> whiteMoves){
		return whiteMoves.size() - blackMoves.size();
	}
	
	/**
	 * rating based on how many disks are next to empty spaces
	 * @param board
	 * @return
	 */
	private static int getFrontier(VirtualBoard board){
		int rating = 0;
		
		//Get all black and white tiles on the board
		Set<VirtualTile> tiles = board.getTileSet();
		for (VirtualTile tile : tiles) {
			//Check to see if each tile has a blank tile adjacent to it
			if (board.hasAdjacentBlanks(tile)) {
				if (tile.getState() == Tile.State.WHITE) {
					//Increment for white
					rating++;
				}
				else if (tile.getState() == Tile.State.BLACK) {
					//Decrement for black
					rating--;
				}
			}
		}
		
		return rating;
	}
	
	/**
	 * rating based on the current score of the game
	 * @param board
	 * @return
	 */
	private static int getScore(VirtualBoard board){
		int rating = 0;
		rating += board.getWhiteTiles();
		rating -= board.getBlackTiles();
		return rating;
	}
	
	/**
	 * 
	 * @param board
	 * @param player
	 * @return set of all possible moves for given player
	 */
	private static Set<Move> getPossibleMoves(Board board, State player) {
		//Set of all possible moves that white or black can make at this turn.
		Set<Move> possibleMoves = new HashSet<Move>();
		
		//Check each tile on the board.
		for (int i = 0; i < VirtualBoard.SIZE; i++) {
			for (int j = 0; j < VirtualBoard.SIZE; j++) {
				//Look at only blank tiles.
				if (board.getTile(i, j).getState() == Tile.State.BLANK) {
					//Is it a valid move for player
					boolean ValidMove = board.isValidMove(i,j,player);
					//boolean blackValidMove = board.isValidMove(i,j,Tile.State.BLACK);
					
					if (ValidMove) {
						//Create the move for white and add to the set of moves.
						Move possibleMove = new Move(i,j,player);
						possibleMoves.add(possibleMove);
					}
				}
			}
		}
		
		return possibleMoves;
	}
	private static Set<Move> getPossibleMoves(VirtualBoard board, State player) {
		//Set of all possible moves that white or black can make at this turn.
		Set<Move> possibleMoves = new HashSet<Move>();
		
		//Check each tile on the board.
		for (int i = 0; i < VirtualBoard.SIZE; i++) {
			for (int j = 0; j < VirtualBoard.SIZE; j++) {
				//Look at only blank tiles.
				if (board.getTile(i, j).getState() == Tile.State.BLANK) {
					//Is it a valid move for player
					boolean ValidMove = board.isValidMove(i,j,player);
					//boolean blackValidMove = board.isValidMove(i,j,Tile.State.BLACK);
					
					if (ValidMove) {
						//Create the move for white and add to the set of moves.
						Move possibleMove = new Move(i,j,player);
						possibleMoves.add(possibleMove);
					}
				}
			}
		}
		
		return possibleMoves;
	}
}
