package me.bscal.game.entity.spell;

import me.bscal.game.Game;
import me.bscal.game.entity.Entity;
import me.bscal.game.entity.projectile.MagicProjectile;
import me.bscal.game.entity.spawner.ParticleSpawner;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.sprites.SpriteHandler;

public class Fireball extends MagicProjectile{
	
	public Fireball(Sprite sprite, Entity entity, int animationLength, int x, int y, double angle) {
		super(x, y, angle);
		super.sprite = sprite;
		caster = entity;
		castRect = entity.getRectangle();
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		
		direction = entity.getDirection();
		
//		if(direction == 3) {
//			layer = 1;
//		}
		
		name = "fireball";
		maxLifespan = 50;
		speed = 12;
		rect = new Rectangle(castRect.x + 2, castRect.y + 5, 16, 16);
		launch();
	}
	
	public void onDestroy(Game game) {
		new ParticleSpawner(rect.getCenterX(), rect.getCenterY(), 45, 45);
		AnimatedSprite animatedExplosion = new AnimatedSprite(SpriteHandler.explosion, 2);
		Explosion spell = new Explosion(animatedExplosion, this, 5);
		Game.getAddedEntities().add(spell);
		remove();
	}
	
	public void onCast(Game game) {}

	@Override
	public void update(Game game) {
		if(lifespan > maxLifespan) {
			onDestroy(game);
		}
		
		if(!checkCollision(game, rect)) {	
			move();
		}
		else {
			onDestroy(game);
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
