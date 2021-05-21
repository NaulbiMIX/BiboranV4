package hack.rawfish2d.client.utils;

import java.awt.Color;
import java.awt.Rectangle;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import hack.rawfish2d.client.Client;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;

public class R2DUtils {
	private static float anim1 = 0.0f;
	private static float anim2 = 0.1f;
	private static float anim3 = 0.0f;
	private static float anim4 = 0.1f;
	public static final RenderItem RENDER_ITEM = new RenderItem();
	private static ScaledResolution scaledResolution;
	
	private static int tex;

	public static void enableGL2D() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
	}
	
	public static void drawLeftArrow(float x, float y, int cborder, int cbg) {
		
		float r = (float)(cbg >> 16 & 255) / 255.0f;
		float g = (float)(cbg >> 8 & 255) / 255.0f;
		float b = (float)(cbg & 255) / 255.0f;
		float a = (float)(cbg >> 24 & 255) / 255.0f;
		
		y += 5;
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor4f(r, g, b, a);
		GL11.glVertex3f(x, y, 0);
		GL11.glVertex3f(x + 5, y + 5, 0);
		GL11.glVertex3f(x + 5, y - 5, 0);
		/*
		GL11.glVertex3f(x, y, 0);
		GL11.glVertex3f(x, y + 10, 0);
		GL11.glVertex3f(x + 5, y + 5, 0);
		*/
		GL11.glEnd();
		
		float r2 = (float)(cborder >> 16 & 255) / 255.0f;
		float g2 = (float)(cborder >> 8 & 255) / 255.0f;
		float b2 = (float)(cborder & 255) / 255.0f;
		float a2 = (float)(cborder >> 24 & 255) / 255.0f;
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		GL11.glLineWidth(1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor4f(r2, g2, b2, a2);
		GL11.glVertex3f(x, y, 0);
		GL11.glVertex3f(x + 5, y + 5, 0);
		GL11.glVertex3f(x + 5, y - 5, 0);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}
	
	public static void drawRightArrow(float x, float y, int cborder, int cbg) {
		float r = (float)(cbg >> 16 & 255) / 255.0f;
		float g = (float)(cbg >> 8 & 255) / 255.0f;
		float b = (float)(cbg & 255) / 255.0f;
		float a = (float)(cbg >> 24 & 255) / 255.0f;
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor4f(r, g, b, a);
		GL11.glVertex3f(x, y, 0);
		GL11.glVertex3f(x, y + 10, 0);
		GL11.glVertex3f(x + 5, y + 5, 0);
		GL11.glEnd();
		
		float r2 = (float)(cborder >> 16 & 255) / 255.0f;
		float g2 = (float)(cborder >> 8 & 255) / 255.0f;
		float b2 = (float)(cborder & 255) / 255.0f;
		float a2 = (float)(cborder >> 24 & 255) / 255.0f;
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		GL11.glLineWidth(1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor4f(r2, g2, b2, a2);
		GL11.glVertex3f(x, y, 0);
		GL11.glVertex3f(x, y + 10, 0);
		GL11.glVertex3f(x + 5, y + 5, 0);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}
	
	public static boolean isMouseOver(double x, double y, double x2, double y2, int mouseX, int mouseY) {
		return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
	}
	
	public static void drawRightString(String str, int xoffset, int y, int color) {
		int width = MiscUtils.getScaledResolution().getScaledWidth();
		int strw = Client.getInstance().mc.fontRenderer.getStringWidth(str);
		Client.getInstance().mc.fontRenderer.drawStringWithShadow(str, xoffset + (width - strw - 4), y, color);
	}

	public static void drawIconNew(String location, int x, int y, int w, int h){
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glEnable(GL11.GL_BLEND);
		Texture.bindTexture(location, w, h);
		GL11.glPopMatrix();
	}
	
	//TODO remove
	public static void drawScaledString1(String str, float scale, int x, int y, int color) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, 0);
		GL11.glScalef(1.0f / scale, 1.0f / scale, 1.0f / scale);
		x *= scale / 2;
		y *= scale / 2;
		Client.getInstance().mc.fontRenderer.drawStringWithShadow(str, x, y, color);
		GL11.glScalef(1.0f * scale, 1.0f * scale, 1.0f * scale);
		GL11.glPopMatrix();
	}
	
	//TODO remove
	public static void drawScaledString2(String str, float scale, int x, int y, int color) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, 0);
		GL11.glScalef(1.0f / scale, 1.0f / scale, 1.0f / scale);
		x *= scale;
		y *= scale;
		Client.getInstance().mc.fontRenderer.drawStringWithShadow(str, x, y, color);
		GL11.glScalef(1.0f * scale, 1.0f * scale, 1.0f * scale);
		GL11.glPopMatrix();
	}
	
	public static int storeTexture() {
		//tex = Client.getInstance().mc.renderEngine.boundTexture; //fix ??
		//tex = GL11.glGetInteger(GL11.GL_TEXTURE_2D); //fix
		tex = 0; //fix
		return tex;
	}
	
	public static void loadTexture() {
		Client.getInstance().mc.renderEngine.bindTexture(tex); //fix
	}
	
	public static void drawScaledFont(String str, int scale, int x, int y, int color) {
		/*
		if(scale < 8 || scale > 92)
			return;
		
		int index = -1;
		int n = scale - 8;
		while(true) {
			if(n < 0) {
				index = 0;
				break;
			}
			else if(n == 0) {
				index++;
				break;
			}
			else {
				n -= 2;
				index++;
			}
		}
		if(index < Client.getInstance().fonts.size() && index >= 0) {
			storeTexture();
			Client.getInstance().fonts.get(index).drawStringWithShadow(str, x, y, color);
			loadTexture();
		}
		*/
		
		storeTexture();
		FontUtils fu = Client.getInstance().fonts.get(new Integer(scale));
		if(fu != null)
			fu.drawStringWithShadow(str, x, y, color);
		loadTexture();
	}
	
	public static float getScaledFontWidth(String str, int scale) {
		/*
		if(scale < 8 || scale > 92)
			return 0;
		
		int index = -1;
		int n = scale - 8;
		while(true) {
			if(n == 0) {
				index++;
				break;
			}
			else {
				n -= 2;
				index++;
			}
		}
		if(index < Client.getInstance().fonts.size() && index >= 0) {
			return Client.getInstance().fonts.get(index).getWidth(str);
		}
		return 0;
		*/
		
		FontUtils fu = Client.getInstance().fonts.get(new Integer(scale));
		if(fu != null)
			return fu.getWidth(str);
		else
			return 0;
	}

	//ok
	public static void drawBorderedCircle(int x2, int y2, float radius, int outsideC, int insideC) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glPushMatrix();
		float scale = 0.1f;
		GL11.glScalef(0.1f, 0.1f, 0.1f);
		drawAnimCircle1(x2 *= 10, y2 *= 10, radius *= 10.0f, insideC);
		drawUnfilledCircle(x2, y2, radius, 1.0f, outsideC);
		GL11.glScalef(10.0f, 10.0f, 10.0f);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	//ok
	public static void drawAnimCircle1(int x2, int y2, double r2, int c2) {
		float r = (float)(c2 >> 16 & 255) / 255.0f;
		float g = (float)(c2 >> 8 & 255) / 255.0f;
		float b = (float)(c2 & 255) / 255.0f;
		float a = (float)(c2 >> 24 & 255) / 255.0f;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(r, g, b, a);
		GL11.glBegin(3);
		if ((anim1 += anim2) >= 360.0f) {
			anim1 -= anim2;
			if ((anim3 += anim4) >= 360.0f) {
				anim3 = 0.0f;
				anim1 = 0.0f;
			}
		}
		float i2 = anim3;
		while (i2 <= anim1) {
			double x22 = Math.sin((double)i2 * 3.141592653589793 / 180.0) * r2;
			double y22 = Math.cos((double)i2 * 3.141592653589793 / 180.0) * r2;
			GL11.glVertex2d((double)x2 + x22, (double)y2 + y22);
			i2 += 1.0f;
		}
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	private static void drawAnimCircle2(int x, int y, double r, int c) {
	    float f = (c >> 24 & 0xFF) / 255.0F;
	    float f1 = (c >> 16 & 0xFF) / 255.0F;
	    float f2 = (c >> 8 & 0xFF) / 255.0F;
	    float f3 = (c & 0xFF) / 255.0F;
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glDisable(GL11.GL_TEXTURE_2D);
	    GL11.glEnable(GL11.GL_LINE_SMOOTH);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    GL11.glColor4f(f1, f2, f3, f);
	    GL11.glBegin(3);
	    anim1 += anim2;
	    if(anim1 >= 360){
	    	anim1 -= anim2;
	    	anim3 += anim4;
	    	if(anim3 >= 360){
	    		anim3 = 0;
	    		anim1 = 0;
	    	}
	  
	    }
	    for (float i = anim3; i <= anim1; i++) {
	      double x2 = Math.sin(i * 3.141592653589793D / 180.0D) * r;
	      double y2 = Math.cos(i * 3.141592653589793D / 180.0D) * r;
	      GL11.glVertex2d(x + x2, y + y2);
	    }
	    GL11.glEnd();
	    GL11.glDisable(GL11.GL_LINE_SMOOTH);
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	    GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawUnfilledCircle(int x2, int y2, float radius, float lineWidth, int color) {
		float alpha = (float)(color >> 24 & 255) / 255.0f;
		float red = (float)(color >> 16 & 255) / 255.0f;
		float green = (float)(color >> 8 & 255) / 255.0f;
		float blue = (float)(color & 255) / 255.0f;
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glLineWidth(4.0f);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBegin(2);
		int i2 = 0;
		while (i2 <= 360) {
			GL11.glVertex2d((double)x2 + Math.sin((double)i2 * 3.141526 / 180.0) * (double)radius, (double)y2 + Math.cos((double)i2 * 3.141526 / 180.0) * (double)radius);
			++i2;
		}
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}
	
	//ok
	public static void drawFilledCircle(int x2, int y2, double r2, int c2) {
		float f2 = (float)(c2 >> 24 & 255) / 255.0f;
		float f1 = (float)(c2 >> 16 & 255) / 255.0f;
		float f22 = (float)(c2 >> 8 & 255) / 255.0f;
		float f3 = (float)(c2 & 255) / 255.0f;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(f1, f22, f3, f2);
		GL11.glBegin(6);
		int i2 = 0;
		while (i2 <= 360) {
			double x22 = Math.sin((double)i2 * 3.141592653589793 / 180.0) * r2;
			double y22 = Math.cos((double)i2 * 3.141592653589793 / 180.0) * r2;
			GL11.glVertex2d((double)x2 + x22, (double)y2 + y22);
			++i2;
		}
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawCircle(int x2, int y2, double r2, int c2) {
		float f2 = (float)(c2 >> 24 & 255) / 255.0f;
		float f1 = (float)(c2 >> 16 & 255) / 255.0f;
		float f22 = (float)(c2 >> 8 & 255) / 255.0f;
		float f3 = (float)(c2 & 255) / 255.0f;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(f1, f22, f3, f2);
		GL11.glBegin(2);
		int i2 = 0;
		while (i2 <= 360) {
			double x22 = Math.sin((double)i2 * 3.141592653589793 / 180.0) * r2;
			double y22 = Math.cos((double)i2 * 3.141592653589793 / 180.0) * r2;
			GL11.glVertex2d((double)x2 + x22, (double)y2 + y22);
			++i2;
		}
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void drawTexturedCube(String location, double x, double y, double w, double h) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		Texture.bindTexture(location, (int)w, (int)h);
		Tessellator var2 = Tessellator.instance;
		var2.startDrawingQuads();
		var2.setColorOpaque_I(0xFFFFFF);
		var2.addVertexWithUV(0.0D, 1.0D, 0.0D, 0.0D, 0.0D);
		var2.addVertexWithUV(1.0D, 1.0D, 0.0D, 0.0D, 0.0D);
		var2.addVertexWithUV(1.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		var2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		var2.draw();
		GL11.glPopMatrix();
	}

	public static String getPrettyName(String name, String splitBy) {
		String prettyName = "";
		String[] actualNameSplit = name.split(splitBy);
		if (actualNameSplit.length > 0) {
			String[] var3 = actualNameSplit;
			int var4 = actualNameSplit.length;
			int var5 = 0;
			while (var5 < var4) {
				String arg2 = var3[var5];
				arg2 = String.valueOf(String.valueOf(arg2.substring(0, 1).toUpperCase())) + arg2.substring(1, arg2.length()).toLowerCase();
				prettyName = String.valueOf(String.valueOf(prettyName)) + arg2 + " ";
				++var5;
			}
		} else {
			prettyName = String.valueOf(String.valueOf(actualNameSplit[0].substring(0, 1).toUpperCase())) + actualNameSplit[0].substring(1, actualNameSplit[0].length()).toLowerCase();
		}
		return prettyName.trim();
	}

	//ok
	public static void drawRectNoBlend(float x1, float y1, float x2, float y2, int col1) {
		float f2 = (float)(col1 >> 24 & 255) / 255.0f;
		float f1 = (float)(col1 >> 16 & 255) / 255.0f;
		float f22 = (float)(col1 >> 8 & 255) / 255.0f;
		float f3 = (float)(col1 & 255) / 255.0f;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glPushMatrix();
		GL11.glColor4f(f1, f22, f3, f2);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(x1, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y1);
		GL11.glVertex2d(x1, y1);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public static void prepareOpenGL() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
	}

	public static void disableGL2D() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, 4352);
		GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, 4352);
	}

	public static void drawRect(Rectangle rectangle, int color) {
		drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, color);
	}

	public static void drawRect(float x2, float y2, float x1, float y1, int color) {
		enableGL2D();
		glColor(color);
		drawRect(x2, y2, x1, y1);
		disableGL2D();
	}
	
	//ok
	public static void drawGradientRect(float x2, float y2, float x1, float y1, int topColor, int bottomColor) {
		enableGL2D();
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glBegin(GL11.GL_QUADS);
		glColor(topColor);
		GL11.glVertex2f(x2, y1);
		GL11.glVertex2f(x1, y1);
		glColor(bottomColor);
		GL11.glVertex2f(x1, y2);
		GL11.glVertex2f(x2, y2);
		GL11.glEnd();
		GL11.glShadeModel(GL11.GL_FLAT);
		disableGL2D();
	}
	
	public static void drawGradientDoubleBorderedRect_DD(float x1, float y1, float x2, float y2, int border1, int border2, int grad1, int grad2) {
		//enableGL2D();
		GL11.glPushMatrix();
		GL11.glLineWidth(0.5f);
		drawRect(x1, y1, x2, y2, border1); //draw outside border
		GL11.glLineWidth(0.5f);
		drawRect(x1 + 0.5f, y1 + 0.5f, x2 - 0.5f, y2 - 0.5f, border2); //draw inside border
		
		x1 += 1.0f;
		y1 += 1.0f;
		x2 -= 1.0f;
		y2 -= 1.0f;
		
		float yy = y1 + ((y2 - y1) / 3) * 2;
		//float yy = y1 + 5f;
		GL11.glLineWidth(0.5f);
		drawRect(x1, yy, x2, y2, border1);
		GL11.glLineWidth(0.5f);
		drawGradientRect(x1, y1, x2, yy, grad1, grad2);
		
		GL11.glPopMatrix();
		//disableGL2D();
	}
	
	//?
	public static void drawGradientBorderedRect(float x2, float y2, float x22, float y22, float l1, int col1, int col2, int col3) {
		enableGL2D();
		GL11.glPushMatrix();
		glColor(col1);
		GL11.glLineWidth(l1);
		GL11.glBegin(1);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y22);
		GL11.glVertex2d(x22, y22);
		GL11.glVertex2d(x22, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x22, y2);
		GL11.glVertex2d(x2, y22);
		GL11.glVertex2d(x22, y22);
		GL11.glEnd();
		GL11.glPopMatrix();
		drawGradientRect(x2, y2, x22, y22, col2, col3);
		disableGL2D();
	}

	public static void glColor(Color color) {
		GL11.glColor4f((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
	}

	public static void glColor(int hex) {
		float alpha = (float)(hex >> 24 & 255) / 255.0f;
		float red = (float)(hex >> 16 & 255) / 255.0f;
		float green = (float)(hex >> 8 & 255) / 255.0f;
		float blue = (float)(hex & 255) / 255.0f;
		GL11.glColor4f(red, green, blue, alpha);
	}

	public static void drawStrip(int x2, int y2, float width, double angle, float points, float radius, int color) {
		int i2;
		float yc2;
		float a2;
		float xc2;
		float f1 = (float)(color >> 24 & 255) / 255.0f;
		float f2 = (float)(color >> 16 & 255) / 255.0f;
		float f3 = (float)(color >> 8 & 255) / 255.0f;
		float f4 = (float)(color & 255) / 255.0f;
		GL11.glPushMatrix();
		GL11.glTranslated(x2, y2, 0.0);
		GL11.glColor4f(f2, f3, f4, f1);
		GL11.glLineWidth(width);
		if (angle > 0.0) {
			GL11.glBegin(3);
			i2 = 0;
			while ((double)i2 < angle) {
				a2 = (float)((double)i2 * (angle * 3.141592653589793 / (double)points));
				xc2 = (float)(Math.cos(a2) * (double)radius);
				yc2 = (float)(Math.sin(a2) * (double)radius);
				GL11.glVertex2f(xc2, yc2);
				++i2;
			}
			GL11.glEnd();
		}
		if (angle < 0.0) {
			GL11.glBegin(3);
			i2 = 0;
			while ((double)i2 > angle) {
				a2 = (float)((double)i2 * (angle * 3.141592653589793 / (double)points));
				xc2 = (float)(Math.cos(a2) * (double)(- radius));
				yc2 = (float)(Math.sin(a2) * (double)(- radius));
				GL11.glVertex2f(xc2, yc2);
				--i2;
			}
			GL11.glEnd();
		}
		disableGL2D();
		GL11.glDisable(3479);
		GL11.glPopMatrix();
	}

	public static void drawRect(float x2, float y2, float x1, float y1, float r2, float g2, float b2, float a2) {
		enableGL2D();
		GL11.glColor4f(r2, g2, b2, a2);
		drawRect(x2, y2, x1, y1);
		disableGL2D();
	}

	public static void drawRect(float x2, float y2, float x1, float y1) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x2, y1);
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x1, y2);
		GL11.glVertex2f(x2, y2);
		GL11.glEnd();
	}

	public static void drawCircle(float cx2, float cy2, float r2, int num_segments, int c2) {
		cx2 *= 2.0f;
		cy2 *= 2.0f;
		float f2 = (float)(c2 >> 24 & 255) / 255.0f;
		float f22 = (float)(c2 >> 16 & 255) / 255.0f;
		float f3 = (float)(c2 >> 8 & 255) / 255.0f;
		float f4 = (float)(c2 & 255) / 255.0f;
		float theta = (float)(6.2831852 / (double)num_segments);
		float p2 = (float)Math.cos(theta);
		float s2 = (float)Math.sin(theta);
		float x2 = r2 *= 2.0f;
		float y2 = 0.0f;
		//enableGL2D();
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		GL11.glColor4f(f22, f3, f4, f2);
		GL11.glBegin(2);
		int ii2 = 0;
		while (ii2 < num_segments) {
			GL11.glVertex2f(x2 + cx2, y2 + cy2);
			float t2 = x2;
			x2 = p2 * x2 - s2 * y2;
			y2 = s2 * t2 + p2 * y2;
			++ii2;
		}
		GL11.glEnd();
		GL11.glScalef(2.0f, 2.0f, 2.0f);
		
		//disableGL2D();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glPopMatrix();
	}

	//ok
	public static void drawFullCircle(int cx2, int cy2, double r2, int c2) {
		r2 *= 2.0;
		cx2 *= 2;
		cy2 *= 2;
		float f2 = (float)(c2 >> 24 & 255) / 255.0f;
		float f22 = (float)(c2 >> 16 & 255) / 255.0f;
		float f3 = (float)(c2 >> 8 & 255) / 255.0f;
		float f4 = (float)(c2 & 255) / 255.0f;
		enableGL2D();
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		GL11.glColor4f(f22, f3, f4, f2);
		GL11.glBegin(6);
		int i2 = 0;
		while (i2 <= 360) {
			double x2 = Math.sin((double)i2 * 3.141592653589793 / 180.0) * r2;
			double y2 = Math.cos((double)i2 * 3.141592653589793 / 180.0) * r2;
			GL11.glVertex2d((double)cx2 + x2, (double)cy2 + y2);
			++i2;
		}
		GL11.glEnd();
		GL11.glScalef(2.0f, 2.0f, 2.0f);
		disableGL2D();
	}

	public static void drawSmallString(String s2, int x2, int y2, int color) {
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		Client.getInstance().mc.getMinecraft();
		Client.getInstance().mc.fontRenderer.drawStringWithShadow(s2, x2 * 2, y2 * 2, color);
		GL11.glPopMatrix();
	}

	public static void drawLargeString(String text, int x2, int y2, int color) {
		GL11.glPushMatrix();
		GL11.glScalef(2.0f, 2.0f, 2.0f);
		Client.getInstance().mc.getMinecraft();
		Client.getInstance().mc.fontRenderer.drawStringWithShadow(text, x2 *= 2, y2, color);
		GL11.glPopMatrix();
	}

	public static void drawbigfont(String text, int x2, int y2, int color) {
		GL11.glPushMatrix();
		GL11.glScalef(2.0f, 2.0f, 2.0f);
		Client.getInstance().mc.getMinecraft();
		Client.getInstance().mc.fontRenderer.drawStringWithShadow(text, x2 *= 2, y2, color);
		GL11.glPopMatrix();
	}

	public static void drawLogo(double x2, double y2, double width, double height) {
		Texture tex = new Texture("/icons/logo.png");
		GL11.glPopMatrix();
		tex.renderTexture(x2, y2, width, height, new Color(44975));
		GL11.glPopMatrix();
	}

	public static void renderTexture(int texID, double x2, double y2, double width, double height) {
		boolean tex2D = GL11.glGetBoolean(GL11.GL_TEXTURE_2D);
		boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		GL11.glBegin(4);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2d((x2 *= 2.0) + (width *= 2.0), y2 *= 2.0);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2d(x2, y2);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2d(x2, y2 + (height *= 2.0));
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2d(x2, y2 + height);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2d(x2 + width, y2 + height);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2d(x2 + width, y2);
		GL11.glEnd();
		if (!tex2D) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
		if (!blend) {
			GL11.glDisable(GL11.GL_BLEND);
		}
		GL11.glPopMatrix();
	}

	public static void drawIcon(String icon, double x2, double y2, double width, double height, int color) {
		Texture tex = new Texture(icon);
		tex.renderTexture(x2, y2, width, height, new Color(color));
	}
	
	public static int[] getMousePos() {
		int[] pos = {0, 0};
		pos[0] = Mouse.getEventX() / 2;
		pos[1] = (Client.getInstance().mc.displayHeight - Mouse.getEventY()) / 2;
		
		return pos;
	}

	public static ScaledResolution getScaledResolution() {
		return scaledResolution = new ScaledResolution(Client.getInstance().mc.gameSettings, Client.getInstance().mc.displayWidth, Client.getInstance().mc.displayHeight);
	}
	
	public static final void drawRect2(double x, double y, double x2, double y2, int color) {
		double f1;

		if (x < x2) {
			f1 = x;
			x = x2;
			x2 = f1;
		}

		if (y < y2) {
			f1 = y;
			y = y2;
			y2 = f1;
		}
		
		float red = (float)(color >> 16 & 255) / 255.0f;
		float green = (float)(color >> 8 & 255) / 255.0f;
		float blue = (float)(color & 255) / 255.0f;
		float alpha = (float)(color >> 24 & 255) / 255.0f;
		
		Tessellator var9 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(red, green, blue, alpha);
		var9.startDrawingQuads();
		var9.addVertex(x, y2, 0.0D);
		var9.addVertex(x2, y2, 0.0D);
		var9.addVertex(x2, y, 0.0D);
		var9.addVertex(x, y, 0.0D);
		var9.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void drawBetterRect(double x, double y, double x1, double y1, float l1, int color, int color2) {
		drawRect2((int)x, (int)y, (int)x1, (int)y1, color);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		
		drawLine2D((float)x * 2, (float)y * 2, (float)x * 2, (float)y1 * 2, l1, color2);
		drawLine2D((float)x * 2, (float)y * 2 + 1, (float)x1 * 2, (float)y * 2 + 1, l1, color2);
		drawLine2D((float)x1 * 2, (float)y * 2, (float)x1 * 2, (float)y1 * 2, l1, color2);
		drawLine2D((float)x * 2, (float)y1 * 2, (float)x1 * 2, (float)y1 * 2, l1, color2);

		GL11.glScalef(2F, 2F, 2F);
	}
	
	public static void drawRect(float x, float y, float w, float h, float width, int color) {
		drawLine2D(x, y, x + w, y, width, color);
		drawLine2D(x + w, y, x + w, y + h, width, color);
		drawLine2D(x + w, y + h, x, y + h, width, color);
		drawLine2D(x, y + h, x, y, width, color);
	}
	
	public static void drawLine2D(float x, float y, float x1, float y1, float width, int color){
		float red = (float)(color >> 16 & 255) / 255.0f;
		float green = (float)(color >> 8 & 255) / 255.0f;
		float blue = (float)(color & 255) / 255.0f;
		float alpha = (float)(color >> 24 & 255) / 255.0f;
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		//GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glLineWidth(width);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x1, y1);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		//GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}
	
	public static void drawCrossBox(Vector2f pos, float size, int iboxCorner, int iboxBG, int icross) {
		float margin = 3.4f;
		drawBetterRect(pos.x, pos.y, pos.x + size, pos.y + size, 1f, iboxCorner, iboxBG);
		drawLine2D(pos.x + margin, pos.y + margin, pos.x + size - margin - 1, pos.y + size - margin, 2f, icross);
		drawLine2D(pos.x + margin, pos.y + size - margin, pos.x + size - margin - 1, pos.y + margin, 2f, icross);
	}
	
	//=====
	
	public void drawTextWithRainBow(String str, int posX, int posY) {
		int width = 0;
		StringBuilder parColor = new StringBuilder();
		for (int i = 0; i < str.length(); ++i) {
			if (str.charAt(i) == '\u00a7' && str.length() - 1 > i) {parColor.append("\u00a7").append(str.charAt(i + 1));
				++i;
				continue;
			}
			int color = rainbow((float)(-1.05f * (float)i), (float)1.0f);
			drawStringWithShadow(parColor.toString() + str.charAt(i), posX + width, posY, color, 1.0F);
			width += getTextWidth(parColor.toString() + str.charAt(i));
		}
	}

	public int rainbow(float offset, float fade) {
		int color = Color.HSBtoRGB((float)(System.currentTimeMillis() % 3500L) / 3500.0f + offset, 1.0f, fade);
		return new Color(color).getRGB();
	}  

	public void drawStringWithShadow(String text, int posX, int posY, int color, float size) {
		GL11.glPushMatrix();
		GL11.glScalef(size, size, size);
		Client.mc.fontRenderer.drawStringWithShadow(text, posX, posY, color);
		GL11.glScalef(size, size, size);
		GL11.glPopMatrix();
	}

	public int getTextWidth(String par1) {
		return Client.mc.fontRenderer.getStringWidth(par1);
	}
}
