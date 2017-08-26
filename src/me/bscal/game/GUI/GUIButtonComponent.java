package me.bscal.game.GUI;

import java.awt.Graphics;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.Sprite;

public class GUIButtonComponent extends GUIComponent{

	private Sprite backround;
	private GUIButton[] buttons;
	private boolean isFixed;
	
	public GUIButtonComponent(Sprite backround, GUIButton[] buttons, int x, int y, boolean fixed) {
		super(x, y);
		this.backround = backround;
		this.buttons = buttons;
		if(backround != null) {
			rect.width = backround.getWidth();
			rect.height = backround.getHeight();
		}
		isFixed = fixed;
	}
	
	public GUIButtonComponent(GUIButton[] buttons, int x, int y, boolean fixed) {
		this(null, buttons, x, y, fixed);
	}
	
	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		if(backround != null) {
			renderer.renderSprite(backround, rect.x, rect.y, xZoom, yZoom, isFixed);
		}
		if(buttons != null) {
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].render(renderer, xZoom, yZoom, this.rect);
			}
		}
	}

	public void update(Game game) {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].update(game);
		}
	}

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
}
