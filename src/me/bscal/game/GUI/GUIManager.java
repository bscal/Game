package me.bscal.game.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.bscal.game.Game;
import me.bscal.game.GUI.GUIButton.Action;
import me.bscal.game.GUI.GUIPanel.BarType;
import me.bscal.game.debug.Console;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class GUIManager {
	
	public static List<GUIPanel> panels = new ArrayList<GUIPanel>();
	public static List<GUIPanel> panelsAfter = new ArrayList<GUIPanel>();
	public static GUITextList chat;
	public static GUITextList console;
	public static GUIPanel panel = new GUIPanel(new Rectangle(0, 0, Game.width, Game.height));
	
	public GUIManager() {
		createGUIBox(Game.width/2, Game.height/2, 150, 150);
		console = new Console();
		console.setParent(panel);
	}
	
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
	
	public static void delete(Container panel) { 
		if(panels.contains(panel)) {
			panels.remove(panel);
		}
		else if(panelsAfter.contains(panel)) {
			panelsAfter.remove(panel);
		}
	}
	
	private void createGUIBox(int x, int y, int width, int height) {
		//GUIPanel panel = new GUIPanel(new Rectangle(x, y, width, height));
		panel.type = BarType.HORIZONTAL;
		panel.hasBackground = false;
		panel.isVisible = false;
		
		GUIBox box = new GUIBox(x, y, width, height);
		box.parent = panel;
		box.borderColor = new Color(0xff990000);
		
		GUIGraphicsButton button = new GUIGraphicsButton(new Rectangle(x + 4, y + 4, 10, 10));
		button.setSolidColor(0xff990000);
		button.parentBox = box;
		button.parent = panel;
		button.isFixed = false;
		button.action = Action.HIDE;
		
		chat = new GUITextList(25, 25, 250, 300, 10, 6);
		//chat.color = new Color(0xff999999, true);
		chat.color = new Color(0xbb999999, true);
		chat.fontColor = new Color(0xff990000);
		chat.hasBorder = false;
		chat.parent = panel;
		
		box.add(button);
		panel.add(box);
		panel.add(chat);
		addAfterEffect(panel);
	}
	
}
