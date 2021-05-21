package hack.rawfish2d.client.ModRender;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.Notification;
import hack.rawfish2d.client.utils.ConfigManager;
import hack.rawfish2d.client.utils.GuiUtils;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.PlayerUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.TestUtils;
import hack.rawfish2d.client.utils.Vector4f;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ContainerChest;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.Potion;
import net.minecraft.src.RenderManager;

public class RegionRender extends Module{
	
	public static double x1;
	public static double y1;
	public static double z1;
	
	public static double x2;
	public static double y2;
	public static double z2;
	
	private long prevMs;
	private static boolean showedOnce = false;
	
	public RegionRender() {
		super("RegionRender", 0, ModuleType.RENDER);
		setDescription("Показывает границы выделенного региона");
		sel();
		toggled = true;
	}
	
	//лети наxуй в помойку зае бал уже бл9ть про енотов писать, завались наxуй!
	///tempmute miky2 30m флуд+капс+мат
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void preMotionUpdate() {
		
	}
	
	@Override
	public void onUpdate() {
		
	}
	
	@Override
	public void postMotionUpdate() {

	}
	
	@Override
	public void onRenderHand() {
		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}
	
	@Override
	public void onRenderOverlay() {
		
		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		int []colors = new int[6];
		
		colors[0] = 0xFF0000;
		colors[1] = 0xFFFF00;
		colors[2] = 0x00FF00;
		colors[3] = 0x00FFFF;
		colors[4] = 0x0000FF;
		colors[5] = 0xFF00FF;
		
		try {
			//GL11.glBegin(GL11.GL_QUADS);
			//GL11.glPushMatrix();
			//TestUtils.dvar1 = 0;
			//TestUtils.dvar2 = 0;
			//TestUtils.dvar3 = 0;
			
			TestUtils.dvar3 += 180.1f;

			double xxx = TestUtils.dvar1++;
			double yyy = TestUtils.dvar2++;
			float ang = (float)TestUtils.dvar3;
			
			//GL11.glTranslated(xxx, yyy, 0);
		
			//GL11.glScaled(2, 0.5, 1);
			
			//GL11.glTranslated(320, 240, 0);
			//GL11.glScaled(0.5, 0.5, 1);
			//GL11.glRotatef(ang, 0, 0, 1f);
			
			//GL11.glTranslated(-300, -240, 0);
			//GL11.glPopMatrix();
			
			//this
			//drawBlendRectangle(30, 30, 20, 100, colors);
			//GuiUtils.drawStringWithShadow("RegionRenderer:", 30, 20, 0xFFFF0000);
		}
		catch(Exception ex) {ex.printStackTrace();}
		
	}
	
	public static void drawBlendRectangle(double x, double y, double x1, double y1, int []colors) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glBegin(GL11.GL_QUADS);
		for (int i = 0; i < colors.length - 1; i++) {
			Vector4f c = MiscUtils.int2Vector4f(colors[i]);
			//double height = (y1 - y) / colors.length;
			
			GL11.glColor4d(c.x, c.y, c.z, 1f);
			GL11.glVertex3d(x + (i * x1), y, 0); // top-left
			GL11.glVertex3d(x + (i * x1), y + y1, 0); // top-right
			
			c = MiscUtils.int2Vector4f(colors[i + 1]);
			GL11.glColor4d(c.x, c.y, c.z, 1f);
			GL11.glVertex3d(x + (i * x1) + x1, y + y1, 0); // bottom-right
			GL11.glVertex3d(x + (i * x1) + x1, y, 0); // bottom-left
		}
		GL11.glEnd();
		
		double size = colors.length;
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4d(1f, 1f, 1f, 0f);
		GL11.glVertex3d(x, y, 1); // top-left
		
		GL11.glColor4d(0f, 0f, 0f, 1f);
		GL11.glVertex3d(x, y + y1, 1); // top-right
		
		GL11.glColor4d(0f, 0f, 0f, 1f);
		GL11.glVertex3d(x + ((size - 1) * x1), y + y1, 1);
		
		GL11.glColor4d(1f, 1f, 1f, 0f);
		GL11.glVertex3d(x + ((size - 1) * x1), y, 1);
		GL11.glEnd();
		
		//R2DUtils.drawBetterRect(x, x + (colors.length * x1) + x1, y, y + y1, 0f, 0, 0);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}
	
	@Override
	public void onRender() {
		//render region box
		
		int []colors = new int[6];
		
		colors[0] = 0xFF0000;
		colors[1] = 0xFFFF00;
		colors[2] = 0x00FF00;
		colors[3] = 0x00FFFF;
		colors[4] = 0x0000FF;
		colors[5] = 0xFF00FF;
		
		try {
			
			//double xxx = TestUtils.dvar1++;
			//double yyy = TestUtils.dvar2++;
			//float ang = (float)TestUtils.dvar3;
			//GL11.glTranslated(300, 240, 0);
			//GL11.glRotatef(ang, 0, 0, 1);
			//GL11.glTranslated(-300, -240, 0);
			//GL11.glEnable(GL11.GL_DEPTH_TEST);
			
			
			//GL11.glBegin(GL11.GL_QUADS);
			
			//GL11.glPushMatrix();
			
			//GL11.glTranslated(0, 0, 0);
			//GL11.glRotatef(33, 1, 1, 1);
			//GL11.glScaled(1.1, 2.1, 3.1);
			
			//GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			
			//GL11.glPopMatrix();
			//drawBlendRectangle(30, 30, 40, 200, colors);
		}
		catch(Exception ex) {ex.printStackTrace();}
		
		drawGrid();
	}
	
