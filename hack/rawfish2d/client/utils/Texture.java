package hack.rawfish2d.client.utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Tessellator;

public class Texture {
	private int texID;
	int rotation;
	int rotations;

	public Texture(String texture) {
		this.rotation = 0;
		this.rotations = 1;
		this.texID = Client.getInstance().mc.renderEngine.getTexture(texture);
	}
	
	public static void bindTexture(String location, int par1, int par2)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		Client.getInstance().mc.renderEngine.bindTexture(location);
		
		Tessellator tes = Tessellator.instance;
		tes.startDrawingQuads();
		tes.addVertexWithUV(0.0D, par2, -90.0D, 0.0D, 1.0D);
		tes.addVertexWithUV(par1, par2, -90.0D, 1.0D, 1.0D);
		tes.addVertexWithUV(par1, 0.0D, -90.0D, 1.0D, 0.0D);
		tes.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tes.draw();
		
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public static void bindTexture(String location, int par1, int par2, float alpha)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		Client.getInstance().mc.renderEngine.bindTexture(location);
		
		Tessellator tes = Tessellator.instance;
		tes.startDrawingQuads();
		tes.addVertexWithUV(0.0D, par2, -90.0D, 0.0D, 1.0D);
		tes.addVertexWithUV(par1, par2, -90.0D, 1.0D, 1.0D);
		tes.addVertexWithUV(par1, 0.0D, -90.0D, 1.0D, 0.0D);
		tes.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tes.draw();
		
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void renderTexture(double x2, double y2, double width, double height, Color c2) {
		GL11.glColor4f((float)c2.getRed() / 255.0f, (float)c2.getGreen() / 255.0f, (float)c2.getBlue() / 255.0f, (float)c2.getAlpha() / 255.0f);
		Texture.renderTexture(this.texID, x2, y2, width, height);
	}

	public void rotationTexutere(double x2, double y2, double width, double height, Color c2) {
		GL11.glColor4f((float)c2.getRed() / 255.0f, (float)c2.getGreen() / 255.0f, (float)c2.getBlue() / 255.0f, (float)c2.getAlpha() / 255.0f);
		this.RenderTextureRotation(this.texID, x2, y2, width, height);
	}

	public void RenderTextureRotation(int texID, double x2, double y2, double width, double height) {
		boolean tex2D = GL11.glGetBoolean(3553);
		boolean blend = GL11.glGetBoolean(3042);
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glEnable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		GL11.glBindTexture(3553, texID);
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
			GL11.glDisable(3553);
		}
		if (!blend) {
			GL11.glDisable(3042);
		}
		GL11.glPopMatrix();
	}

	public int getTexID() {
		return this.texID;
	}

	public void setTextue(String texture) {
		this.texID = Client.getInstance().mc.renderEngine.getTexture(texture);
	}

	public static void renderTexture(int texID, double x2, double y2, double width, double height) {
		boolean tex2D = GL11.glGetBoolean(3553);
		boolean blend = GL11.glGetBoolean(3042);
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glEnable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		GL11.glBindTexture(3553, texID);
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
			GL11.glDisable(3553);
		}
		if (!blend) {
			GL11.glDisable(3042);
		}
		GL11.glPopMatrix();
	}
}
