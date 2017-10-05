package me.bscal.game.GUI;

import java.awt.Graphics;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class GUIChatBox extends GUIComponent {

	public int maxMessages = Integer.MAX_VALUE;

	public GUIChatBox(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		// TODO Auto-generated method stub
		
	}

	public void update(Game game) {
		
	}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return false;
	}
	
}
