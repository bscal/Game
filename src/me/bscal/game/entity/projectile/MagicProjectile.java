package me.bscal.game.entity.projectile;

import me.bscal.game.entity.Entity;
import me.bscal.game.entity.Spell;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.Sprite;

public abstract class MagicProjectile extends Spell implements Projectile{

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected double newX, newY, xx, yy;
	protected boolean flying = true;
	
	public MagicProjectile(int x, int y, double dir, double speed) {
		super.speed = speed;
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		newX = speed * Math.cos(angle);
		newY = speed * Math.sin(angle);
		//System.out.println(newX + ", " + newY + ", " + speed + ", " + dir);
	}
	
	public MagicProjectile(Sprite sprite, Entity caster, int animationLength, int x, int y, double dir, double speed) {
		super(sprite, caster, animationLength);
		super.speed = speed;
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		newX = speed * Math.cos(angle);
		newY = speed * Math.sin(angle);
	}
	
	public void render(Render renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
			renderer.renderSprite(animatedSprite,(int) xx,(int) yy, xZoom, yZoom, false);
		}
		else if(sprite != null) {
			renderer.renderSprite(sprite,(int) xx,(int) yy, xZoom, yZoom, false);
		}
		else {
			renderer.renderRectangle(rect, xZoom, yZoom, false);
		}
	}
	
	public void move() {
		xx += newX;
		yy += newY;
	}
	
	public boolean isFlying() {
		return flying;
	}
}
