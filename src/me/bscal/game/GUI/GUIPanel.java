package me.bscal.game.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bscal.game.Game;
import me.bscal.game.entity.GameObject;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class GUIPanel implements GameObject, Container{

	public enum BarType {
		SIDE, HORIZONTAL, WHOLE_SCREEN
	}
	
	private List<GUIComponent> components = new ArrayList<GUIComponent>();
	
	public Rectangle rect;
	public boolean hasBackground = false;
	public boolean isVisible = true;
	public int backroundColor;
	public BarType type;
	
	public GUIPanel(Rectangle rect) {
		this(rect, Game.ALPHA);
	}
	
	public GUIPanel(Rectangle rect, int backgroundColor) {
		this.rect = rect;
		this.backroundColor = backgroundColor;
	}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		if(isVisible) { 
			if(hasBackground) {
				g.setColor(new Color(backroundColor));
				g.fillRect(rect.x, rect.y, rect.width, rect.height);
			}
			for(GUIComponent comp : components) {
				comp.render(renderer, g, xZoom, yZoom);
			}	
		}
	}
	
	public void update(Game game) {
		if(isVisible) {
			if(type == BarType.SIDE) {
				rect.x = Game.width - game.getRenderer().CAMERA_GUI_OFFSET;
				rect.height = Game.height;
			}
			else if(type == BarType.HORIZONTAL) {
				rect.width = Game.width;
				rect.height = Game.height;
			}
			
			else if(type == BarType.WHOLE_SCREEN) {
				rect.x = Game.width/2;
				rect.y = Game.height;
				rect.width = Game.width;
				rect.height = Game.height;
			}
			for(GUIComponent comp : components) {
				comp.update(game);
			}
		}
	}
	
	public void add(GUIComponent component) {
		components.add(component);
	}
	
	public void remove(GUIComponent component) {
		components.remove(component);
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
	
	public GUIPanel hasBackground(boolean visible) {
		this.hasBackground = visible;
		return this;
	}
	
	public void render(Render renderer, int xZoom, int yZoom) {}
	
	public GUIPanel setBar(BarType type) {
		this.type = type;
		return this;
	}

	public void delete() {
		GUIManager.delete(this);
	}
	
	public void delete(GUIComponent comp) {
		components.remove(comp);
	}
	
}
