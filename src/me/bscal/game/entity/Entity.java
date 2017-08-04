package me.bscal.game.entity;

import java.util.ArrayList;
import java.util.Iterator;

import me.bscal.game.Game;
import me.bscal.game.entity.projectile.Projectile;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.util.Cooldown;

public abstract class Entity implements GameObject{
	
	protected ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	protected ArrayList<Cooldown> cooldowns = new ArrayList<Cooldown>();
	
	protected String name;
	protected Rectangle rect;
	protected Sprite sprite;
	protected AnimatedSprite animatedSprite = null;
	private boolean removed = false;
	protected boolean isInvulnerable = false;
	protected boolean isCollidable = true;
	protected int layer = 0;
	protected int direction = 0;	//0 = Right,1 = Left,2 = Up,3 = Down
	protected int animationLength = 0;
	protected double speed;
	
	public Entity() {}
	
	public void init() {
		Game.getEntities().add(this);
	}
	
	protected void updateDirection() {
		if(animatedSprite != null) {
			animatedSprite.setAnimationRange(direction * animationLength, direction * animationLength + (animationLength - 1));
		}
	}
	
	public void render(Render renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
			renderer.renderSprite(animatedSprite, rect.x, rect.y, xZoom, yZoom, false);
		}
		else if(sprite != null) {
			renderer.renderSprite(sprite, rect.x, rect.y, xZoom, yZoom, false);
		}
		else {
			renderer.renderRectangle(rect, xZoom, yZoom, false);
		}
	}
	
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }
	
	public Rectangle getRectangle() {
		return rect;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public String getName() {
		return name;
	}
	
	public Sprite getSprite() {
		return animatedSprite;
	}
	
	public void remove() {
		Game.getRemovedEntities().add(this);
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	/**
	 * Returns int 0 - 4 holding the directions Right, Left, Up, Down respectively.
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * Simple collision detections. Entities will not slide on/off walls or areas that collision is detected.
	 * @return false if no collision is detected.
	 */
	public boolean checkCollision(Game game, Rectangle rect) {
		if(!game.getMap().checkCollision(rect, layer, game.getXZoom(), game.getYZoom()) 
				&& !game.getMap().checkCollision(rect, layer + 1, game.getXZoom(), game.getYZoom())
				&& !game.getMap().checkCollision(rect, layer + 2, game.getXZoom(), game.getYZoom())) {
			return false;
		}
		return true;
	}
	
	public double getProjectileDirection(double targetX, double targetY, double srcX, double srcY) {
		double dx = targetX - srcX;
		double dy = targetY - srcY;
		double dir = Math.atan2(dy, dx);
		return dir;
	}
	
	public int getDirectionFromMouse(double angle) {
		return -1;
	}
	
	public void updateCooldowns() {
		if(!cooldowns.isEmpty()) {
			Iterator<Cooldown> i = cooldowns.iterator();
			while (i.hasNext()) {
				Cooldown cd = i.next();
				cd.update();
				if(!cd.onCooldown()) {
				   i.remove();
				}
			}
		}
	}
	
	public boolean isOnCooldown(String s) {
		for(int i = 0; i < cooldowns.size(); i++) {
			if(s == cooldowns.get(i).getId()) {
				return true;
			}
		}
		return false;
	}
	
	public Cooldown getCooldown(String s) {
		for(int i = 0; i < cooldowns.size(); i++) {
			if(s == cooldowns.get(i).getId()) {
				return cooldowns.get(i);
			}
		}
		return null;
	}
	
}
