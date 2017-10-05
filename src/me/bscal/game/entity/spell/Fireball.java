package me.bscal.game.entity.spell;

import me.bscal.game.Game;
import me.bscal.game.entity.Entity;
import me.bscal.game.entity.projectile.ProjectileEntity;
import me.bscal.game.entity.spawner.ParticleSpawner;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.sprites.SpriteHandler;

public class Fireball extends ProjectileEntity{
	
	public Fireball(Sprite sprite, int animationLength, int x, int y, double angle) {
		this(sprite, new Rectangle(x, y, 1, 1), animationLength, x, y ,angle);
	}
	
	public Fireball(Sprite sprite, Entity entity, int animationLength, int x, int y, double angle) {
		this(sprite, entity.getRectangle(), animationLength, x, y ,angle);
		this.ownerID = entity.id;
		this.direction = entity.getDirection();
	}
	
	public Fireball(Sprite sprite, Rectangle origin, int animationLength, int x, int y, double angle) {
		super(x, y, angle);
		super.sprite = sprite;
		castRect = origin;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		
		projType = 1;
		name = "fireball";
		maxLifespan = 50;
		speed = 12;
		rect = new Rectangle(castRect.x, castRect.y, 16, 16);
		collisionRect = new Rectangle(rect.x, rect.y, 14, 14);
		launch();
	}
	
	public void onDestroy(Game game) {
		int x = rect.getCenterX();
		int y = rect.getCenterY();
		new ParticleSpawner(x, y, 45, 45);
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
		collisionRect.x = rect.x + collisionRect.width;
		collisionRect.y = rect.y;
		if(!simpleCollisionCheck(game, collisionRect)) {
			move();
		}
		else {
			onDestroy(game);
		}
		if(animatedSprite != null) {
			animatedSprite.update(game);
		}
		lifespan++;
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
	}
	
	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return false;
	}
}
