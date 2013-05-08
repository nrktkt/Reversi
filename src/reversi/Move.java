package reversi;
import reversi.Tile.State;

/**
 * A Move consists of the player that made that move
 * and a coordinate pair. Optionally, a Move may include
 * a score. Forfeit is set to true when a move causes the 
 * opposing player to forfeit their move.
 *
 */
public class Move implements Comparable {

	private int x, y;
	private State player;
	private int score; // how good the move is for the player
	private boolean forfeit; // does this move create a forfeit

	//Creates a move for a given player at [x,y]
	public Move(int x, int y, State player) {
		this.x = x;
		this.y = y;
		this.player = player;
		score = 0;
		forfeit = false;
	}

	//Creates a move for a given player at [x,y] with a score
	public Move(int x, int y, State player, int score) {
		this.x = x;
		this.y = y;
		this.player = player;
		this.score = score;
		forfeit = false;
	}
/**
	 * @return the forfeit
	 */
	public boolean isForfeit() {
		return forfeit;
	}

	/**
	 * @param forfeit the forfeit to set
	 */
	public void setForfeit(boolean forfeit) {
		this.forfeit = forfeit;
	}

	private String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}

	//Pretty prints the move
	@Override
	public String toString() {
		return "Move [("+getCharForNumber(y+1)+","+(x+1)+") player=" + player + ", score="
				+ score + "]";
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
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
	 * @param x
	 *            the x to set
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
	 * @param y
	 *            the y to set
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
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(State player) {
		this.player = player;
	}

	/**
	 * read: Move.
	 * @return a negative integer as this object is less than, zero if equal to,
	 *         or a positive integer as this object is greater than the
	 *         specified object.
	 */
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			throw new NullPointerException("comparing object is null");
		Move other = (Move) o;
		return score - other.getScore();
	}

	//Compares against another Move object
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (player != other.player)
			return false;
		if (score != other.score)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
