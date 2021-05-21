package hack.rawfish2d.client.pbots.modules;

import java.util.ArrayList;
import java.util.List;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBot;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.LiteEntity;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import hack.rawfish2d.client.utils.Vector3f;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.PathEntity;

public class KillAura extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("KillAura", "gToggled", false);
	private TimeHelper time;
	private LiteEntity target;
	
	public KillAura(PBotThread bot) {
		super(bot, "KillAura");
		toggled = gToggled.get(false);
		time = new TimeHelper();
		target = null;
	}
	
	@Override
	public void onEnable() {
		target = null;
	}
	
	@Override
	public void onDisable() {
		bot.motionX = 0;
		bot.motionZ = 0;
	}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
	}
	
	@Override
	public void onTick() {
		//toggled = false;
		//toggled = true;
		if(!toggled) {
			return;
		}
		
		//Entity ent = new EntityZombie(bot.chunks.world);
		//PathEntity pe = bot.chunks.world.getEntityPathToXYZ(ent, 79, 64, 298, 10.0F, true, false, false, true);
		//Client.mc.theWorld.getEntityPathToXYZ(null, 0, 0, 0, 0, toggled, toggled, toggled, toggled)
		
		if(time.hasReached(200L)) {
			time.reset();
			aura();
		}
	}
	
	private LiteEntity getTarget(float angle, float range) {
		for(LiteEntity ent : bot.getEntityes()) {
			double mindistance = 99;
			if(ent.getType() == 1) {
				
				boolean isOk = true;
				for(PBot otherbot : PBotCmd.bots) {
					if(otherbot.pbotth != null) {
						if(otherbot.pbotth.entityId == ent.getId()) {
							isOk = false;
							break;
						}
					}
				}
				
				if(!isOk)
					continue;
				
				double distance = MiscUtils.getDistance(ent.getPos(), new Vector3f(bot.x, bot.y, bot.z));
				distance = Math.sqrt(distance);
				
				if(mindistance > distance) {
					mindistance = distance;
				}
				
				if(distance < range && mindistance == distance) {
					target = ent;
				}
			}
		}
		return target;
	}
	
	private void aura() {
		if(!bot.dead) {
			target = getTarget(360f, 4.9f);
			
			if (target != null) {
				Vector2f angles = MiscUtils.getBlockAngles(target.getPos().x, target.getPos().y + 1f, target.getPos().z, bot.x, bot.y, bot.z);
				
				bot.sendPacket(new Packet12PlayerLook(angles.y, angles.x, bot.onGround));
				//MiscUtils.sleep(4L);
				bot.sendPacket(new Packet7UseEntity(bot.entityId, target.getId(), 1));
				//MiscUtils.sleep(4L);
				bot.sendPacket(new Packet18Animation(bot.entityId, 1));
				//MiscUtils.sleep(4L);
				/*
				for(int slot = 0; slot < bot.inventory.length; ++slot) {
					ItemStack item = bot.inventory[slot];
					MiscUtils.sendChatClient("slot:" + slot + " " + item);
				}
				*/
				target = null;
			}
		}
	}
}
