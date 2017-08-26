package me.bscal.game.entity.spell;

import me.bscal.game.Game;
import me.bscal.game.entity.projectile.ProjectileEntity;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.sprites.Sprite;

public class Iceblast extends ProjectileEntity{
	
	public Iceblast(Sprite sprite, int animationLength, int x, int y, double dir) {
		super(x, y, dir);
		this.rect = new Rectangle(x, y, 0, 0);
		this.sprite = sprite;
		this.animationLength = animationLength;
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
		//animatedSprite.update(game);
	}

}
