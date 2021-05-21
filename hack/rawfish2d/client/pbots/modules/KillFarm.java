package hack.rawfish2d.client.pbots.modules;

import java.util.ArrayList;
import java.util.List;

import hack.rawfish2d.client.cmd.PBotCmd;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet3Chat;

public class KillFarm extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("KillFarm", "gToggled", false);
	
	private static PBotVar walkYaw = new PBotVar("KillFarm", "walkYaw", 270D);
	private static PBotVar warp = new PBotVar("KillFarm", "warp", "kill");
	private static PBotVar walkSpeed = new PBotVar("KillFarm", "walkSpeed", 0.18D);
	
	private TimeHelper walk_timer = new TimeHelper();
	private TimeHelper tp_timer = new TimeHelper();
	private TimeHelper tp_iffail_timer = new TimeHelper();
	private boolean onwarp = false;
	private boolean tptoggle = false;
	//public static float walk_yaw = 270;
	//public static String warp = "farm228";
	
	public KillFarm(PBotThread bot) {
		super(bot, "KillFarm");
		toggled = gToggled.get(false);
	}
	
	@Override
	public void onEnable() {
		tp_timer.reset();
		tp_iffail_timer.reset();
		onwarp = false;
	}
	
	@Override
	public void onDisable() {}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
		
		if(packet instanceof Packet3Chat) {
			Packet3Chat p = (Packet3Chat)packet;
			String str = "§6Перемещение на§c " + warp.get("") + "§6.";
			str = str.toLowerCase();
			if(p.message.toLowerCase().contains(str)) {
				onwarp = true;
			}
		}
		
		if(packet instanceof Packet3Chat) {
			Packet3Chat p = (Packet3Chat)packet;
			String str = "§6Телепортации выключены.";
			String str2 = "§6Телепортации включены.";
			str = str.toLowerCase();
			str2 = str2.toLowerCase();
			if(p.message.toLowerCase().contains(str)) {
				tptoggle = true;
			}
			else if(p.message.toLowerCase().contains(str)) {
				tptoggle = false;
			}
		}
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		if(!tptoggle) {
			bot.send("/tptoggle");
			MiscUtils.sleep(500L);
			return;
		}
		
		if(bot.dead) {
			tp_timer.reset();
			tp_iffail_timer.reset();
			onwarp = false;
		}
		
		if(tp_timer.hasReached(1000L) && !onwarp) {
			bot.send("/warp " + warp.get(""));
			tp_timer.reset();
		}
		
		if(tp_iffail_timer.hasReached(5000L) && onwarp) {
			bot.send("/warp " + warp.get(""));
			tp_iffail_timer.reset();
			tp_timer.reset();
		}
		
		walk();
	}
	
	private void walk() {
		if(!bot.dead && bot.onSurvivalServer) {
			//if(walk_timer.hasReached(50L)) {
				float offset = (float) walkSpeed.get(0.D);
				
				bot.yaw = (float) walkYaw.get(0D);
				if(bot.yaw == 0)
					bot.z += offset;
				else if(bot.yaw == -180)
					bot.z -= offset;
				
				if(bot.yaw == 90)
					bot.x -= offset;
				else if(bot.yaw == 270)
					bot.x += offset;
			//}
			
			bot.sendPacket(new Packet13PlayerLookMove(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.yaw, bot.pitch, bot.onGround));
			//if(walk_timer.hasReached(50L)) {
				bot.sendPacket(new Packet11PlayerPosition(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.onGround));
			//	walk_timer.reset();
			//}
		}
	}
}
