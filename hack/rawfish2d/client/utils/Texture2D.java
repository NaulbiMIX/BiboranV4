package hack.rawfish2d.client.utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;


public class Texture2D {
	private int texID;
	int rotation = 0;
	int rotations = 1;
	public Texture2D(String texture) {
		texID = Minecraft.getMinecraft().renderEngine.getTexture(texture);
	}
	
	public void renderTexture(double x, double y, double width, double height, Color c) {
		GL11.glColor4f((float)c.getRed() / 255F, (float)c.getGreen() / 255F, (float)c.getBlue() / 255F, (float)c.getAlpha() / 255f);
		renderTexture(texID, x, y, width, height);
	}
	public void rotationTexutere(double x, double y, double width, double height, Color c){
		GL11.glColor4f((float)c.getRed() / 255F, (float)c.getGreen() / 255F, (float)c.getBlue() / 255F, (float)c.getAlpha() / 255f);
		
		RenderTextureRotation(texID, x, y, width, height);

	}
	
	public void RenderTextureRotation(int texID, double x, double y, double width, double height){
		boolean tex2D = GL11.glGetBoolean(GL11.GL_TEXTURE_2D);
		boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		x *= 2;
		y *= 2;
		width *= 2;
		height *= 2;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2d(x + width, y);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2d(x, y);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2d(x, y + height);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2d(x , y + height);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2d(x + width, y + height);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2d(x + width, y);
		
		GL11.glEnd();
		
		if(!tex2D)
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		if(!blend)
			GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		
	}
	
	public int getTexID() {
		return texID;
	}

	public void setTextue(String texture) {
		texID = Minecraft.getMinecraft().renderEngine.getTexture(texture);
	}
	
	public static void renderTexture(int texID, double x, double y, double width, double height) {
		boolean tex2D = GL11.glGetBoolean(GL11.GL_TEXTURE_2D);
		boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		x *= 2;
		y *= 2;
		width *= 2;
		height *= 2;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2d(x + width, y);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2d(x, y);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2d(x, y + height);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2d(x , y + height);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2d(x + width, y + height);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2d(x + width, y);
		GL11.glEnd();
		if(!tex2D)
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		if(!blend)
			GL11.glDisable(GL11.GL_BLEND);

		GL11.glPopMatrix();
	}
	
}
