package me.bscal.game.entity.projectile;

import me.bscal.game.entity.Entity;
import me.bscal.game.entity.Spell;
import me.bscal.game.sprites.Sprite;

public abstract class MagicProjectile extends Spell implements Projectile{

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected double newX, newY;
	
	public MagicProjectile(int x, int y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		//System.out.println(newX + ", " + newY + ", " + speed + ", " + dir);
	}
	
	public MagicProjectile(Sprite sprite, Entity caster, int animationLength, int x, int y, double dir) {
		super(sprite, caster, animationLength);
		xOrigin = x;
		yOrigin = y;
		angle = dir;
	}
	
	public void launch() {
		newX = speed * Math.cos(angle);
		newY = speed * Math.sin(angle);
		rect.dx = rect.x;
		rect.dy = rect.y;
	}
	
	public void move() {
		rect.dx += newX;
		rect.dy += newY;
		rect.x = (int) rect.dx;
		rect.y = (int) rect.dy;
	}
}
