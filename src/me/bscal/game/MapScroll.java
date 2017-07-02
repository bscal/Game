package me.bscal.game;

import me.bscal.game.sprites.Sprite;

public class MapScroll {

	private Sprite background;

	public MapScroll(Sprite background)
	{
		this.background = background;
	}

	public void update(Game game)
	{

	}

	public void render(Render renderer, int xZoom, int yZoom){
		Rectangle camera = renderer.getCamera();
		renderer.renderSprite(background, 0, 0, camera.width, camera.height, 1, 1, true, camera.x, camera.y);
	}

	public int getWidth()
	{
		return background.getWidth();
	}
}
