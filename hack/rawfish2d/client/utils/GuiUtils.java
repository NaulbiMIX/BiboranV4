package hack.rawfish2d.client.utils;

import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Packet;
import net.minecraft.src.RenderItem;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;

import hack.rawfish2d.client.Client;

public class GuiUtils
{
	private static RenderItem itemRenderer = new RenderItem();

	public static void drawBorderedRect1(float x, float y, float x1, float y1, int borderC, int insideC)
	{
		//R2DUtils.drawBorderedRect(x, y, x1, y1, y1, insideC, borderC);
		
		R2DUtils.drawBetterRect(x, y, x1 - 0.5f, y1, 1f, insideC, borderC);
		
		//drawRect(x, y, x1, y1, insideC); //cool stuff
		//drawRect(x, y, x1 - 1f, y1 - 1f, insideC);
		/*
		R2DUtils.drawLine2D(x, y - 0.5f, x, y1, 1f, borderC);
		R2DUtils.drawLine2D(x1 - 0.5f, y - 0.5f, x1 - 0.5f, y1, 1f, borderC);
		R2DUtils.drawLine2D(x, y, x1 - 1.0f, y, 1f, borderC);
		R2DUtils.drawLine2D(x,  y1, x1 - 1.0f, y1, 1f, borderC);
		*/
		
		/*
		drawVLine(x, y, y1 - 1.0f, borderC);
		drawVLine(x1 - 1.0f, y, y1 - 1.0f, borderC);
		drawHLine(x, x1 - 1.0f, y, borderC);
		drawHLine(x, x1 - 1.0f, y1 - 1.0f, borderC);
		*/
	}

