package me.bscal.game.entity.spell;

import me.bscal.game.Game;
import me.bscal.game.entity.Player;
import me.bscal.game.entity.projectile.MagicProjectile;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.sprites.SpriteHandler;

public class Fireball extends MagicProjectile{
	
	public Fireball(Sprite sprite, Player player, int animationLength, int x, int y, double angle) {
		super(x, y, angle, 8);
		super.sprite = sprite;
		castRect = player.getRectangle();
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		
		direction = player.getDirection();
		
		if(direction == 3) {
			layer = 2;
		}
		maxLifespan = 50;
		rect = new Rectangle(castRect.getCenterX(), castRect.getCenterY(), 1, 1);
		collisionRect = new Rectangle(castRect.x, castRect.y, 16, 16);
	}
	
	public void onDestroy(Game game) {
		AnimatedSprite animatedExplosion = new AnimatedSprite(SpriteHandler.explosion, 2);
		Explosion spell = new Explosion(animatedExplosion, this, 5);
		Game.getAddedEntities().add(spell);
	}
	
	public void onCast(Game game) {
		
	}

	@Override
	public void update(Game game) {
		if(isRemoved()) {
			onDestroy(game);
		}
		if(lifespan > maxLifespan) {
			remove();
		}
		if(!checkCollision(game, collisionRect)) {
			move();
		}
		else {
			remove();
		}
//		boolean moved = false;
//		collisionRect.x = rect.x;
//		collisionRect.y = rect.y;
//		if(direction == 1) {
//			collisionRect.x -= speed;
//			moved = true;
//		}
//		if(direction == 0) {
//			collisionRect.x += speed;
//			moved = true;
//		}
//		if(direction == 2) {
//			collisionRect.y -= speed;
//			moved = true;
//		}
//		if(direction == 3) {
//			collisionRect.y += speed;
//			moved = true;
//		}
//		if(moved) {
//			if(checkCollision(game)) {
//				onDestroy(game);
//			}
//		}
		animatedSprite.update(game);
		lifespan++;
	}

	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return false;
	}
}
