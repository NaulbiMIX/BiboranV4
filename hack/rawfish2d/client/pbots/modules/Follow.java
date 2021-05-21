package hack.rawfish2d.client.pbots.modules;

import java.util.ArrayList;
import java.util.List;

import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.LiteEntity;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet3Chat;

public class Follow extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("Follow", "gToggled", false);
	private static PBotVar target = new PBotVar("Follow", "target", "none");
	private static PBotVar walkSpeed = new PBotVar("Follow", "walkSpeed", 0.20D);
	
	public Follow(PBotThread bot) {
		super(bot, "Follow");
		toggled = gToggled.get(false);
	}
	
	@Override
	public void onEnable() {
		//bot.changeSlot(0);
		//MiscUtils.sleep(30L);
		//bot.sendPacket(new Packet15Place(-1, -1, -1, 255, bot.inventory[bot.curSlot + 36], 0.0f, 0.0f, 0.0f));
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
		
		walk();
	}
	
	private void walk() {
		//if(!bot.dead && bot.onSurvivalServer) {
		if(!bot.dead) {
			float offset = (float) walkSpeed.get(0.D);
			
			//bot.yaw = (float) walkYaw.get(0D);
			/*
			if(bot.yaw == 0)
				bot.z += offset;
			else if(bot.yaw == -180)
				bot.z -= offset;
			
			if(bot.yaw == 90)
				bot.x -= offset;
			else if(bot.yaw == 270)
				bot.x += offset;
			*/
			
			//90 -x
			/*
			bot.yaw += 8f;
			bot.yaw = MiscUtils.normalizeYaw(bot.yaw);
			bot.pitch = 180;
			
			double yaw = bot.yaw;
			double cos = Math.cos(Math.toRadians(yaw + 90.0f));
			double sin = Math.sin(Math.toRadians(yaw + 90.0f));
			float offx = (float) cos / 5f;
			float offz = (float) sin / 5f;
			
			//bot.x += offx;
			//bot.z += offz;
			bot.motionX = offx;
			bot.motionZ = offz;
			
			//bot.sendPacket(new Packet13PlayerLookMove(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.yaw, bot.pitch, bot.onGround));
			//MiscUtils.sleep(4L);
			//bot.sendPacket(new Packet11PlayerPosition(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.onGround));
			//MiscUtils.sleep(4L);
			
			*/
			
			LiteEntity final_ent = bot.getEntityByName(target.get(""));
			
			if(final_ent == null) {
				return;
			}
			
			Vector2f angles = MiscUtils.getBlockAngles(final_ent.getPos().x, final_ent.getPos().y + 1, final_ent.getPos().z, bot.x, bot.y, bot.z);
			
			bot.yaw = MiscUtils.normalizeYaw(angles.y);
			bot.pitch = MiscUtils.normalizePitch(angles.x);
			
			//bot.sendPacket(new Packet12PlayerLook(bot.yaw, bot.pitch, bot.onGround));
			
			double yaw = bot.yaw;
			double cos = Math.cos(Math.toRadians(yaw + 90.0f));
			double sin = Math.sin(Math.toRadians(yaw + 90.0f));
			float offx = (float) (cos / 7);
			float offz = (float) (sin / 7);
			
			bot.motionX = offx;
			bot.motionZ = offz;
			//bot.pitch = 180;
			
			//bot.x += offx;
			//bot.z += offz;
			
			/*
			bot.sendPacket(new Packet13PlayerLookMove(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.yaw, bot.pitch, bot.onGround));
			MiscUtils.sleep(4L);
			bot.sendPacket(new Packet11PlayerPosition(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.onGround));
			MiscUtils.sleep(4L);
			*/
		}
	}
}
