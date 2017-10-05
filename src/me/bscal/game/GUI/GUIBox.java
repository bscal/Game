package me.bscal.game.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class GUIBox extends GUIComponent implements Container{

	private List<GUIComponent> components = new ArrayList<GUIComponent>();
	
	public Color color = new Color(0xff9c9c9c);
	public boolean hasBorder = true;
	
	public boolean clicked = false;
	
	public GUIBox(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		if(hasBorder) {
			g.setColor(borderColor);
			g.fillRect(rect.x - 4, rect.y - 4, rect.width + 8, rect.height + 8);
		}
		g.setColor(color);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		for(GUIComponent comp : components) {
			comp.render(renderer, g, xZoom, yZoom);
		}
	}

	public void update(Game game) {
		for(GUIComponent comp : components) {
			comp.update(game);
		}
	}
	
	public void add(GUIComponent comp) {
		components.add(comp);
	}
	
	public void remove(GUIComponent comp) {
		components.remove(comp);
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

	public void delete() {
		parent.delete(this);
	}
	
}
