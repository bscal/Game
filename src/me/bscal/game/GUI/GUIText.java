package me.bscal.game.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class GUIText extends GUIComponent{

	private String text;
	private Color color;
	private Font font;
	private boolean shadow = false;
	
	public GUIText (String text, int x, int y) {
		this(text, x, y, false);
	}
	
	public GUIText (String text, int x, int y, boolean fixed) {
		super(x, y);
		this.text = text;
		this.font = new Font("Arial", 0, 24);
		this.color = new Color(0xffffffff);
		this.fixed = fixed;
	}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		if(shadow) {
			g.setColor(new Color(0));
			g.setFont(font);
			g.drawString(text, parent.rect.x + rect.x + 1, parent.rect.y + rect.y + 1);
		}
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, xOffset + rect.x, yOffset + rect.y);
	}
	
	public void update(Game game) {
		if (fixed) {
			xOffset = parent.rect.width/2;
			yOffset = parent.rect.height;
		} else {
			xOffset = parent.rect.x;
			yOffset = parent.rect.y;
		}
	}
	
	public GUIText setColor(int c) {
		this.color = new Color(c);
		return this;
	}
	
	public GUIText setFont(Font f) {
		this.font = f;
		return this;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setShadow() {
		shadow = true;
	}

	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return false;
	}
}
