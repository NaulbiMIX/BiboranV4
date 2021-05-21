package hack.rawfish2d.client.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorUtil {
	public static Map colors;
	static {
		colors = new HashMap();
	}

	public static int transparency(int color, double alpha) {
		Color c2 = new Color(color);
		float r2 = 0.003921569f * (float)c2.getRed();
		float g2 = 0.003921569f * (float)c2.getGreen();
		float b2 = 0.003921569f * (float)c2.getBlue();
		return new Color(r2, g2, b2, (float)alpha).getRGB();
	}

	public static Color rainbow(double d2, float fade) {
		float hue = (float)((double)System.nanoTime() + d2) / 1.0E10f % 1.0f;
		long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
		Color c2 = new Color((int)color);
		return new Color((float)c2.getRed() / 255.0f * fade, (float)c2.getGreen() / 255.0f * fade, (float)c2.getBlue() / 255.0f * fade, (float)c2.getAlpha() / 255.0f);
		
		/*
		Random rng = new Random();
		float hue = rng.nextFloat();
		long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
		Color c2 = new Color((int)color);
		return new Color((float)c2.getRed() / 255.0f * fade, (float)c2.getGreen() / 255.0f * fade, (float)c2.getBlue() / 255.0f * fade, (float)c2.getAlpha() / 255.0f)
		*/
	}

	public static Color random(long offset, float fade) {
		float hue = (float)(System.nanoTime() + offset) / 1.0E10f % 1.0f;
		long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 20);
		Color c2 = new Color((int)color);
		return new Color((float)c2.getRed() / 255.0f * fade, (float)c2.getGreen() / 255.0f * fade, (float)c2.getBlue() / 255.0f * fade, (float)c2.getAlpha() / 255.0f);
	}

	public static float[] getRGBA(int color) {
		float a2 = (float)(color >> 24 & 255) / 255.0f;
		float r2 = (float)(color >> 16 & 255) / 255.0f;
		float g2 = (float)(color >> 8 & 255) / 255.0f;
		float b2 = (float)(color & 255) / 255.0f;
		return new float[]{r2, g2, b2, a2};
	}

	public static int intFromHex(String hex) {
		if (hex.equalsIgnoreCase("rainbow")) {
			return ColorUtil.rainbow(0.0, 1.0f).getRGB();
		}
		return Integer.parseInt(hex, 16);
	}

	public static String hexFromInt(int color) {
		return ColorUtil.hexFromInt(new Color(color));
	}

	public static String hexFromInt(Color color) {
		return Integer.toHexString(color.getRGB()).substring(2);
	}

	public static Color blend(Color color1, Color color2, double ratio) {
		float r2 = (float)ratio;
		float ir2 = 1.0f - r2;
		float[] rgb1 = new float[3];
		float[] rgb2 = new float[3];
		color1.getColorComponents(rgb1);
		color2.getColorComponents(rgb2);
		Color color = new Color(rgb1[0] * r2 + rgb2[0] * ir2, rgb1[1] * r2 + rgb2[1] * ir2, rgb1[2] * r2 + rgb2[2] * ir2);
		return color;
	}

	public static Color blend(Color color1, Color color2) {
		return ColorUtil.blend(color1, color2, 0.5);
	}

	public static Color darker(Color color, double fraction) {
		int red = (int)Math.round((double)color.getRed() * (1.0 - fraction));
		int green = (int)Math.round((double)color.getGreen() * (1.0 - fraction));
		int blue = (int)Math.round((double)color.getBlue() * (1.0 - fraction));
		if (red < 0) {
			red = 0;
		} else if (red > 255) {
			red = 255;
		}
		if (green < 0) {
			green = 0;
		} else if (green > 255) {
			green = 255;
		}
		if (blue < 0) {
			blue = 0;
		} else if (blue > 255) {
			blue = 255;
		}
		int alpha = color.getAlpha();
		return new Color(red, green, blue, alpha);
	}

	public static Color lighter(Color color, double fraction) {
		int red = (int)Math.round((double)color.getRed() * (1.0 + fraction));
		int green = (int)Math.round((double)color.getGreen() * (1.0 + fraction));
		int blue = (int)Math.round((double)color.getBlue() * (1.0 + fraction));
		if (red < 0) {
			red = 0;
		} else if (red > 255) {
			red = 255;
		}
		if (green < 0) {
			green = 0;
		} else if (green > 255) {
			green = 255;
		}
		if (blue < 0) {
			blue = 0;
		} else if (blue > 255) {
			blue = 255;
		}
		int alpha = color.getAlpha();
		return new Color(red, green, blue, alpha);
	}

	public static String getHexName(Color color) {
		int r2 = color.getRed();
		int g2 = color.getGreen();
		int b2 = color.getBlue();
		String rHex = Integer.toString(r2, 16);
		String gHex = Integer.toString(g2, 16);
		String bHex = Integer.toString(b2, 16);
		return String.valueOf(rHex.length() != 2 ? new StringBuilder("0").append(rHex).toString() : new StringBuilder().append(rHex).toString()) + (gHex.length() != 2 ? new StringBuilder("0").append(gHex).toString() : new StringBuilder().append(gHex).toString()) + (bHex.length() != 2 ? new StringBuilder("0").append(bHex).toString() : new StringBuilder().append(bHex).toString());
	}

	public static double colorDistance(double r1, double g1, double b1, double r2, double g2, double b2) {
		double a2 = r2 - r1;
		double b3 = g2 - g1;
		double c2 = b2 - b1;
		return Math.sqrt(a2 * a2 + b3 * b3 + c2 * c2);
	}

	public static double colorDistance(double[] color1, double[] color2) {
		return ColorUtil.colorDistance(color1[0], color1[1], color1[2], color2[0], color2[1], color2[2]);
	}

	public static double colorDistance(Color color1, Color color2) {
		float[] rgb1 = new float[3];
		float[] rgb2 = new float[3];
		color1.getColorComponents(rgb1);
		color2.getColorComponents(rgb2);
		return ColorUtil.colorDistance(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
	}

	public static boolean isDark(double r2, double g2, double b2) {
		double dWhite = ColorUtil.colorDistance(r2, g2, b2, 1.0, 1.0, 1.0);
		double dBlack = ColorUtil.colorDistance(r2, g2, b2, 0.0, 0.0, 0.0);
		if (dBlack < dWhite) {
			return true;
		}
		return false;
	}

	public static boolean isDark(Color color) {
		float r2 = (float)color.getRed() / 255.0f;
		float g2 = (float)color.getGreen() / 255.0f;
		float b2 = (float)color.getBlue() / 255.0f;
		return ColorUtil.isDark(r2, g2, b2);
	}

	public static int random(float f2, float g2, float h2) {
		return 0;
	}
}
