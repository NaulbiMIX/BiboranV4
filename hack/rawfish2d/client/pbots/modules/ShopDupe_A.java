package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.ModMisc.AutoWarp;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet3Chat;

public class ShopDupe_A extends ModuleBase {
	TimeHelper walkdelay = new TimeHelper();
	TimeHelper postwalkdelay = new TimeHelper();
	TimeHelper postwarpdelay = new TimeHelper();
	TimeHelper postgmdelay = new TimeHelper();
	boolean warped = false;
	boolean duped = false;
	int stage = 0;
	
	public ShopDupe_A(PBotThread bot) {
		super(bot, "ShopDupe_A");
		walkdelay.reset();
		postwalkdelay.reset();
		postwarpdelay.reset();
		postgmdelay.reset();
	}
	
	@Override
	public void onEnable() {}
	
	@Override
	public void onDisable() {}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		/*
		if(bot.dead) {
			postwalkdelay.reset();
			postwarpdelay.reset();
			postgmdelay.reset();
			warped = false;
			duped = false;
			stage = 0;
		}
		*/
		
		if(stage == 0 && !bot.dead) {
			//walk();
		}
		else if(stage == 1 && warped == false) {
			warped = true;
			bot.sendPacket(new Packet3Chat("/warp shopdupe2"));
		}
		else if(stage == 2 && duped == false) {
			bot.sendPacket(new Packet3Chat("/gm 1"));
			ItemStack itemClock = new ItemStack(347, 64, 0);
			itemClock.stackSize = 64;
			for(int a = 0; a < 45; ++a) {
				bot.sendPacket(new Packet107CreativeSetSlot(a, itemClock));
			}
			bot.sendPacket(new Packet3Chat("/gm 0"));
			duped = true;
		}
		else if(stage == 3) {
			dmg(127);
			
			//MiscUtils.sendChatClient("MS:" + (postwalkdelay.getCurrentMS() - postwalkdelay.getLastMS()));
			
			postwalkdelay.reset();
			postwarpdelay.reset();
			postgmdelay.reset();
			warped = false;
			duped = false;
			stage = 1;
		}
		
		//if(postwalkdelay.hasReached(1000L) && stage == 0) {
		if(stage == 0) {
			stage = 1;
			postwarpdelay.reset();
		}
		else if(postwarpdelay.hasReached(100L) && stage == 1 && warped == true) {
			stage = 2;
			postgmdelay.reset();
		}
		else if(postgmdelay.hasReached(1500L) && stage == 2 && duped == true) {
			stage = 3;
		}
	}
	
	private void dmg(double dmg) {
		bot.sendPacket(
				new Packet13PlayerLookMove(
						bot.x,
						bot.y + 0.2f,
						bot.y + bot.yOffset + 0.2f,
						bot.z,
						bot.yaw,
						bot.pitch,
						false));
		
		bot.sendPacket(
				new Packet11PlayerPosition(
						bot.x,
						bot.y - 3.0f - dmg,
						bot.y + bot.yOffset - 3.0f - dmg,
						bot.z,
						false));
	}
	
	private void walk() {
		if(!bot.dead && bot.onSurvivalServer) {
			walkdelay.reset();
			//z -= 0.15;
			//yaw = -180;
			
			float offset = 0.10f;
			
			bot.yaw = -180;
			if(bot.yaw == 0)
				bot.z += offset;
			else if(bot.yaw == -180)
				bot.z -= offset;
			
			if(bot.yaw == 90)
				bot.x -= offset;
			else if(bot.yaw == 270)
				bot.x += offset;
			
			bot.sendPacket(new Packet13PlayerLookMove(bot.x, bot.y, bot.y + bot.yOffset, bot.z, bot.yaw, bot.pitch, true));
			if(walkdelay.hasReached(100L))
				bot.sendPacket(new Packet11PlayerPosition(bot.x, bot.y, bot.y + bot.yOffset, bot.z, true));
		}
	}
}
