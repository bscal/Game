package me.bscal.game.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public abstract class GUIComponent{

	public Rectangle rect = new Rectangle();
	protected GUIPanel parent;
	
	protected Color borderColor;
	protected int borderWidth, xOffset, yOffset;
	protected boolean hasBorder = false;
	protected boolean fixed = false;
	
	public GUIComponent(int x, int y) {
		this(x, y, 0, 0);
	}
	
	public GUIComponent(int x, int y, int width, int height) {
		rect.x = x;
		rect.y = y;
		rect.width = width;
		rect.height = height;
	}

	public abstract void render(Render renderer, Graphics g, int xZoom, int yZoom);

	public abstract void update(Game game);

	public abstract boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);
	
	public GUIComponent setParent(GUIPanel parent) {
		parent.add(this);
		this.parent = parent;
		return this;
	}
	
	public GUIPanel getParent() {
		return parent;
	}
	
	public GUIComponent setBorder(int borderWidth, int borderColor) {
		this.borderWidth = borderWidth;
		this.borderColor = new Color(borderColor);
		this.hasBorder = true;
		return this;
	}
	
	protected void renderBorder(Graphics g, int xOffset, int yOffset) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(borderColor);
		g2D.setStroke(new BasicStroke(borderWidth));
		g2D.drawRect(rect.x + xOffset - borderWidth + 1, rect.y + yOffset - borderWidth + 1, rect.width + borderWidth, rect.height + borderWidth);
	}
	
}
