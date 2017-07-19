package me.bscal.game.graphics;

public class PreciseRectangle extends Rectangle{

	public double dx, dy;
	
	public PreciseRectangle(double x, double y, int width, int height) {
		this.dx = x;
		this.dy = y;
		this.x = (int) x;
		this.y = (int) y;
		this.height = height;
		this.width = width;
	}
}
