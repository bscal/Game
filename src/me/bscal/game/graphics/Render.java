package me.bscal.game.graphics;

import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import me.bscal.game.Game;
import me.bscal.game.sprites.Sprite;

public class Render {
	
	private BufferedImage view;
	private int[] pixels;
	public static Rectangle camera;
	private final int TEXT_COLOR = 0xffffffff;
	public final int CAMERA_GUI_OFFSET = 256;

	public Render(int width, int height) {
		GraphicsDevice[] graphicDevices = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		
		int maxScreenWidth = 0;
		int maxScreenHeight = 0;
		for(int i = 0; i < graphicDevices.length; i++) {
			if(maxScreenWidth < graphicDevices[i].getDisplayMode().getWidth()) {
				maxScreenWidth = graphicDevices[i].getDisplayMode().getWidth();
			}
			if(maxScreenHeight < graphicDevices[i].getDisplayMode().getHeight()) {
				maxScreenHeight = graphicDevices[i].getDisplayMode().getHeight();
			}
		}
		
		//Create a BufferedImage that will represent our view.
		view = new BufferedImage(maxScreenWidth, maxScreenHeight, BufferedImage.TYPE_INT_RGB);

		camera = new Rectangle(0, 0, width - CAMERA_GUI_OFFSET, height);
		//Create an array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}

	public void render(Graphics g) {
		g.drawImage(view.getSubimage(0, 0, camera.width, camera.height), 0, 0, camera.width, camera.height, null);
	}
	
	/**
	 * Renders image to pixels in an array
	 * @param img BufferedImage
	 * @param xPos X position
	 * @param yPos Y position
	 * @param xZoom X zoom multiplier
	 * @param yZoom Y zoom multiplier
	 */
	public void renderImage(BufferedImage img, int xPos, int yPos, int xZoom, int yZoom, boolean fixed) {
		int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		renderPixels(pixels, img.getWidth(), img.getHeight(), xPos, yPos, xZoom, yZoom, fixed);
	}
	
	public void renderPixels(int[] pixels, int width, int height, int xPos, int yPos, int xZoom, int yZoom, boolean fixed) {
		renderPixels(pixels, width, height, width, height, xPos, yPos, xZoom, yZoom, fixed, 0, 0);
	}
	
	/**
	@param renderPixels = pixels to render
	@param imageWidth = width of entire image
	@param imageHeight = height of entire image
	@param renderWidth = width of image to render
	@param renderHeight = height of image to render
	@param xPosition = x position to render image
	@param yPosition = y position to render image
	@param xZoom = horizontal zoom
	@param yZoom = vertical zoom
	@param fixed = should offset by camera position
	@param xOffset = offset into the full image to render x
	@param yOffset = offset into the full image to render y
	*/
	public void renderPixels(int[] renderPixels, int imageWidth, int imageHeight, int renderWidth, int renderHeight, int xPosition, int yPosition,
			int xZoom, int yZoom, boolean fixed, int xOffset, int yOffset) {
		for (int y = yOffset; y < yOffset + renderHeight; y++) {
			for (int x = xOffset; x < xOffset + renderWidth; x++) {
				for (int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++) {
					for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++) {
						setPixel(renderPixels[x + y * imageWidth], ((x - xOffset) * xZoom) + xPosition + xZoomPosition,
								(((y - yOffset) * yZoom) + yPosition + yZoomPosition), fixed);
					}
				}
			}
		}
	}
	
	public void renderPixels(int[] renderPixels, int imageWidth, int imageHeight, int renderWidth, int renderHeight, int xPosition, int yPosition,
			int xZoom, int yZoom, boolean fixed, int xOffset, int yOffset, int newColor, int oldColor) {
		for (int y = yOffset; y < yOffset + renderHeight; y++) {
			for (int x = xOffset; x < xOffset + renderWidth; x++) {
				for (int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++) {
					for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++) {
						setPixel(renderPixels[x + y * imageWidth], ((x - xOffset) * xZoom) + xPosition + xZoomPosition,
								(((y - yOffset) * yZoom) + yPosition + yZoomPosition), fixed, newColor, oldColor);
					}
				}
			}
		}
	}
	
	private void setPixel(int pixel, int x, int y, boolean fixed) {
		int pixelIndex = -1;
		if(!fixed) {
			if(x >= camera.x && y >= camera.y && x <= camera.x + camera.width && y <= camera.y + camera.height)
				pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
		}
		else {
			if(x >= 0 && y >= 0 && x <= camera.width && y <= camera.height)
				pixelIndex = x + y * view.getWidth();
		}

		if(pixelIndex > 0 && pixels.length > pixelIndex && pixel != Game.ALPHA)
			pixels[pixelIndex] = pixel;
	}
	
	private void setPixel(int pixel, int x, int y, boolean fixed, int newColor, int oldColor) {
		int pixelIndex = 0;
		if(!fixed) {
			if(x >= camera.x && y >= camera.y && x <= camera.x + camera.width && y <= camera.y + camera.height)
				pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
		}
		else {
			if(x >= 0 && y >= 0 && x <= camera.width && y <= camera.height)
				pixelIndex = x + y * view.getWidth();
		}

		if(pixelIndex > 0 && pixels.length > pixelIndex && pixel != Game.ALPHA) {
			if(pixel == oldColor) {
				pixel = newColor;
			}
			pixels[pixelIndex] = pixel;
		}
	}
	
	public void renderRectangle(Rectangle rect, int xZoom, int yZoom, boolean fixed) {
		int[] pixels = rect.getPixels();
		if(pixels != null) {
			renderPixels(pixels, rect.width, rect.height, rect.x, rect.y, xZoom, yZoom, fixed);
		}
	}
	
	public void renderRectangle(Rectangle rect, Rectangle offset, int xZoom, int yZoom, boolean fixed) {
		int[] pixels = rect.getPixels();
		if(pixels != null) {
			renderPixels(pixels, rect.width, rect.height, rect.x + offset.x, rect.y + offset.y, xZoom, yZoom, fixed);
		}
	}
	
	public void renderSprite(Sprite sprite, int x, int y, int xZoom, int yZoom, boolean fixed) {
		renderPixels(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), x, y, xZoom, yZoom, fixed);
	}
	
	public void renderSprite(Sprite sprite, int xPosition, int yPosition, int renderWidth, int renderHeight, int xZoom, int yZoom, boolean fixed, int xOffset, int yOffset) {
		renderPixels(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), renderWidth, renderHeight, xPosition, yPosition, 
					xZoom, yZoom, fixed, xOffset, yOffset);
	}
	
	public void renderSprite(Sprite sprite, int newColor, int oldColor, int x, int y, int xZoom, int yZoom, boolean fixed) {
		renderPixels(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), sprite.getWidth(), sprite.getHeight(), x, y,
				xZoom, yZoom, fixed, 0, 0, newColor, oldColor);
	}
	
	public void renderString(Sprite sprite, int x, int y, int xZoom, int yZoom, int color, boolean fixed) {
		renderPixels(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), sprite.getWidth(), sprite.getHeight(), x, y,
				xZoom, yZoom, fixed, 0, 0, color, TEXT_COLOR);
	}
	
	public Rectangle getCamera() {
		return camera;
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
}
