package me.bscal.game.GUI;

import me.bscal.game.Game;
import me.bscal.game.entity.GameObject;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.Sprite;

public abstract class GUIButton implements GameObject{

	private Sprite sprite;
	private Rectangle rect;
	private boolean isFixed;
	
	public GUIButton(Sprite sprite, Rectangle rect, boolean fixed) {
		this.sprite = sprite;
		this.rect = rect;
		this.isFixed = fixed;
	}
	
	public void render(Render renderer, int xZoom, int yZoom) {
		renderer.renderSprite(sprite, rect.x, rect.y, xZoom, yZoom, isFixed);
	}
	
	public void render(Render renderer, int xZoom, int yZoom, Rectangle interfaceRectangle) {
		renderer.renderSprite(sprite, rect.x + interfaceRectangle.x, rect.y+ interfaceRectangle.y, xZoom, yZoom, isFixed);
	}

	public void update(Game game) {}

	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle Camera, int xZoom, int yZoom) {
		if(mouseRectangle.intersects(rect)) {
			activate();
			return true;
		}
		return false;
	}
	
	/**
	 * Called when a button is clicked.
	 */
	public abstract void activate();

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public boolean isFixed() {
		return isFixed;
	}

	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}
	
	public String toString() {
		return "[" + rect.x + " , " + rect.y + " , " + rect.width + " , " + rect.height + "]";
	}
}
