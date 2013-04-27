package reversi;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import reversi.Tile.State;

public class Board extends JFrame {
	public static final int SIZE = 8;
	Tile[][] tiles = new Tile[SIZE][SIZE];
	
	//The set of all black or white tiles
	Set<Tile> playerTiles = new HashSet<Tile>();
	
	private int blankTiles = 0;
	private int blackTiles = 0;
	private int whiteTiles = 0;
	private Game game;
	
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
		
//		tiles[middle - 1][middle - 1].setState(Tile.State.WHITE);
//		tiles[middle - 1][middle].setState(Tile.State.BLACK);
//		tiles[middle][middle].setState(Tile.State.WHITE);
//		tiles[middle][middle - 1].setState(Tile.State.BLACK);

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
}
