package me.bscal.game.net.player;

import me.bscal.game.Game;
import me.bscal.game.entity.Player;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;

public class NetPlayer extends Player {

	private int oldDirection;

	public NetPlayer() {
	}

	public NetPlayer(Game game, Sprite sprite, int animationLength) {
		this.sprite = sprite;
		this.animationLength = animationLength;
		this.layer = 1;
		this.rect = new Rectangle(0, 0, 20, 26);

		if (sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}

		// Player Default Stats
		speed = 8;
		health = maxHealth;
		mana = maxMana;
		game.players.add(this);
		Game.getAddedEntities().add(this);
	}

	public NetPlayer setName(String name) {
		this.name = name;
		return this;
	}

	public void update(Game game) {
		if (oldDirection != direction) {
			updateDirection();
			oldDirection = direction;
		}
		if (isMoving) {
			animatedSprite.update(game);
		} else {
			animatedSprite.reset();
		}

	}
}
