package me.bscal.game.GUI;

import java.awt.Graphics;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class GUIComponent{

	protected Rectangle rect = new Rectangle();
	protected GUIPanel parent;
	
	public GUIComponent(int x, int y) {
		this(x, y, 0, 0);
	}
	
	public GUIComponent(int x, int y, int width, int height) {
		this.rect.x = x;
		this.rect.y = y;
		this.rect.width = width;
		this.rect.height = height;
	}

	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {}

	public void update(Game game) {}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }
	
	public GUIComponent setParent(GUIPanel parent) {
		this.parent = parent;
		this.parent.add(this);
		return this;
	}
	
}
