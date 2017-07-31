package me.bscal.game.sprites;

import java.awt.image.BufferedImage;

import me.bscal.game.Game;

public class Sprite {
	
	protected int width, height;
	protected int[] pixels;
	
	public Sprite() {}
	
	public Sprite(SpriteSheet sheet, int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
		sheet.getImage().getRGB(x, y, width, height, pixels, 0, width);
	}
	
	public Sprite(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}

	public void rotate(double angle) {
		int[] result = new int[width * height];
		
		double newXX = rotateX(-angle, 1.0, 0.0);
		double newXY = rotateY(-angle, 1.0, 0.0);
		double newYX = rotateX(-angle, 0.0, 1.0);
		double newYY = rotateY(-angle, 0.0, 1.0);
		
		double x0 = rotateX(-angle, -width/2.0, -height/2.0) + width/2.0;
		double y0 = rotateY(-angle, -width/2.0, -height/2.0) + height/2.0;
		
		for(int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for(int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int color = 0;
				if(xx < 0 || xx >= width || yy < 0 || yy >= height) color = Game.ALPHA;
				else color = pixels[xx + yy * width];
				result[x + y * width] = color;
				x1 += newXX;
				y1 += newXY;
			}
			x0 += newYX;
			y0 += newYY;
		}
		pixels = result;
	}
	
	private double rotateX(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * cos + y * -sin;
	}
	
	private double rotateY(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * sin + y * cos;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
}
