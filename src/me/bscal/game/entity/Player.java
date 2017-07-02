package me.bscal.game.entity;

import me.bscal.game.Game;
import me.bscal.game.Rectangle;
import me.bscal.game.Render;
import me.bscal.game.listeners.KeyboardListener;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;

public class Player implements GameObject{
	
	private Rectangle pRect;
	private Sprite player;
	private AnimatedSprite animatedSprite = null;
	private int direction = 0;	//Right, Left, Up, Down
	private int animationLength = 0;
	private int vel = 0;
	private int speed = 8;
	
	//public Player() {
	//	pRect = new Rectangle(32, 32, 16, 16);
	//	pRect.createGraphics(3, 25676);
	//}
	
	public Player(Sprite sprite, int animationLength) {
		this.player = sprite;
		this.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		updateDirection();
		pRect = new Rectangle(800, 500, 20, 26);
		pRect.createGraphics(3, 25676);
	}

	private void updateDirection() {
		if(animatedSprite != null) {
			animatedSprite.setAnimationRange(direction * animationLength, direction * animationLength + (animationLength - 1));
		}
	}
	
	@Override
	public void render(Render renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
			renderer.renderSprite(animatedSprite, pRect.x, pRect.y, xZoom, yZoom, false);
		}
		else if(player != null) {
			renderer.renderSprite(player, pRect.x, pRect.y, xZoom, yZoom, false);
		}
		else {
			renderer.renderRectangle(pRect, xZoom, yZoom, false);
		}
	}

	@Override
	public void update(Game game) {
		boolean moved = false;
		int newDirection = direction;
		
		KeyboardListener listener = game.getKeyListener();
		if(listener.left()) {
			pRect.x -= speed;
			newDirection = 1;
			moved = true;
		}
		if(listener.right()) {
			pRect.x += speed;
			newDirection = 0;
			moved = true;
		}
		if(listener.up()) {
			pRect.y -= speed;
			newDirection = 2;
			moved = true;
		}
		if(listener.down()) {
			if(!listener.isCtrlDown()) {
				pRect.y += speed;
			}
			newDirection = 3;
			moved = true;
		}
		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}
		if(moved) {
			animatedSprite.update(game);
		}
		else {
			animatedSprite.reset();
		}
		updateCamera(game, game.getRenderer().getCamera());
	}
	
	public void updateCamera(Game game, Rectangle camera) {
		camera.x = pRect.x - (camera.width / 2);
		camera.y = pRect.y - (camera.height / 2);
	}

	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return false;
	}
	
	public Rectangle getRectangle() {
		return pRect;
	}
	
	public int getDirection() {
		return direction;
	}
}
