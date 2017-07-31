package me.bscal.game.mapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.sprites.SpriteSheet;

public class Tiles {

	private Scanner scanner;
	private List<Tile> tileList = new ArrayList<Tile>();
	
	public Tiles(File tilesFile, SpriteSheet sheet) {
		try {
			scanner = new Scanner(tilesFile);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(!line.startsWith("//")) {
					String[] split = line.split(",");
					String tileName = split[0];
					int x = Integer.parseInt(split[1]);
					int y = Integer.parseInt(split[2]);
					Tile tile = new Tile(tileName, sheet.getSprite(x, y));
					
					if(split.length >= 4) {
						tile.collisionType = Integer.parseInt(split[3]);
//						if(tile.collisionType != -1) {
//							tile.collidable = true;
//						}
//						if(split.length >= 5) {
//							tile.solidType = Integer.parseInt(split[4]);
//							if(tile.solidType == 1) {
//								tile.isSolid = false;
//							}
//						}
					}
					tileList.add(tile);
				}			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			scanner.close();
		}
	}
	
	public void renderTile(int tileID, Render renderer, int xPos, int yPos, int xZoom, int yZoom) {
		if(tileList.size() > tileID && tileID >= 0) {
			renderer.renderSprite(tileList.get(tileID).sprite, xPos, yPos, xZoom, yZoom, false);
		}
		else {
			System.out.println("TileID out of bounds: " + tileID);
		}
	}
	
	public int size() {
		return tileList.size();
	}
	
	public Sprite[] getSprites() {
		Sprite[] sprites = new Sprite[size()];
		for(int i = 0; i < size(); i++) {
			sprites[i] = tileList.get(i).sprite;
		}
		return sprites;
	}
	
	public int getCollisionType(int tileID) {
		if(tileList.size() > tileID && tileID >= 0) {
			return tileList.get(tileID).collisionType;
		}
		else {
			System.out.println("Tile Collision: TileID out of bounds: " + tileID);
		}
		return -1;
	}
	
	public boolean isSolid(int tileID) {
		if(tileList.size() > tileID) {
			Tile tile = tileList.get(tileID);
			if(tile.collisionType >= 0) {
				return true;
			}
		}
		else {
			System.out.println("Tile Collision: TileID out of bounds: " + tileID);
		}
		return false;
	}
	
	class Tile {
		public String tileName;
		public Sprite sprite;
		public boolean solid;
		public int collisionType = -1; //-1 = Tile has no collision.
		
		public Tile(String tileName, Sprite sprite) {
			this.tileName = tileName;
			this.sprite = sprite;
		}
	}
	
}
