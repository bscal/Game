package me.bscal.game.GUI;

import java.awt.Color;
import java.awt.Graphics;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class GUILoadingBar extends GUIComponent{

	private Color barColor;
	private Color backgroundColor;
	private int barWidth;
	/** Will always be between 0.0 to 1.0 */
	private double progress;
	
	public GUILoadingBar(Rectangle rect) {
		super(rect.x, rect.y, rect.width, rect.height);
		this.barWidth = rect.width;
	}
	
	public GUILoadingBar(Rectangle rect, boolean fixed) {
		super(rect.x, rect.y, rect.width, rect.height);
		this.barWidth = rect.width;
		this.fixed = fixed;
	}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		if (hasBorder) {
			renderBorder(g, xOffset, yOffset);
		}
		barWidth = (int) (progress * rect.width);
		// Background bar.
		g.setColor(backgroundColor);
		g.fillRect(rect.x + xOffset + barWidth, rect.y + yOffset, rect.width - barWidth, rect.height);
		// This is the actual bar.
		g.setColor(barColor);
		g.fillRect(rect.x + xOffset, rect.y + yOffset, barWidth, rect.height);
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

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return false;
	}

}
