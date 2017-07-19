package me.bscal.game.graphics;

import me.bscal.game.Game;
import me.bscal.game.entity.Entity;

public class Rectangle {
	
	public int x,y,height,width;
	private boolean isSolid = false;	//If false allows flying objects to not collide with it.
	private Entity owner;				//Used to get the owner of the rectangle.
	private int[] pixels;
	
	public Rectangle() {
		this(0, 0, 0, 0);
	}
	
	//Viewport
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.owner = null;
	}
	
	public Rectangle(int x, int y, int width, int height, Entity owner) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.owner = owner;
	}
	
	public boolean intersects(Rectangle otherRect) {
		if(x > otherRect.x + otherRect.width || otherRect.x > x + width) {
			return false;
		}
		if(y > otherRect.y + otherRect.height || otherRect.y > y + height) {
			return false;
		}
		return true;
	}
	
	public void createGraphics(int color) {
		pixels = new int[width * height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				pixels[x + y * width] = color;
			}
		}
	}
	
	public void createGraphics(int borderWidth, int color) {
		pixels = new int[width * height];
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = Game.alpha;
		}
		
		for(int y = 0; y < borderWidth; y++) {					//Top
			for(int x = 0; x < width; x++) {
				pixels[x + y * width] = color;
			}
		}
		for(int y = 0; y < height; y++) {						//Left
			for(int x = 0; x < borderWidth; x++) {
				pixels[x + y * width] = color;
			}
		}
		for(int y = height - borderWidth; y < height; y++) {	//Bottom
			for(int x = 0; x < width; x++) {
				pixels[x + y * width] = color;
			}
		}
		for(int y = 0; y < height; y++) { 
			for(int x = width - borderWidth; x < width; x++) {	//Right
				pixels[x + y * width] = color;
			}
		}
	}
	
	public int getCenterX() {
		return x + width/2;
	}
	
	public int getCenterY() {
		return y + height/2;
	}
	
	public Entity getOwner() {
		if(owner != null) {
			return owner;
		}
		return null;
	}
	
	public boolean isSolid() {
		return isSolid;
	}
	
	public int[] getPixels() {
		if(pixels == null) {
			System.out.println("No graphics found!");
			return null;
		}
		return pixels;
	}
	
}
