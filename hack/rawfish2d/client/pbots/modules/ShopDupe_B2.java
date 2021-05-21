package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.AutoWarp;
import hack.rawfish2d.client.ModUnused.ShopDupe;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.pbots.utils.LiteEntity;
import hack.rawfish2d.client.pbots.utils.PBotVar;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import hack.rawfish2d.client.utils.Vector2f;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet100OpenWindow;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet207SetScore;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet7UseEntity;

public class ShopDupe_B2 extends ModuleBase {
	private static PBotVar gToggled = new PBotVar("ShopDupe_B2", "gToggled", false);
	private TimeHelper delay = new TimeHelper();
	
	private int slot = 0;
	private int maxSlot = 54;
	private int state = 0;
	private boolean debug = false;
	private long ldelay = 2150;
	
	private TimeHelper resetDelay = new TimeHelper();
	
	public String warp_dupe = "dupe";
	public String warp_shop = "dupe_sell";
	
	public ShopDupe_B2(PBotThread bot) {
		super(bot, "ShopDupe_B2");
		toggled = gToggled.get(false);
		delay.reset();
	}
	
	@Override
	public void onEnable() {
		bot.changeSlot(8);
		bot.sendPacket(new Packet14BlockDig(3, 0, 0, 0, 0));
		MiscUtils.sleep(2L);
		
		state = 0;
		delay.reset();
	}
	
	@Override
	public void onDisable() {}
	
	@Override
	public void onReadPacket(Packet packet) {
		if(!toggled) {
			return;
		}
		
		if(packet instanceof Packet207SetScore) {
			Packet207SetScore p = (Packet207SetScore)packet;
			if(p.itemName.contains("Äåíåã")) {
				resetDelay.reset();
			}
		}
		
		if(resetDelay.hasReached(1000L * 10L)) {
			resetDelay.reset();
			//bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + "§cShopDupe_B2 glitched, resetting...");
			state = 0;
			delay.reset();
		}
		
		if(packet instanceof Packet10Flying) {
			if(state == 1) {
				if(debug) bot.sendChatClient("1. bot teleported to /warp dupe");
				state = 2;
			}
			else if(state == 7) {
				if(debug) bot.sendChatClient("7. bot teleported to /warp dupe_sell");
				state = 8;
			}
		}
		
		if(packet instanceof Packet100OpenWindow) {
			if(state == 3) {
				if(debug) bot.sendChatClient("4. received OpenWindow");
				state = 5;
			}
		}
		
		if(packet instanceof Packet101CloseWindow) {
			
		}
	}
	
	private void warpShop() {
		bot.sendPacket(new Packet3Chat("/warp " + warp_shop));
	}
	
	private void warpDupe() {
		bot.sendPacket(new Packet3Chat("/warp " + warp_dupe));
	}
	
	@Override
	public void onTick() {
		if(!toggled) {
			return;
		}
		
		if(bot.money >= 2000000) {
			if(Client.getInstance().mc.session != null) {
				if(Client.getInstance().mc.session.username != null) {
					if(!Client.getInstance().mc.getNetHandler().disconnected) {
						bot.send("/pay " + Client.getInstance().mc.session.username + " " + (bot.money - 1000));
						bot.money = 1000;
						MiscUtils.sleep(1000L);
						return;
					}
				}
			}
		}
		
		if(state == 0 && delay.hasReached(ldelay)) {
			state = 1;
			
			if(debug) bot.sendChatClient("0. /warp dupe");
			
			warpDupe();
		}
		
		//state == 1, 'warp dupe' was sent, but bot is not on warp
		
		else if(state == 2) {
			state = 3;
			
			delay.reset();
			if(debug) bot.sendChatClient("2. bot in warp dupe");
		}
		
		else if(state == 3 && delay.hasReached(100)) {
			delay.reset();
			state = 3;
			
			if(debug) bot.sendChatClient("3. bot trying to open chest");
			/*
			for(int a = 0; a < 90; ++a) {
				bot.windowInventory[a] = null;
			}
			*/
			
			//MiscUtils.sleep(100L);
			bot.changeSlot(8);
			
			bot.yaw = 90;
			bot.pitch = 40;
			
			bot.sendPacket(new Packet12PlayerLook(bot.yaw, bot.pitch, true));
			MiscUtils.sleep(4L);
			
			bot.sendPacket(new Packet15Place((int)bot.x - 2, (int)bot.y, (int)bot.z, 1, bot.inventory[8], 0.6f, 0.875f, 0.5f));
			MiscUtils.sleep(4L);
		}
		
		//state == 4, waiting for 'packet open window'
		
		else if(state == 5) {
			state = 6;
			if(debug) bot.sendChatClient("5. bot openned chest, trying to get stuff");
			//MiscUtils.sleep(250L);
			
			int count = 0;
			//maxSlot = 54;
			for(slot = 0; slot < maxSlot; slot++) {
				if(bot.windowInventory[slot] == null)
					continue;
				
				if(count > 36)
					break;
				
				count++;
				bot.sendPacket(new Packet102WindowClick(bot.ContainerWindowId, slot, 0, 1, bot.windowInventory[slot], bot.getTransactionID()));
				MiscUtils.sleep(75L);
			}
			bot.sendPacket(new Packet101CloseWindow(bot.ContainerWindowId));
			MiscUtils.sleep(4L);
		}
		
		else if(state == 6 && delay.hasReached(ldelay)) {
			state = 7;
			delay.reset();
			
			if(debug) bot.sendChatClient("6. bot got the stuff, let's go to /warp shop");
			warpShop();
		}
		
		//state == 7, 'warp dupe_sell' was sent, but bot is not on warp
		
		else if(state == 8) {
			state = 9;
			
			if(debug) bot.sendChatClient("8. bot is finally on warp shop, selling items");
			
			int ent_shoper_id = -1;
			
			LiteEntity ent_shoper = bot.getEntityByName("Êóçíåö");
			
			if(ent_shoper == null) {
				bot.sendChatClient("§eBot §8[§a" + bot.instance.name + "§8] " + " §a§l- §cunable to find entity with name 'Êóçíåö'.");
				return;
			}
			ent_shoper_id = ent_shoper.getId();
			
			Vector2f aim_angles = MiscUtils.getBlockAngles(ent_shoper.getPos().x, ent_shoper.getPos().y, ent_shoper.getPos().z, bot.x, bot.y, bot.z);
			bot.sendPacket(new Packet12PlayerLook(aim_angles.y, aim_angles.x, true));
			MiscUtils.sleep(4L);
			bot.sendPacket(new Packet7UseEntity(bot.entityId, ent_shoper_id, 0));
			MiscUtils.sleep(4L);
			
			for(int a = 54; a < 90; ++a) {
				bot.sendPacket(new Packet102WindowClick(bot.ContainerWindowId + 1, a, 0, 0, (ItemStack)null, (short)1));
				MiscUtils.sleep(2L);
			}
			
			bot.sendPacket(new Packet101CloseWindow(bot.ContainerWindowId + 1));
			MiscUtils.sleep(4L);
		}
		else if(state == 9) {
			state = 0;
			
			if(debug) bot.sendChatClient("9. all shit selled, let's get out of here");
		}
	}
}
