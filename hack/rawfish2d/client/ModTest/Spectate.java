package hack.rawfish2d.client.ModTest;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.gui.ingame.ListBox;
import hack.rawfish2d.client.pbots.utils.LiteEntity;
import hack.rawfish2d.client.utils.SharedStringList;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet19EntityAction;

public class Spectate extends Module{
	public static Module instance = null;
	
	private Random random = new Random();
	public static SharedStringList list; 	
	public static ListBox listbox;
	private TimeHelper time;

	Vector3f originPos;
	Vector2f originAngles;
	
	Vector3f entPos;
	Vector2f entAngles;
	
	public Spectate() {
		super("Spectate", 0, ModuleType.TEST);
		setDescription("Вид от первого лица другого игрока");
		instance = this;
		
		list = new SharedStringList();
		listbox = new ListBox(this, "Player name", list, 0, 0);
		time = new TimeHelper();
		
		this.elements.add(listbox);
	}

	@Override
	public void onEnable() {
		EntityPlayer ent = getEntityWithName(listbox.getValue());
		if(ent == null) {
			toggled = false;
			return;
		}
		
		originPos = new Vector3f();
		originAngles = new Vector2f();
		
		entPos = new Vector3f();
		entAngles = new Vector2f();
		
		originPos.x = (float) mc.thePlayer.posX;
		originPos.y = (float) mc.thePlayer.posY;
		originPos.z = (float) mc.thePlayer.posZ;
		
		originAngles.x = mc.thePlayer.rotationPitch;
		originAngles.y = mc.thePlayer.rotationYaw;
		
		entPos.x = (float) ent.posX;
		entPos.y = (float) ent.posY;
		entPos.z = (float) ent.posZ;
		
		entAngles.x = ent.rotationPitch;
		entAngles.y = ent.rotationYaw;
		
		mc.thePlayer.setPositionAndRotation(entPos.x, entPos.y, entPos.z, entAngles.y, entAngles.x);
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.setPositionAndRotation(originPos.x, originPos.y, originPos.z, originAngles.y, originAngles.x);
	}
	
	public static EntityPlayer getEntityWithName(String name) {
		for(Object obj : mc.theWorld.loadedEntityList) {
			if(obj instanceof EntityPlayer) {
				EntityPlayer e = (EntityPlayer)obj;
				if(e.username.equalsIgnoreCase(name)) {
					return e;
				}
			}
		}
		return null;
	}

	@Override
	public void onUpdate() {
		
	}

	@Override
	public void preMotionUpdate() {
		mc.thePlayer.motionX = 0;
		mc.thePlayer.motionY = 0;
		mc.thePlayer.motionZ = 0;
		
		EntityPlayer ent = getEntityWithName(listbox.getValue());
		if(ent == null) {
			toggle();
			return;
		}
		entPos.x = (float) ent.posX;
		entPos.y = (float) ent.posY;
		entPos.z = (float) ent.posZ;
		
		entAngles.x = ent.rotationPitch;
		entAngles.y = ent.rotationYaw;
		
		mc.thePlayer.setPositionAndRotation(entPos.x, entPos.y + 1.62, entPos.z, entAngles.y, entAngles.x);
	}
	
	@Override
	public void onButtonRightClick() {
		
	}
	
	@Override
	public void callAlways() {
		if(time.hasReached(1000L)) {
			list.clear();
			for(Object obj : mc.theWorld.loadedEntityList) {
				if(obj instanceof EntityPlayer) {
					EntityPlayer e = (EntityPlayer)obj;
					list.add(e.username);
				}
			}
			time.reset();
		}
	}

	@Override
	public void postMotionUpdate() {
		
	}
}
