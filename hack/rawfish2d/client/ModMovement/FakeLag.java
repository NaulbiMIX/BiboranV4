package hack.rawfish2d.client.ModMovement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.ModMisc.FastUse;
import hack.rawfish2d.client.gui.ingame.NewSlider;
import hack.rawfish2d.client.utils.DoubleValue;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.R3DUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector3d;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.RenderManager;

public class FakeLag extends Module{
	public static Module instance = null;
	
	public static CopyOnWriteArrayList<Packet> storedPacketsayList = new CopyOnWriteArrayList();
	private TimeHelper time = new TimeHelper();
	public static boolean fakeLagEnabled;
	private DoubleValue timeFake;
	private DoubleValue timeOffDV;
	
	private boolean thread_done;
	private Thread th;
	
	public FakeLag() {
		super("FakeLag", 0, ModuleType.MOVEMENT);
		setDescription("‘ÂÈÍ Î‡„Ë");
		instance = this;
		fakeLagEnabled = false;
		thread_done = true;
		th = null;
		
		timeFake = new DoubleValue(600.0D, 0.0D, 1500.0D);
		timeOffDV = new DoubleValue(600.0D, 140.0D, 1500.0D);
		
		elements.add(new NewSlider(this, "Fake ms", timeFake, 0, 0, true));
		elements.add(new NewSlider(this, "Not fake ms", timeOffDV, 0, 20, true));
	}
	
	@Override
	public void onEnable() {
		time.reset();
		fakeLagEnabled = false;
		storedPacketsayList.clear();
	}
	
	@Override 
	public void onUpdate() {
		if(FastUse.instance.isToggled() && FastUse.fastUseCheck() == true) {
			endFakeMode();
		}
	}
	
	private void endFakeMode() {
		//if(fakeEnt != null) {
		
		//new Thread(new Runnable() {
		//	public void run() {
				if(fakeLagEnabled) {
					time.reset();
					fakeLagEnabled = false;
					
					for (Packet pk : storedPacketsayList) {
						mc.thePlayer.sendQueue.addToSendQueue(pk);
						MiscUtils.sleep(10L);
					}
					
					fake_y = 0;
					storedPacketsayList.clear();
				}
			//}
		//}).start();
	}
	
	@Override
	public void onAddPacketToQueue(Packet packet) {
		if (packet instanceof Packet15Place || packet instanceof Packet14BlockDig || packet instanceof Packet18Animation || packet instanceof Packet19EntityAction/* || packet instanceof Packet7UseEntity*/ || packet instanceof Packet11PlayerPosition || packet instanceof Packet12PlayerLook || packet instanceof Packet13PlayerLookMove || packet instanceof Packet16BlockItemSwitch) {
			
			if(!time.hasReached(timeFake.getValue()) && fakeLagEnabled) {
				//fake
				if(storedPacketsayList.size() == 0) {
					//begin fake
					fake_x = mc.thePlayer.posX;
					fake_y = mc.thePlayer.posY - 1.62;
					fake_z = mc.thePlayer.posZ;
					
					fake_pitch = mc.thePlayer.rotationPitch;
					fake_yaw = mc.thePlayer.rotationYaw;
				}
				
				System.out.println("packet : " + packet.toString());
				storedPacketsayList.add(packet);
				setName("FakeLag [" + storedPacketsayList.size() + "]");
				doNotSendNextPacket(true);
			}
			else if(time.hasReached(timeFake.getValue()) && fakeLagEnabled)
			{
				//not fake
				endFakeMode();
			}
			else if(time.hasReached(timeOffDV.getValue()) && !fakeLagEnabled) {
				//begin fake
				fakeLagEnabled = true;
				time.reset();
			}
			
			if(!fakeLagEnabled) {
				setName("FakeLag ßc" + (time.getCurrentMS() - time.getLastMS()));
			}
		}
	}
	
	private double fake_x = 0;
	private double fake_y = 0;
	private double fake_z = 0;
	
	private double fake_pitch = 0;
	private double fake_yaw = 0;
	
	@Override
	public void onRender() {
		if(fake_y != 0) {
			double render_x;
			double render_y;
			double render_z;
			
			render_x = (fake_x + (fake_x - fake_x) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosX; 
			render_y = (fake_y + (fake_y - fake_y) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosY;
			render_z = (fake_z + (fake_z - fake_z) * mc.timer.renderPartialTicks) - RenderManager.instance.renderPosZ;
			
			R3DUtils.drawBlockESP(new Vector3d(render_x - 0.5d, render_y, render_z - 0.5d), 0x8800AA00, 0xFF000000, 2f);
			
			//R3DUtils.drawEntityESP(render_x, render_y, render_z, 0.5f, 1f, 1.0f, 0f, 0f, 0.15F, 0F, 0F, 0F, 1F, 2F);
		}
	}
	
	@Override
	public void onDisable() {
		endFakeMode();
		setName("FakeLag");
	}
}
