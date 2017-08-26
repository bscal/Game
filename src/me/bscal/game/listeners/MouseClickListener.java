package me.bscal.game.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import me.bscal.game.Game;
import me.bscal.game.events.EventListener;
import me.bscal.game.events.eventTypes.MouseMoveEvent;
import me.bscal.game.events.eventTypes.MousePressedEvent;
import me.bscal.game.events.eventTypes.MouseReleasedEvent;
import me.bscal.game.graphics.Rectangle;

public class MouseClickListener implements MouseListener, MouseMotionListener{

	private Game game;
	public static int button;
	public static double x;
	public static double y;
	
	private EventListener listener;
	
	public MouseClickListener(Game game) {
		this.game = game;
		this.listener = game;
	}
	
	public void mouseDragged(MouseEvent e) {
		MouseMoveEvent event = new MouseMoveEvent(e.getX(), e.getY(), true);
		listener.onEvent(event);
	}

	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		MouseMoveEvent event = new MouseMoveEvent(e.getX(), e.getY(), false);
		listener.onEvent(event);
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		button = e.getButton();
		MousePressedEvent event = new MousePressedEvent(e.getX(), e.getY(), e.getButton());
		listener.onEvent(event);
		if(e.getButton() == MouseEvent.BUTTON1) {	//LeftClick Sets Tile
			Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
			boolean clicked = false;
			
			for(int i = 0; i < Game.getGUI().getPanels().size(); i++) {
				if(!clicked) {
					clicked = Game.getGUI().getPanels().get(i).handleMouseClick(mouseRectangle, game.getRenderer().getCamera(), game.getXZoom(), game.getYZoom());
					if(clicked) return;
				}
			}
			if(!clicked) {
				for(int j = 0; j < Game.getEntities().size(); j++) {
					clicked = Game.getEntities().get(j).handleMouseClick(mouseRectangle, game.getRenderer().getCamera(), game.getXZoom(), game.getYZoom());
				}
			}
			
			
			if(!clicked && Game.SDKMode) {
				x = (int) Math.floor((x + game.getRenderer().getCamera().x) / (16.0 * game.getXZoom()));
				y = (int) Math.floor((y + game.getRenderer().getCamera().y) / (16.0 * game.getYZoom()));
				game.getMap().setTile(game.getSelectedLayer(), x, y, game.getSelectedTileID());
			}
		}
		
		if(e.getButton() == MouseEvent.BUTTON3 && Game.SDKMode) {	//RighClick Removes Tile
			x = (int) Math.floor((x + game.getRenderer().getCamera().x) / (16.0 * game.getXZoom()));
			y = (int) Math.floor((y + game.getRenderer().getCamera().y) / (16.0 * game.getYZoom()));
			game.getMap().removeTile(game.getSelectedLayer(), x, y);
		}
	}

	public void mouseReleased(MouseEvent e) {
		button = MouseEvent.NOBUTTON;
		MouseReleasedEvent event = new MouseReleasedEvent(e.getX(), e.getY(), e.getButton());
		listener.onEvent(event);
	}
	
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

}
