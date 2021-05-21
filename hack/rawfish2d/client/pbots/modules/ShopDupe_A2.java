package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.AutoWarp;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.LiteEntity;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet3Chat;

public class ShopDupe_A2 extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("ShopDupe_A2", "gToggled", false);
	//private static PBotVar warp_dupe = new PBotVar("ShopDupe_A2", "warp_dupe", "dupe");
	TimeHelper dupeDelay = new TimeHelper();
	TimeHelper refillDelay = new TimeHelper();
	TimeHelper chatDelay = new TimeHelper();
	int slot = 0;
	int maxSlot = 54;
	boolean opened = false;
	boolean gm1 = false;
	boolean warped = false;
	
	public String warp_dupe = "dupe";
	
	public ShopDupe_A2(PBotThread bot) {
		super(bot, "ShopDupe_A2");
		toggled = gToggled.get(false);
		dupeDelay.reset();
		refillDelay.reset();
		chatDelay.reset();
	}
	
	@Override
	public void onEnable() {
		this.warped = false;
		this.gm1 = false;
		this.opened = false;
	}
	
	@Override
	public void onDisable() {}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
	}
	
	///warp dupe_test_chest_area
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		if(warped == false && gm1 == false && opened == false) {
			chatDelay.reset();
			//bot.sendPacket(new Packet3Chat("/warp " + warp_dupe.get("")));
			bot.sendPacket(new Packet3Chat("/warp " + warp_dupe));
			warped = true;
			return;
		}
		
		if(warped == true && gm1 == false && opened == false && chatDelay.hasReached(1000L)) {
			chatDelay.reset();
			bot.sendPacket(new Packet3Chat("/gm 1"));
			gm1 = true;
			
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			bot.changeSlot(8);

			bot.yaw = 90;
			bot.pitch = 40;
			
			bot.sendPacket(new Packet12PlayerLook(bot.yaw, bot.pitch, true));
			
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ItemStack itemClock = new ItemStack(347, 64, 0);
			itemClock.stackSize = 64;
			bot.sendPacket(new Packet107CreativeSetSlot(9, itemClock));
			
			return;
		}
		
		if(warped == true && gm1 == true && opened == false) {
			bot.sendPacket(new Packet15Place((int)bot.x - 2, (int)bot.y, (int)bot.z, 1, bot.inventory[8], 0.6f, 0.875f, 0.5f));
			
			opened = true;
			refillDelay.reset();
			
			return;
		}
		
		if(warped == true && gm1 == true && opened == true && refillDelay.hasReached(2000L)) {
			refillDelay.reset();
			fill();
			return;
		}
	}
	
	private void fill() {
		for(slot = 0; slot < bot.windowInventory.length; slot++) {
			if(bot.windowInventory[slot] == null)
				dupe();
		}

		bot.sendPacket(new Packet101CloseWindow(bot.ContainerWindowId));
		opened = false;
	}
	
	private void dupe() {
		bot.sendPacket(new Packet102WindowClick(bot.ContainerWindowId, 54, 0, 3, (ItemStack)null, bot.getTransactionID()));
		
		MiscUtils.sleep(8L);
		
		bot.sendPacket(new Packet102WindowClick(bot.ContainerWindowId, slot, 0, 0, bot.windowInventory[slot], bot.getTransactionID()));
		//bot.sendPacket(new Packet102WindowClick(bot.windowId, slot, 0, 0, (ItemStack)null, bot.getTransactionID()));
		
		MiscUtils.sleep(8L);
	}
}
