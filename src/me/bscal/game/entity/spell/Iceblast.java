package me.bscal.game.entity.spell;

import me.bscal.game.Game;
import me.bscal.game.entity.Entity;
import me.bscal.game.entity.projectile.MagicProjectile;
import me.bscal.game.sprites.Sprite;

public class Iceblast extends MagicProjectile{
	
	public Iceblast(Sprite sprite, Entity caster, int animationLength, int x, int y, double dir) {
		super(sprite, caster, animationLength, x, y, dir);
		maxLifespan = 35;
		speed = 9;
		launch();
	}

	@Override
	public void update(Game game) {
		if(maxLifespan < lifespan) {
			remove();
		}
		if(!checkCollision(game, rect)) {
			move();
		}
		else {
			remove();
		}
		lifespan++;
		animatedSprite.update(game);
	}

}
