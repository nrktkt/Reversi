package reversi;
import reversi.Tile.State;

//Interface that Tile and VirtualTile conform to.
public interface TileForm {
	
	public int getXCoord();
	
	public int getYCoord();
	
	public void flip();

	public boolean isBlank();
	
	public State getReverseState();

	public void setState(State state);

	public State getState();
}