	public void drawGrid() {
		if(y1 != -1 && y2 != -1) {
			double x = x1 - RenderManager.instance.viewerPosX;
			double y = y1 - RenderManager.instance.viewerPosY;
			double z = z1 - RenderManager.instance.viewerPosZ;
			
			double xx = x2 - RenderManager.instance.viewerPosX;
			double yy = y2 - RenderManager.instance.viewerPosY;
			double zz = z2 - RenderManager.instance.viewerPosZ;
			
			//R3DUtils.drawBlockESP(x, y, z, 0.5f, 0, 0, 0.2f, 0, 1.0f, 0, 1.0f, 2);
			//R3DUtils.drawBlockESP(xx, yy, zz, 0, 0, 0.5f, 0.2f, 0, 1.0f, 0, 1.0f, 2);
			
			if(y < yy)
				yy += 1D;
			else if(yy < y)
				y += 1D;
			else if(y == yy)
				y += 1D;
			
			if(x < xx)
				xx += 1D;
			else if(xx < x)
				x += 1D;
			else if(x == xx)
				x += 1D;
			
			if(z < zz)
				zz += 1D;
			else if(zz < z)
				z += 1D;
			else if(z == zz)
				z += 1D;
			
			R3DUtils.drawRegionESP(x, y, z, xx, yy, zz, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 2);
			
			int color = 0x880000FF;
			float lw = 1f;
			
			//horizontal side lines
			if(y < yy) {
				for(double ay = y + 1; ay < yy; ++ay) {
					R3DUtils.drawLine(x, ay, z, xx, ay, z, color, lw);
					R3DUtils.drawLine(x, ay, z, x, ay, zz, color, lw);
					R3DUtils.drawLine(xx, ay, z, xx, ay, zz, color, lw);
					R3DUtils.drawLine(xx, ay, zz, x, ay, zz, color, lw);
				}
			}
			else {
				for(double ay = yy + 1; ay < y; ++ay) {
					R3DUtils.drawLine(x, ay, z, xx, ay, z, color, lw);
					R3DUtils.drawLine(x, ay, z, x, ay, zz, color, lw);
					R3DUtils.drawLine(xx, ay, z, xx, ay, zz, color, lw);
					R3DUtils.drawLine(xx, ay, zz, x, ay, zz, color, lw);
				}
			}
			
			//vertical lines on X side and Z lines on top/bot
			if(x < xx) {
				for(double ax = x + 1; ax < xx; ++ax) {
					R3DUtils.drawLine(ax, y, z, ax, yy, z, color, lw);
					R3DUtils.drawLine(ax, y, zz, ax, yy, zz, color, lw);
				}
				for(double ax = x + 1; ax < xx; ++ax) {
					R3DUtils.drawLine(ax, y, z, ax, y, zz, color, lw);
					R3DUtils.drawLine(ax, yy, z, ax, yy, zz, color, lw);
				}
			}
			else {
				for(double ax = xx + 1; ax < x; ++ax) {
					R3DUtils.drawLine(ax, y, z, ax, yy, z, color, lw);
					R3DUtils.drawLine(ax, y, zz, ax, yy, zz, color, lw);
				}
				for(double ax = xx + 1; ax < x; ++ax) {
					R3DUtils.drawLine(ax, y, z, ax, y, zz, color, lw);
					R3DUtils.drawLine(ax, yy, z, ax, yy, zz, color, lw);
				}
			}
			
			//vertical lines on Z side and X lines on top/bot
			if(z < zz) {
				for(double az = z + 1; az < zz; ++az) {
					R3DUtils.drawLine(x, y, az, x, yy, az, color, lw);
					R3DUtils.drawLine(xx, y, az, xx, yy, az, color, lw);
				}
				for(double az = z + 1; az < zz; ++az) {
					R3DUtils.drawLine(x, y, az, xx, y, az, color, lw);
					R3DUtils.drawLine(x, yy, az, xx, yy, az, color, lw);
				}
			}
			else {
				for(double az = zz + 1; az < z; ++az) {
					R3DUtils.drawLine(x, y, az, x, yy, az, color, lw);
					R3DUtils.drawLine(xx, y, az, xx, yy, az, color, lw);
				}
				for(double az = zz + 1; az < z; ++az) {
					R3DUtils.drawLine(x, y, az, xx, y, az, color, lw);
					R3DUtils.drawLine(x, yy, az, xx, yy, az, color, lw);
				}
			}
		}
	}
	
