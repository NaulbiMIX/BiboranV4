package hack.rawfish2d.client.ModTest;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModUnused.ShopDupe;
import hack.rawfish2d.client.pbots.PBot;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R2DUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.ContainerChest;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EnumMovingObjectType;
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
import net.minecraft.src.Potion;
import net.minecraft.src.RenderManager;

public class AutoWorldTP extends Module{
	public static Module instance = null;
	public TimeHelper time;
	
	public int r = 4600;
	public int cx = 92;
	public int cz = 279;
	
	public int minx; //-4300
	public int minz; //-4200
	
	public int maxx; //4800
	public int maxz; //4900
	
	public int x = -4800;
	public int z = -4800;
	public float fi = 0;
	
	public boolean done_inside = false;
	public boolean done = false;
	
	public AutoWorldTP() {
		super("AutoWorldTP", 0, ModuleType.TEST);
		setDescription("???");
		instance = this;
		time = new TimeHelper();
		time.reset();
		
		minx = cx - r;
		maxx = cx + r;
		
		minz = cz - r;
		maxz = cz + r;
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onEnable() {
		//x = 1280;
		//z = 4630;
	}
	
	@Override
	public void preMotionUpdate() {
		//done_inside = true;
		//done = false;
		//fi = 0;
	}
	
	@Override
	public void onUpdate() {
		if(time.hasReached(2000L)) {
			if(!done_inside) {
				if(isInside(4600, 92, 279, x, z) >= 0) {
					time.reset();
					MiscUtils.sendChatClient("§c§lx:" + x + " z:" + z);
					tp();
					nextTp();
				}
				else
				{
					time.reset();
					int trytimes = 100;
					while(isInside(4600, 92, 279, x, z) < 0 && trytimes >= 0) {
						nextTp();
						trytimes--;
					}
					
					if(trytimes <= 0) {
						done_inside = true;
						MiscUtils.sendChatClient("§c§lDone");
						this.toggle();
					}
				}
			}
			else if(!done) {
				time.reset();
				
				x = (int) (r * Math.cos(fi)) + cx;
				z = (int) (r * Math.sin(fi)) + cz;
				fi += 0.025;
				
				MiscUtils.sendChatClient("§c§lx:" + x + " z:" + z + " fi:" + fi);
				tp();
				
				if(fi >= 360) {
					done = true;
					MiscUtils.sendChatClient("§c§lDone");
					this.toggle();
				}
			}
		}
	}
	
	public void nextTp() {
		if(x < maxx) {
			x += 190;
		}
		else if(x >= maxx) {
			x = minx;
			z += 190;
		}
		
		if(z >= maxz) {
			done = true;
		}
	}
	
	public void tp() {
		MiscUtils.sendChat("/tppos " + x + " 100 " + z);
	}
	
	public int isInside(double r, double xc, double zc, double x, double z) {
		if (((x - xc) * (x - xc) + (z - zc) * (z - zc)) < r * r) {
			System.out.println("inside");
	        return 1;
	    } else if (((x - xc) * (x - xc) + (z - zc) * (z - zc)) == r * r) {
	    	System.out.println("on");
	        return 0;
	    } else {
	    	System.out.println("out side");
	        return -1;
	    }
	}
	
	@Override
	public void postMotionUpdate() {
		
	}
	
	@Override
	public void onRenderHand() {
		
	}
	
	@Override
	public void onRender() {
		
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		
	}
	
	@Override
	public void onRenderOverlay() {
		
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
