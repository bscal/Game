package me.bscal.game.entity;

import me.bscal.game.Game;
import me.bscal.game.Rectangle;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;

public class Explosion extends Spell {
	
	public Explosion(Sprite sprite, GameObject spawn, int animationLength) {
		super.spellSprite = sprite;
		super.hostRect = spawn.getRectangle();
		super.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			super.animatedSprite = (AnimatedSprite) sprite;
		}
		super.rect = new Rectangle(hostRect.x, hostRect.y, 32, 32);
		super.rect.createGraphics(3, 25676);
	}

	@Override
	public void update(Game game) {
		if(animationLength < super.lifespan) {
			game.getRemovedEntities().add(this);
		}
		animatedSprite.update(game);
		super.lifespan++;
		
	}

	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

	@Override
	protected void onDestroy(Game game) {}

}
