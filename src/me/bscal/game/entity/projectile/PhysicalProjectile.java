package me.bscal.game.entity.projectile;

import me.bscal.game.Game;
import me.bscal.game.entity.Entity;

public abstract class PhysicalProjectile extends Entity implements Projectile{

	protected final double xOrigin, yOrigin;
	protected double angle;
	protected double newX, newY;
	protected int speed, range, attackSpeed, damage;
	protected boolean flying = true;
	
	public PhysicalProjectile(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		range = 50;
		speed = 12;
		
		newX = speed * Math.cos(angle);
		newY = speed * Math.sin(angle);
	}
	
	public void move() {
		rect.x += newX;
		rect.y += newY;
	}
	
	public boolean checkCollision(Game game) {
		if(!game.getMap().checkCollision(rect, layer, game.getXZoom(), game.getYZoom()) 
				&& !game.getMap().checkCollision(rect, layer + 1, game.getXZoom(), game.getYZoom())
				&& !game.getMap().checkCollision(rect, layer + 2, game.getXZoom(), game.getYZoom())) {
			return false;
		}
		return true;
	}
	
	public boolean isFlying() {
		return flying;
	}
}
