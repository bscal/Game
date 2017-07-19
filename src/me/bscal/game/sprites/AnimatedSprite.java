package me.bscal.game.sprites;

import java.awt.image.BufferedImage;

import me.bscal.game.Game;
import me.bscal.game.entity.GameObject;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class AnimatedSprite extends Sprite implements GameObject{

	private Sprite[] sprites;
	private int index = 0;
	private int speed;
	private int counter = 0;
	private int startSprite = 0;
	private int endSprite = 0;
	private int updates = 0;
	
	public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed) {
		sprites = new Sprite[positions.length];
		this.speed = speed;
		
		for(int i = 0; i < positions.length; i++) {
			sprites[i] = new Sprite(sheet, positions[i].x, positions[i].y, positions[i].width, positions[i].height);
		}
	}
	
	public AnimatedSprite(BufferedImage[] images, int speed) {
		sprites = new Sprite[images.length];
		this.speed = speed;
		
		for(int i = 0; i < images.length; i++) {
			sprites[i] = new Sprite(images[i]);
		}
	}
	
	public AnimatedSprite(SpriteSheet sheet, int speed) {
		sprites = sheet.getLoadedSprites();
		this.speed = speed;
		this.endSprite = sprites.length - 1;
	}

	public void render(Render renderer, int xZoom, int yZoom) {}

	public void update(Game game) {
		counter++;
		if(counter == speed) {
			counter = 0;
			nextSprite();
		}
	}
	
	public void reset() {
		counter = 0;
		index = startSprite;
	}
	
	public int getWidth() {
		return sprites[index].getWidth();
	}
	
	public int getHeight() {
		return sprites[index].getHeight();
	}
	
	public int[] getPixels() {
		return sprites[index].getPixels();
	}

	public void setAnimationRange(int start, int finish) {
		startSprite = start;
		endSprite = finish;
		reset();
	}
	
	public void nextSprite() {
		index++;
		updates++;
		if(index > endSprite) {
			index = startSprite;
		}
		
	}

	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return false;
	}

	public Rectangle getRectangle() {
		return null;
	}
	
	public int getLayer() {
		return -1;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getTotalUpdates() {
		return updates;
	}
	
	public int getStartSprite() {
		return startSprite;
	}
	
	public int getEndSprite() {
		return endSprite;
	}
	
}
