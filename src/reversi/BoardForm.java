package reversi;

import java.util.Set;
import reversi.Tile.State;

//Interface that Tile and VirtualTile conform to.
public interface BoardForm {
	public static final int SIZE = 8;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int UPLEFT = 4;
	public static final int UPRIGHT = 5;
	public static final int DOWNLEFT = 6;
	public static final int DOWNRIGHT = 7;

			
	public void addTile(State tile);
	
	public void removeTile(State tile);
	
	public int getBlackTiles();
	
	public int getWhiteTiles();
	
	public int getBlankTiles();
	
	public boolean hasBlankTiles();
	
	/**
	 * 
	 * @param x x position of tile to return
	 * @param y y position of tile to return
	 * @return
	 */
	public TileForm getTile(int x, int y);
	
	public void placeTile(int x, int y, State color);
	
	public Set<TileForm> getTileSet();
	
	public void setTile(int x, int y, State color);
	
	public void debugPrint();	

	/**
	 * flips a tile at the given location
	 * @param x x position of tile to flip
	 * @param y y position of tile to flip
	 */
	public void flipTile(int x, int y);
	
	public TileForm[][] getTiles();

	public boolean moveFromLeft(int x, int y, State col);
	public boolean moveFromRight(int x, int y, State col);
	public boolean moveFromUp(int x, int y, State col);
	public boolean moveFromUpRight(int x, int y, State col);
	public boolean moveFromUpLeft(int x, int y, State col);
	public boolean moveFromDown(int x, int y, State col);
	public boolean moveFromDownRight(int x, int y, State col);
	public boolean moveFromDownLeft(int x, int y, State col);
	public boolean isValidMove(int x, int y, State player);
	public void flipTiles(int x, int y, Tile.State player);
}
