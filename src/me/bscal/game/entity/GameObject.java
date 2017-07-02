package me.bscal.game.entity;

import me.bscal.game.Game;
import me.bscal.game.Rectangle;
import me.bscal.game.Render;

public interface GameObject {

	/**
	 * Call as fast as computer can handle it
	 * @param renderer Renderer
	 * @param xZoom Zoom multipier
	 * @param yZoom Zoom multipier
	 */
	public void render(Render renderer, int xZoom, int yZoom);
	
	/**
	 * Called on specified framemate
	 * @param game Game
	 */
	public void update(Game game);
	
	/**
	 * Called whenever mouse leftclicks on canvas
	 * @param mouseRectangle
	 * @param camera Camera object from renderer
	 * @param xZoom Zoom multipier
	 * @param yZoom Zoom multipier
	 * @return True if not to handle more clicks (Clicked on something on screen). False if to continue more handle.
	 */
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);
	
	public Rectangle getRectangle();
}