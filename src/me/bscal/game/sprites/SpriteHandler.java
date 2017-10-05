package me.bscal.game.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.bscal.game.Game;

public class SpriteHandler {
	
	public static SpriteSheet tileSheet;
	public static SpriteSheet playerSheet;
	public static SpriteSheet fireball;
	public static SpriteSheet frostbolt;
	public static SpriteSheet explosion;
	
	public static Sprite[] waterTiles;
	
	public SpriteHandler() {
		BufferedImage sheetImg = loadImage(Game.PATH + "img/Tiles1.png");
		tileSheet = new SpriteSheet(sheetImg);
		tileSheet.init(16, 16);
		//AnimatedSprites Sheet
		BufferedImage playerImg = loadImage(Game.PATH + "img/Player.png");
		playerSheet = new SpriteSheet(playerImg);
		playerSheet.init(20, 26);
		//Fireball Sheet
		BufferedImage fireballImg = loadAlphaImage(Game.PATH + "img/Fireball.png");
		fireball = new SpriteSheet(fireballImg);
		fireball.init(16, 16);
		//Frostbolt Sheet
		BufferedImage frostboltImg = loadAlphaImage(Game.PATH + "img/Frostbolt.png");
		frostbolt = new SpriteSheet(frostboltImg);
		frostbolt.init(16, 16);
		//Explosion Sheet
		BufferedImage explosionImg = loadAlphaImage(Game.PATH + "img/Explosion.png");
		explosion = new SpriteSheet(explosionImg);
		explosion.init(16, 16);
		
		SpriteSheet tilesSheet;
		BufferedImage tiles = loadAlphaImage(Game.PATH + "img/GameTiles.png");
		tilesSheet = new SpriteSheet(tiles);
		tilesSheet.init(16, 16);
		
		waterTiles = new Sprite[3];
		waterTiles[0] = tilesSheet.getSprite(0, 0);
		waterTiles[1] = tilesSheet.getSprite(1, 0);
		waterTiles[2] = tilesSheet.getSprite(2, 0);
		//TODO Water Tiles
	}
	
	public static BufferedImage loadImage(String path) {
		try {
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formated = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formated.getGraphics().drawImage(loadedImage, 0, 0, null);
			return formated;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage loadAlphaImage(String path) {
		try {
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formated = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
			formated.getGraphics().drawImage(loadedImage, 0, 0, null);
			return formated;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