	//ok
	public static void drawBorderedRect2(float x, float y, float x2, float y2, float l1, int col1, int col2) {
		drawRect(x, y, x2, y2, col2);

		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		//GL11.glEnable(GL11.GL_LINE_SMOOTH); //weird color

		GL11.glColor4f(f1, f2, f3, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2 + 0.5f);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glPopMatrix();
	}
/*
	//ok
	public static void drawHLine(float x1, float y1, float x2, int y2) {
		if (y1 < x1) {
			float swap = x1;
			x1 = y1;
			y1 = swap;
		}
		//drawRect(x1, x2, y1 + 1.0f, x2 + 1.0f, y2);
		drawRect(x1, x2, y1 + 0.5f, x2 + 0.5f, y2);
	}

	//ok
	public static void drawVLine(float x1, float y1, float x2, int y2)
	{
		if (x2 < y1) {
			float swap = y1;
			y1 = x2;
			x2 = swap;
		}
		//drawRect(x1, y1 + 1.0f, x1 + 1.0f, x2, y2);
		drawRect(x1, y1 + 0.5f, x1 + 0.5f, x2, y2);
	}
*/
	//ok
	public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor)
	{
		float alpha = (float)(paramColor >> 24 & 0xFF) / 255F;
		float red = (float)(paramColor >> 16 & 0xFF) / 255F;
		float green = (float)(paramColor >> 8 & 0xFF) / 255F;
		float blue = (float)(paramColor & 0xFF) / 255F;
		
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		//GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(paramXEnd, paramYStart);
		GL11.glVertex2d(paramXStart, paramYStart);
		GL11.glVertex2d(paramXStart, paramYEnd);
		GL11.glVertex2d(paramXEnd, paramYEnd);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glPopMatrix();
		
		/*
		float var7 = (float)(paramColor >> 24 & 255) / 255.0F;
		float var8 = (float)(paramColor >> 16 & 255) / 255.0F;
		float var9 = (float)(paramColor >> 8 & 255) / 255.0F;
		float var10 = (float)(paramColor & 255) / 255.0F;
		float var11 = (float)(paramColor >> 24 & 255) / 255.0F;
		float var12 = (float)(paramColor >> 16 & 255) / 255.0F;
		float var13 = (float)(paramColor >> 8 & 255) / 255.0F;
		float var14 = (float)(paramColor & 255) / 255.0F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, var7);
		float z = GuiScreen.zLevel;
		var15.addVertex((double)paramXEnd, (double)paramYStart, (double)z);
		var15.addVertex((double)paramXStart, (double)paramYStart, (double)z);
		var15.setColorRGBA_F(var12, var13, var14, var11);
		var15.addVertex((double)paramXStart, (double)paramYEnd, (double)z);
		var15.addVertex((double)paramXEnd, (double)paramYEnd, (double)z);
		var15.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		*/
	}

	public static void drawRadialGradientRect(double x, double y, double x2, double y2, int col1, int col2)
	{
		double a1 = (float)(col1 >> 24 & 0xFF) / 255F;
		double r1 = (float)(col1 >> 16 & 0xFF) / 255F;
		double g1 = (float)(col1 >> 8 & 0xFF) / 255F;
		double b1 = (float)(col1 & 0xFF) / 255F;

		double a2 = (float)(col2 >> 24 & 0xFF) / 255F;
		double r2 = (float)(col2 >> 16 & 0xFF) / 255F;
		double g2 = (float)(col2 >> 8 & 0xFF) / 255F;
		double b2 = (float)(col2 & 0xFF) / 255F;
		/*
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4d(r1, g1, b1, a1);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);

		GL11.glColor4d(r2, g2, b2, a2);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		*/

		GL11.glPushMatrix();
		//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
/*
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor4d(r1, g1, b1, a1);
		GL11.glVertex2d(x, y);
		GL11.glColor4d(r2, g2, b2, a2);
		GL11.glVertex2d(x2 / 2, y2 / 2);
		GL11.glColor4d(r1, g1, b1, a1);
		GL11.glVertex2d(x, y);
		GL11.glEnd();
*/
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor4d(r1, g1, b1, a1);
		GL11.glVertex2d(x, y);
		GL11.glColor4d(r2, g2, b2, a2);
		GL11.glVertex2d(x2, y);
		GL11.glColor4d(r1, g1, b1, a1);
		GL11.glVertex2d(x, y2);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor4d(r1, g1, b1, a1);
		GL11.glVertex2d(x, y2);
		GL11.glColor4d(r2, g2, b2, a2);
		GL11.glVertex2d(x2, y2);
		GL11.glColor4d(r1, g1, b1, a1);
		GL11.glVertex2d(x2, y);
		GL11.glEnd();
		

		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor4d(r1, g1, b1, a1);
		GL11.glVertex2d(x2, y2);
		GL11.glColor4d(r2, g2, b2, a2);
		GL11.glVertex2d(x, y2);
		GL11.glColor4d(r1, g1, b1, a1);
		GL11.glVertex2d(x2, y);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glFlush();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
		/*
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
		*/
	}

	public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2)
	{
		
		double f = (float)(col1 >> 24 & 0xFF) / 255F;
		double f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		double f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		double f3 = (float)(col1 & 0xFF) / 255F;

		double f4 = (float)(col2 >> 24 & 0xFF) / 255F;
		double f5 = (float)(col2 >> 16 & 0xFF) / 255F;
		double f6 = (float)(col2 >> 8 & 0xFF) / 255F;
		double f7 = (float)(col2 & 0xFF) / 255F;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4d(f1, f2, f3, f);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);

		GL11.glColor4d(f5, f6, f7, f4);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		/*
		float var7 = (float)(col1 >> 24 & 255) / 255.0F;
		float var8 = (float)(col1 >> 16 & 255) / 255.0F;
		float var9 = (float)(col1 >> 8 & 255) / 255.0F;
		float var10 = (float)(col1 & 255) / 255.0F;
		float var11 = (float)(col2 >> 24 & 255) / 255.0F;
		float var12 = (float)(col2 >> 16 & 255) / 255.0F;
		float var13 = (float)(col2 >> 8 & 255) / 255.0F;
		float var14 = (float)(col2 & 255) / 255.0F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, var7);
		float z = GuiScreen.zLevel;
		var15.addVertex((double)x2, (double)y, (double)z);
		var15.addVertex((double)x, (double)y, (double)z);
		var15.setColorRGBA_F(var12, var13, var14, var11);
		var15.addVertex((double)x, (double)y2, (double)z);
		var15.addVertex((double)x2, (double)y2, (double)z);
		var15.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		*/
	}

	public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3)
	{
		double f = (float)(col1 >> 24 & 0xFF) / 255F;
		double f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		double f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		double f3 = (float)(col1 & 0xFF) / 255F;

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		x *= 2;
		y *= 2;
		x2 *= 2;
		y2 *= 2;
		GL11.glScaled(0.5, 0.5, 0.5);

		GL11.glColor4d(f1, f2, f3, f);
		GL11.glLineWidth(l1);
		
		/*
		//create buffer
		FloatBuffer vertex_data = BufferUtils.createFloatBuffer(2 * 8);
		vertex_data.put(new float[] { (float)x, (float)y });
		vertex_data.put(new float[] { (float)x, (float)y2 });
		vertex_data.put(new float[] { (float)x2, (float)y2 });
		vertex_data.put(new float[] { (float)x2, (float)y });
		
		vertex_data.put(new float[] { (float)x, (float)y });
		vertex_data.put(new float[] { (float)x2, (float)y });
		vertex_data.put(new float[] { (float)x, (float)y2 });
		vertex_data.put(new float[] { (float)x2, (float)y2 });
		vertex_data.flip();

		FloatBuffer color_data = BufferUtils.createFloatBuffer(3 * 8);
		color_data.put(new float[] { (float)f1, (float)f2, (float)f3 });
		color_data.put(new float[] { (float)f1, (float)f2, (float)f3 });
		color_data.put(new float[] { (float)f1, (float)f2, (float)f3 });
		color_data.put(new float[] { (float)f1, (float)f2, (float)f3 });
		
		color_data.put(new float[] { (float)f1, (float)f2, (float)f3 });
		color_data.put(new float[] { (float)f1, (float)f2, (float)f3 });
		color_data.put(new float[] { (float)f1, (float)f2, (float)f3 });
		color_data.put(new float[] { (float)f1, (float)f2, (float)f3 });
		color_data.flip();
		
		int vbo_vertex_handle = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertex_data, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		int vbo_color_handle = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_color_handle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, color_data, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		//create buffer
		
		//Render
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
		GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0l);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_color_handle);
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0l);

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

		GL11.glDrawArrays(GL11.GL_LINES, 0, 8);

		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		//Render
		
		//Delete VBO
		GL15.glDeleteBuffers(vbo_vertex_handle);
		GL15.glDeleteBuffers(vbo_color_handle);
		//Delete VBO
		*/
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		
		GL11.glScaled(2, 2, 2);
		GL11.glPopMatrix();

		GL11.glScaled(0.5, 0.5, 0.5);
		drawGradientRect(x, y, x2, y2, col2, col3);
		GL11.glScaled(2, 2, 2);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public static void drawCenteredStringWithShadow(String str, int x, int y, int color) {
		Client.getInstance().mc.fontRenderer.drawStringWithShadow(str, x, y, color);
	}

	public static void drawStringWithShadow(String str, int x, int y, int color) {
		Client.getInstance().mc.fontRenderer.drawStringWithShadow(str, x, y, color);
	}

	public static void drawCenteredString(String str, float x, float y, int color) {
		Client.getInstance().mc.fontRenderer.drawString(str, x, y, color);
	}
}