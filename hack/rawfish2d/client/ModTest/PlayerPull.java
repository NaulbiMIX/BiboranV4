package hack.rawfish2d.client.ModTest;

import org.lwjgl.input.Keyboard;
import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.Module;
import hack.rawfish2d.client.ModuleType;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.Vector2f;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet7UseEntity;

public class PlayerPull extends Module {
	public boolean send;
	public boolean flip;

	public PlayerPull() {
		super("PlayerPull", 0, ModuleType.TEST);
		setDescription("При ударе ваши враги летят к вам");
		send = false;
		flip = false;
	}

	@Override
	public void onDisable() {
		send = false;
	}

	@Override
	public void onEnable() {
		send = false;
	}

	@Override
	public void onAddPacketToQueue(Packet packet) {
		before_1(packet);
	}
	
	void before_1(Packet packet) {
		if(packet instanceof Packet7UseEntity) {
			Packet7UseEntity p = (Packet7UseEntity)packet;
			
			//flip = false;
			
			EntityPlayer target = null;
			for(Object obj : mc.theWorld.loadedEntityList) {
				if(obj instanceof EntityPlayer) {
					EntityPlayer ent = (EntityPlayer)obj;
					if(ent.entityId == p.targetEntity) {
						target = ent;
						break;
					}
				}
			}
			
			if(target == null) {
				return;
			}
			
			Vector2f angles = MiscUtils.getFaceAngles(target);
			
			if(flip) {
				angles.y = MiscUtils.normalizeYaw(angles.y - 180.f);
			}
			else {
				//nothing
			}
			
			MiscUtils.setAngles(angles.x, angles.y, true);
			
			for(int a = 0; a < 2; ++a) {
				//mc.getNetHandler().addToSendQueue(new Packet12PlayerLook(angles.y, angles.x, mc.thePlayer.onGround) );
				mc.getNetHandler().addToSendQueue(new Packet13PlayerLookMove(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY, mc.thePlayer.posY, mc.thePlayer.posZ, angles.y, angles.x, mc.thePlayer.onGround) );
				MiscUtils.sleep(2L);
			}
			
			flip = !flip;
		}
	}

	@Override
	public void afterOnAddPacketToQueue(Packet packet) {
		//after_1(packet);
	}
	
	void after_1(Packet packet) {
		if(packet instanceof Packet7UseEntity) {
			Packet7UseEntity p = (Packet7UseEntity)packet;
			
			EntityPlayer target = null;
			for(Object obj : mc.theWorld.loadedEntityList) {
				if(obj instanceof EntityPlayer) {
					EntityPlayer ent = (EntityPlayer)obj;
					if(ent.entityId == p.targetEntity) {
						target = ent;
						break;
					}
				}
			}
			
			if(target == null) {
				return;
			}
			
			Vector2f angles = MiscUtils.getFaceAngles(target);
			angles.y = MiscUtils.normalizeYaw(angles.y);
			MiscUtils.setAngles(angles.x, angles.y, true);
			/*
			for(int a = 0; a < 5; ++a) {
				mc.getNetHandler().addToSendQueue(new Packet12PlayerLook(angles.y, angles.x, mc.thePlayer.onGround) );
				MiscUtils.sleep(2L);
			}
			*/
		}
		/*
		if(packet instanceof Packet7UseEntity) {
			if(send) {
				float newyaw = Client.getInstance().getRotationUtils().getYaw();
				float pitch = Client.getInstance().getRotationUtils().getPitch();
				newyaw = MiscUtils.normalizeYaw(newyaw - 180.f);
				MiscUtils.setAngles(pitch, newyaw, false);
			}
		}

		if(packet instanceof Packet10Flying) {
			Packet10Flying packetFly = (Packet10Flying)packet;
			if(packetFly.pitch != 0 || packetFly.yaw != 0) {
				//System.out.println("packetFly 2 - pitch:" + packetFly.pitch);
				//System.out.println("packetFly 2 - yaw:" + packetFly.yaw);
			}

			if(send) {
				packetFly.yaw = Client.getInstance().getRotationUtils().getYaw() - 180.f;
			}
		}
		*/
	}
}
