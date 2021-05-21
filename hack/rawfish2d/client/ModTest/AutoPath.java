package hack.rawfish2d.client.ModTest;

import java.util.List;

import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.CheckBox;
import hack.rawfish2d.client.utils.BoolValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.TestUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import hack.rawfish2d.client.utils.Vector3d;
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

public class AutoPath extends Module {
	public static Module instance = null;
	public static PathEntity path;
	public static int delay = 0;
	private BoolValue autoWalk;
	private BoolValue recalcPath;
	public static int x;
	public static int y;
	public static int z;
	public static Thread th;
	public double anim = 0;
	
	public AutoPath() {
		super("AutoPath", 0, ModuleType.TEST);
		setDescription("Автоматически идёт на заданную позицию (.autopath help)");
		instance = this;
		autoWalk = new BoolValue(false);
		recalcPath = new BoolValue(false);
		
		this.elements.add(new CheckBox(this, "Auto walk", autoWalk, 0, 0));
		this.elements.add(new CheckBox(this, "Recalculate path", recalcPath, 0, 10));
	}
	
	@Override
	public void onDisable() {
		path = null;
	}
	
	@Override
	public void onEnable() {
		//this.pathSearchRange, this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim
		
		if(th != null) {
			th.interrupt();
			th.stop();
			th = null;
		}
		
		if(recalcPath.getValue() && th == null) {
			th = new Thread(new Runnable() {
				public void run() {
					TimeHelper time = new TimeHelper();
					while(toggled) {
						
						if(time.hasReached(250L)) {
							path = mc.theWorld.getEntityPathToXYZ(mc.thePlayer, x, y, z, 256, true, false, true, true);
							time.reset();
						}
						MiscUtils.sleep(100L);
					}
				}
			});
			th.start();
		}
		else {
			th = new Thread(new Runnable() {
				public void run() {
					path = mc.theWorld.getEntityPathToXYZ(mc.thePlayer, x, y, z, 128, true, false, true, true);
				}
			});
			th.start();
		}
	}
	
	@Override
	public void preMotionUpdate() {
		
	}
	
	@Override
	public void postMotionUpdate() {
		
	}
	
	@Override
	public void onUpdate() {
		if(path == null)
			return;
		
		Vec3 vec = path.getPosition(mc.thePlayer);
		if(autoWalk.getValue()) {
			if(vec != null) {
				
				Vector2f angles = MiscUtils.getBlockAngles(vec.xCoord, vec.yCoord + 2.5d, vec.zCoord, mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
				
				MiscUtils.setAngles(angles.x, angles.y, true);
				
				double yaw = angles.y;
				double cos = Math.cos(Math.toRadians(yaw + 90.0f));
				double sin = Math.sin(Math.toRadians(yaw + 90.0f));
				float speed = 4.5f;
				
				if(!mc.thePlayer.onGround) {
					speed = 7f;
				}
				
				float offx = (float) (cos / speed);
				float offz = (float) (sin / speed);
				
				mc.thePlayer.motionX = offx;
				mc.thePlayer.motionZ = offz;
				
				if(mc.thePlayer.posY < vec.yCoord + 1) {
					mc.gameSettings.keyBindJump.pressed = true;
				}
				else {
					mc.gameSettings.keyBindJump.pressed = false;
				}
			}
		}
		
		List list = mc.theWorld.getEntitiesWithinAABB(mc.thePlayer.getClass(), new AxisAlignedBB(vec.xCoord - 0.5d, vec.yCoord, vec.zCoord - 0.5d, vec.xCoord - 0.5d + 1d, vec.yCoord + 1d, vec.zCoord - 0.5d + 1d));
		if(list.size() != 0) {
			for(Object obj : list) {
				if(obj instanceof EntityClientPlayerMP) {
					if(path.getCurrentPathIndex() < path.getCurrentPathLength() - 1)
						path.setCurrentPathIndex(path.getCurrentPathIndex() + 1);
					else {
						toggle();
						mc.gameSettings.keyBindJump.pressed = false;
						
						MiscUtils.sendChatClient("Готово!");
					}
				}
			}
		}
	}
	
	@Override
	public void onRender() {
		if(path == null)
			return;
		
		try {
			for(int a = 0; a < path.pathLength - 1; ++a) {
				Vec3 vec1 = path.getVectorFromIndex(mc.thePlayer, a);
				Vec3 vec2 = path.getVectorFromIndex(mc.thePlayer, a + 1);
				
				if(vec1 != null && vec2 != null) {
					drawVec(vec1, vec2, 0xFFFF0000);
				}
			}
			
			Vec3 vec = path.getVectorFromIndex(mc.thePlayer, path.getCurrentPathLength() - 1);
			if(vec != null) {
				double renderX = vec.xCoord - RenderManager.renderPosX;
				double renderY = vec.yCoord - RenderManager.renderPosY;
				double renderZ = vec.zCoord - RenderManager.renderPosZ;
				
				double offY = Math.sin(anim);
				anim += 3.11d;
				
				if(offY < 0)
					offY = -offY;
				
				R3DUtils.drawBlockESP(new Vector3d(renderX - 0.5d, renderY + offY, renderZ - 0.5d), 0x00000000, 0xFFFF00FF, 3f);
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
	}
	
	public void drawVec(Vec3 vec, Vec3 vec2, int color) {
		try {
			if(vec != null) {
				double renderX = vec.xCoord - RenderManager.renderPosX;
				double renderY = vec.yCoord - RenderManager.renderPosY;
				double renderZ = vec.zCoord - RenderManager.renderPosZ;
				
				double renderX2 = vec2.xCoord - RenderManager.renderPosX;
				double renderY2 = vec2.yCoord - RenderManager.renderPosY;
				double renderZ2 = vec2.zCoord - RenderManager.renderPosZ;
				
				R3DUtils.drawLine(renderX, renderY + 0.05d, renderZ, renderX2, renderY2 + 0.05d, renderZ2, color, 3);
				
				double size = 0.35d;
				
				R3DUtils.drawBorderedRect(new Vector3d(renderX - size, renderY + 0.01d, renderZ - size), new Vector3d(renderX + size, renderY + 0.01d, renderZ + size), 0xFFFF0000, 0xAA00FF00);
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
