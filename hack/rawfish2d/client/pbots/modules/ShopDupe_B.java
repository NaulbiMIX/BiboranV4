package hack.rawfish2d.client.pbots.modules;

import hack.rawfish2d.client.Client;
import hack.rawfish2d.client.ModMisc.AutoWarp;
import hack.rawfish2d.client.ModUnused.ShopDupe;
import hack.rawfish2d.client.pbots.PBotThread;
import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.TimeHelper;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet107CreativeSetSlot;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet7UseEntity;

public class ShopDupe_B extends ModuleBase {
	TimeHelper shop_wait = new TimeHelper();
	TimeHelper warp_wait = new TimeHelper();
	boolean warped = false;
	int stage = 0;
	
	public ShopDupe_B(PBotThread bot) {
		super(bot, "ShopDupe_B");
		shop_wait.reset();
		warp_wait.reset();
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
		
		if(warped == false && warp_wait.hasReached(1000L)) {
			warped = true;
			bot.sendPacket(new Packet3Chat("/warp shopdupe"));
			
			shop_wait.reset();
		}
		else if(warped && shop_wait.hasReached(1000L)) {
			/*
			EntityPlayer shop_ent = ShopDupe.getShopEntity();
			if(shop_ent == null) {
				bot.sendPacket(new Packet3Chat("/warp shopdupe"));
				return;
			}
			else
				System.out.println("ok: " + shop_ent.entityId);
			*/
			//bot.sendPacket(new Packet7UseEntity(bot.entityId, shop_ent.entityId, 0));
			bot.sendPacket(new Packet7UseEntity(bot.entityId, 100, 0));
			
			for(int a = 54; a < 90; ++a) {
				bot.sendPacket(new Packet102WindowClick(bot.ContainerWindowId + 1, a, 0, 0, (ItemStack)null, (short)1));
			}
			
			bot.sendPacket(new Packet101CloseWindow(bot.ContainerWindowId + 1));
			MiscUtils.sleep(100L);
			bot.sendPacket(new Packet3Chat("/warp shopdupe2"));
			
			warped = false;
			warp_wait.reset();
		}
	}
}
