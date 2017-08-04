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

	private List<GUIComponent> components = new ArrayList<GUIComponent>();
	public Rectangle rect;
	private int backroundColor;
	
	public GUIPanel(Rectangle rect) {
		this(rect, Game.ALPHA);
	}
	
	public GUIPanel(Rectangle rect, int backgroundColor) {
		this.rect = rect;
		this.backroundColor = backgroundColor;
	}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		g.setColor(new Color(backroundColor));
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		for(GUIComponent comp : components) {
			comp.render(renderer, g, xZoom, yZoom);
		}	
	}
	
	public void update(Game game) {
		rect.x = Game.width - game.getRenderer().CAMERA_GUI_OFFSET;
		rect.height = Game.height;
		for(GUIComponent comp : components) {
			comp.update(game);
		}
	}
	
	public void add(GUIComponent component) {
		components.add(component);
	}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		boolean clicked = false;
		for (int i = 0; i < components.size(); i++) {
			boolean result = components.get(i).handleMouseClick(mouseRectangle, camera, xZoom, yZoom);
			if (clicked == false) {
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
	
	public void render(Render renderer, int xZoom, int yZoom) {}
	
}
