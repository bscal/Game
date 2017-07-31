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
	
	public SpriteHandler() {
		BufferedImage sheetImg = loadImage(Game.PATH + "Tiles1.png");
		tileSheet = new SpriteSheet(sheetImg);
		tileSheet.init(16, 16);
		//AnimatedSprites Sheet
		BufferedImage playerImg = loadImage(Game.PATH + "Player.png");
		playerSheet = new SpriteSheet(playerImg);
		playerSheet.init(20, 26);
		//Fireball Sheet
		BufferedImage fireballImg = loadImage(Game.PATH + "Fireball.png");
		fireball = new SpriteSheet(fireballImg);
		fireball.init(16, 16);
		//Frostbolt Sheet
		BufferedImage frostboltImg = loadImage(Game.PATH + "Frostbolt.png");
		frostbolt = new SpriteSheet(frostboltImg);
		frostbolt.init(16, 16);
		//Explosion Sheet
		BufferedImage explosionImg = loadImage(Game.PATH + "Explosion.png");
		explosion = new SpriteSheet(explosionImg);
		explosion.init(16, 16);
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
	
}
