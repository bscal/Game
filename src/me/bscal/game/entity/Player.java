package me.bscal.game.entity;

import java.awt.Font;

import me.bscal.game.Game;
import me.bscal.game.GUI.GUILoadingBar;
import me.bscal.game.GUI.GUIManager;
import me.bscal.game.GUI.GUIPanel;
import me.bscal.game.GUI.GUIText;
import me.bscal.game.entity.spell.Fireball;
import me.bscal.game.entity.spell.Iceblast;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.listeners.KeyboardListener;
import me.bscal.game.listeners.MouseClickListener;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.sprites.SpriteHandler;
import me.bscal.game.util.Cooldown;

public class Player extends LivingEntity{
	
	private GUIManager gui;
	private GUILoadingBar hpBar;
	private GUILoadingBar manaBar;
	private GUIText hpText;
	private GUIText manaText;
	private int time = 0;
	
	public Player(Sprite sprite, int animationLength) {
		this.sprite = sprite;
		this.animationLength = animationLength;
		this.layer = 1;
		this.rect = new Rectangle(0, 0, 20, 26);
		this.collisionRect = new Rectangle(0, 0, 10*Game.XZOOM, 18*Game.YZOOM);
		this.name = "bscal";
		
		//Player UI
		Font barFont = new Font("Constantia", Font.PLAIN, 14);
		gui = Game.getGUI();
		GUIPanel panel = new GUIPanel(new Rectangle(Game.width - 256, 0, 256, Game.height), 0xff9c9c9c);
		GUIText text = new GUIText(name,16, 216);
		text.setColor(0);
		text.setFont(new Font("Constantia", Font.BOLD, 22));
		text.setParent(panel);
		hpBar = new GUILoadingBar(new Rectangle(16, 232, panel.rect.width - 32, 20));
		hpBar.setParent(panel);
		hpBar.setColors(0xff878787, 0xffff3d3d);
		hpBar.setBorder(2, 0xff404040);
		manaBar = new GUILoadingBar(new Rectangle(16, 264, panel.rect.width - 32, 20));
		manaBar.setParent(panel);
		manaBar.setColors(0xff878787, 0xff3853ff);
		manaBar.setBorder(2, 0xff404040);
		hpText = new GUIText("", hpBar.bar.x + 2, hpBar.bar.y + 14);
		hpText.setColor(0xffffffff);
		hpText.setFont(barFont);
		hpText.setParent(panel);
		manaText = new GUIText("", manaBar.bar.x + 2, manaBar.bar.y + 14);
		manaText.setColor(0xffffffff);
		manaText.setFont(barFont);
		manaText.setParent(panel);
		gui.add(panel);
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		updateDirection();
		xCollisionOffset = 14;
		yCollisionOffset = 20;
		
		//Player Default Stats
		speed = 8;
		health = 25;
		mana = maxMana;
	}

	@Override
	public void update(Game game) {
		KeyboardListener keyboard = game.getKeyListener();
		MouseClickListener mouse = game.getMouseListener();
		
		collisionRect.x = rect.x;
		collisionRect.y = rect.y;
		
		move(game, keyboard);
		updatePlayer(game);
		updateCooldowns();
		playerActions(game, keyboard, mouse);
		updateCamera(game, game.getRenderer().getCamera());
	}
	
	private void move(Game game, KeyboardListener keyboard) {
		boolean moved = false;
		int newDirection = direction;
		
		if(keyboard.left()) {
			collisionRect.x -= speed;
			newDirection = 1;
			moved = true;
		}
		if(keyboard.right()) {
			collisionRect.x += speed;
			newDirection = 0;
			moved = true;
		}
		if(keyboard.up()) {
			collisionRect.y -= speed;
			newDirection = 2;
			moved = true;
		}
		if(keyboard.down()) {
			if(!keyboard.isCtrlDown()) {
				collisionRect.y += speed;
			}
			newDirection = 3;
			moved = true;
		}
		
		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}
		
		if(moved) {	
			collisionRect.x += xCollisionOffset;
			collisionRect.y += yCollisionOffset;
			checkCollision(game);
			animatedSprite.update(game);
		}
		else {
			animatedSprite.reset();
		}
	}

	private void playerActions(Game game, KeyboardListener keyboard, MouseClickListener mouse) {
		if(mouse.getButton() == 1 && !isOnCooldown("fireball")) {
			AnimatedSprite animatedFireball = new AnimatedSprite(SpriteHandler.fireball, 3);
			
			Fireball spell = new Fireball(animatedFireball, this, 5, rect.x, rect.y, 
					getProjectileDirection(mouse.getX(), mouse.getY(), Game.width/2.0, Game.height/2.0));
			
			Game.getAddedEntities().add(spell);
			this.projectiles.add(spell);
			cooldowns.add(new Cooldown("fireball", this, 5));
		}
		
		else if(mouse.getButton() == 3 && !isOnCooldown("iceblast")) {
			AnimatedSprite animatedFrostbolt = new AnimatedSprite(SpriteHandler.frostbolt, 2);
			
			Iceblast spell = new Iceblast(animatedFrostbolt, this, 5, rect.x, rect.y, 
					getProjectileDirection(mouse.getX(), mouse.getY(), Game.width/2.0, Game.height/2.0));
			
			Game.getAddedEntities().add(spell);
			this.projectiles.add(spell);
			cooldowns.add(new Cooldown("iceblast", this, 5));
		}
	}
	
	private void updatePlayer(Game game) {
		health += .5;
		if(health == maxHealth) {
			health = 0;
		}
		hpBar.setProgress(health/maxHealth);
		manaBar.setProgress(mana/maxMana);
		hpText.setText("Health: " + health + " / " + maxHealth);
		manaText.setText("Mana: " + mana + " / " + maxMana);
	}
	
	public void updateCamera(Game game, Rectangle camera) {
		camera.x = rect.x + (rect.width/2*Game.XZOOM) - (camera.width / 2);
		camera.y = rect.y + (rect.height/2*Game.YZOOM) - (camera.height / 2);
	}
}
