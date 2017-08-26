package me.bscal.game.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.bscal.game.Game;
import me.bscal.game.entity.projectile.Projectile;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.util.Cooldown;
import me.bscal.serialization.QVField;
import me.bscal.serialization.QVObject;
import me.bscal.serialization.QVString;

public abstract class Entity implements GameObject{
	
	protected List<Projectile> projectiles = new ArrayList<Projectile>();
	protected List<Cooldown> cooldowns = new ArrayList<Cooldown>();
	
	protected String name;
	protected Rectangle rect;
	protected Sprite sprite;
	protected AnimatedSprite animatedSprite = null;
	protected boolean removed = false;
	protected boolean isMoving = false;
	protected boolean isInvulnerable = false;
	protected boolean isCollidable = true;
	protected int layer = 0;
	protected int direction = 0;	//0 = Right,1 = Left,2 = Up,3 = Down
	protected int animationLength = 0;
	protected float speed;
	
	public Entity() {}
	
	public void init() {
		Game.getEntities().add(this);
	}
	
	protected void updateDirection() {
		if(animatedSprite != null) {
			int range = direction * animationLength;
			animatedSprite.setAnimationRange(range, range + (animationLength - 1));
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
	
	public List<Projectile> getProjectiles() {
		return projectiles;
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
	
	public static double getProjectileDirection(double targetX, double targetY, double srcX, double srcY) {
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
	
	public void serialize(QVObject o) {
		o.addField(QVField.createInt("x", rect.x));
		o.addField(QVField.createInt("y", rect.y));
		o.addField(QVField.createInt("direction", direction));
		o.addField(QVField.createBoolean("Moving", isMoving));
		o.addString(QVString.create("Name", name.toCharArray()));
//		for(int i = 0; i < projectiles.size(); i++) {
//			QVObject obj = new QVObject("proj" + i);
//			projectiles.get(i).serialize(obj);
//			o.addObject(obj);
//		}
//		o.addString(QVString.create("SpritePath", "resources/sprites/player.png"));
	}
	
	public void deserialize(QVObject o) {
		this.rect.x = o.findField("x").getInt();
		this.rect.y = o.findField("y").getInt();
		this.direction = o.findField("direction").getInt();
		this.name = o.findString("Name").getString();
		this.isMoving = o.findField("Moving").getBoolean();
	}
	
}
