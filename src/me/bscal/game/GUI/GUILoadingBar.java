package me.bscal.game.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class GUILoadingBar extends GUIComponent{

	private Color barColor;
	private Color backgroundColor;
	private Color borderColor;
	private int borderWidth;
	public Rectangle bar;
	private boolean hasBorder = false;
	/**
	 * 0.0 to 1.0
	 */
	private double progress;
	
	public GUILoadingBar(Rectangle rect) {
		super(rect.x, rect.y, rect.width, rect.height);
		this.bar = new Rectangle(rect.x, rect.y, rect.width, rect.height);
	}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		//Border
		if(hasBorder) {
			Graphics2D g2D = (Graphics2D) g;
			g2D.setColor(borderColor);
			g2D.setStroke(new BasicStroke(borderWidth));
			g2D.drawRect(rect.x + parent.rect.x - borderWidth + 1, rect.y + parent.rect.y - borderWidth + 1, rect.width + borderWidth, rect.height + borderWidth);
		}
		bar.width = (int) (progress * rect.width);
		//Background bar.
		g.setColor(backgroundColor);
		g.fillRect(rect.x + parent.rect.x + bar.width, rect.y + parent.rect.y, rect.width - bar.width, rect.height);
		//This is the actual bar.
		g.setColor(barColor);
		g.fillRect(rect.x + parent.rect.x, rect.y + parent.rect.y, bar.width, bar.height);
		
	}
	
	public void update(Game game) {
		
	}
	
	public void setProgress(double progress) {
		if(progress < 0.0 || progress > 1.0) {
			System.out.println("Progress of GUILoadingBar should not be less then 0.0 or greater then 1.0!");
		}
		this.progress = progress;
	}
	
	public double getProgress() {
		return progress;
	}
	
	public GUILoadingBar setColors(int backgroundColor, int barColor) {
		this.backgroundColor = new Color(backgroundColor);
		this.barColor = new Color(barColor);
		return this;
	}
	
	public GUILoadingBar setBorder(int borderWidth, int borderColor) {
		this.borderWidth = borderWidth;
		this.borderColor = new Color(borderColor);
		this.hasBorder = true;
		return this;
	}

}
