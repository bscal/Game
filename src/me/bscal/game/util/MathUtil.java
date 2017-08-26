package me.bscal.game.util;

public class MathUtil {

	private MathUtil() {}
	
	public static int min(int value, int min) {
		if(value < min) {
			return min;
		}
		return value;
	}
	
	public static int max(int value, int max) {
		if(value > max) {
			return max;
		}
		return value;
	}
	
	public static int clamp(int value, int min, int max) {
		if(value < min) {
			return min;
		}
		if(value > max) {
			return max;
		}
		return value;
	}
	
}
