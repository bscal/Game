package me.bscal.game.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.bscal.game.graphics.Render;

public class GUIText extends GUIComponent{

	private String text;
	private Color color;
	private Font font;
	
	public GUIText (String text, int x, int y) {
		super(x, y);
		this.text = text;
		this.font = new Font("Arial", 0, 24);
		this.color = new Color(0xffffffff);
	}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, parent.rect.x + rect.x, parent.rect.y + rect.y);
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
}
