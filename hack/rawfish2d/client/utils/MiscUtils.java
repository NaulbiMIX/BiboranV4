package hack.rawfish2d.client.utils;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagInt;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Vec3;

public class MiscUtils {
	private static int state;
	private static float r;
	private static float g;
	private static float b;
	
	public static void facePlayer(EntityPlayer ent, boolean fake) {
		if(ent == null)
			return;
		
		Vector2f angles = getFaceAngles(ent);
		
		setAngles(angles.x, angles.y, fake);
	}
	
	public static void setAngles(float pitch, float yaw, boolean fake) {
		Vector2f angles = new Vector2f(pitch, yaw);
		
		if(fake) {
			Client.getInstance().getRotationUtils().setPitch(angles.x);
			Client.getInstance().getRotationUtils().setYaw(angles.y);
		} else {
			Client.mc.thePlayer.rotationPitch = angles.x;
			Client.mc.thePlayer.rotationYaw = angles.y;
		}
	}
	
	public static void faceBlock(double blockx, double blocky, double blockz, boolean fake) {
		Vector2f angles = getBlockAngles(blockx, blocky, blockz, Client.mc.thePlayer.posX, Client.mc.thePlayer.posY, Client.mc.thePlayer.posZ);
		
		if(fake) {
			Client.getInstance().getRotationUtils().setPitch(angles.x);
			Client.getInstance().getRotationUtils().setYaw(angles.y);
		} else {
			Client.mc.thePlayer.rotationPitch = angles.x;
			Client.mc.thePlayer.rotationYaw = angles.y;
		}
	}
	
	public static Block getBlock(int x, int y, int z) {
		return Block.blocksList[getBlockId(x, y, z)];
	}
	
	public static int getBlockId(int x, int y, int z) {
		return Client.mc.theWorld.getBlockId(x, y, z);
	}
	
	public static Vector2f getBlockAngles(double blockx, double blocky, double blockz, double entx, double enty, double entz) {
		Vector2f out = null;
		
		double x = blockx - entx;
		double z = blockz - entz;
		double y = blocky - enty - 1f;
		
		double len = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float)Math.toDegrees(- Math.atan(x / z));
		float pitch = (float)(- Math.toDegrees(Math.atan(y / len)));
		
