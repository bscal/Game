package me.bscal.game.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import me.bscal.game.Game;
import me.bscal.game.Rectangle;
import me.bscal.game.entity.GameObject;

public class MouseClickListener implements MouseListener, MouseMotionListener{

	private Game game;
	
	public MouseClickListener(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		int x = e.getX();
		int y = e.getY();
		if(e.getButton() == MouseEvent.BUTTON1) {	//LeftClick Sets Tile
			Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
			boolean clicked = false;
			
			for(GameObject entity : game.getEntities()) {
				if(!clicked) {
					clicked = entity.handleMouseClick(mouseRectangle, game.getRenderer().getCamera(), game.getXZoom(), game.getYZoom());
				}
			}
			if(!clicked) {
				x = (int) Math.floor((x + game.getRenderer().getCamera().x) / (16.0 * game.getXZoom()));
				y = (int) Math.floor((y + game.getRenderer().getCamera().y) / (16.0 * game.getYZoom()));
				game.getMap().setTile(x, y, game.getSelectedTileID());
			}
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {	//RighClick Removes Tile
			x = (int) Math.floor((x + game.getRenderer().getCamera().x) / (16.0 * game.getXZoom()));
			y = (int) Math.floor((y + game.getRenderer().getCamera().y) / (16.0 * game.getYZoom()));
			game.getMap().removeTile(x, y);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
