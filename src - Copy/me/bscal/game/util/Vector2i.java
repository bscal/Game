package me.bscal.game.util;

public class Vector2i {

	private int x, y;
	
	public Vector2i() {
		set(0, 0);
	}
	
	public Vector2i(Vector2i v) {
		set(v.x, v.y);
	}
	
	public Vector2i(int x, int y) {
		set(x, y);
	}
	
	public Vector2i set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}
	
	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}
	
	public double getDistance(Vector2i v0) {
		double x = this.getX() - v0.getX();
		double y = this.getY() - v0.getY();
		return Math.sqrt((x * x) + (y * y));
	}
	
	public Vector2i add(Vector2i v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Vector2i subtract(Vector2i v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public boolean equals(Object object) {
		if(!(object instanceof Vector2i)) return false;
		Vector2i vector = (Vector2i) object;
		if(vector.getX() == this.getX() && vector.getY() == this.getY()) return true;
		return false;
	}
	
}