	public static boolean checkDist(double a, double b, double c, double dist) {
		double dx = a + RenderManager.instance.renderPosX;
		double dy = b + RenderManager.instance.renderPosY;
		double dz = c + RenderManager.instance.renderPosZ;
		
		double dist2 = mc.thePlayer.getDistance(dx, dy, dz);
		if(dist2 > dist)
			return true;
		else
			return false;
	}
	
	public static void sel() {
		x1 = -1;
		y1 = -1;
		z1 = -1;
		
		x2 = -1;
		y2 = -1;
		z2 = -1;
	}
	
	@Override
	public void onChatMessage(String str) {
		/*
		§dПервая точка указана (2301.0, 67.0, -2102.0).
		§dВторая точка указана (2301.0, 67.0, -2102.0) (1).
		*/
		
		// /rg claim wsp6 RawFish2D vlad289zip1 ddos1337 ilyapods
		
		if(str == null)
			return;
		
		if (str.startsWith("§dПервая точка указана (")) {
			int index = str.indexOf(")");
			String []split = str.substring(29, index).split(", ");
			
			int n = 0;
			int n2 = 0;
			
			n = str.indexOf("(");
			n2 = str.indexOf(", ");
			if(n > 0 && n2 > 0)
				x1 = Double.parseDouble( str.substring(n + 1, n2) );
			
			n = str.indexOf(", ");
			n2 = str.lastIndexOf(", ");
			if(n > 0 && n2 > 0)
				y1 = Double.parseDouble( str.substring(n + 2, n2) );
			
			n = str.lastIndexOf(", ");
			n2 = str.indexOf(")");
			if(n > 0 && n2 > 0)
				z1 = Double.parseDouble( str.substring(n + 2, n2) );
			
			if(showedOnce == false) {
				Notification notif = new Notification("3", "§l§nУведомление", "Чтобы выключить сетку напишите команду '.sel'");
				Client.getInstance().getGUI().notifications.add(notif);
				showedOnce = true;
			}
		}
		
		if (str.startsWith("§dВторая точка указана (")) {
			/*
			int index = str.indexOf(")");
			String []split = str.substring(29, index).split(", ");
			
			if (mc.objectMouseOver != null && !(mc.objectMouseOver.entityHit instanceof Entity)) {
				x2 = mc.objectMouseOver.blockX;
				y2 = mc.objectMouseOver.blockY;
				z2 = mc.objectMouseOver.blockZ;
			}
			*/
			int index = str.indexOf(")");
			String []split = str.substring(29, index).split(", ");
			
			int n = 0;
			int n2 = 0;
			
			n = str.indexOf("(");
			n2 = str.indexOf(", ");
			if(n > 0 && n2 > 0)
				x2 = Double.parseDouble( str.substring(n + 1, n2) );
			
			n = str.indexOf(", ");
			n2 = str.lastIndexOf(", ");
			if(n > 0 && n2 > 0)
				y2 = Double.parseDouble( str.substring(n + 2, n2) );
			
			n = str.lastIndexOf(", ");
			n2 = str.indexOf(")");
			if(n > 0 && n2 > 0)
				z2 = Double.parseDouble( str.substring(n + 2, n2) );
			
			if(showedOnce == false) {
				Notification notif = new Notification("3", "§l§nУведомление", "Чтобы выключить сетку напишите команду '.sel'");
				Client.getInstance().getGUI().notifications.add(notif);
				showedOnce = true;
			}
		}
		
		//§dКоординаты: (362,14,-24) (382,114,-4)
		
		if (str.startsWith("§dКоординаты: (")) {
			int n1 = str.indexOf("(");
			int n2 = str.indexOf(")");
			
			int n3 = str.lastIndexOf("(");
			int n4 = str.lastIndexOf(")");
			
			String str1 = str.substring(n1 + 1, n2);
			String str2 = str.substring(n3 + 1, n4);
			
			System.out.println("str1 : " + str1);
			System.out.println("str2 : " + str2);
			
			String []xyz1 = str1.split(",");
			String []xyz2 = str2.split(",");
			
			x1 = Double.parseDouble(xyz1[0]);
			y1 = Double.parseDouble(xyz1[1]);
			z1 = Double.parseDouble(xyz1[2]);
			
			x2 = Double.parseDouble(xyz2[0]);
			y2 = Double.parseDouble(xyz2[1]);
			z2 = Double.parseDouble(xyz2[2]);
			
			if(showedOnce == false) {
				Notification notif = new Notification("3", "§l§nУведомление", "Чтобы выключить сетку напишите команду '.sel'");
				Client.getInstance().getGUI().notifications.add(notif);
				showedOnce = true;
			}
		}
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		
	}
	
	public static void static_sub1() {
		
	}
	
	public static void static_sub2() {
		
	}
	
	public static void sub1() {
			
	}
	
	public static void sub2() {
		
	}
}
