package me.bscal.game.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bscal.game.Game;
import me.bscal.game.entity.GameObject;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class GUIPanel implements GameObject{

	public enum BarType {
		SIDE, HORIZONTAL
	}
	
	private List<GUIComponent> components = new ArrayList<GUIComponent>();
	
	public Rectangle rect;
	private boolean isVisible = true;
	private int backroundColor;
	private boolean sideBar = false;
	private boolean horizontalBar = false;
	
	public GUIPanel(Rectangle rect) {
		this(rect, Game.ALPHA);
	}
	
	public GUIPanel(Rectangle rect, int backgroundColor) {
		this.rect = rect;
		this.backroundColor = backgroundColor;
	}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		if(isVisible) {
			g.setColor(new Color(backroundColor));
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		for(GUIComponent comp : components) {
			comp.render(renderer, g, xZoom, yZoom);
		}	
	}
	
	public void update(Game game) {
		if(sideBar) {
			rect.x = Game.width - game.getRenderer().CAMERA_GUI_OFFSET;
			rect.height = Game.height;
		}
		if(horizontalBar) {
			rect.width = Game.width;
			rect.height = Game.height;
		}
		for(GUIComponent comp : components) {
			comp.update(game);
		}
	}
	
	public void add(GUIComponent component) {
		components.add(component);
	}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		boolean clicked = false;
		GUIComponent comp;
		for (int i = 0; i < components.size(); i++) {
			comp = components.get(i);
			boolean result = comp.handleMouseClick(mouseRectangle, camera, xZoom, yZoom);
			if (!clicked) {
				clicked = result;
			}
		}
		return clicked;
	}

	public Rectangle getRectangle() {
		return rect;
	}
	
	public int getLayer() {
		return Integer.MAX_VALUE;
	}

	public GUIPanel setColor(int c) {
		this.backroundColor = c;
		return this;
	}
	
	/**
	 * Set to true by default.
	 * @param visible
	 * @return GUIPanel
	 */
	public GUIPanel setVisibilty(boolean visible) {
		this.isVisible = visible;
		return this;
	}
	
	public void render(Render renderer, int xZoom, int yZoom) {}
	
	public GUIPanel setBar(BarType type) {
		if(type == BarType.SIDE) {
			sideBar = true;
		}
		else if(type == BarType.HORIZONTAL) {
			horizontalBar = true;
		}
		return this;
	}
	
}
