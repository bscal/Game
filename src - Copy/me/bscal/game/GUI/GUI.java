package me.bscal.game.GUI;

import me.bscal.game.Game;
import me.bscal.game.entity.GameObject;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.Sprite;

public class GUI implements GameObject{

	private Sprite backround;
	private GUIButton[] buttons;
	private Rectangle rect = new Rectangle();
	private boolean isFixed;
	
	public GUI(Sprite backround, GUIButton[] buttons, int x, int y, boolean fixed) {
		this.backround = backround;
		this.buttons = buttons;
		rect.x = x;
		rect.y = y;
		if(backround != null) {
			rect.width = backround.getWidth();
			rect.height = backround.getHeight();
		}
		isFixed = fixed;
	}
	
	public GUI(GUIButton[] buttons, int x, int y, boolean fixed) {
		this(null, buttons, x, y, fixed);
	}
	
	@Override
	public void render(Render renderer, int xZoom, int yZoom) {
		if(backround != null) {
			renderer.renderSprite(backround, rect.x, rect.y, xZoom, yZoom, isFixed);
		}
		if(buttons != null) {
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].render(renderer, xZoom, yZoom, this.rect);
			}
		}
	}

	@Override
	public void update(Game game) {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].update(game);
		}
	}

	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		boolean clicked = false;
		if(!isFixed) {
			mouseRectangle = new Rectangle(mouseRectangle.x + camera.x, mouseRectangle.y + camera.y, 1, 1);
		}
		else {
			mouseRectangle = new Rectangle(mouseRectangle.x, mouseRectangle.y, 1, 1);
		}
		if(rect.width == 0 || rect.height == 0 || mouseRectangle.intersects(rect)) {
			mouseRectangle.x -= rect.x;
			mouseRectangle.y -= rect.y;
			for(int i = 0; i < buttons.length; i++) {
				boolean result = buttons[i].handleMouseClick(mouseRectangle, camera, xZoom, yZoom);
				if(clicked == false) {
					clicked = result;
				}
			}
		}
		return clicked;
	}

	@Override
	public Rectangle getRectangle() {
		return rect;
	}
	
	public int getLayer() {
		return Integer.MAX_VALUE;
	}
}
