package reversi;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import reversi.Tile.State;

public class Board extends JFrame {
	public static final int SIZE = 8;
	
	//Tiles for GUI
	Tile[][] tiles = new Tile[SIZE][SIZE];

	//directions for refrencing the CCR
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int UPLEFT = 4;
	public static final int UPRIGHT = 5;
	public static final int DOWNLEFT = 6;
	public static final int DOWNRIGHT = 7;

	private boolean[] CCR = new boolean[8];

	//The set of all black or white tiles
	Set<Tile> playerTiles = new HashSet<Tile>();
	
	private int blankTiles = 0;
	private int blackTiles = 0;
	private int whiteTiles = 0;
	private Game game;
	
	/**
	 * creates a board from another board with no GUI
	 */
	public Board(Board board) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Tile.State tileState = board.getTile(i,j).getState();
				tiles[i][j] = new Tile(tileState,this,i,j);
				placeTile(i,j,tileState);
			}
		}
	}
	
	/**
	 * creates a board with tiles in the default positions
	 */
	public Board(Game game){	
		this.game = game;
		//GUI setup
		setSize(500, 500);
		setResizable(false);
		setLayout(new GridLayout(SIZE,SIZE));
		getContentPane().setBackground(Color.white);
		
		//Layout tiles
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				tiles[i][j] = new Tile(this,i,j);
				add(tiles[i][j]);
			}
		}
		
		//Middle of Board
		final int middle = SIZE / 2;
		
		//Setup initial tiles
		placeTile(middle - 1,middle - 1, Tile.State.WHITE);
		placeTile(middle - 1,middle, Tile.State.BLACK);
		placeTile(middle,middle, Tile.State.WHITE);
		placeTile(middle,middle - 1, Tile.State.BLACK);
		
		clearCCR();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
		
	public void addTile(State tile) {
		switch (tile) {
			case BLACK:
				blackTiles++;
				break;
			case WHITE:
				whiteTiles++;
				break;
			default:
				blankTiles++;
				break;
			}
	}
	
	public void removeTile(State tile) {
		switch (tile) {
			case BLACK:
				blackTiles--;
				break;
			case WHITE:
				whiteTiles--;
				break;
			default:
				blankTiles--;
				break;
			}
	}
	
	public int getBlackTiles() {
		return blackTiles;
	}
	
	public int getWhiteTiles() {
		return whiteTiles;
	}
	
	public int getBlankTiles() {
		return blankTiles;
	}
	
	public void tileClick(int x, int y) {
		game.tileClick(x, y);
	}

	public void unHighLightTiles() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				tiles[i][j].lowlight();
			}
		}
	}
	
	public boolean hasBlankTiles() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (tiles[i][j].isBlank()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param x x position of tile to return
	 * @param y y position of tile to return
	 * @return
	 */
	public Tile getTile(int x, int y){
		return tiles[x][y];
	}
	
	public void placeTile(int x, int y, State color) {
		if (tiles[x][y].getState() == State.BLANK) {
			setTile(x,y,color);
		}
		
		playerTiles.add(tiles[x][y]);
	}
	
	public Set<Tile> getTileSet() {
		return playerTiles;
	}
	
	public void setTile(int x, int y, State color){
		tiles[x][y].setState(color);
	}
	
	public void debugPrint(){
		for (int x = 0; x < 8; x++) {
		    for (int y = 0; y < 8; y++) {
		    	if(tiles[y][x].getState().equals(State.BLANK))
					System.out.print("*");
				if(tiles[y][x].getState().equals(State.BLACK))
					System.out.print("X");
				if(tiles[y][x].getState().equals(State.WHITE))
				    System.out.print("O");
		    }
		    System.out.println();
		}
	}
	

	/**
	 * flips a tile at the given location
	 * @param x x position of tile to flip
	 * @param y y position of tile to flip
	 */
	public void flipTile(int x, int y){
		tiles[x][y].flip();
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	
	private void clearCCR(){
		for(int i = 0; i < Board.SIZE; i++){
			CCR[i] = false;
		}
	}

	public boolean moveFromLeft(int x, int y, State col){
		if(x-1 >= 0){
			if(getTile(x-1, y).isBlank())
				return false;
			if(getTile(x-1, y).getState().equals(col)){
				if(getTile(x, y).isBlank())
					return false;
				if(getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromLeft(x-1, y, col);
			}
		}
		return false;
	}
	public boolean moveFromRight(int x, int y, State col){
		if(x+1 <= 7){
			if(getTile(x+1, y).isBlank())
				return false;
			if(getTile(x+1, y).getState().equals(col)){
				if(getTile(x, y).isBlank())
					return false;
				if(getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromRight(x+1, y, col);
			}
		}
		return false;
	}
	public boolean moveFromUp(int x, int y, State col){
		if(y-1 >= 0){
			if(getTile(x, y-1).isBlank())
				return false;
			if(getTile(x, y-1).getState().equals(col)){
				if(getTile(x, y).isBlank())
					return false;
				if(getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromUp(x, y-1, col);
			}
		}
		return false;
	}
	public boolean moveFromUpRight(int x, int y, State col){
		if(y-1 >= 0 && x+1 <=7){
			if(getTile(x+1, y-1).isBlank())
				return false;
			if(getTile(x+1, y-1).getState().equals(col)){
				if(getTile(x, y).isBlank())
					return false;
				if(getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromUpRight(x+1, y-1, col);
			}
		}
		return false;
	}
	public boolean moveFromUpLeft(int x, int y, State col){
		if(y-1 >= 0 && x-1 >=0){
			if(getTile(x-1, y-1).isBlank())
				return false;
			if(getTile(x-1, y-1).getState().equals(col)){
				if(getTile(x, y).isBlank())
					return false;
				if(getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromUpLeft(x-1, y-1, col);
			}
		}
		return false;
	}
	public boolean moveFromDown(int x, int y, State col){
		if(y+1 <= 7){
			if(getTile(x, y+1).isBlank())
				return false;
			if(getTile(x, y+1).getState().equals(col)){
				if(getTile(x, y).isBlank())
					return false;
				if(getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromDown(x, y+1, col);
			}
		}
		return false;
	}
	public boolean moveFromDownRight(int x, int y, State col){
		if(y+1 <= 7 && x+1 <= 7){
			if(getTile(x+1, y+1).isBlank())
				return false;
			if(getTile(x+1, y+1).getState().equals(col)){
				if(getTile(x, y).isBlank())
					return false;
				if(getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromDownRight(x+1, y+1, col);
			}
		}
		return false;
	}
	public boolean moveFromDownLeft(int x, int y, State col){
		if(y+1 <= 7 && x-1 >= 0){
			if(getTile(x-1, y+1).isBlank())
				return false;
			if(getTile(x-1, y+1).getState().equals(col)){
				if(getTile(x, y).isBlank())
					return false;
				if(getTile(x, y).getState().equals(col))// may not be needed
					return false;
				return true;
			}
			else{
				return moveFromDownLeft(x-1, y+1, col);
			}
		}
		return false;
	}
	//need to change null checks to checks for blank state
	public boolean isValidMove(int x, int y, State player){
		clearCCR();
		boolean u = false;
		boolean	d = false;
		if(!getTile(x, y).isBlank())
			return false;
		if(x != 0){
			if(moveFromLeft(x,y,player)) //check left
				CCR[LEFT] = true;
			if(y != 0){
				if(moveFromUp(x,y,player))//check up
					CCR[UP] = true;
				u = true;
				//check up-left
				if(moveFromUpLeft(x,y,player))
					CCR[UPLEFT] = true;
			}
			if(y != 7){
				if(moveFromDown(x,y,player))//check down
					CCR[DOWN] = true;
				d = true;
				//check down-left
				if(moveFromDownLeft(x,y,player))
					CCR[DOWNLEFT] = true;
			}
		}
		if(x != 7){
			//check right
			if(moveFromRight(x,y,player))
				CCR[RIGHT] = true;
			if(y != 0){
				//check up-right
				if(moveFromUpRight(x,y,player))
					CCR[UPRIGHT] = true;
				if(!u){
					//check up
					if(moveFromUp(x,y,player))
						CCR[UP] = true;
				}
				
			}
			if(y != 7){
				if(!d){
					//check down
					if(moveFromDown(x,y,player))
						CCR[DOWN] = true;
				}
				
				//check down-right
				if(moveFromDownRight(x,y,player))
					CCR[DOWNRIGHT] = true;
			}
		}
		for(boolean dir : CCR){
			if(dir) {
				return true;
			}
		}
		return false;
		
	}
	
	public void flipTiles(int x, int y, Tile.State player) {
		if(CCR[LEFT]){
			flipLeft(x, y, player);
		}
		if(CCR[RIGHT]){
			flipRight(x, y, player);
		}
		if(CCR[UP]){
			flipUp(x, y, player);
		}
		if(CCR[DOWN]){
			flipDown(x, y, player);
		}
		if(CCR[DOWNLEFT]){
			flipDownLeft(x, y, player);
		}
		if(CCR[DOWNRIGHT]){
			flipDownRight(x, y, player);
		}
		if(CCR[UPLEFT]){
			flipUpLeft(x, y, player);
		}
		if(CCR[UPRIGHT]){
			flipUpRight(x, y, player);
		}
	}
	
	/**
	 * @pre the x,y must have a tile in it
	 * @param x 
	 * @param y
	 * @param player the player who's turn it is
	 */
	private void flipLeft(int x, int y, State player){
		if(getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			flipTile(x, y);
		}
		if(x-1 >= 0){
			if(getTile(x-1, y).getState().equals(Tile.getOppositeState(player)))
				flipLeft(x-1, y, player);
		}
	}
	private void flipRight(int x, int y, State player){
		if(getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			flipTile(x, y);
		}
		if(x+1 <= 7){
			if(getTile(x+1, y).getState().equals(Tile.getOppositeState(player)))
				flipRight(x+1, y, player);
		}
	}
	private void flipUp(int x, int y, State player){
		if(getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			flipTile(x, y);
		}
		if(y-1 >= 0){
			if(getTile(x, y-1).getState().equals(Tile.getOppositeState(player)))
				flipUp(x, y-1, player);
		}
	}
	private void flipUpRight(int x, int y, State player){
		if(getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			flipTile(x, y);
		}
		if(y-1 >= 0 && x+1 <= 7){
			if(getTile(x+1, y-1).getState().equals(Tile.getOppositeState(player)))
				flipUpRight(x+1, y-1, player);
		}
	}
	private void flipUpLeft(int x, int y, State player){
		if(getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			flipTile(x, y);
		}
		if(y-1 >= 0 && x-1 >= 0){
			if(getTile(x-1, y-1).getState().equals(Tile.getOppositeState(player)))
				flipUpLeft(x-1, y-1, player);
		}
	}
	private void flipDown(int x, int y, State player){
		if(getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			flipTile(x, y);
		}
		if(y+1 <= 7){
			if(getTile(x, y+1).getState().equals(Tile.getOppositeState(player)))
				flipDown(x, y+1, player);
		}
	}
	private void flipDownLeft(int x, int y, State player){
		if(getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			flipTile(x, y);
		}
		if(y+1 <= 7 && x-1 >= 0){
			if(getTile(x-1, y+1).getState().equals(Tile.getOppositeState(player)))
				flipDownLeft(x-1, y+1, player);
		}
	}
	private void flipDownRight(int x, int y, State player){
		if(getTile(x, y).getState().equals(Tile.getOppositeState(player)))
		{
			flipTile(x, y);
		}
		if(y+1 <= 7 && x+1 <= 7){
			if(getTile(x+1, y+1).getState().equals(Tile.getOppositeState(player)))
				flipDownRight(x+1, y+1, player);
		}
	}
}
