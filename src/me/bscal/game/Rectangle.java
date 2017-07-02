package me.bscal.game;

public class Rectangle {
	
	public int x,y,height,width;
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
	
	public int[] getPixels() {
		if(pixels == null) {
			System.out.println("No graphics found!");
			return null;
		}
		return pixels;
	}
	
}
