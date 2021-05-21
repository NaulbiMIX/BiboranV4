package hack.rawfish2d.client.ModTest;

import java.util.List;

import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.PathFinder.PathScanner;
import hack.rawfish2d.client.PathFinder.Point;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.TestUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import hack.rawfish2d.client.utils.Vector3d;
import hack.rawfish2d.client.utils.Vector3i;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityFishHook;
import net.minecraft.src.ItemEditableBook;
import net.minecraft.src.ItemFishingRod;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.PathEntity;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Vec3;

public class AutoPath2 extends Module {
	public static Module instance = null;
	public static PathScanner scanner;
	public static int delay = 0;
	private BoolValue autoWalk;
	private BoolValue recalcPath;
	public static int x;
	public static int y;
	public static int z;
	public static Thread th;
	public double anim = 0;
	public int index = 0;
	
	public AutoPath2() {
		super("AutoPath2", 0, ModuleType.TEST);
		setDescription("Автоматически идёт на заданную позицию (.autopath help)");
		instance = this;
		autoWalk = new BoolValue(false);
		recalcPath = new BoolValue(false);
		
		this.elements.add(new CheckBox(this, "Auto walk", autoWalk, 0, 0));
		this.elements.add(new CheckBox(this, "Recalculate path", recalcPath, 0, 10));
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEnable() {
		//this.pathSearchRange, this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim
		index = 0;
		/*
		try {
			scanner = new PathScanner(
					new Vector3i((int)mc.thePlayer.posX, (int)mc.thePlayer.posY, (int)mc.thePlayer.posZ), //start point
					new Vector3i(x, y + 1, z), //final point
					mc.theWorld, //world instance
					512, //max distance
					-3, //max fall distance
					1); //step height
			
			MiscUtils.sendChatClient("Scanning...");
			scanner.scan();
			MiscUtils.sendChatClient("Done!");
		}
		catch(Exception ex) {ex.printStackTrace();}
		*/
		
		th = new Thread(new Runnable() {
			public void run() {
				TimeHelper time = new TimeHelper();
				//while(toggled) {
					
					try {
						scanner = new PathScanner(
								new Vector3i((int)mc.thePlayer.posX, (int)mc.thePlayer.posY, (int)mc.thePlayer.posZ), //start point
								new Vector3i(x, y + 1, z), //final point
								mc.theWorld, //world instance
								512, //max distance
								-3, //max fall distance
								1); //step height
						
						MiscUtils.sendChatClient("Scanning...");
						scanner.scan();
						MiscUtils.sendChatClient("Done!");
					}
					catch(Exception ex) {ex.printStackTrace();}
				//}
			}
		});
		th.start();
	}
	
	@Override
	public void preMotionUpdate() {
		
	}
	
	@Override
	public void postMotionUpdate() {
		
	}
	
	private boolean shouldDoPath() {
		if(scanner == null)
			return false;
		
		if(!scanner.done)
			return false;
		
		return true;
	}
	
	@Override
	public void onUpdate() {
		if(!shouldDoPath())
			return;
		
		try {
			Point point = scanner.path.get(index);
			if(autoWalk.getValue()) {
				if(point != null) {
					//MiscUtils.sendChatClient("point:" + point + " pos: " + point.pos.x + " " + point.pos.y + " " + point.pos.z);
					double offset = 0.5d;
					Vector2f angles = MiscUtils.getBlockAngles(point.pos.x + offset, point.pos.y + 2.5d, point.pos.z + offset, mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
					
					MiscUtils.setAngles(angles.x, angles.y, true);
					
					double yaw = angles.y;
					double cos = Math.cos(Math.toRadians(yaw + 90.0f));
					double sin = Math.sin(Math.toRadians(yaw + 90.0f));
					float speed = 4.5f;
					/*
					if(!mc.thePlayer.onGround) {
						speed = 7f;
					}
					*/
					float offx = (float) (cos / speed);
					float offz = (float) (sin / speed);
					
					mc.thePlayer.motionX = offx;
					mc.thePlayer.motionZ = offz;
					
					if(mc.thePlayer.posY < point.pos.y + 1) {
						mc.gameSettings.keyBindJump.pressed = true;
					}
					else {
						mc.gameSettings.keyBindJump.pressed = false;
					}
				}
			}
			
			if(point != null) {
				List list = mc.theWorld.getEntitiesWithinAABB(mc.thePlayer.getClass(), new AxisAlignedBB(point.pos.x - 0.5d, point.pos.y, point.pos.z - 0.5d, point.pos.x - 0.5d + 1d, point.pos.y + 1d, point.pos.z - 0.5d + 1d));
				if(list.size() != 0) {
					for(Object obj : list) {
						if(obj instanceof EntityClientPlayerMP) {
							if(index < scanner.path.size() - 1)
								index++;
							else {
								toggle();
								mc.gameSettings.keyBindJump.pressed = false;
								
								MiscUtils.sendChatClient("Готово!");
							}
						}
					}
				}
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
	}
	
	@Override
	public void onRender() {
		/*
		if(!shouldDoPath())
			return;
		*/
		try {
			List<Point> list = scanner.path;
			for(int a = list.size() - 1; a > 0; --a) {
				Point p1 = list.get(a);
				Point p2 = list.get(a - 1);
				
				if(p1 != null && p2 != null) {
					drawVec(p1.pos, p2.pos, 0xFFFF0000);
				}
			}
			
			Point end = list.get(scanner.path.size() - 1);
			if(end != null) {
				double renderX = end.pos.x - RenderManager.renderPosX;
				double renderY = end.pos.y - RenderManager.renderPosY;
				double renderZ = end.pos.z - RenderManager.renderPosZ;
				
				double offY = Math.sin(anim);
				anim += 3.11d;
				
				if(offY < 0)
					offY = -offY;
				
				double offset = 0.0d;
				
				R3DUtils.drawBlockESP(new Vector3d(renderX - offset, renderY + offY, renderZ - offset), 0x00000000, 0xFFFF00FF, 3f);
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
	}
	
	public void drawVec(Vector3i vec, Vector3i vec2, int color) {
		try {
			if(vec != null) {
				double renderX = vec.x - RenderManager.renderPosX;
				double renderY = vec.y - RenderManager.renderPosY;
				double renderZ = vec.z - RenderManager.renderPosZ;
				
				double renderX2 = vec2.x - RenderManager.renderPosX;
				double renderY2 = vec2.y - RenderManager.renderPosY;
				double renderZ2 = vec2.z - RenderManager.renderPosZ;
				
				double offset = 0.5d;
				
				R3DUtils.drawLine(renderX + offset, renderY + 0.05d, renderZ + offset, renderX2 + offset, renderY2 + 0.05d, renderZ2 + offset, color, 3);
				
				double size = 0.35d;
				
				R3DUtils.drawBorderedRect(new Vector3d(renderX - size + offset, renderY + 0.01d, renderZ - size + offset), new Vector3d(renderX + size + offset, renderY + 0.01d, renderZ + size + offset), 0xFFFF0000, 0xAA00FF00);
			}
		}
		catch(Exception ex) {}
	}
	
	@Override
	public void onRenderHand() {
		
	}
	
	@Override
	public void onRenderOverlay() {
		
	}
}