		if (z < 0.0 && x < 0.0) {
			yaw = (float)(90.0 + Math.toDegrees(Math.atan(z / x)));
		} else if (z < 0.0 && x > 0.0) {
			yaw = (float)(-90.0 + Math.toDegrees(Math.atan(z / x)));
		}
		//out = new Vector2D(pitch, MiscUtils.normalizeYaw(yaw + 150f));
		out = new Vector2f(pitch, MiscUtils.normalizeYaw(yaw));
		return out;
	}
	
	public static double getDistance(Vector3f pos1, Vector3f pos2) {
		double delta_x = pos1.x - pos2.x;
		double delta_y = pos1.y - pos2.y;
		double delta_z = pos1.z - pos2.z;
		return delta_x * delta_x + delta_y * delta_y + delta_z * delta_z;
	}
	
	public static Random rng = new Random();
	
	public static int random(int min, int max) {
		int max2 = (max - min) + 1;
		if(max2 < 0)
			max2 = min + 1;
		return rng.nextInt(max2) + min;
	}
	
	public static List LoreGet(ItemStack item) {
		List<String> list = new ArrayList<String>();
		
		if (item.stackTagCompound.hasKey("display")) {
			NBTTagCompound tagcomp = item.stackTagCompound.getCompoundTag("display");

			if (tagcomp.hasKey("Lore")) {
				NBTTagList taglist = tagcomp.getTagList("Lore");
				
				if (taglist.tagCount() > 0) {
					for (int index = 0; index < taglist.tagCount(); ++index) {
						list.add(((NBTTagString)taglist.tagAt(index)).data);
					}
					return list;
				}
			}
		}
		
		return null;
	}
	
	public static String LoreGetLine(ItemStack item, int line) {
		if (item.stackTagCompound.hasKey("display")) {
			NBTTagCompound tagcomp = item.stackTagCompound.getCompoundTag("display");

			if (tagcomp.hasKey("Lore")) {
				NBTTagList taglist = tagcomp.getTagList("Lore");
				
				if (taglist.tagCount() > line) {
					return ((NBTTagString)taglist.tagAt(line)).data;
				}
			}
		}
		
		return null;
	}
	
	public static void LoreCreateTag(ItemStack item) {
		if (item.stackTagCompound.hasKey("display")) {
			NBTTagCompound tagcomp = item.stackTagCompound.getCompoundTag("display");

			if (tagcomp.hasKey("Lore")) {
				
			}
			else {
				NBTTagList taglist = new NBTTagList("Lore");
				tagcomp.setTag("Lore", taglist);
			}
		}
		else {
			NBTTagCompound tagcomp = new NBTTagCompound("display");
			NBTTagList taglist = new NBTTagList("Lore");
			
			tagcomp.setTag("Lore", taglist);
			item.stackTagCompound.setCompoundTag("display", tagcomp);
		}
	}
	
	public static void LoreSet(ItemStack item, List<String> lines) {
		LoreCreateTag(item);
		
		NBTTagCompound tagcomp = item.stackTagCompound.getCompoundTag("display");
		NBTTagList taglist = tagcomp.getTagList("Lore");

		for(int a = 0; a < lines.size(); ++a) {
			NBTTagString tagstr = new NBTTagString("", lines.get(a));
			taglist.appendTag(tagstr);
		}
	}
	
	public static ItemStack makeItemColored(ItemStack item) {
		if(item == null) {
			return null;
		}
		
		//ïðîâåðêà íà êîæàíóþ áðîíþ
		if(item.itemID >= 298 && item.itemID <= 301) {
			NBTTagCompound nbt = item.stackTagCompound;
			
			if(nbt == null) {
				nbt = new NBTTagCompound("tag");
				item.setTagCompound(nbt);
			}
			
			if(nbt != null) {
				NBTTagCompound display = (NBTTagCompound) nbt.getTag("display");
				
				if(display == null) {
					display = new NBTTagCompound("display");
					//nbt.setCompoundTag("display", display);
					nbt.setTag("display", display);
				}
				
				
				if(display != null) {
					NBTTagInt color = (NBTTagInt) display.getTag("color");
					
					if(color == null) {
						color = new NBTTagInt("color", 0);
						display.setTag("color", color);
						//display.setInteger("color", 0);
					}
					
					if(color != null) {
						int rng = MiscUtils.random(0, 0xFFFFFF);
						color.data = rng;
					}
				}
			}
		}
		return item;
	}
	
	public static int[] blockCoordsToChunkCoords(double x, double z) {
		return blockCoordsToChunkCoords((int)x, (int)z);
	}
	
	public static int[] blockCoordsToChunkCoords(int x, int z) {
		int []ret = new int[2];
		ret[0] = x >> 4;
		ret[1] = z >> 4;
		return ret;
	}
	
	/*
	public static void setLoreList(ItemStack item, List<String> lines) {
		if (item.stackTagCompound.hasKey("display"))
		{
			NBTTagCompound tagcomp = item.stackTagCompound.getCompoundTag("display");

			if (tagcomp.hasKey("Lore"))
			{
				NBTTagList taglist = tagcomp.getTagList("Lore");

				for(int a = 0; a < lines.size(); ++a) {
					NBTTagString tagstr = new NBTTagString("", lines.get(a));
					taglist.appendTag(tagstr);
				}
			}
			else {
				NBTTagList taglist = new NBTTagList("Lore");
				NBTTagString nbt = new NBTTagString("", "§aAre you ahueli tam?! 2");
				
				for(int a = 0; a < lines.size(); ++a) {
					NBTTagString tagstr = new NBTTagString("", lines.get(a));
					taglist.appendTag(tagstr);
				}
				tagcomp.setTag("Lore", taglist);
			}
		}
		else {
			NBTTagCompound tagcomp = new NBTTagCompound("display");
			NBTTagList taglist = new NBTTagList("Lore");
			
			for(int a = 0; a < lines.size(); ++a) {
				NBTTagString tagstr = new NBTTagString("", lines.get(a));
				taglist.appendTag(tagstr);
			}
			
			tagcomp.setTag("Lore", taglist);
			item.stackTagCompound.setCompoundTag("display", tagcomp);
		}
	}
	*/
	public static void LoreClear(ItemStack item) {
		//LoreCreateTag(item);
		
		NBTTagCompound tagcomp = item.stackTagCompound.getCompoundTag("display");
		if(tagcomp != null)
		{
			NBTTagList taglist = new NBTTagList("Lore");
			tagcomp.setTag("Lore", taglist);
		}
	}
	
	public static void LoreSetLine(ItemStack item, int line, String str) {
		List list = LoreGet(item);
		if(list.size() > line) {
			list.set(line, str);
			LoreClear(item);
			LoreSet(item, list);
		}
	}
	
	public static void LoreAddLine(ItemStack item, String text) {
		LoreCreateTag(item);
		
		NBTTagCompound tagcomp = item.stackTagCompound.getCompoundTag("display");
		NBTTagList taglist = tagcomp.getTagList("Lore");

		NBTTagString tagstr = new NBTTagString("", text);
		taglist.appendTag(tagstr);
	}
	
	public static void LoreDelLine(ItemStack item, int line) {
		List<String> list = new ArrayList<String>();
		
		for(int a = 0; a < LoreGet(item).size(); ++a) {
			list.add((String) LoreGet(item).get(a));
		}

		if(list.size() > line) {
			list.remove(line);
			LoreClear(item);
			LoreSet(item, list);
		}
	}
	
	public static void selfKick() {
		Client.mc.thePlayer.sendQueue.addToSendQueue(new Packet7UseEntity(Client.mc.thePlayer.entityId, Client.mc.thePlayer.entityId, 0));
		Client.mc.thePlayer.sendChatMessage("§§§§§§§§§§cKEK");
		Client.mc.thePlayer.sendChatMessage("§§§§§§§§§§cKEK");
		Client.mc.thePlayer.sendChatMessage("§§§§§§§§§§cKEK");
		Client.mc.thePlayer.sendChatMessage("§§§§§§§§§§cKEK");
		Client.mc.thePlayer.sendChatMessage("§§§§§§§§§§cKEK");
		Client.mc.thePlayer.sendChatMessage("§§§§§§§§§§cKEK");
		Client.mc.thePlayer.sendChatToPlayer(null);
	}
	
	public static boolean isLoreContains(ItemStack item, String str) {
		
		return false;
	}
	
	public static Color int2Color(int color) {
		float r = (float)(color >> 16 & 255) / 255.0f;
		float g = (float)(color >> 8 & 255) / 255.0f;
		float b = (float)(color & 255) / 255.0f;
		float a = (float)(color >> 24 & 255) / 255.0f;
		
		return new Color(r, g, b, a);
	}
	
	public static org.newdawn.slick.Color int2Color2(int color) {
		float r = (float)(color >> 16 & 255) / 255.0f;
		float g = (float)(color >> 8 & 255) / 255.0f;
		float b = (float)(color & 255) / 255.0f;
		float a = (float)(color >> 24 & 255) / 255.0f;
		
		return new org.newdawn.slick.Color(r, g, b, a);
	}
	
	public static Vector4f int2Vector4f(int color) {
		float r = (float)(color >> 16 & 255) / 255.0f;
		float g = (float)(color >> 8 & 255) / 255.0f;
		float b = (float)(color & 255) / 255.0f;
		float a = (float)(color >> 24 & 255) / 255.0f;
		
		return new Vector4f(r, g, b, a);
	}
	
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {}
	}
	
	public static void sendPacket(Packet packet) {
		if(Client.mc.thePlayer != null)
			if(Client.mc.thePlayer.sendQueue != null)
				Client.mc.thePlayer.sendQueue.addToSendQueue(packet);
	}
	
	public static void sendChat(String msg) {
		if(Client.mc.thePlayer != null)
			Client.mc.thePlayer.sendChatMessage(msg);
	}
	
	public static void sendChatClient(String msg) {
		if(Client.mc.thePlayer != null)
			Client.mc.thePlayer.sendChatToPlayer(msg);
		else
			System.out.println(msg);
	}
	
	public static final int getScreenWidth() {
		return getScaledResolution().getScaledWidth();
	}
	
	public static final ScaledResolution getScaledResolution() {
		return new ScaledResolution(Client.mc.gameSettings, Client.mc.displayWidth, Client.mc.displayHeight);
	}
	
	public static final int getScreenHeight() {
		return getScaledResolution().getScaledHeight();
	}

	public static boolean isEntityVisable(Entity ent) {
		if(ent == null)
			return false;
		
		if (rayTraceAllBlocks(Client.mc.theWorld.getWorldVec3Pool().getVecFromPool(Client.mc.thePlayer.posX, Client.mc.thePlayer.posY + 0.12, Client.mc.thePlayer.posZ), Client.mc.theWorld.getWorldVec3Pool().getVecFromPool(ent.posX, ent.posY + 1.82, ent.posZ)) == null) {
			return true;
		}
		return false;
	}

	public static MovingObjectPosition rayTraceAllBlocks(Vec3 start, Vec3 end) {
		return Client.mc.theWorld.rayTraceBlocks_do_do(start, end, false, true);
	}

	public static EntityPlayer getTarget(double angle, double range, boolean legit) {
		EntityPlayer ent = null;
		double angleTemp = angle;
		float[] angles;
		double dist;
		
		for (int a = 0; a < Client.mc.theWorld.playerEntities.size(); ++a) {
			Object obj = Client.mc.theWorld.playerEntities.get(a);
			
			if (obj == null)
				continue;
			
			if (obj == Client.mc.thePlayer)
				continue;
			
			EntityPlayer entPlayer = (EntityPlayer)obj;
			
			if(entPlayer.entityId == Client.mc.thePlayer.entityId)
				continue;
			
			angles = getYawAndPitch(entPlayer);
			dist = (double)getDistanceBetweenYaw(angles[0]);
			
			if( !isValidTarget(entPlayer, dist, angleTemp, range, legit) )
				continue;
			
			ent = entPlayer;
			angleTemp = dist;
		}
		return ent;
	}
	
	public static EntityPlayer getBowAimTarget(int fov) {
		EntityPlayer target = null;
		double angleYaw = 180.0D;
		double anglePitch = 90.0D;
		Iterator it = Client.mc.theWorld.playerEntities.iterator();

		while (it.hasNext()) {
			Object obj = it.next();

			if (obj != null) {
				if (obj != Client.mc.thePlayer) {
					EntityPlayer ent = (EntityPlayer)obj;

					if( MiscUtils.isValidTarget(ent, 255, false)) {
						float[] angles = MiscUtils.getYawAndPitch(ent);
						angles[0] = MiscUtils.normalizeYaw(angles[0]);
						angles[1] = MiscUtils.normalizePitch(angles[1]);
						double distYaw = MiscUtils.getDistanceBetweenYaw(angles[0]);
						
						if (distYaw < angleYaw)
						{
							target = ent;
							angleYaw = distYaw;
						}
						
					}
				}
			}
		}
		return target;
	}
	
	public static EntityPlayer getClosestTarget(double angle, double range, boolean legit) {
		EntityPlayer ent = null;
		double rangeTemp = range;
		float[] angles;
		double dist;
		
		for (int a = 0; a < Client.mc.theWorld.playerEntities.size(); ++a) {
			Object obj = Client.mc.theWorld.playerEntities.get(a);
			
			if (obj == null)
				continue;
			
			if (obj == Client.mc.thePlayer)
				continue;
			
			EntityPlayer entPlayer = (EntityPlayer)obj;
			
			if( !isValidTarget(entPlayer, range, legit) )
				continue;
			
			if(Client.mc.thePlayer.getDistanceToEntity(entPlayer) < rangeTemp) {
				rangeTemp = Client.mc.thePlayer.getDistanceToEntity(entPlayer);
				ent = entPlayer;
			}
		}
		return ent;
	}
	
	public static EntityPlayer getRenderTarget(double angle, double range, boolean legit) {
		EntityPlayer ent = null;
		
		for (int a = 0; a < Client.mc.theWorld.playerEntities.size(); ++a) {
			Object obj = Client.mc.theWorld.playerEntities.get(a);
			
			if (obj == null)
				continue;
			
			EntityPlayer entPlayer = (EntityPlayer)obj;
			
			if (Client.mc.thePlayer.getDistanceToEntity(entPlayer) > range ||
					!PlayerUtils.isPlayer(entPlayer) ||
					Client.mc.thePlayer == entPlayer ||
					legit && entPlayer.isInvisible() ||
					!entPlayer.isEntityAlive())
				continue;
			
			ent = entPlayer;
		}
		return ent;
	}
	
	public static float normalizeYaw(float yaw) {
		while(yaw > 360.0f)
			yaw -= 360.0f;
		
		while(yaw < 0)
			yaw += 360.0f;
		
		return yaw;
	}
	
	public static float normalizePitch(float pitch) {
		while(pitch > 90.0f)
			pitch -= 90.0f;
		
		while(pitch < -90)
			pitch += 90.0f;
		
		return pitch;
	}
	
	public static Vector3f rotatePoint3D(Vector3f point, Vector3f center, float angle) {
		Vector3f out = null;
		
		if(point == null || center == null)
			return null;
		
		Vector3f temp = point.sub(center);
		
		float f = angle;
		
		float x = (float) (center.x + (point.x - center.x) * Math.cos(f) - (point.y - center.y) * Math.sin(f));
		float z = (float) (center.y + (point.y - center.y) * Math.cos(f) + (point.x - center.x) * Math.sin(f));
		
		out = new Vector3f(x, temp.y, z);
		
		return out;
	}
	
	public static Vector2f rotatePoint2D(Vector2f point, Vector2f center, float angle) {
		Vector2f out = null;
		
		if(point == null || center == null)
			return null;
		
		Vector2f temp = point.sub(center);
		
		float f = 0;
		float x = 0;
		float z = 0;
		
		f = (float) Math.toRadians(angle + 90);
		
		x = (float) (center.x + (point.x - center.x) * Math.cos(f) - (point.y - center.y) * Math.sin(f)); //OMFG ITS WORKING!
		z = (float) (center.y + (point.y - center.y) * Math.cos(f) + (point.x - center.x) * Math.sin(f));
		
		out = new Vector2f(x, z);
		
		return out;
	}
	
	public static Vector2f getFaceAngles(EntityPlayer ent) {
		Vector2f out = null;
		if(ent == null)
			return null;
		
		double x = ent.posX - Client.mc.thePlayer.posX;
		double z = ent.posZ - Client.mc.thePlayer.posZ;
		double y = ent.posY - Client.mc.thePlayer.posY + 1.4;
		double len = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float)Math.toDegrees(- Math.atan(x / z));
		float pitch = (float)(- Math.toDegrees(Math.atan(y / len)));
		
		if (z < 0.0 && x < 0.0) {
			yaw = (float)(90.0 + Math.toDegrees(Math.atan(z / x)));
		} else if (z < 0.0 && x > 0.0) {
			yaw = (float)(-90.0 + Math.toDegrees(Math.atan(z / x)));
		}
		out = new Vector2f(pitch, yaw);
		return out;
	}

	public static float[] getYawAndPitch(EntityPlayer ent) {
		if(ent == null)
			return new float[] {Client.mc.thePlayer.rotationYaw, Client.mc.thePlayer.rotationPitch};
		
		double x = ent.posX - Client.mc.thePlayer.posX;
		double z = ent.posZ - Client.mc.thePlayer.posZ;
		double y = Client.mc.thePlayer.posY + 0.12 - (ent.posY + 1.82);
		double len = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
		float pitch = (float)(Math.atan2(y, len) * 180.0 / Math.PI);
		return new float[]{yaw, pitch};
	}
	
	//same function as getYawAndPitch
	public static float[] getRotationFromPosition(double x, double y, double z) {
		double x2 = x - Client.mc.thePlayer.posX;
		double z2 = z - Client.mc.thePlayer.posZ;
		double y2 = y - Client.mc.thePlayer.posY + Client.mc.thePlayer.getEyeHeight();
		double len = MathHelper.sqrt_double(x2 * x2 + z2 * z2);
		float yaw = (float)(Math.atan2(z2, x2) * 180.0 / Math.PI) - 90.0f;
		float pitch = (float)(- Math.atan2(y2, len) * 180.0 / Math.PI);
		return new float[]{yaw, pitch};
	}

	public static float getDistanceBetweenYaw(float yaw) {
		//float dist = Math.abs(yaw - Client.mc.thePlayer.rotationYaw) % 360.0f;
		float dist = Math.abs(yaw - Client.mc.thePlayer.rotationYaw);
		dist = normalizeYaw(dist);
		
		if (dist > 180.0f)
			dist = 360.0f - dist;
		/*
		if (dist > 360.0f)
			dist -= 360.0f;
		else if(dist < 0)
			dist += 360.0f;
		*/
		return dist;
	}
	
	public static float getDistanceBetweenPitch(float pitch) {
		//float dist = Math.abs(yaw - Client.mc.thePlayer.rotationYaw) % 360.0f;
		float dist = Math.abs(pitch - Client.mc.thePlayer.rotationPitch);
		dist = normalizePitch(dist);
		
		if (dist > 90.0f)
			dist = 180 - dist;
			
		return dist;
	}
	
	public static float getBowAimYaw(Entity ent) {
		if(ent == null)
			return Client.mc.thePlayer.rotationYaw;
		
		double x = ent.posX - Client.mc.thePlayer.posX;
		double z = ent.posZ - Client.mc.thePlayer.posZ;
		return (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
	}
	
	public static float getBowAimPitch(EntityLiving ent)
	{
		double x = ent.posX - Client.mc.thePlayer.posX;
		double y = ent.posY - Client.mc.thePlayer.posY;
		double z = ent.posZ - Client.mc.thePlayer.posZ;
		double dist = -MathHelper.sqrt_double(x * x + z * z);
		double distsq = -MathHelper.sqrt_double(y * y);
		distsq = -0.5D;
		double velsq = MiscUtils.getVelocity() * MiscUtils.getVelocity();
		double velpow4 = velsq * velsq;
		double gravity = 0.044D; // 0.006F  0.044D
		
		double var1 = (velsq - MathHelper.sqrt_double(velpow4 - gravity * (gravity * dist * dist + 2.0D * distsq * velsq)));
		double var2 = gravity * dist;
		double var3 = var1 / var2;
		double neededPitch = Math.atan(var3);
		return (float)neededPitch * 100.0F;
	}
	
	public static double getVelocity()
	{
		if(Client.mc.thePlayer.getItemInUse() == null)
			return 0;
		
		int duration = Client.mc.thePlayer.getItemInUse().getMaxItemUseDuration() - Client.mc.thePlayer.getItemInUseCount();
		float vel = duration / 20.0F;
		vel = (vel * vel + vel * 2.0F) / 3.0F;

		if (vel > 1.0F) {
			vel = 1.0F;
		}

		return vel * 3.2D;
	}

	public static boolean isValidRenderTarget(Entity ent, double dist, double angle, double range, boolean legit) {
		if(ent == null)
			return false;
		
		if (Client.mc.thePlayer.getDistanceToEntity(ent) > range ||
				!PlayerUtils.isPlayer(ent) ||
				Client.mc.thePlayer == ent ||
				!ent.isEntityAlive() ||
				dist >= angle)
			return false;
		else
			return true;
	}
	
	public static boolean isValidRenderTarget(Entity ent, double range, boolean legit) {
		if(ent == null)
			return false;
		
		if (Client.mc.thePlayer.getDistanceToEntity(ent) > range ||
				!PlayerUtils.isPlayer(ent) ||
				Client.mc.thePlayer == ent ||
				!ent.isEntityAlive())
			return false;
		else
			return true;
	}
	
	public static boolean isValidTarget(Entity ent, double range, boolean legit) {
		if(ent == null)
			return false;
		
		if (Client.mc.thePlayer.getDistanceToEntity(ent) > range ||
				!PlayerUtils.isPlayer(ent) ||
				PlayerUtils.isFriend(ent) ||
				Client.mc.thePlayer == ent ||
				legit && ent.isInvisible() ||
				!ent.isEntityAlive())
			return false;
		else
			return true;
	}
	
	public static boolean isValidTarget(Entity ent, double dist, double angle, double range, boolean legit) {
		if(ent == null)
			return false;
		
		if (Client.mc.thePlayer.getDistanceToEntity(ent) > range ||
				!PlayerUtils.isPlayer(ent) ||
				PlayerUtils.isFriend(ent) ||
				Client.mc.thePlayer == ent ||
				legit && ent.isInvisible() ||
				!ent.isEntityAlive() ||
				dist >= angle)
			return false;
		else
			return true;
	}
	
	//public static float[] getRainbow() {
	public static int getRainbow() {
		if (state == 0) {
			r += 0.02;
			b -= 0.02;
			if (r >= 0.85) {
				++state;
			}
		}
		else if (state == 1) {
			g += 0.02;
			r -= 0.02;
			if (g >= 0.85) {
				++state;
			}
		}
		else {
			b += 0.02;
			g -= 0.02;
			if (b >= 0.85) {
				state = 0;
			}
		}
		if(r > 1f)
			r = 1f;
		
		if(g > 1f)
			g = 1f;
		
		if(b > 1f)
			b = 1f;
		
		if(r < 0f)
			r = 0f;
		
		if(g < 0f)
			g = 0f;
		
		if(b < 0f)
			b = 0f;
		
		return new Color(r, g, b, 1f).getRGB();
		//return new float[] { r, g, b };
	}
	
	public static void renderText(ItemStack stack, int x, int y, int color) {
		GL11.glDisable(GL11.GL_LIGHTING);
		Client.mc.fontRenderer.drawStringWithShadow(stack.getDisplayName(), x, y, color);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public static void renderEffect(FontRenderer fr, RenderEngine renderEngine, ItemStack stack, int x, int y)
	{
		if (stack != null)
		{
			if (stack.hasEffect())
			{
				GL11.glDepthFunc(GL11.GL_GREATER);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(false);
				renderEngine.bindTexture("%blur%/misc/glint.png");
				RenderItem.zLevel += 0.1F;
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
				GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
				
				//custom color
				//GL11.glColor4f(1.5F, 0.5F, 1.0F, 1.0F);
				
				RenderItem.renderGlint(x * 431278612 + y * 32178161, x - 2, y - 2, 20, 20);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glDepthMask(true);
				RenderItem.zLevel -= 0.1F;
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
			}
		}
	}
	
	public static int getStringSize(String str) {
		int size = str.length();
		int out = size;
		for(int a = 0; a < size; ++a)
		{
			if(str.charAt(a) == '§') {
				--out;
			}
		}
		return out * 2;
	}
}

