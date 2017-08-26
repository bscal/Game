package me.bscal.game.GUI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.listeners.MouseClickListener;
import me.bscal.game.sprites.Sprite;

public abstract class GUIButton extends GUIComponent implements GUIListener{

	private Sprite sprite;
	private boolean isFixed;
	protected boolean clicked = false;
	protected boolean inside = false;
	protected int x, y;
	
	public GUIButton(Sprite sprite, Rectangle rect, boolean fixed) {
		super(rect.x, rect.y, rect.width, rect.height);
		this.sprite = sprite;
		this.isFixed = fixed;
	}
	
	public void render(Render renderer, int xZoom, int yZoom) {
		renderer.renderSprite(sprite, rect.x, rect.y, xZoom, yZoom, isFixed);
	}
	
	public void render(Render renderer, int xZoom, int yZoom, Rectangle interfaceRectangle) {
		if(sprite != null) {
			renderer.renderSprite(sprite, rect.x + interfaceRectangle.x, rect.y + interfaceRectangle.y, xZoom, yZoom, isFixed);
		}
		else {
			renderer.renderRectangle(rect, xZoom, yZoom, isFixed);
		}
	}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		renderer.renderRectangle(rect, 1, 1, isFixed);
		if(sprite != null) {
			renderer.renderSprite(sprite, rect.x, rect.y, xZoom, yZoom, isFixed);
		}
	}

	public void update(Game game) {
		if(isFixed) {
			rect.x = parent.rect.width/2 + x;
			rect.y = parent.rect.height + y;
		}
		Rectangle mouse = new Rectangle((int) MouseClickListener.x, (int) MouseClickListener.y, 1, 1);
		if(clicked && MouseClickListener.button == MouseEvent.NOBUTTON) {
			released();
			clicked = false;
		}
		if(mouse.intersects(rect)) {
			if(!inside) {
				hovered();
				inside = true;
			}
		}
		else {
			if(!inside) {
				exited();
			}
			inside = false;
		}
	}
	
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle Camera, int xZoom, int yZoom) {
		if(mouseRectangle.intersects(rect)) {
			clicked();
			clicked = true;
			return clicked;
		}
		clicked = false;
		return clicked;
	}

	public Sprite getSprite() {
		if(sprite != null) {
			return sprite;
		}
		return null;
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public boolean isFixed() {
		return isFixed;
	}
	
	public String toString() {
		return "[" + rect.x + " , " + rect.y + " , " + rect.width + " , " + rect.height + "]";
	}
	
}
