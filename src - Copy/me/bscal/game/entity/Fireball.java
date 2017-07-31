package me.bscal.game.entity;

import me.bscal.game.Game;
import me.bscal.game.Rectangle;
import me.bscal.game.Render;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;

public class Fireball extends Spell{

	private Rectangle rect;
	private Rectangle pRect;
	private Sprite spell;
	private AnimatedSprite animatedSprite = null;
	private int direction = 0;	//Right, Left, Up, Down
	private int animationLength = 0;
	private int speed = 15;
	private int lifespan = 0;
	private int maxLifespan = 45;
	private boolean removed = false;
	
	public Fireball(Sprite sprite, Player player, int animationLength) {
		this.spell = sprite;
		this.pRect = player.getRectangle();
		this.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		direction = player.getDirection();
		rect = new Rectangle(pRect.x, pRect.y, 16, 16);
		rect.createGraphics(3, 25676);
	}
	
	protected void onDestroy(Game game) {
		AnimatedSprite animatedExplosion = new AnimatedSprite(game.getExplosion(), 2);
		Explosion spell = new Explosion(animatedExplosion, this, 5);
		game.getAddedEntities().add(spell);
	}
		
	
	@Override
	public void render(Render renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
			renderer.renderSprite(animatedSprite, rect.x, rect.y, xZoom, yZoom, false);
		}
		else if(spell != null) {
			renderer.renderSprite(spell, rect.x, rect.y, xZoom, yZoom, false);
		}
		else {
			renderer.renderRectangle(rect, xZoom, yZoom, false);
		}
	}

	@Override
	public void update(Game game) {
		if(direction == 1) {
			rect.x -= speed;
		}
		if(direction == 0) {
			rect.x += speed;
		}
		if(direction == 2) {
			rect.y -= speed;
		}
		if(direction == 3) {
			rect.y += speed;
		}
		animatedSprite.update(game);
		if(lifespan > maxLifespan) {
			onDestroy(game);
			game.getRemovedEntities().add(this);
		}
		lifespan++;
	}

	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return false;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public Rectangle getRectangle() {
		return rect;
	}
}
