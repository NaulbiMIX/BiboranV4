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

public class Walk extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("Walk", "gToggled", false);
	private static PBotVar walkYaw = new PBotVar("Walk", "walkYaw", 90.D);
	private static PBotVar walkSpeed = new PBotVar("Walk", "walkSpeed", 0.20D);
	private static PBotVar walkRotate = new PBotVar("Walk", "rotate", false);
	
	private TimeHelper walk_delay = new TimeHelper();
	
	public Walk(PBotThread bot) {
		super(bot, "Walk");
		toggled = gToggled.get(false);
	}
	
	@Override
	public void onEnable() {
		bot.yaw = MiscUtils.random(0, 360);
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
			if(walkRotate.get(false)) {
				bot.yaw += 8f;
				bot.pitch = 180;
			}
			bot.yaw = MiscUtils.normalizeYaw(bot.yaw);
			
			double yaw = bot.yaw;
			double cos = Math.cos(Math.toRadians(yaw + 90.0f));
			double sin = Math.sin(Math.toRadians(yaw + 90.0f));
			float offx = (float) cos / 6f;
			float offz = (float) sin / 6f;
			
			bot.motionX = offx;
			bot.motionZ = offz;
		}
	}
}
