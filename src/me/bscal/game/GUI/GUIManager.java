package me.bscal.game.GUI;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bscal.game.Game;
import me.bscal.game.graphics.Render;

public class GUIManager {
	
	private List<GUIPanel> panels = new ArrayList<GUIPanel>();
	private List<GUIPanel> panelsAfter = new ArrayList<GUIPanel>();
	
	public GUIManager() {}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		for(GUIPanel panel : panels) {
			panel.render(renderer, g, xZoom, yZoom);
		}
	}
	
	public void renderAfterEffect(Render renderer, Graphics g, int xZoom, int yZoom) {
		for(GUIPanel panel : panelsAfter) {
			panel.render(renderer, g, xZoom, yZoom);
		}
	}
	
	public void update(Game game) {
		for(GUIPanel panel : panels) {
			panel.update(game);
		}
		for(GUIPanel panelAfter : panelsAfter) {
			panelAfter.update(game);
		}
	}
	
	public void add(GUIPanel panel) {
		panels.add(panel);
	}
	
	public void addAfterEffect(GUIPanel panel) {
		panelsAfter.add(panel);
	}
	
	public List<GUIPanel> getPanels() {
		List<GUIPanel> gui = new ArrayList<GUIPanel>();
		gui.addAll(panels);
		gui.addAll(panelsAfter);
		return gui;
	}
	
}
