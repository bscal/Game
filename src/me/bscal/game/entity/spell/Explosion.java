package me.bscal.game.entity.spell;

import me.bscal.game.Game;
import me.bscal.game.entity.GameObject;
import me.bscal.game.entity.Spell;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;

public class Explosion extends Spell {
	
	public Explosion(Sprite sprite, GameObject spawn, int animationLength) {
		super.sprite = sprite;
		super.castRect = spawn.getRectangle();
		super.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		layer = 1;
		rect = new Rectangle(castRect.x, castRect.y, 36, 36);
	}

	@Override
	public void update(Game game) {
		animatedSprite.update(game);
		if(animatedSprite.getTotalUpdates() > animatedSprite.getEndSprite()) {
			Game.getRemovedEntities().add(this);
		}
	}

	@Override
	public void onDestroy(Game game) {}

	@Override
	public void onCast(Game game) {}
}
