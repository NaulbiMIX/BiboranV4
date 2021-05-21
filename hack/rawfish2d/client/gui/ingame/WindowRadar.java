package hack.rawfish2d.client.gui.ingame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MapColor;
import net.minecraft.src.MapData;
import net.minecraft.src.MapInfo;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class WindowRadar implements IGuiElement {
	public Module mod;
	private int xPos;
	private int yPos;
	private int w;
	private int h;
	private DoubleValue zoom;
	private DoubleValue scale;
	private DoubleValue radius;
	private BufferedImage bimg;
	int[][] pixels;
	
	public int dragX;
	public int dragY;
	public int lastDragX;
	public int lastDragY;
	public boolean dragging;
	
	private int[] xywhRADAR;
	
	public MapData mapData;
	public ItemStack mapItem;
	
	public WindowRadar(Module mod, int x, int y, int w, int h, DoubleValue zoom, DoubleValue scale, DoubleValue radius) {
		this.mod = mod;
		this.xPos = x;
		this.yPos = y;
		this.w = w;
		this.h = h;
		this.zoom = zoom;
		this.scale = scale;
		this.radius = radius;
		this.bimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		//this.pixels = new int[w * h];
		this.pixels = new int[h][w];
		
		xywhRADAR = new int[] {0, 0};
		
		wr2 = new WindowRadar_2(Client.mc, xPos, yPos, w * 2, h * 2);
	}
	
	private void update() {
		xywhRADAR[0] = xPos + dragX + 1;
		xywhRADAR[1] = yPos + dragY + 1;
	}
	
	WindowRadar_2 wr2;
	
	@Override
	public void draw(int mx, int my) {
		wr2.onUpdate();
		wr2.render(mx, my);
		return;
		/*
		if(this.dragging) {
			dragX = mx - lastDragX;
			dragY = my - lastDragY;
			
			update();
		}
		
		//R2DUtils.drawBetterRect(xywhRADAR[0], xywhRADAR[1], xywhRADAR[0] + w + 1, xywhRADAR[1] + h + 1, 1, 0x88000000, 0xFFFF0000);
		
		int yoffset = xywhRADAR[1] + 2;
		int maxy = 0;
		int maxw = 120;
		
		List<String> arr = new ArrayList<String>();
		
		for(Object obj : mod.mc.theWorld.playerEntities) {
			if(obj instanceof EntityPlayer) {
				EntityPlayer ent = (EntityPlayer)obj;
				if(MiscUtils.isValidRenderTarget(ent, 255, false)) {
					double distance = mod.mc.thePlayer.getDistanceToEntity(ent);
					distance = (double) Math.round(distance * 100) / 100;
					
					String str = ent.getTranslatedEntityName() + " §2[" + distance + "]";
					arr.add(str);
					//GuiUtils.drawStringWithShadow(str, xywhRADAR[0] + 2, yoffset, 0xFFFFFFFF);
					
					int wstr = Client.getInstance().mc.fontRenderer.getStringWidth(str) + 5;
					
					if(wstr > maxw)
						maxw = wstr;
					
					yoffset += 12;
				}
			}
		}
		maxy = yoffset + 12;
		
		R2DUtils.drawBetterRect(xywhRADAR[0], xywhRADAR[1], xywhRADAR[0] + maxw, xywhRADAR[1] + 12, 1, 0xAA000000, 0xFF00FF00);
		
		GuiUtils.drawStringWithShadow("Player List", xywhRADAR[0] + 2, xywhRADAR[1] + 2, 0xFFFFFFFF);
		
		if(!arr.isEmpty()) {
			R2DUtils.drawBetterRect(xywhRADAR[0], xywhRADAR[1] + 12, xywhRADAR[0] + maxw, maxy, 1, 0x88000000, 0xFF00FF00);
			
			yoffset = xywhRADAR[1] + 2 + 12;
			for(String str : arr) {
				GuiUtils.drawStringWithShadow(str, xywhRADAR[0] + 2, yoffset, 0xFFFFFFFF);
				yoffset += 12;
			}
		}
		*/
	}

	public void makeImage() {
		Graphics2D g2d = bimg.createGraphics();

		//g2d.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		//g2d.fillRect(0, 0, w, h);
		/*
		g2d.setColor(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		g2d.fillRect(0, 0, w, h); //A transparent white background

		g2d.setColor(Color.red);
		g2d.drawRect(0, 0, w, h); //A red frame around the image
		g2d.fillRect(10, 10, 10, 10); //A red box 

		g2d.setColor(Color.blue);
		g2d.drawString("Test image", 10, 64); //Some blue text
		*/
		
		int tex = loadTexture(bimg);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D); //Enable texturing
		
		GL11.glPushMatrix();
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		//GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		
		GL11.glTranslatef(xPos + dragX + 1, yPos + dragY + 1.5f, 0);
		Tessellator tes = Tessellator.instance;
		tes.startDrawingQuads();
		tes.addVertexWithUV(0.0D, h, -90.0D, 0.0D, 1.0D);
		tes.addVertexWithUV(w, h, -90.0D, 1.0D, 1.0D);
		tes.addVertexWithUV(w, 0.0D, -90.0D, 1.0D, 0.0D);
		tes.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tes.draw();
		
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glPopMatrix();
			
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public int loadTexture(BufferedImage image){
		final int BYTES_PER_PIXEL = 4;

		//System.out.println("1. " + pixels[0]);
		//image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		//System.out.println("2. " + pixels[1]);

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

		for(int y = 0; y < w; y++){
			for(int x = 0; x < h; x++){
				//int pixel = pixels[y * image.getWidth() + x];
				int pixel = pixels[y][x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));               // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
			}
		}

		buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using 
		// whatever OpenGL method you want, for example:

		int textureID = GL11.glGenTextures(); //Generate texture ID
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID); //Bind texture ID

		//Setup wrap mode
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		//Setup texture scaling filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		//Send texel data to OpenGL
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		//Return the texture ID so we can bind it later again
		return textureID;
	}
	
	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public int getW() {
		return 0;
	}

	@Override
	public int getH() {
		return 0;
	}

	@Override
	public String text() {
		return null;
	}

	@Override
	public int getTextWidth() {
		return 0;
	}

	@Override
	public void mouseClicked(int mx, int my, int button) {
		if(R2DUtils.isMouseOver(xywhRADAR[0], xywhRADAR[1], xywhRADAR[0] + w, xywhRADAR[1] + h, mx, my) && button == 0) {
			dragging = true;
			lastDragX = mx - dragX;
			lastDragY = my - dragY;
		}
	}

	@Override
	public void mouseMovedOrUp(int x, int y, int button) {
		if(button == 0) {
			dragging = false;
		}
	}

	@Override
	public void keyTyped(char ch, int ivar) {
	}

	@Override
	public void mouseOver(int mx, int my) {
	}

	@Override
	public boolean isPinned() {
		return mod.isToggled();
	}
}
