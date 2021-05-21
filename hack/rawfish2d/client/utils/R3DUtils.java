package hack.rawfish2d.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Tessellator;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderLiving;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ScaledResolution;

import java.awt.Color;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModRender.Nametags;

public class R3DUtils {
	
	static FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
	static FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);
	static IntBuffer viewPort = BufferUtils.createIntBuffer(4);
	static FloatBuffer win_pos = BufferUtils.createFloatBuffer(3);
	
	public static void GetModelMatrix()
	{
		modelBuffer.rewind();
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelBuffer);
	}
	
	public static void GetProjectionMatrix()
	{
		projectionBuffer.rewind();
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionBuffer);
	}
	
	public static Vector2f WorldToScreen(Minecraft mc, Vector3f player, Vector3f enemy) {
		ScaledResolution resolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		viewPort.rewind();
		viewPort.put(0);
		viewPort.put(0);
		viewPort.put(resolution.getScaledWidth());
		viewPort.put(resolution.getScaledHeight());
		viewPort.rewind();

		win_pos.rewind();
		modelBuffer.rewind();
		projectionBuffer.rewind();
		GLU.gluProject((float) (player.x - enemy.x), (float) (player.y - enemy.y), (float) (player.z - enemy.z), modelBuffer, projectionBuffer, viewPort, win_pos);
		
		win_pos.rewind();
		int sx = (int) win_pos.get();
		int sy = resolution.getScaledHeight() - (int) win_pos.get();
		
		if (win_pos.get() < 1) {
			return new Vector2f(-100, -100);
		} else {
			return new Vector2f(sx, sy);
		}
	}
	
	public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(3);
		tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
		tessellator.draw();
		tessellator.startDrawing(3);
		tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
		tessellator.draw();
		tessellator.startDrawing(1);
		tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
		tessellator.draw();
	}
	
	public static void drawBoundingBox(AxisAlignedBB aa)  {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
		tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
		tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
		tessellator.draw();
	}
	
	public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(red, green, blue, alpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	//public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
	public static void drawBlockESP(Vector3d pos, int color1, int color2, float lineWidth) {
		float r = (float)(color1 >> 16 & 255) / 255.0f;
		float g = (float)(color1 >> 8 & 255) / 255.0f;
		float b = (float)(color1 & 255) / 255.0f;
		float a = (float)(color1 >> 24 & 255) / 255.0f;
		
		float r2 = (float)(color2 >> 16 & 255) / 255.0f;
		float g2 = (float)(color2 >> 8 & 255) / 255.0f;
		float b2 = (float)(color2 & 255) / 255.0f;
		float a2 = (float)(color2 >> 24 & 255) / 255.0f;
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(r, g, b, a);
		AxisAlignedBB bb = new AxisAlignedBB(pos.x, pos.y, pos.z, pos.x + 1D, pos.y + 1D, pos.z + 1D);
		drawBoundingBox(bb);
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(r2, g2, b2, a2);
		drawOutlinedBoundingBox(bb);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawRegionESP(double x, double y, double z, double xx, double yy, double zz, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		
		R3DUtils.drawBoundingBox(new AxisAlignedBB(x, y, z, xx, yy, zz));
		
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
		
		R3DUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, xx, yy, zz));
		
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2) {
		Tessellator tes = Tessellator.instance;
		tes.startDrawing(GL11.GL_LINES);
		tes.addVertex(x1, y1, z1);
		tes.addVertex(x2, y2, z2);
		tes.draw();
	}
	
	public static void drawBorderedRect(Vector3d pos1, Vector3d pos2, int colorBorder, int colorFill) {
		drawFilledRect(pos1, pos2, colorFill);
		drawRect(pos1, pos2, colorBorder);
	}
	
	public static void drawRect(Vector3d pos1, Vector3d pos2, int colorBorder) {
		Vector4f c1 = MiscUtils.int2Vector4f(colorBorder);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glLineWidth(1f);
		
		GL11.glColor4f(c1.x, c1.y, c1.z, c1.w);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(pos1.x, pos1.y, pos2.z);
		GL11.glVertex3d(pos2.x, pos1.y, pos2.z);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(pos2.x, pos1.y, pos2.z);
		GL11.glVertex3d(pos2.x, pos1.y, pos1.z);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(pos2.x, pos1.y, pos1.z);
		GL11.glVertex3d(pos1.x, pos1.y, pos1.z);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(pos1.x, pos1.y, pos1.z);
		GL11.glVertex3d(pos1.x, pos1.y, pos2.z);
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void drawFilledRect(Vector3d pos1, Vector3d pos2, int color) {
		Vector4f c1 = MiscUtils.int2Vector4f(color);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glColor4f(c1.x, c1.y, c1.z, c1.w);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3d(pos1.x, pos1.y, pos2.z);
		GL11.glVertex3d(pos2.x, pos1.y, pos2.z);
		GL11.glVertex3d(pos2.x, pos1.y, pos1.z);
		GL11.glVertex3d(pos1.x, pos1.y, pos1.z);
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void drawRegionESP2(double x, double y, double z, double xx, double yy, double zz, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		
		R3DUtils.drawBoundingBox(new AxisAlignedBB(x, y, z, xx, yy, zz));
		
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
		
		R3DUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, xx, yy, zz));
		
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawSolidEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void DrawOnScreen(Vector2f ScreenSize) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0, ScreenSize.x, 0.0, ScreenSize.y, -1.0, 1.0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();

		GL11.glLoadIdentity();
		//GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glColor3f(1,1,1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//glBindTexture(GL11.GL_TEXTURE_2D, mark_textures[0].id);

		// Draw a textured quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0); GL11.glVertex3f(0, 0, 0);
		GL11.glTexCoord2f(0, 1); GL11.glVertex3f(0, 100, 0);
		GL11.glTexCoord2f(1, 1); GL11.glVertex3f(100, 100, 0);
		GL11.glTexCoord2f(1, 0); GL11.glVertex3f(100, 0, 0);
		GL11.glEnd();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public static void drawEntityESP2(double x, double y, double z, EntityPlayer ent, float red, float green, float blue, float alpha, float lineWdith) {
		float var14 = 0.016666668F * 3.0F;
		var14 = 0.052F;
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.0F, (float)(y + ent.height + 0.5F), (float)z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef((float) (-Client.getInstance().mc.thePlayer.rotationYaw), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef((float) (-Client.getInstance().mc.thePlayer.rotationPitch * Math.PI / 180), 1.0F, 0.0F, 0.0F);
		//GL11.glRotatef(Client.getInstance().mc.thePlayer.rotationPitch, 1.0F, 0.0F, 0.0F);
		
		GL11.glScalef(-var14, -var14, var14);
		
		//GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		double var17 = 12;
		double var19 = 50;
		
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(red, green, blue, alpha);
		
		GL11.glBegin(2);
		GL11.glVertex3d((-var17), (y + 7), 0.0D);
		GL11.glVertex3d((-var17), (var19 + y), 0.0D);
		GL11.glEnd();
		
		GL11.glBegin(2);
		GL11.glVertex3d((-var17), (var19 + y), 0.0D);
		GL11.glVertex3d((var17), (var19 + y), 0.0D);
		GL11.glEnd();
		
		GL11.glBegin(2);
		GL11.glVertex3d((var17), (var19 + y), 0.0D);
		GL11.glVertex3d((var17), (y + 7), 0.0D);
		GL11.glEnd();
		
		GL11.glBegin(2);
		GL11.glVertex3d((var17), (y + 7), 0.0D);
		GL11.glVertex3d((-var17), (y + 7), 0.0D);
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		//GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
	public static void drawEntityESP3(double x, double y, double z, EntityPlayer ent, float red, float green, float blue, float alpha, float lineWdith) {
		float var14 = 0.016666668F * 3.0F;
		var14 = 0.052F;
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.0F, (float)(y + ent.height + 0.5F), (float)z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef((float) (-Client.getInstance().mc.thePlayer.rotationYaw), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef((float) (-Client.getInstance().mc.thePlayer.rotationPitch * Math.PI / 180), 1.0F, 0.0F, 0.0F);
		//GL11.glRotatef(Client.getInstance().mc.thePlayer.rotationPitch, 1.0F, 0.0F, 0.0F);
		
		GL11.glScalef(-var14, -var14, var14);
		
		//GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		double var17 = 12;
		double var19 = 50;
		
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(red, green, blue, alpha);
		
		GL11.glBegin(2);
		GL11.glVertex3d((-var17), (y + 7), 0.0D);
		GL11.glVertex3d((-var17), (var19 + y), 0.0D);
		GL11.glEnd();
		
		GL11.glBegin(2);
		GL11.glVertex3d((-var17), (var19 + y), 0.0D);
		GL11.glVertex3d((var17), (var19 + y), 0.0D);
		GL11.glEnd();
		
		GL11.glBegin(2);
		GL11.glVertex3d((var17), (var19 + y), 0.0D);
		GL11.glVertex3d((var17), (y + 7), 0.0D);
		GL11.glEnd();
		
		GL11.glBegin(2);
		GL11.glVertex3d((var17), (y + 7), 0.0D);
		GL11.glVertex3d((-var17), (y + 7), 0.0D);
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		//GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
	public static void drawEntityESP4(double x, double y, double z, EntityPlayer ent, float red, float green, float blue, float alpha, float lineWdith) {
		float scale = 0.016666668F * 3.0F;
		scale = 0.052F;
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.0F, (float)(y + ent.height + 0.5F), (float)z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef((float) (-Client.getInstance().mc.thePlayer.rotationYaw), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef((float) (-Client.getInstance().mc.thePlayer.rotationPitch * Math.PI / 180), 1.0F, 0.0F, 0.0F);
		//GL11.glRotatef(Client.getInstance().mc.thePlayer.rotationPitch, 1.0F, 0.0F, 0.0F);
		
		GL11.glScalef(-scale, -scale, scale);
		
		//GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		double x2 = 12;
		double y2 = 50;
		
		double ms = System.currentTimeMillis() / 50;
		x2 -= (Math.sin(ms) * 1);
		y2 -= (Math.sin(ms) * 1);
		x -= (Math.sin(ms) * 1);
		y += (Math.sin(ms) * 1);
		
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(red, green, blue, alpha);
		
		//top left
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(-x2, (y + 7), 0.0D);
		GL11.glVertex3d(-x2 + 7, (y + 7), 0.0D);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(-x2, (y + 7), 0.0D);
		GL11.glVertex3d(-x2, (y + 14), 0.0D);
		GL11.glEnd();
		
		//top right
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(x2, (y + 7), 0.0D);
		GL11.glVertex3d(x2, (y + 14), 0.0D);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(x2 - 7, (y + 7), 0.0D);
		GL11.glVertex3d(x2, (y + 7), 0.0D);
		GL11.glEnd();
		
		//bot rigth
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(x2, y2 - 1, 0.0D);
		GL11.glVertex3d(x2, y2 - 8, 0.0D);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(x2 - 7, y2 - 1, 0.0D);
		GL11.glVertex3d(x2, y2 - 1, 0.0D);
		GL11.glEnd();
		
		//bot left
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(-x2, y2 - 1, 0.0D);
		GL11.glVertex3d(-x2, y2 - 8, 0.0D);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(-x2 + 7, y2 - 1, 0.0D);
		GL11.glVertex3d(-x2, y2 - 1, 0.0D);
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		//GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
	public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(2);
		// ULTRA KEK
		//GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0D);
		GL11.glVertex3d(0.0D, 0.0D, 0.0D);
		GL11.glVertex3d(x, y, z);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	public static void drawLine(double x, double y, double z, double x1, double y1, double z1, int color, float lineWdith) {
		float r = (float)(color >> 16 & 255) / 255.0f;
		float g = (float)(color >> 8 & 255) / 255.0f;
		float b = (float)(color & 255) / 255.0f;
		float a = (float)(color >> 24 & 255) / 255.0f;
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		//GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(r, g, b, a);
		GL11.glBegin(2);
		GL11.glVertex3d(x, y, z);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	public static void enableGL3D(float lineWidth) {
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_CULL_FACE);
		Client.getInstance().mc.entityRenderer.disableLightmap(0);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glLineWidth(lineWidth);
	}

	public static void disableGL3D() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(true);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
		GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
	}
}
