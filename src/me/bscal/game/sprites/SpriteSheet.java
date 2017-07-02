package me.bscal.game.sprites;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private int[] pixels;
	private BufferedImage image;
	private final int WIDTH;
	private final int HEIGHT;
	private Sprite[] loadedSprites = null;
	private boolean spritesLoaded = false;
	private int spriteX;
	
	public SpriteSheet(BufferedImage img) {
		image = img;
		WIDTH = img.getWidth();
		HEIGHT = img.getHeight();
		
		pixels = new int[WIDTH * HEIGHT];
		pixels = image.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
	}
	
	public void init(int spriteX, int spriteY) {
		int spriteID = 0;
		this.spriteX = spriteX;
		loadedSprites = new Sprite[(WIDTH / spriteX) * (HEIGHT / spriteY)];
		
		for(int y = 0; y < HEIGHT; y += spriteY) {
			for(int x = 0; x < WIDTH; x += spriteX) {
				loadedSprites[spriteID] = new Sprite(this, x, y, spriteX, spriteY);
				spritesLoaded = true;
				spriteID++;
			}
		}
	}
	
	public void init(int spriteX, int spriteY, int padding) {
		int spriteID = 0;
		this.spriteX = spriteX;
		loadedSprites = new Sprite[(WIDTH / spriteX) * (HEIGHT / spriteY)];
		
		for(int y = 0; y < HEIGHT; y += spriteY + padding) {
			for(int x = 0; x < WIDTH; x += spriteX + padding) {
				loadedSprites[spriteID] = new Sprite(this, x, y, spriteX, spriteY);
				spritesLoaded = true;
				spriteID++;
			}
		}
	}
	
	public Sprite[] getLoadedSprites() {
		return loadedSprites;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public Sprite getSprite(int x, int y) {
		if(spritesLoaded) {
			int spriteID = x + y * (WIDTH / spriteX);
			if(spriteID < loadedSprites.length) {
				return loadedSprites[spriteID];
			}
			System.out.println("Loaded sprite out of bounds. ID: " + spriteID);
			return null;
		}
		System.out.println("No sprites loaded!");
		return null;
	}
	
}
