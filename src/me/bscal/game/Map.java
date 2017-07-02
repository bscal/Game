package me.bscal.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import me.bscal.game.Tiles.Tile;

public class Map {

	private Scanner scanner;
	private Tiles tileSet;
	private int fillID = -1;
	
	private List<MappedTile> mappedTiles = new ArrayList<MappedTile>();
	private GridBlock[][] grids;
	private int startX, startY;
	private int gridWidth = 6;
	private int gridHeight = 6;
	private int blockPixelWidth = gridWidth * 16;
	private int blockPixelHeight = gridHeight * 16;
	
	private File mapFile;
	
	public Map(File mapFile, Tiles tileSet) {
		this.tileSet = tileSet;
		this.mapFile = mapFile;
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		try {
			scanner = new Scanner(mapFile);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(!line.startsWith("//")) {
					//Finds filler tile from file
					if(line.contains(":")) {
						String[] split = line.split(":");
						if(split[0].equalsIgnoreCase("FILL")) {
							fillID = Integer.parseInt(split[1]);
							continue;
						}
					}
					//Maps the tile from files
					String[] split = line.split(",");
					if(split.length >= 3) {
						MappedTile mappedTile = new MappedTile(
								Integer.parseInt(split[0]),
								Integer.parseInt(split[1]),
								Integer.parseInt(split[2]));
						
						if(mappedTile.x < minX) {
							minX = mappedTile.x;
						}
						if(mappedTile.x > maxX) {
							maxX = mappedTile.x;
						}
						if(mappedTile.y < minY) {
							minY = mappedTile.y;
						}
						if(mappedTile.y > maxY) {
							maxY = mappedTile.y;
						}
						
						mappedTiles.add(mappedTile);
					}
				}			
			}
			startX = minX;
			startY = minY;
			int blockWidth = maxX + gridWidth - minX;
			int blockHeight = maxY + gridHeight - minY;
			grids = new GridBlock[blockWidth][blockHeight];
			
			for(int i = 0; i < mappedTiles.size(); i++) {
				MappedTile mappedTile = mappedTiles.get(i);
				int gridX = (mappedTile.x - minX)/gridWidth;
				int gridY = (mappedTile.y - minY)/gridWidth;
				assert(gridX >= 0 && gridX < grids.length && gridY >= 0 && gridY < grids[0].length);
				if(grids[gridX][gridY] == null) {
					grids[gridX][gridY] = new GridBlock();
				}
				grids[gridX][gridY].mappedTiles.add(mappedTile);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			scanner.close();
		}
	}
	
	public void saveMap() {
		if(mapFile.exists()) {
			mapFile.delete();
		}
		try {
			mapFile.createNewFile();
			PrintWriter printer = new PrintWriter(mapFile);
			
			printer.println("//Two slashes mark a comment!");
			printer.println("//Fill:ID - the id of the tile to fill that whole screen");
			printer.println("//TileID,X,Y - id of tile, x position, y position");
			
			if(fillID >= 0) {
				printer.println("Fill:" + fillID);
			}
			for(int i = 0; i < mappedTiles.size(); i++) {
				MappedTile tile = mappedTiles.get(i);
				printer.println(tile.id + "," + tile.x + "," + tile.y);
			}
			printer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setTile(int tileX, int tileY, int id) {
		boolean foundTile = false;
		MappedTile mappedTile = null;
		
		for(int i = 0; i < mappedTiles.size(); i++) {
			mappedTile = mappedTiles.get(i);
			if(mappedTile.x == tileX && mappedTile.y == tileY) {
				mappedTile.id = id;
				foundTile = true;
				break;
			}
		}
		if(!foundTile) {
			mappedTiles.add(new MappedTile(id, tileX, tileY));
		}
		if(mappedTile == null) {
			mappedTile = new MappedTile(id, tileX, tileY);
			mappedTiles.add(new MappedTile(id, tileX, tileY));
		}
		int blockX = (tileX - startX)/gridWidth;
		int blockY = (tileY - startY)/gridHeight;
		if(blockX >= 0 && blockY >= 0 && blockX < grids.length && blockY < grids[0].length) {
			grids[blockX][blockY].setTile(mappedTile);
		}
	}
	
	public void removeTile(int tileX, int tileY) {
		for(int i = 0; i < mappedTiles.size(); i++) {
			MappedTile mappedTile = mappedTiles.get(i);
			if(mappedTile.x == tileX && mappedTile.y == tileY) {
				mappedTiles.remove(i);
			}
		}	
	}
	
	public void render(Render renderer, int xZoom, int yZoom) {
		int xIncrement = 16 * xZoom;
		int yIncrement = 16 * yZoom;
		
		if(fillID >= 0) {
			Rectangle camera = renderer.getCamera();
			for(int y = camera.y - yIncrement - (camera.y % yIncrement); y < camera.y + camera.height; y += yIncrement) {
				for(int x = camera.x - xIncrement - (camera.x % xIncrement); x < camera.x + camera.width; x += xIncrement) {
					tileSet.renderTile(fillID, renderer, x, y, xZoom, yZoom);
				}
			}
		}
		
		int topLeftX = renderer.getCamera().x;
		int topLeftY = renderer.getCamera().y;
		int bottomRightX = renderer.getCamera().x + renderer.getCamera().width;
		int bottomRightY = renderer.getCamera().y + renderer.getCamera().height;
		
		int leftBlockX = (topLeftX/xIncrement - startX - 16)/gridWidth;
		int blockX = leftBlockX;
		int blockY = (topLeftY/yIncrement - startY - 16)/gridHeight;
		int pixelX = topLeftX;
		int pixelY = topLeftY;
		
		while(pixelX < bottomRightX && pixelY < bottomRightY) {
			if(blockX >= 0 && blockY >= 0 && blockX < grids.length && blockY < grids[0].length) {
				if(grids[blockX][blockY] != null) {
					grids[blockX][blockY].render(renderer, xIncrement, yIncrement, xZoom, yZoom);
				}
			}
			blockX++;
			pixelX += blockPixelWidth;
			if(pixelX > bottomRightX) {
				pixelX = topLeftX;
				blockX = leftBlockX;
				blockY++;
				pixelY += blockPixelHeight;
				if(pixelY > bottomRightY) {
					break;
				}
			}
		}
	}
	
	class MappedTile {
		public int id, x, y;
		
		public MappedTile(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
	
	class GridBlock {
		public ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();
		
		public void render(Render renderer, int xIncrement, int yIncrement, int xZoom, int yZoom) {
			for(int i = 0; i < mappedTiles.size(); i++) {
				MappedTile mappedTile = mappedTiles.get(i);
				tileSet.renderTile(mappedTile.id, renderer, mappedTile.x * xIncrement, mappedTile.y * yIncrement, xZoom, yZoom);
			}
		}
		
		public void setTile(MappedTile mappedTile) {
			boolean foundTile = false;
			for(int i = 0; i < mappedTiles.size(); i++) {
				MappedTile currentTile = mappedTiles.get(i);
				if(currentTile.x == mappedTile.x && currentTile.y == mappedTile.y) {
					mappedTiles.set(i, mappedTile);
				}
			}
			if(!foundTile) {
				mappedTiles.add(mappedTile);
			}
		}
	}
	
}
