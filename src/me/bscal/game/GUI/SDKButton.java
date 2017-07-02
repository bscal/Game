package me.bscal.game.GUI;

import me.bscal.game.Game;
import me.bscal.game.Rectangle;
import me.bscal.game.Render;
import me.bscal.game.sprites.Sprite;

public class SDKButton extends GUIButton{

	private final int defaultColor = 0xFF7b7b7b;
	private Game game;
	private int tileID;
	private boolean isSelected = false;
	
	public SDKButton(Game game, int tileID, Sprite sprite, Rectangle rect) {
		super(sprite, rect, true);
		rect.createGraphics(defaultColor);
		this.game = game;
		this.tileID = tileID;
	}

	@Override
	public void render(Render renderer, int xZoom, int yZoom) {
		renderer.renderRectangle(super.getRect(), xZoom, yZoom, super.isFixed());
		renderer.renderSprite(super.getSprite(), super.getRect().x, super.getRect().y, xZoom, yZoom, super.isFixed());
	}
	
	@Override
	public void render(Render renderer, int xZoom, int yZoom, Rectangle interfaceRectangle) {
		renderer.renderRectangle(super.getRect(), interfaceRectangle, 1, 1, super.isFixed());
		renderer.renderSprite(super.getSprite(),
				super.getRect().x + interfaceRectangle.x + (xZoom - (xZoom - 1)) * super.getRect().width/2/xZoom,
				super.getRect().y + interfaceRectangle.y + (yZoom - (yZoom - 1)) * super.getRect().height/2/yZoom,
				xZoom - 1, yZoom - 1, super.isFixed());
	}
	
	@Override
	public void update(Game game) {
		if(tileID == game.getSelectedTileID()) {
			if(!isSelected) {
				super.getRect().createGraphics(0xFF494949);
				isSelected = true;
			}
		}
		else {
			super.getRect().createGraphics(defaultColor);
			isSelected = false;
		}
	}
	
	@Override
	public void activate() {
		game.setSelectedTile(tileID);
	}

	@Override
	public Rectangle getRectangle() {
		return super.getRect();
	}

}
