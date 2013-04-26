/**
 * 
 */
package reversi;

import reversi.Tile.State;

/**
 * @author kAG0
 *
 */
public class Move implements Comparable{
		private int x, y;
		private State player;
		private int score;	// how good the move is for the player
		
		public Move(int x, int y, State player){
			this.x = x;
			this.y = y;
			this.player = player;
			score = 0;
		}
		public Move(int x, int y, State player, int score){
			this.x = x;
			this.y = y;
			this.player = player;
			this.score = score;
		}
		
		/**
		 * @return the score
		 */
		public int getScore() {
			return score;
		}

		/**
		 * @param score the score to set
		 */
		public void setScore(int score) {
			this.score = score;
		}

		/**
		 * @return the x
		 */
		public int getX() {
			return x;
		}
		/**
		 * @param x the x to set
		 */
		public void setX(int x) {
			this.x = x;
		}
		/**
		 * @return the y
		 */
		public int getY() {
			return y;
		}
		/**
		 * @param y the y to set
		 */
		public void setY(int y) {
			this.y = y;
		}
		/**
		 * @return the player
		 */
		public State getPlayer() {
			return player;
		}
		/**
		 * @param player the player to set
		 */
		public void setPlayer(State player) {
			this.player = player;
		}
		@Override
		public int compareTo(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}


}
