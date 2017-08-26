package me.bscal.game.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.Sprite;

public class GUIGraphicsButton extends GUIButton{

	private BufferedImage image;
	private Color color = new Color(0xffffffff);
	private Color backgroundColor;
	private Color hoverColor;
	
	public GUIGraphicsButton(Rectangle rect) {
		this(null, rect, true);
	}
	
	public GUIGraphicsButton(Sprite sprite, Rectangle rect, boolean isFixed) {
		super(null, rect, isFixed);
		this.x = rect.x;
		this.y = rect.y;
		//image = SpriteHandler.fireball.getImage();
	}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		if(hasBorder) {
			renderBorder(g, 0, 0);
		}
		g.setColor(color);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		if(image != null) {
			g.drawImage(image, rect.x, rect.y, null);
		}
	}
	
	public GUIGraphicsButton setColors(int color, int hoverColor) {
		this.backgroundColor = new Color(color, true);
		this.hoverColor = new Color(hoverColor, true);
		return this;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void clicked() {
		System.out.println("Clicked!");
	}

	public void hovered() {
		color = hoverColor;
	}
	
	public void released() {
		System.out.println("Released!");
	}

	public void exited() {
		color = backgroundColor;
	}
}
