package me.bscal.game.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import me.bscal.game.Game;

public class MouseClickListener implements MouseListener, MouseMotionListener{

	@SuppressWarnings("unused")
	private Game game;
	private int button;
	private int x, y;
	
	public MouseClickListener(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		button = e.getButton();
//		x = e.getX();
//		y = e.getY();
//		if(e.getButton() == MouseEvent.BUTTON1) {	//LeftClick Sets Tile
//			Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
//			boolean clicked = false;
//			
//			for(GameObject entity : game.getEntities()) {
//				if(!clicked) {
//					clicked = entity.handleMouseClick(mouseRectangle, game.getRenderer().getCamera(), game.getXZoom(), game.getYZoom());
//				}
//			}
//			if(!clicked) {
//				x = (int) Math.floor((x + game.getRenderer().getCamera().x) / (16.0 * game.getXZoom()));
//				y = (int) Math.floor((y + game.getRenderer().getCamera().y) / (16.0 * game.getYZoom()));
//				game.getMap().setTile(game.getSelectedLayer(), x, y, game.getSelectedTileID());
//			}
//		}
//		
//		if(e.getButton() == MouseEvent.BUTTON3) {	//RighClick Removes Tile
//			x = (int) Math.floor((x + game.getRenderer().getCamera().x) / (16.0 * game.getXZoom()));
//			y = (int) Math.floor((y + game.getRenderer().getCamera().y) / (16.0 * game.getYZoom()));
//			game.getMap().removeTile(game.getSelectedLayer(), x, y);
//		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		button = -1;
	}
	
	public int getButton() {
		return button;
	}
	
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

}