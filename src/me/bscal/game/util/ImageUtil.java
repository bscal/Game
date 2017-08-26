package me.bscal.game.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;

public class ImageUtil {

	private ImageUtil() {}
	
	/**
	 * Changes the brightness of an BufferedImage returning a new BufferedImage.
	 * @param original BufferedImage
	 * @param scale float
	 * @param offset int
	 * @return A new BufferedImage.
	 */
	public static BufferedImage changeBrightness(BufferedImage original, float scale, int offset) {
		ColorModel cm = original.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = original.copyData(null);
		BufferedImage result = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		
		RescaleOp rescaleOp = new RescaleOp(scale, offset, null);
		rescaleOp.filter(result, result);
		return result;
	}
}
