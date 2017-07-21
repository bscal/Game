package me.bscal.game.entity.projectile;

import me.bscal.game.entity.Entity;
import me.bscal.game.entity.Spell;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.Sprite;

public abstract class MagicProjectile extends Spell implements Projectile{

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected double newX, newY;
	protected boolean flying = true;
	
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
		rect.xx = rect.x;
		rect.yy = rect.y;
	}
	
	public void render(Render renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
			renderer.renderSprite(animatedSprite,(int) rect.xx,(int) rect.yy, xZoom, yZoom, false);
		}
		else if(sprite != null) {
			renderer.renderSprite(sprite,(int) rect.xx,(int) rect.yy, xZoom, yZoom, false);
		}
		else {
			renderer.renderRectangle(rect, xZoom, yZoom, false);
		}
	}
	
	public void move() {
		rect.xx += newX;
		rect.yy += newY;
		rect.x = (int) rect.xx;
		rect.y = (int) rect.yy;
	}
	
	public boolean isFlying() {
		return flying;
	}
}
