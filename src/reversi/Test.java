package reversi;

import reversi.Tile.State;

public class Test {
	public static void main(String[] args){
		Game test = new Game();
		System.out.println(AI.getBestMove(test.board, State.BLACK));
		
//		test.board.getTile(3, 3).setState(State.BLANK);
//		test.board.getTile(3, 4).setState(State.BLANK);
//		test.board.getTile(4, 3).setState(State.BLANK);
//		test.board.getTile(4, 4).setState(State.BLANK);
//		test.board.setTile(3,3,State.WHITE);
//		test.board.setTile(6,1,State.BLACK);
//		test.board.setTile(4,6,State.BLACK);
//		test.board.setTile(6,3,State.BLACK);
//		test.board.setTile(1,3,State.BLACK);
//		test.board.setTile(7,6,State.BLACK);
//		test.board.setTile(4,0,State.BLACK);
//		test.board.setTile(1,6,State.BLACK);
//		test.board.setTile(1,0,State.BLACK);
//		test.board.setTile(4,1,State.WHITE);
//		test.board.setTile(5,4,State.WHITE);
//		test.board.setTile(6,5,State.WHITE);
//		test.board.setTile(4,2,State.WHITE);
//		test.board.setTile(5,2,State.WHITE);
//		test.board.setTile(3,2,State.WHITE);
//		test.board.setTile(2,1,State.WHITE);
//		test.board.setTile(5,3,State.WHITE);
//		test.board.setTile(2,3,State.WHITE);
//		test.board.setTile(3,4,State.WHITE);
//		test.board.setTile(2,5,State.WHITE);
//		test.board.setTile(4,5,State.WHITE);
//		test.board.setTile(4,4,State.WHITE);
//		test.board.debugPrint();
//		System.out.println(test.isValidMove(4, 3, State.BLACK));
//		test.board.setTile(4, 3, State.BLACK);
//		test.makeMove(4, 3);
//		test.board.debugPrint();
	}
}
