package me.bscal.game.GUI;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bscal.game.Game;
import me.bscal.game.graphics.Render;

public class GUIManager {
	
	private List<GUIPanel> panels = new ArrayList<GUIPanel>();
	private List<GUIComponent> components = new ArrayList<GUIComponent>();
	
	public GUIManager() {}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		for(GUIPanel panel : panels) {
			panel.render(renderer, g, xZoom, yZoom);
		}
	}
	
	public void renderAfterEffect(Render renderer, Graphics g, int xZoom, int yZoom) {
		for(GUIComponent component : components) {
			component.render(renderer, g, xZoom, yZoom);
		}
	}
	
	public void update(Game game) {
		for(GUIPanel panel : panels) {
			panel.update(game);
		}
	}
	
	public void add(GUIPanel panel) {
		panels.add(panel);
	}
	
	public void addAfterEffect(GUIComponent component) {
		components.add(component);
	}
	
	public List<GUIPanel> getPanels() {
		return panels;
	}
	
}
